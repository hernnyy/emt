package com.example.hernan.esmiturno;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.example.hernan.esmiturno.adapter.list.AutoCompleteArrayAdapter;
import com.example.hernan.esmiturno.model.Customer;
import com.example.hernan.esmiturno.model.Meet;
import com.example.hernan.esmiturno.model.MeetPlace;
import com.example.hernan.esmiturno.model.Provider;
import com.example.hernan.esmiturno.model.list.AutoCompleteDTO;
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
    private AutoCompleteTextView editProv;
    private AutoCompleteTextView editPlace;
    private AutoCompleteTextView editCustom;
    private Button search;
    private int[] colors;

    private RecyclerView recyclerView;
    private MeetNewAdapter adapter;
    private ArrayList<Meet> blackMeetList = new ArrayList<>();
    private ArrayList<Meet> meetList = new ArrayList<>();

    //helpers
    private Long idSelectedProvider;
    private Long idSelectedCustomer;
    private Long idSelectedPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meet);

        colors = getResources().getIntArray(R.array.initial_colors);
        Bundle bundle = getIntent().getExtras();
//        String fraseimportada=bundle.getString("email");
        final String idUser = bundle.getString("idUser");

        idSelectedCustomer = null;
        editCustom = (AutoCompleteTextView) findViewById(R.id.txtCust);
        loadCustomersOption(this,"0", editCustom);
        editCustom.setOnItemClickListener(onItemCustomerClickListener);

        idSelectedProvider = null;
        editProv = (AutoCompleteTextView) findViewById(R.id.txtProv);
        loadProviderOption(mctx,idUser,editProv);
        editProv.setOnItemClickListener(onItemProviderClickListener);

        editPlace = (AutoCompleteTextView) findViewById(R.id.txtPlace);

        editDate = (EditText) findViewById(R.id.fecha);
        editDate.setOnClickListener(this);
        search = (Button) findViewById(R.id.searchMeet);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchMeets(mctx,idUser);
            }
        });

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_dropdown_item_1line, COUNTRIES);
//        dummy.setAdapter(adapter);

    }

    /**
     * recordar que este metodo solo busca proveedores
     * @param mctx
     * @param idUser
     */
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
                                    meeto.setMeetPlace(new MeetPlace(Long.parseLong(editPlace.getText().toString())));
                                    meeto.setProvider(new Provider(idSelectedProvider));
                                    meeto.setCustomer(new Customer(idSelectedCustomer));//TODO obtener el id customer del usuario actual
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

    private AdapterView.OnItemClickListener onItemCustomerClickListener =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.d("id selected:", ((AutoCompleteDTO)adapterView.getItemAtPosition(i)).getId().toString());
                    idSelectedCustomer = ((AutoCompleteDTO)adapterView.getItemAtPosition(i)).getId();
                }
            };

    private AdapterView.OnItemClickListener onItemProviderClickListener =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.d("id selected:", ((AutoCompleteDTO)adapterView.getItemAtPosition(i)).getId().toString());
                    idSelectedProvider = ((AutoCompleteDTO)adapterView.getItemAtPosition(i)).getId();
                }
            };



    private void loadCustomersOption(final Context mctx, final String idUser,final AutoCompleteTextView editCustom){
        String url = "http://ikaroira.com/ws-user.php/getAllCust/"+idUser;
        StringRequest strRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                        try {
                            JSONArray jsonResp = new JSONArray(response);
                            Log.d("Response json:", jsonResp.toString());

//                            String[] customs = new String[jsonResp.length()];
                            ArrayList<AutoCompleteDTO> customs = new ArrayList<>();
                            for (int i=0;i<jsonResp.length();i++){
                                customs.add(new AutoCompleteDTO(jsonResp.getJSONObject(i).getLong("idCustomer"),
                                        jsonResp.getJSONObject(i).getString("username")));
                            }
//                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(mctx,
//                                    android.R.layout.simple_dropdown_item_1line, customs);
                            AutoCompleteArrayAdapter adapter =  new AutoCompleteArrayAdapter(mctx,
                                    android.R.layout.simple_spinner_dropdown_item, customs);
                            editCustom.setAdapter(adapter);
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
                });
        NetManager.getInstance(this).addToRequestQueue(strRequest);

    }

    private void loadProviderOption(final Context mctx, final String idUser,final AutoCompleteTextView edit){
        String url = "http://ikaroira.com/ws-user.php/getAllProv/"+idUser;
        StringRequest strRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                        try {
                            JSONArray jsonResp = new JSONArray(response);
                            Log.d("Response json:", jsonResp.toString());

//                            String[] customs = new String[jsonResp.length()];
                            ArrayList<AutoCompleteDTO> customs = new ArrayList<>();
                            for (int i=0;i<jsonResp.length();i++){
                                customs.add(new AutoCompleteDTO(jsonResp.getJSONObject(i).getLong("idProvider"),
                                        jsonResp.getJSONObject(i).getString("username")));
                            }
//                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(mctx,
//                                    android.R.layout.simple_dropdown_item_1line, customs);
                            AutoCompleteArrayAdapter adapter =  new AutoCompleteArrayAdapter(mctx,
                                    android.R.layout.simple_spinner_dropdown_item, customs);
                            edit.setAdapter(adapter);
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
                });
        NetManager.getInstance(this).addToRequestQueue(strRequest);

    }
}
