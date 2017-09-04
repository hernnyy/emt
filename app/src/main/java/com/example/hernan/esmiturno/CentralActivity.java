package com.example.hernan.esmiturno;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.QuickContactBadge;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CentralActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private QuickContactBadge meetBadge;

    private TableLayout mMeetTableList;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    mTextMessage.setVisibility(View.VISIBLE);
                    homeTabCreate();
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    mTextMessage.setVisibility(View.INVISIBLE);
                    dashboardTabCreate();
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    mTextMessage.setVisibility(View.VISIBLE);
                    mMeetTableList.removeAllViews();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_central);

        mTextMessage = (TextView) findViewById(R.id.message);
        mMeetTableList = (TableLayout) findViewById(R.id.meetTableList);
        mMeetTableList.removeAllViews();
        homeTabCreate();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //se recogen datos de la anterior pantalla
        Bundle bundle = getIntent().getExtras();
        String fraseimportada=bundle.getString("email");

        Log.d("email: ", fraseimportada);
    }

    private void homeTabCreate(){
//TODO colocar la url que trae los meets por id
        String url = "http://ikaroira.com/ws-meet.php/getAll";
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
                            Log.d("Response json:", String.valueOf(jsonResp.length()));
                            Log.d("Response json:", jsonResp.getJSONObject(0).toString());
                            Log.d("Response json:", jsonResp.getJSONObject(0).getString("fecha"));
                            for (int i=0;i<jsonResp.length();i++){
                                addRowToTableMeet(jsonResp.getJSONObject(i));
                            }
//                            Intent intent = new Intent (mctx, CentralActivity.class);
//                            intent.putExtra("email",jsonResp.getString("email"));
//                            startActivityForResult(intent, 0);

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


        //carga dinamica de rows
        for (int i=0;i<5;i++){
            addRowToTableMeet(null);

        }
    }
    private void dashboardTabCreate(){
        mMeetTableList.removeAllViews();

    }

    private void addRowToTableMeet(JSONObject rowData){
        LinearLayout outerLayout = new LinearLayout(this);
        TableRow row= new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
        lp.setMargins(10,0,10,10);
        row.setLayoutParams(lp);
        row.setBackgroundColor(Color.WHITE);
        row.setClickable(true);
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TableRow tr = (TableRow)v;
                tr.setBackgroundColor(Color.MAGENTA);
                //TODO ir a otra pagina
//                    Intent myIntent = new Intent(PNs.this, Main.class);
//                    PNs.this.startActivity(myIntent);
            }
        });

        TextView head = new TextView(this);
        if (rowData != null){
            try {
                head.setText("Turno "+rowData.getString("fecha"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            head.setText("Turno Dummy");
        }
        LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textLayoutParams.setMargins(20, 0, 0, 0);

        TextView body = new TextView(this);
        body.setText("Este es un turno");
        LinearLayout.LayoutParams pictureLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        pictureLayoutParams.setMargins(20, 0, 0, 0);

        outerLayout.setOrientation(LinearLayout.VERTICAL);
        outerLayout.addView(head, textLayoutParams);
        outerLayout.addView(body, pictureLayoutParams);
        row.addView(outerLayout);


//            row.addView(head);
//            row.addView(body);
        mMeetTableList.addView(row);

        addSeparatorToTableMeet();

    }

    private void addSeparatorToTableMeet(){
        TableRow separator = new TableRow(this);
        View line = new View(this);
        TableRow.LayoutParams separatorLayoutParams = new TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 30);
        separatorLayoutParams.setMargins(0, 0, 0, 0);
//            line.setBackgroundColor(Color.BLUE);
        separator.addView(line, separatorLayoutParams);

        mMeetTableList.addView(separator);

    }

}
