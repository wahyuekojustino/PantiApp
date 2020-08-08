package com.android.pantiasuhan.pantiasuhan.donatur;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.pantiasuhan.pantiasuhan.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DonasiActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "DonasiActivity";
    CoordinatorLayout coordinatorLayout;
    SessionManager sessionManager;
    private CollapsingToolbarLayout collapsingToolbarLayout = null;

    //uang
    EditText etUang;
    //pegawai
    EditText etNamaSdm, etJk, etAgama, etKeahlian, etAlamat, etTelp;
    //chekbox
    CheckBox cbUang, cbAlat, cbSdm;

    String sUang = "-", sNama = "-", sJk = "-", sAgama = "-", sKeahlian = "-", sAlamat = "-", sTelp = "-", sSpinner= "-";

    String uang = "0", sdm = "0", panti = "0";

    TextView tvDataUang, tvAlat, tvSdm;
    LinearLayout LUang, LAlat, LSdm, LBtnSimpan;

    ArrayList<String> arrayListPanti;
    // List<ItemPanti> itemPantiList = new ArrayList<ItemPanti>();
    JSONArray result;

    // SessionManager sessionManager;
    String nik;

    private Button buttonAdd;
    private Button buttonTampil;

    ArrayList<String> listItems=new ArrayList<>();
    ArrayAdapter<String> dataAdapter;

    Spinner spPanti;
    String stringPanti="";
    ArrayAdapter<ItemPanti> adapterPanti;
    ArrayList<ItemPanti> pantiArrayList = new ArrayList<>();

    //fitur kamera
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;

    String mCurrentPhotoPath;
    private Bitmap mImageBitmap;
    int PIC_CODE = 0;
    int serverResponseCode = 0;


    public static final int RequestPermissionCode = 1;

    ImageView btTakePicture,  ImageViewHolder1;
    TextView tv1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donasi);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#303F9F")));
        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        nik = user.get(SessionManager.kunci_username);

        //Inisialisasi dari View
        etUang = (EditText) findViewById(R.id.etUang);
        etNamaSdm = (EditText) findViewById(R.id.etNamaSDM);
        etJk = (EditText) findViewById(R.id.etJk);
        etAgama = (EditText) findViewById(R.id.etAgama);
        etKeahlian = (EditText) findViewById(R.id.etKeahlian);
        etAlamat = (EditText) findViewById(R.id.etAlamat);
        etTelp = (EditText) findViewById(R.id.etTlpSdm);

        cbUang = (CheckBox) findViewById(R.id.cbUang);
        cbSdm = (CheckBox) findViewById(R.id.cbPegawai);

        tvDataUang = (TextView) findViewById(R.id.tvDataUang);
        LUang = (LinearLayout) findViewById(R.id.LUang);
        tvSdm = (TextView) findViewById(R.id.tvDataSdm);
        LSdm = (LinearLayout) findViewById(R.id.LSdm);
        LBtnSimpan = (LinearLayout) findViewById(R.id.LSimpan);

        // Spinner element
        spPanti = (Spinner) findViewById(R.id.spinPanti);

        ImageViewHolder1 = (ImageView)findViewById(R.id.imageView1);
        btTakePicture = (ImageView)findViewById(R.id.takePicture);


        tv1 = (TextView)findViewById(R.id.txt1);

        //L1
        btTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });


       // dataAdapter=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.txt,listItems);
       // spPanti.setAdapter(dataAdapter);

         //Spinner Drop down elements
//        List<String> categories = new ArrayList<String>();
//        categories.add("--Pilih Panti Asuhan--");
//        categories.add("Budi Mulya 2");
//        categories.add("Bunda Asih");
//        categories.add("Kasih Ibu");
//        categories.add("Surya Mandiri");

        // Creating adapter for spinner
       // dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listItems);

        // Drop down layout style - list view with radio button
       // dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        //spPanti.setAdapter(dataAdapter);

        getDataPanti();

        spPanti.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ItemPanti itemPanti = (ItemPanti) parent.getSelectedItem();

                stringPanti = itemPanti.getId_panti();
               // Toast.makeText(DonasiActivity.this, stringPanti, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLay);

        buttonAdd = (Button) findViewById(R.id.btnSimpan);
        // buttonTampil= (Button) findViewById(R.id.btnTampil);

        //Setting listeners to button
        buttonAdd.setOnClickListener(this);
//        buttonTampil.setOnClickListener(this);

        cbUang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    //Toast.makeText(TambahDataPMKS.this, "Lansia dipilih", Toast.LENGTH_SHORT).show();
                    uang = "1";
                    tvDataUang.setVisibility(View.VISIBLE);
                    LUang.setVisibility(View.VISIBLE);
                    etUang.setVisibility(View.VISIBLE);
                    LBtnSimpan.setVisibility(View.VISIBLE);
                } else {
                    uang = "0";
                    tvDataUang.setVisibility(View.GONE);
                    LUang.setVisibility(View.GONE);
                    etUang.setVisibility(View.GONE);
                    LBtnSimpan.setVisibility(View.GONE);
                }
            }
        });

        cbSdm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    //Toast.makeText(TambahDataPMKS.this, "Lansia dipilih", Toast.LENGTH_SHORT).show();
                    sdm = "1";
                    tvSdm.setVisibility(View.VISIBLE);
                    LSdm.setVisibility(View.VISIBLE);
                    etNamaSdm.setVisibility(View.VISIBLE);
                    etJk.setVisibility(View.VISIBLE);
                    etAgama.setVisibility(View.VISIBLE);
                    etKeahlian.setVisibility(View.VISIBLE);
                    etAlamat.setVisibility(View.VISIBLE);
                    etTelp.setVisibility(View.VISIBLE);
                    LBtnSimpan.setVisibility(View.VISIBLE);
                } else {
                    sdm = "0";
                    tvSdm.setVisibility(View.GONE);
                    LSdm.setVisibility(View.GONE);
                    etNamaSdm.setVisibility(View.GONE);
                    etJk.setVisibility(View.GONE);
                    etAgama.setVisibility(View.GONE);
                    etKeahlian.setVisibility(View.GONE);
                    etAlamat.setVisibility(View.GONE);
                    etTelp.setVisibility(View.GONE);
                    LBtnSimpan.setVisibility(View.GONE);
                }
            }
        });

    }

    private void getDataPanti() {
        pantiArrayList.clear();
       // adapterJP.notifyDataSetChanged();
        final ProgressDialog progressDialog = new ProgressDialog(DonasiActivity.this);
        progressDialog.setMessage("Mengambil data server...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        RequestQueue queue = Volley.newRequestQueue(DonasiActivity.this);
        String url = "http://gascoding.id/rest/SIGPAK/pengunjung/tampilSemuaPantiDonasi.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");

                    int i;
                    pantiArrayList.add(new ItemPanti("0","Pilih Panti"));
                    for (i=0;i<jsonArray.length();i++){
                        try {

                            JSONObject json = jsonArray.getJSONObject(i);
                            pantiArrayList.add(new ItemPanti(json.getString("id_panti"),json.getString("nama_panti")));

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }


                    adapterPanti = new ArrayAdapter<ItemPanti>(DonasiActivity.this, R.layout.support_simple_spinner_dropdown_item, pantiArrayList);

                } catch (JSONException e) {
                    e.printStackTrace();
                  
                }
                //Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                spPanti.setAdapter(adapterPanti);
                progressDialog.dismiss();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                String info = error.getMessage();
                Toast.makeText(DonasiActivity.this, info, Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("status", "200");

                return params;
            }
        };
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        queue.add(stringRequest);
    }

    public void onStart(){
        super.onStart();
        //BackTask bt=new BackTask();
       // bt.execute();
    }
    private class BackTask extends AsyncTask<Void,Void,Void> {
        ArrayList<String> list;
        protected void onPreExecute(){
            super.onPreExecute();
            list=new ArrayList<>();
        }
        protected Void doInBackground(Void...params){
            InputStream is=null;
            String result="";
            try{
                HttpClient httpclient=new DefaultHttpClient();
                HttpPost httppost= new HttpPost("http://gascoding.id/rest/SIGPAK/pengunjung/tampilSemuaPantiDonasi.php");
                HttpResponse response=httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                // Get our response as a String.
                is = entity.getContent();
            }catch(IOException e){
                e.printStackTrace();
            }

            //convert response to string
            try{
                BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    result+=line;
                }
                is.close();
                //result=sb.toString();
            }catch(Exception e){
                e.printStackTrace();
            }
            // parse json data
            try{
                JSONArray jArray =new JSONArray(result);
                for(int i=0;i<jArray.length();i++){
                    JSONObject jsonObject=jArray.getJSONObject(i);
                    // add interviewee name to arraylist
                    list.add(jsonObject.getString("nama_panti"));
                }
            }
            catch(JSONException e){
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(Void result){
            listItems.addAll(list);
            dataAdapter.notifyDataSetChanged();
        }
    }

    //Dibawah ini merupakan perintah untuk Menambahkan Pegawai (CREATE)
    private void addEmployee() {

//        final String Nama_Kebutuhan = editTextNama_Kebutuhan.getText().toString().trim();

        class AddEmployee extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DonasiActivity.this, "Menambahkan...", "Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if(s.equalsIgnoreCase("1")) {
                    uploadAudio();
                    Toast.makeText(DonasiActivity.this, "Berhasil Menyimpan Data", Toast.LENGTH_LONG).show();
                }
                else   {
                    Toast.makeText(DonasiActivity.this, "Gagal Menyimpan Data", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();

                if (stringPanti.equalsIgnoreCase("0")) {
                    sSpinner = "-";
                } else {
                    sSpinner = stringPanti;
                }

                if (uang.equalsIgnoreCase("0")) {
                    sUang = "-";
                } else {
                    sUang = etUang.getText().toString();
                }

                if (sdm.equalsIgnoreCase("0")) {
                    sNama = "-";
                    sJk = "-";
                    sAgama = "-";
                    sKeahlian = "-";
                    sAlamat = "-";
                    sTelp = "-";
                } else {
                    sNama = etNamaSdm.getText().toString();
                    sJk = etJk.getText().toString();
                    sAgama = etAgama.getText().toString();
                    sKeahlian = etKeahlian.getText().toString();
                    sAlamat = etAlamat.getText().toString();
                    sTelp = etTelp.getText().toString();
                }


                params.put("nik", nik);
                params.put("uang", sUang);
                params.put("nama", sNama);
                params.put("jk", sJk);
                params.put("agama", sAgama);
                params.put("keahlian", sKeahlian);
                params.put("alamat", sAlamat);
                params.put("telp", sTelp);
                params.put("id_panti", sSpinner);

                //params.put(konfigurasi.KEY_EMP_KEBUTUHAN, Nama_Kebutuhan);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.URL_ADD_DONASI, params);
                return res;
            }
        }

        AddEmployee ae = new AddEmployee();
        ae.execute();

//        etUang.setText("");
//        etNamaSdm.setText("");
//        etJk.setText("");
//        etAgama.setText("");
//        etKeahlian.setText("");
//        etTelp.setText("");
//        etTelp.setText("");

//        editTextNama_Kebutuhan.setText("");
    }

    //PROSES CAMERA
    public void EnableRuntimePermissionToAccessCamera(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(DonasiActivity.this,
                Manifest.permission.CAMERA))
        {

            // Printing toast message after enabling runtime permission.
            Toast.makeText(DonasiActivity.this,"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(DonasiActivity.this,new String[]{Manifest.permission.CAMERA}, RequestPermissionCode);

        }
    }

    //REQUEST CAMERA
//L2
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.android.pantiasuhan.pantiasuhan",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }

        if(PIC_CODE==0){
            ImageViewHolder1.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Bundle extras = data.getExtras();
            if(PIC_CODE<=3){
                handleSmallCameraPhoto(PIC_CODE);
                //Toast.makeText(getApplicationContext(),String.valueOf(PIC_CODE),Toast.LENGTH_LONG).show();
            }
            if(PIC_CODE==2){
                btTakePicture.setVisibility(View.GONE);
            }
            PIC_CODE++;


        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void galleryAddPic(int ID) {
        if(ID==0){

            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            File f = new File(mCurrentPhotoPath);
            Uri contentUri = Uri.fromFile(f);
            mediaScanIntent.setData(contentUri);
            this.sendBroadcast(mediaScanIntent);
        }
    }

    private void setPic(int ID) {
        // Get the dimensions of the View

        if(ID==0){

            int targetW = ImageViewHolder1.getWidth();
            int targetH = ImageViewHolder1.getHeight();

            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image
            int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;

            Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

            ImageViewHolder1.setImageBitmap(bitmap);
            tv1.setText(mCurrentPhotoPath);
        }
    }

    private void handleSmallCameraPhoto(int ID) {
        setPic(ID);
        galleryAddPic(ID);
    }

    private void uploadAudio() {
        class UploadAudio extends AsyncTask<Void, Void, String> {

            ProgressDialog uploading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                uploading = ProgressDialog.show(DonasiActivity.this, "Uploading File", "Please wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                uploading.dismiss();
                if(s.equalsIgnoreCase("sukses")){
                    startActivity(new Intent(getApplicationContext(), MenuDonasi.class));

                    Toast.makeText(DonasiActivity.this, "Berhasil Upload Data", Toast.LENGTH_SHORT).show();
                }else if(s.equalsIgnoreCase("gagal")){
                    Toast.makeText(DonasiActivity.this, "Gagal Upload Data ! ", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            protected String doInBackground(Void... params) {

                String fileName1 = tv1.getText().toString();
                UploadFile u = new UploadFile();
                String msg = u.uploadAudio( fileName1, nik );
                return msg;
            }
        }
        UploadAudio uv = new UploadAudio();
        uv.execute();
    }


    @Override
        public void onClick(View v) {
            if (v == buttonAdd) {
                addEmployee();
            }

        }

}