package com.example.hernan.esmiturno;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.QuickContactBadge;
import android.widget.TextView;

public class CentralActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private QuickContactBadge meetBadge;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    mTextMessage.setVisibility(View.VISIBLE);

                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    mTextMessage.setVisibility(View.INVISIBLE);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    mTextMessage.setVisibility(View.VISIBLE);
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
        meetBadge = (QuickContactBadge) findViewById(R.id.mettBadge);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Bundle bundle = getIntent().getExtras();
        String fraseimportada=bundle.getString("email");

        Log.d("email: ", fraseimportada);
    }

}
