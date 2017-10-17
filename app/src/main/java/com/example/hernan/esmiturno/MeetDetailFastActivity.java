package com.example.hernan.esmiturno;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

public class MeetDetailFastActivity extends AppCompatActivity {

    private String idMeet;
    private TextView idTurno;
    private TextView lugar;
    private TextView fecha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet_detail_fast);

        Bundle bundle = getIntent().getExtras();
        idMeet = bundle.getString("idMeet");
        idTurno =  (TextView) findViewById(R.id.txtMeet);
        lugar =  (TextView) findViewById(R.id.txtPlace);
        fecha =  (TextView) findViewById(R.id.txtDate);


        String url = "http://ikaroira.com/ws-meet.php/getByID/"+idMeet;
        StringRequest strRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
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
