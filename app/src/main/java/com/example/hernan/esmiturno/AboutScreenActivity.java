package com.example.hernan.esmiturno;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class AboutScreenActivity extends AppCompatActivity {
    private TextView tv2,tv3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_screen);
        tv2 = (TextView)findViewById(R.id.textv2);
        tv3 = (TextView)findViewById(R.id.textv3);


    }

}
