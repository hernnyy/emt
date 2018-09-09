package com.example.hernan.esmiturno;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HelpScreenActivity extends AppCompatActivity {
    private Context contexto=this;
    private TextView tv1, tv2;
    private Button b1,b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_screen);


        tv1 = (TextView)findViewById(R.id.textv1);
        tv2 = (TextView)findViewById(R.id.textv3);
        Button b1 = (Button)findViewById(R.id.about_button);
        Button b2 = (Button)findViewById(R.id.url_button);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (contexto, AboutScreenActivity.class);
//                intent.putExtra("userSS", Util.parseJSONToUser(jsonResp));
//                                intent.putExtra("id",jsonResp.getString("id"));
                startActivityForResult(intent, 0);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
                myWebLink.setData(Uri.parse("http://ikaroira.com/"));
                startActivity(myWebLink);
            }
        });
    }

        //public void

}
