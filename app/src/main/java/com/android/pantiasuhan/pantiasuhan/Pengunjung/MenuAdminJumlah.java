package com.android.pantiasuhan.pantiasuhan.Pengunjung;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.android.pantiasuhan.pantiasuhan.R;


/**
 * Created by KHAN on 25/03/2018.
 */

public class MenuAdminJumlah extends AppCompatActivity {
    Button btDonatur, btKebutuhan, btPanti, btkegiatan;
    CardView btAnak, btPengurus;
    Bundle extra;
    String id_panti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_jumlah);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#303F9F")));
        extra = getIntent().getExtras();

        id_panti=extra.getString(konfigurasi.TAG_ID);
       // Toast.makeText(this, id_panti, Toast.LENGTH_SHORT).show();

        btAnak=(CardView)findViewById(R.id.home_mAnak);
        btDonatur=(Button)findViewById(R.id.btDonatur);
        btPengurus=(CardView) findViewById(R.id.home_mPengurus);
        btKebutuhan=(Button)findViewById(R.id.btKebutuhan);
        btPanti=(Button)findViewById(R.id.btPanti);
        btkegiatan=(Button)findViewById(R.id.btKegiatan);


        btAnak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuAdminJumlah.this, JumlahDataAnak.class));
            }
        });



        btPengurus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuAdminJumlah.this, JumlahDataPengurus.class));
            }
        });

    }

}
