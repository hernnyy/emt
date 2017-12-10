package com.example.hernan.esmiturno;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hernan.esmiturno.adapter.MeetSimpleAdapter;
import com.example.hernan.esmiturno.util.DatePickerFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddMeetActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mctx = this;
    private EditText editDate;
    private EditText editProv;
    private EditText editPlace;
    private Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meet);

        editDate = (EditText) findViewById(R.id.fecha);
        editProv = (EditText) findViewById(R.id.txtProv);
        editPlace = (EditText) findViewById(R.id.txtPlace);
        editDate.setOnClickListener(this);
        search = (Button) findViewById(R.id.searchMeet);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchMeets(mctx,"1");
            }
        });
    }

    private void searchMeets(final Context mctx, final String idUser){
//        String url = "http://ikaroira.com/ws-meet.php/getAll";
        String url = "http://ikaroira.com/ws-meet.php/search";
        StringRequest strRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                        try {
                            JSONArray jsonResp = new JSONArray(response);
                            Log.d("Response json:", jsonResp.toString());
//                            Log.d("Response json:", String.valueOf(jsonResp.length()));
//                            Log.d("Response json:", jsonResp.getJSONObject(0).toString());
//                            Log.d("Response json:", jsonResp.getJSONObject(0).getString("fecha"));
//                            JSONArray jsonCust = jsonResp.getJSONArray("custom");
//                            JSONArray jsonProv = jsonResp.getJSONArray("provider");

//                            colors = getResources().getIntArray(R.array.initial_colors);
//                            for (int i=0;i<jsonCust.length();i++){
//                                addRowToTableMeet(jsonCust.getJSONObject(i),mctx);
//
//                            }
//                            for (int i=0;i<jsonProv.length();i++){
//                                addRowToTableMeet(jsonProv.getJSONObject(i),mctx);
//                            }
//
//                            if (adapter == null) {
//                                adapter = new MeetSimpleAdapter(mctx, meetList);
//                            }
//                            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//                            recyclerView.setAdapter(adapter);
//                            recyclerView.setLayoutManager(new LinearLayoutManager(mctx));


                        } catch (Throwable t) {
                            Log.e("My App", "Could not parse malformed JSON: \"" + response + "\"");
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", idUser);
                params.put("date", editDate.getText().toString());
                return params;
            }
        };;
        NetManager.getInstance(this).addToRequestQueue(strRequest);

    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate = year + "/" + (month+1) + "/" + day;
                editDate.setText(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fecha:
                showDatePickerDialog();
                break;
        }
    }
}
