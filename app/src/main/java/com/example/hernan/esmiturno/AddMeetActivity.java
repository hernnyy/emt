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
import com.example.hernan.esmiturno.adapter.MeetNewAdapter;
import com.example.hernan.esmiturno.adapter.MeetSimpleAdapter;
import com.example.hernan.esmiturno.model.Meet;
import com.example.hernan.esmiturno.util.DatePickerFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddMeetActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mctx = this;
    private EditText editDate;
    private EditText editProv;
    private EditText editPlace;
    private Button search;
    private int[] colors;

    private RecyclerView recyclerView;
    private MeetNewAdapter adapter;
    private ArrayList<Meet> blackMeetList = new ArrayList<>();
    private ArrayList<Meet> meetList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meet);

        colors = getResources().getIntArray(R.array.initial_colors);
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

                            blackMeetList = new ArrayList<>();
                            meetList = new ArrayList<>();

                            for (int i=0;i<jsonResp.length();i++){
                                addToBlackListMeet(jsonResp.getJSONObject(i),mctx);
                            }

                            String fechaBase = editDate.getText().toString();
                            Calendar basecal = Calendar.getInstance();
                            Calendar limitcal = Calendar.getInstance();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
                            Date d = sdf.parse(fechaBase);
                            basecal.setTime(sdf.parse(fechaBase));
                            limitcal.setTime(sdf.parse(fechaBase));
                            basecal.set(Calendar.HOUR,8);
                            basecal.set(Calendar.MINUTE,0);
                            limitcal.set(Calendar.HOUR,22);
                            limitcal.set(Calendar.MINUTE,0);
                            int i = 0;
                            while(basecal.before(limitcal)){
                                if(i<blackMeetList.size() && basecal.getTime().equals(blackMeetList.get(i).getFecha())){
                                    i++;
                                }else{
                                    Meet meeto = new Meet();
                                    meeto.setFecha(basecal.getTime());
                                    meeto.setColorResource(colors[8]);
                                    meetList.add(meeto);
                                }
                                basecal.add(Calendar.MINUTE,15);
                            }

                            if (adapter == null) {
                                adapter = new MeetNewAdapter(mctx, meetList);
                            }
                            recyclerView = (RecyclerView) findViewById(R.id.recycler_new_view);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(mctx));

                        } catch (Throwable t) {
                            Log.e("My App", "Could not parse malformed JSON: \"" + response + "\"");
                            Log.e("My App",t.getMessage());
                            t.printStackTrace();
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

    public void doSmoothScroll(int position) {
        recyclerView.smoothScrollToPosition(position);
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

    private void addToBlackListMeet(final JSONObject rowData,final Context mctx){
        try {
            Meet meeto = new Meet();
            DateFormat readFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss", Locale.getDefault());

            Date convertedDate = readFormat.parse(rowData.getString("fecha"));

            meeto.setFecha(convertedDate);
            meeto.setColorResource(colors[10]);
            blackMeetList.add(meeto);
        } catch (ParseException e) {
            Log.e("My App", "Could not parse date: \"" + rowData + "\"");
            e.printStackTrace();
        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + rowData + "\"");
        }

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
