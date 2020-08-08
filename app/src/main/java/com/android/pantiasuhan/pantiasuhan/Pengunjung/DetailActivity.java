package com.android.pantiasuhan.pantiasuhan.Pengunjung;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.android.pantiasuhan.pantiasuhan.R;

import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import es.voghdev.pdfviewpager.library.util.FileUtil;

public class DetailActivity extends AppCompatActivity implements DownloadFile.Listener{
    RemotePDFViewPager remotePDFViewPager;
    PDFPagerAdapter adapter;
    LinearLayout container;
    ProgressDialog progressDialog;
    AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        container = (LinearLayout) findViewById(R.id.container);
        String pdf = getIntent().getStringExtra("url_file");

        progressDialog = new ProgressDialog(DetailActivity.this);
        progressDialog.setMessage("Membuka modul dari server ...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        remotePDFViewPager =
                new RemotePDFViewPager(getApplicationContext(), pdf, this);


    }

    @Override
    public void onSuccess(String url, String destinationPath) {
        progressDialog.dismiss();
       // DialogKet();
        adapter = new PDFPagerAdapter(this, FileUtil.extractFileNameFromURL(url));
        remotePDFViewPager.setAdapter(adapter);

        container.addView(remotePDFViewPager, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onFailure(Exception e) {
        // kalo gagal download
        progressDialog.dismiss();
        Toast.makeText(this, "Gagal untuk membuka modul. Silahkan cek koneksi Anda !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressUpdate(int progress, int total) {

    }


}
