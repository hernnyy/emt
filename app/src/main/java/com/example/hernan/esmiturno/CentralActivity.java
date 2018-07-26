package com.example.hernan.esmiturno;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.example.hernan.esmiturno.adapter.MeetSimpleAdapter;
import com.example.hernan.esmiturno.model.Meet;
import com.example.hernan.esmiturno.model.User;
import com.example.hernan.esmiturno.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CentralActivity extends AppCompatActivity {

    private User user;
    private TextView mTextMessage;

    private RecyclerView recyclerView;
    private MeetSimpleAdapter adapter;
    private ArrayList<Meet> meetList = new ArrayList<>();
    private int[] colors;

    private Context contexto;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    mTextMessage.setVisibility(View.VISIBLE);
                    clearHomeViews();
                    homeTabCreate(contexto,user);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    mTextMessage.setVisibility(View.VISIBLE);
                    clearHomeViews();
                    dashboardTabCreate();
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    mTextMessage.setVisibility(View.VISIBLE);
                    clearHomeViews();
                    return true;
            }
            return false;
        }

    };

    public void doSmoothScroll(int position) {
        recyclerView.smoothScrollToPosition(position);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_central);
        contexto = this;
        mTextMessage = (TextView) findViewById(R.id.message);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //se recogen datos de la anterior pantalla
        Bundle bundle = getIntent().getExtras();
//        String fraseimportada=bundle.getString("email");
//        idUser = bundle.getString("id");
        user = (User) bundle.get("userSS");
//        Log.d("email: ", fraseimportada);

        Button dummyBoton = (Button) findViewById(R.id.dummyBoton);
//        mMeetTableList = (TableLayout) findViewById(R.id.meetTableList);
//        mMeetTableList.removeAllViews();
        dummyBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(contexto, AddMeetActivity.class);
//                    intent.putExtra("idUser",idUser);
                    intent.putExtra("userSS",user);
                    contexto.startActivity(intent);
                } catch (Throwable t) {
                    Log.e("My App", "Could not intent");
                }
            }
        });

        homeTabCreate(contexto,user);
    }

    private void homeTabCreate(final Context mctx, final User user){

//        clearHomeViews();

        String url = "http://ikaroira.com/ws-meet.php/getAllByUser/"+user.getId().toString();
        StringRequest strRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonResp = new JSONObject(response);
                            JSONArray jsonCust = jsonResp.getJSONArray("custom");
                            JSONArray jsonProv = jsonResp.getJSONArray("provider");

                            colors = getResources().getIntArray(R.array.initial_colors);
                            for (int i=0;i<jsonCust.length();i++){
                                Meet meet = Util.parseJSONToMeet(jsonCust.getJSONObject(i));
                                meet.setColorResource(colors[10]);
                                meetList.add(meet);
                            }
                            for (int i=0;i<jsonProv.length();i++){
                                Meet meet = Util.parseJSONToMeet(jsonProv.getJSONObject(i));
                                meet.setColorResource(colors[9]);
                                meetList.add(meet);
                            }

                            if (adapter == null) {
                                adapter = new MeetSimpleAdapter(mctx, meetList, user);
                            }
                            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(mctx));


                        } catch (Throwable t) {
                            Log.e("My App", "Could not parse malformed JSON: \"" + response + "\"");
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
    private void dashboardTabCreate(){
        clearHomeViews();
    }

    private void clearHomeViews(){
        meetList.clear();
        recyclerView.removeAllViews();
        adapter.notifyDataSetChanged();
    }

}
