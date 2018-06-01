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
import com.example.hernan.esmiturno.model.Meet;
import com.example.hernan.esmiturno.model.User;
import com.example.hernan.esmiturno.util.Util;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MeetDetailFastActivity extends AppCompatActivity {

    private TextView idTurno;
    private TextView lugar;
    private TextView fecha;
    private TextView providerTxt;
    private TextView customerTxt;
    private TextView codigoTxt;
    private TextView especialidadTxt;
    private TextView credencialTxt;
    private TextView prepagaTxt;
    private Button actionButton;

    private User user;
    private Meet meet;

    private Context contexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet_detail_fast);

        contexto = this;

        final Bundle bundle = getIntent().getExtras();

        user = (User) bundle.get("userSS");
        meet = (Meet) bundle.get("meetSS");

        idTurno =  (TextView) findViewById(R.id.txtMeet);
        lugar =  (TextView) findViewById(R.id.txtPlace);
        fecha =  (TextView) findViewById(R.id.txtDate);
        providerTxt =  (TextView) findViewById(R.id.txtProvider);
        customerTxt =  (TextView) findViewById(R.id.txtCustomer);
        especialidadTxt =  (TextView) findViewById(R.id.txtEspecialidad);
        codigoTxt =  (TextView) findViewById(R.id.txtCode);
        credencialTxt =  (TextView) findViewById(R.id.txtCredential);
        prepagaTxt =  (TextView) findViewById(R.id.txtPrepaga);

        actionButton = (Button) findViewById(R.id.cancelMeet);

        if (meet.getId() != null) {
            actionButton.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {


                                             }
                                         });
            idTurno.setText(meet.getId().toString());
            lugar.setText(meet.getMeetPlace().getFantasyName() + " "
                +meet.getMeetPlace().getAddres().getStreetName() + " "
                +meet.getMeetPlace().getAddres().getStreetNumber());
            fecha.setText(Util.getDateAsStringDefault(meet.getFecha()));
            providerTxt.setText(meet.getUserProvider().getUsername());
            customerTxt.setText(meet.getUserCustomer().getUsername());


//            String url = getResources().getString(R.string.MAIN_URL) +
//                    getResources().getString(R.string.MAIN_MEET_SERVICE) +
//                    "getByID/" + idMeet;
//            StringRequest strRequest = new StringRequest(Request.Method.GET, url,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
//                            try {
//                                JSONObject jsonResp = new JSONObject(response);
////                            Log.d("Response json:", jsonResp.toString());
////                            Log.d("Response json:", String.valueOf(jsonResp.length()));
////                            Log.d("Response json:", jsonResp.getJSONObject(0).toString());
////                            Log.d("Response json:", jsonResp.getJSONObject(0).getString("fecha"));
//                                idTurno.setText(jsonResp.getString("id"));
//                                lugar.setText(jsonResp.getString("lugar"));
//                                fecha.setText(jsonResp.getString("fecha"));
//
//
//                            } catch (Throwable t) {
//                                Log.e("My App", "Could not parse malformed JSON: \"" + response + "\"");
//                            }
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//            NetManager.getInstance(this).addToRequestQueue(strRequest);
        }else{
            actionButton.setText("Confirmar");
            actionButton.setBackgroundColor(getResources().getColor(R.color.blue));

//            idTurno.setText();
            fecha.setText(meet.getFechaAsString());
            lugar.setText(meet.getMeetPlace().getFantasyName());
            providerTxt.setText(meet.getUserProvider().getUsername());
            customerTxt.setText(meet.getUserCustomer().getUsername());

            actionButton.setOnClickListener(new View.OnClickListener() {
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

                                        Intent intent = new Intent(contexto, CentralActivity.class);
                                        intent.putExtra("userSS",user);
                                        contexto.startActivity(intent);
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
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("date", Util.getDateAsStringToMysql(meet.getFecha()));
                            params.put("fk_id_emt_customers", meet.getUserCustomer().getCustomer().getId().toString());
                            params.put("fk_id_emt_providers", meet.getUserProvider().getProvider().getId().toString());
                            params.put("fk_id_emt_meetplaces", meet.getMeetPlace().getId().toString());
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
