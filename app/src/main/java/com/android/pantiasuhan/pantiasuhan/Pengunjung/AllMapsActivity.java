package com.android.pantiasuhan.pantiasuhan.Pengunjung;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.pantiasuhan.pantiasuhan.R;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AllMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Location location;
    CameraPosition cameraPosition;
    Bundle extra = null;
    private JSONArray result;
    public static MarkerOptions markerOptions = new MarkerOptions();
    public static LatLng latLng, myloc;
    String id;
    Spinner spinner;
    String kec;
    ArrayList<String> arrayList;
    String jml_panti, jml_lk, jml_pr;
    TextView tv_jml_panti, tv_jml_lk, tv_jml_pr, tv_total, tv_Info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_all);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Date today = Calendar.getInstance().getTime();//getting date
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");//formating according to my need
        String date = formatter.format(today);
        tv_Info = (TextView)findViewById(R.id.informasi);
        tv_Info.setText("Informasi Jumlah Panti Tahun "+date);
        tv_jml_panti = (TextView)findViewById(R.id.tvjmlpanti);
        tv_jml_lk = (TextView)findViewById(R.id.tvjmllaki);
        tv_jml_pr = (TextView)findViewById(R.id.tvjmlperempuan);
        tv_total = (TextView)findViewById(R.id.tvtotal);
        extra = getIntent().getExtras();
        if (extra == null) {
            return;
        }


    }

    private String getNamaKec(int position) {
        String name ="";

            try {
                JSONObject json = result.getJSONObject(position);
                name = json.getString("kecamatan");

            }catch (JSONException e){
                e.printStackTrace();
            }



        return name;
    }

    private void getDataKec() {
        StringRequest stringRequest = new StringRequest(konfigurasi.URL_KEC, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject j = null;
                try {
                    j = new JSONObject(response);
                    result = j.getJSONArray("result");
                    dataDetail(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        Log.d("url_kec", String.valueOf(stringRequest));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



    private void dataDetail(JSONArray j) {

        for (int i = 0; i < j.length(); i++){
            try {
                JSONObject json = j.getJSONObject(i);
                arrayList.add(json.getString("kecamatan"));
                //Toast.makeText(this, json.getString("kecamatan"), Toast.LENGTH_SHORT).show();
            }catch (JSONException e){
                e.printStackTrace();

            }
        }

        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayList));
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);

        myloc = new LatLng(-5.4026279, 105.2532898);
        cameraPosition = new CameraPosition.Builder().target(myloc).zoom(13).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        spinner = (Spinner)findViewById(R.id.spinKec);
        arrayList = new ArrayList<String>();
        getDataKec();
        //getData2();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(AllMapsActivity.this, getNamaKec(position), Toast.LENGTH_SHORT).show();
                kec = getNamaKec(position);
                getData(kec);
                getData2(kec);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getData(String k) {
        SharedPreferences sharedPreferences = getSharedPreferences(konfigurasi.SHARED_PREF_NAME, Context.MODE_PRIVATE);
         String pkey = sharedPreferences.getString(konfigurasi.TAG_ID,"Not Available");
        String url = "";
         if(k.equals("Semua")){
             url = konfigurasi.URL_GET_ALL;
         }
         else
         {
             url = konfigurasi.URL_GET_PANTI_KEC+k;
         }

        Log.d("url_panti ", url);
        StringRequest stringRequest = new StringRequest(url,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        String title = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);
                            String getObject = j.getString(konfigurasi.JSON_ARRAY);
                            JSONArray jsonArray = new JSONArray(getObject);
                            mMap.clear();
                            for (int i = 0; i< jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                id = jsonObject.getString("id_panti");
                                String kecamatan = jsonObject.getString("kecamatan");
//                                jml = jsonObject.getString("jml");
                                title = jsonObject.getString(konfigurasi.TAG_NAMA_PANTI);
                                latLng = new LatLng(Double.parseDouble(jsonObject.getString("langitude")),
                                        Double.parseDouble(jsonObject.getString("longitude")));


                                addmarker(latLng, title, id, kecamatan);
                                Log.d("LatLng : ", String.valueOf(latLng));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        requestQueue.add(stringRequest);

    }

    private void getData2(String k) {
        SharedPreferences sharedPreferences = getSharedPreferences(konfigurasi.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String pkey = sharedPreferences.getString(konfigurasi.TAG_ID,"Not Available");
        String url = "";
        if(k.equals("Semua")){
            url = konfigurasi.URL_GET_ALL2;
        }
        else
        {
            url = konfigurasi.URL_GET_PANTI_KEC2+k;
        }

        Log.d("url_panti ", url);
        StringRequest stringRequest = new StringRequest(url,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        String title = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);
                            String getObject = j.getString(konfigurasi.JSON_ARRAY);
                            JSONArray jsonArray = new JSONArray(getObject);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                jml_panti = jsonObject.getString("jml_p");
                                jml_lk = jsonObject.getString("jml_lk");
                                jml_pr = jsonObject.getString("jml_pr");

                                int total = Integer.parseInt(jml_lk)+Integer.parseInt(jml_pr);

                                tv_jml_panti.setText("Jumlah Panti : "+jml_panti);
                                tv_jml_lk.setText("Jumlah Laki-Laki : "+jml_lk);
                                tv_jml_pr.setText("Jumlah Perempuan : "+jml_pr);
                                tv_total.setText("Jumlah Total Anak Asuh : "+ String.valueOf(total));
//                                jmpanti(jml);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        requestQueue.add(stringRequest);

    }

private void addmarker(LatLng langlng,final String title, String id, String kecamatan){

        markerOptions.position(langlng);
    markerOptions.title(title);
    markerOptions.snippet(id);
    if(kecamatan.equals("Kedaton")){
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

    }else if(kecamatan.equals("Kemiling")){
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

    }else{
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

    }
    mMap.addMarker(markerOptions);

//    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//        @Override
//        public boolean onMarkerClick(Marker marker) {
//            Toast.makeText(getBaseContext(), "Cliked", Toast.LENGTH_LONG).show();
//            return false;
//        }
//    });

    mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }
    });
    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {
           // Toast.makeText(getBaseContext(), marker.getSnippet(), Toast.LENGTH_LONG).show();
            Intent i = new Intent(AllMapsActivity.this, DetailDataPantiAll.class);
            i.putExtra("id_panti", marker.getSnippet() );
            startActivity(i);
        }
    });
}

    public void jmpanti(final String qty){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_maps_jumlah, null);

        final TextView tvjmlpanti;
        final ImageView ivClose;

        tvjmlpanti= (TextView) mView.findViewById(R.id.tvjmlpanti);
        ivClose= (ImageView) mView.findViewById(R.id.close);

        tvjmlpanti.setText(qty);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

}
