package com.example.hernan.esmiturno;

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
        //carga dinamica de rows
        for (int i=0;i<5;i++){
            LinearLayout outerLayout = new LinearLayout(this);
            TableRow row= new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
            lp.setMargins(10,0,10,10);
            row.setLayoutParams(lp);
            row.setBackgroundColor(Color.WHITE);

            TextView head = new TextView(this);
            head.setText("Turno "+i);
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

            TableRow separator = new TableRow(this);
            View line = new View(this);
            TableRow.LayoutParams separatorLayoutParams = new TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 30);
            separatorLayoutParams.setMargins(100, 0, 0, 0);
//            line.setBackgroundColor(Color.BLUE);
            separator.addView(line, separatorLayoutParams);

            mMeetTableList.addView(separator);

        }
    }
    private void dashboardTabCreate(){
        mMeetTableList.removeAllViews();

    }

}
