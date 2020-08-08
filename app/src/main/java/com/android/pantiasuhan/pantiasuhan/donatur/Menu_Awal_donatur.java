package com.android.pantiasuhan.pantiasuhan.donatur;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.pantiasuhan.pantiasuhan.R;

public class Menu_Awal_donatur extends AppCompatActivity {
    private static final String TAG = "MenuAwal";
    private CoordinatorLayout coordinatorLayout;

    CardView btnregitrasi, btnlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__awal_donatur);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#303F9F")));

        btnregitrasi = (CardView) findViewById(R.id.home_mRegistrasi);
        btnlogin = (CardView) findViewById(R.id.home_mLogin);


        btnregitrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Daftar_Donatur.class));
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });


    }
}
