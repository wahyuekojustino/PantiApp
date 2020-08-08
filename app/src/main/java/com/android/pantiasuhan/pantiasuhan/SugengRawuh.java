package com.android.pantiasuhan.pantiasuhan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by A_Ga on 26/05/2017.
 */
public class SugengRawuh extends AppCompatActivity {
    private static int TIME_SPLASH=4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                finish();
            }
        },TIME_SPLASH);
    }
}
