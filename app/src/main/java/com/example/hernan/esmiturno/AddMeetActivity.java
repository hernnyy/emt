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
import com.example.hernan.esmiturno.model.User;
import com.example.hernan.esmiturno.model.list.AutoCompleteDTO;
import com.example.hernan.esmiturno.util.DatePickerFragment;
import com.example.hernan.esmiturno.util.Util;

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
    private String dateAsStringToSearch;
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
    private User selectedProvider;
    private User selectedCustomer;
    private MeetPlace selectedPlace;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meet);

        colors = getResources().getIntArray(R.array.initial_colors);
        Bundle bundle = getIntent().getExtras();
        user = (User) bundle.get("userSS");

        editCustom = (AutoCompleteTextView) findViewById(R.id.txtCust);
        if (user.getCustomer() != null){
            selectedCustomer = user;
            editCustom.setText(user.getUsername());
        }else {
            selectedCustomer = null;
        }
        loadCustomersOption(this,"0", editCustom);
        editCustom.setOnItemClickListener(onItemCustomerClickListener);

        selectedProvider = null;
        editProv = (AutoCompleteTextView) findViewById(R.id.txtProv);
        loadProviderOption(this, user, editProv);
        editProv.setOnItemClickListener(onItemProviderClickListener);

        selectedPlace = null;
        editPlace = (AutoCompleteTextView) findViewById(R.id.txtPlace);
        loadPlaceOption(this,user,editPlace);
        editPlace.setOnItemClickListener(onItemPlaceClickListener);

        editDate = (EditText) findViewById(R.id.fecha);
        editDate.setOnClickListener(this);
        search = (Button) findViewById(R.id.searchMeet);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchMeets(mctx,user);
            }
        });

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_dropdown_item_1line, COUNTRIES);
//        dummy.setAdapter(adapter);

    }

    /**
     * recordar que este metodo solo busca proveedores
     * @param mctx
     * @param user
     */
    private void searchMeets(final Context mctx, final User user){
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

                            Calendar basecal = Calendar.getInstance();
                            Calendar limitcal = Calendar.getInstance();
                            basecal.setTime(Util.getDateFromDatePickerFormat(dateAsStringToSearch));
                            limitcal.setTime(Util.getDateFromDatePickerFormat(dateAsStringToSearch));
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
                                    meeto.setMeetPlace(selectedPlace);
                                    meeto.setUserProvider(selectedProvider);
                                    meeto.setUserCustomer(selectedCustomer);
                                    meetList.add(meeto);
                                }
                                basecal.add(Calendar.MINUTE,15);
                            }

                            if (adapter == null) {
                                adapter = new MeetNewAdapter(mctx, meetList,user);
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
                params.put("id", user.getId().toString());
                params.put("date", dateAsStringToSearch);
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
                dateAsStringToSearch = selectedDate;
                try {
                    editDate.setText(Util.getDateAsString(Util.getDateFromDatePickerFormat(selectedDate),"EEEE d 'de' MMMM, yyyy"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void addToBlackListMeet(final JSONObject rowData,final Context mctx){
        try {
            Meet meeto = Util.parseJSONToMeet(rowData);
            meeto.setColorResource(colors[10]);
            blackMeetList.add(meeto);
        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + rowData + "\"");
            t.printStackTrace();
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
                    selectedCustomer = ((AutoCompleteDTO)adapterView.getItemAtPosition(i)).getCustomer();
                }
            };

    private AdapterView.OnItemClickListener onItemProviderClickListener =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.d("id selected:", ((AutoCompleteDTO)adapterView.getItemAtPosition(i)).getId().toString());
                    selectedProvider = ((AutoCompleteDTO)adapterView.getItemAtPosition(i)).getProvider();
                }
            };

    private AdapterView.OnItemClickListener onItemPlaceClickListener =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.d("id selected:", ((AutoCompleteDTO)adapterView.getItemAtPosition(i)).getId().toString());
                    selectedPlace = ((AutoCompleteDTO)adapterView.getItemAtPosition(i)).getMeetplace();
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
                                User user = Util.parseJSONToUser(jsonResp.getJSONObject(i));
                                customs.add(new AutoCompleteDTO(user, user.getCustomer()));
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

    private void loadProviderOption(final Context mctx, final User user,final AutoCompleteTextView edit){
        String url = "http://ikaroira.com/ws-user.php/getAllProv/"+user.getId().toString();
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

                                User user = Util.parseJSONToUser(jsonResp.getJSONObject(i));
                                customs.add(new AutoCompleteDTO(user, user.getProvider()));
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


    private void loadPlaceOption(final Context mctx, final User user, final AutoCompleteTextView edit){
        String url = "http://ikaroira.com/ws-user.php/getAllPlaces";
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

                                MeetPlace meetplace = Util.parseJSONToMeetPlace(jsonResp.getJSONObject(i));
                                customs.add(new AutoCompleteDTO(meetplace));
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
