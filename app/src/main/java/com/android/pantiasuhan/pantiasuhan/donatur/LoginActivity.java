package com.android.pantiasuhan.pantiasuhan.donatur;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.pantiasuhan.pantiasuhan.R;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    EditText etUsername, etPasswd;
    Button btMasuk;
    ProgressDialog pd;
    JsonArrayRequest jsonArrayRequest;
    private JSONArray result;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sessionManager = new SessionManager(getApplicationContext());

        etUsername = (EditText)findViewById(R.id.username);
        etPasswd = (EditText)findViewById(R.id.password);
        btMasuk = (Button)findViewById(R.id.btMasuk);

        btMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etUsername.getText().toString().equalsIgnoreCase("") || etPasswd.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(LoginActivity.this, "Username atau Password tidak boleh kosong !", Toast.LENGTH_LONG).show();
                }else{
                    CekLogin(etUsername.getText().toString(), etPasswd.getText().toString());
                    //Toast.makeText(LoginActivity.this, etUsername.getText().toString()+"\n"+etPasswd.getText().toString(), Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(LoginActivity.this, "cek clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void CekLogin(String uname, String passwd) {
        String url = konfigurasi.URL;
        StringRequest stringRequest = new StringRequest(url+"Login.php?nik="+uname+"&password="+passwd,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the Array of JSON String to our JSON Array
                            result = j.getJSONArray(konfigurasi.JSON_ARRAY);

                            //Calling method getStudents to get the students from the JSON Array
                            ProsesLogin(result);
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

    private void ProsesLogin(JSONArray j){
        //Traversing through all the items in the json array
        String pesan = "", status_valid = "";
        try {
            //Getting json object
            JSONObject json = j.getJSONObject(0);

            //Adding the name of the student to array list

            pesan = json.getString("pesan");
           status_valid = json.getString("status_valid");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(pesan.equalsIgnoreCase("sukses")){
            if(status_valid.equalsIgnoreCase("1")){
                sessionManager.createSession(etUsername.getText().toString(), etPasswd.getText().toString(), status_valid);
                //System.out.println(level);
                Intent intent = new Intent(getBaseContext(),MenuDonasi.class);
                startActivity(intent);
                Toast.makeText(this, "Login Berhasil !", Toast.LENGTH_SHORT).show();
                LoginActivity.this.finish();
            }else if(status_valid.equalsIgnoreCase("0")){
                sessionManager.createSession(etUsername.getText().toString(), etPasswd.getText().toString(), status_valid);
                Intent intent = new Intent(getBaseContext(),LoginActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
                Toast.makeText(this, "Proses Validasi 2 X 24 Jam, Agar Dapat Akses Login !", Toast.LENGTH_SHORT).show();
            }


        }else if(pesan.equalsIgnoreCase("gagal")){
            Toast.makeText(this, "Login Gagal !", Toast.LENGTH_SHORT).show();
        }
        //Setting adapter to show the items in the spinner

    }
}
