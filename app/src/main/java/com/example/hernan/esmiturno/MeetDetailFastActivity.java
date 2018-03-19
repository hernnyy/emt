package com.example.hernan.esmiturno;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MeetDetailFastActivity extends AppCompatActivity {

    private String idMeet;
    private TextView idTurno;
    private TextView lugar;
    private TextView fecha;
    private TextView prov;
    private TextView cust;
    private Button addButton;


    private Context contexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet_detail_fast);

        contexto = this;

        final Bundle bundle = getIntent().getExtras();

        idMeet = bundle.getString("idMeet");

        idTurno =  (TextView) findViewById(R.id.txtMeet);
        lugar =  (TextView) findViewById(R.id.txtPlace);
        fecha =  (TextView) findViewById(R.id.txtDate);
        prov =  (TextView) findViewById(R.id.txtProvider);
        cust =  (TextView) findViewById(R.id.txtCustomer);

        addButton = (Button) findViewById(R.id.cancelMeet);

        if (!"".equalsIgnoreCase(idMeet)) {
            String url = getResources().getString(R.string.MAIN_URL) +
                    getResources().getString(R.string.MAIN_MEET_SERVICE) +
                    "getByID/" + idMeet;
            StringRequest strRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                            try {
                                JSONObject jsonResp = new JSONObject(response);
//                            Log.d("Response json:", jsonResp.toString());
//                            Log.d("Response json:", String.valueOf(jsonResp.length()));
//                            Log.d("Response json:", jsonResp.getJSONObject(0).toString());
//                            Log.d("Response json:", jsonResp.getJSONObject(0).getString("fecha"));
                                idTurno.setText(jsonResp.getString("id"));
                                lugar.setText(jsonResp.getString("lugar"));
                                fecha.setText(jsonResp.getString("fecha"));


                            } catch (Throwable t) {
                                Log.e("My App", "Could not parse malformed JSON: \"" + response + "\"");
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
            NetManager.getInstance(this).addToRequestQueue(strRequest);
        }else{
            addButton.setText("Confirmar");
            addButton.setBackgroundColor(getResources().getColor(R.color.blue));

            idMeet = bundle.getString("idMeet");
//            idTurno.setText();
            fecha.setText(bundle.getString("idDate"));
            lugar.setText(bundle.getString("idPlace"));
            prov.setText(bundle.getString("idProvider"));
            cust.setText(bundle.getString("idCustomer"));

            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = getResources().getString(R.string.MAIN_URL) +
                            getResources().getString(R.string.MAIN_MEET_SERVICE) +
                            "insert";
                    StringRequest strRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                                    try {
                                        JSONObject jsonResp = new JSONObject(response);
                            Log.d("Response json:", jsonResp.toString());
                            Log.d("Response json:", String.valueOf(jsonResp.length()));
//                            Log.d("Response json:", jsonResp.getJSONObject(0).toString());
//                            Log.d("Response json:", jsonResp.getJSONObject(0).getString("fecha"));

                                    } catch (Throwable t) {
                                        Log.e("My App", "Could not parse malformed JSON: \"" + response + "\"");
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            })
                    {
                        @Override
                        protected Map<String, String> getParams()
                        {
                            SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("date", dateForm.format((Date) bundle.get("objectDate")));
                            params.put("fk_id_emt_customers", cust.getText().toString());
                            params.put("fk_id_emt_providers", prov.getText().toString());
                            params.put("fk_id_emt_meetplaces", lugar.getText().toString());
                            return params;
                        }
                    };
                    NetManager.getInstance(contexto).addToRequestQueue(strRequest);
//                    try {
//                        Intent intent = new Intent(contexto, AddMeetActivity.class);
//                        contexto.startActivity(intent);
//                    } catch (Throwable t) {
//                        Log.e("My App", "Could not intent");
//                    }
                }
            });
        }
    }
}
