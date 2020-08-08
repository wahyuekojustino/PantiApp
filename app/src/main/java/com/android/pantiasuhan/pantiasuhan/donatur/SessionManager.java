package com.android.pantiasuhan.pantiasuhan.donatur;

/**
 * Created by KHAN on 25/03/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

//import com.lesprivatkotakediri.silesprivat.admin.MenuAdmin;
//import com.lesprivatkotakediri.silesprivat.tentor.MenuTentor;

public class SessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int mode = 0;

    private static final String pref_name = "crudpref";
    private static final String is_login = "islogin";
    public static final String kunci_username = "nik";
    public static final String kunci_password = "password";
    public static final String kunci_level = "status_valid";
   // public static final String kunci_kd_user = "kd_user";
   // public static final String kunci_nama_lengkap = "nama_lengkap";

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(pref_name, mode);
        editor = pref.edit();
    }

    public void createSession(String nik, String password, String status_valid){
        editor.putBoolean(is_login, true);
        editor.putString(kunci_username, nik);
        editor.putString(kunci_password, password);
        editor.putString(kunci_level, status_valid);
       // editor.putString(kunci_kd_user, kd_user);
       // editor.putString(kunci_nama_lengkap, nama_lengkap);
        editor.commit();

    }

    public void checkLogin(){

        HashMap<String, String> user = getUserDetails();
        String status_valid = user.get(kunci_level);

        if (!this.is_login()){
            Intent i = new Intent(context, Menu_Awal_donatur.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
        else {
            String level_master = status_valid;
            if(level_master.equalsIgnoreCase("1")){

                Intent i = new Intent(context, MenuDonasi.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
            else if(level_master.equalsIgnoreCase("0")){
                Intent i = new Intent(context, LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        }
    }

    private boolean is_login() {
        return pref.getBoolean(is_login, false);
    }

    public void logout(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, Menu_Awal_donatur.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(pref_name, pref.getString(pref_name, null));
        user.put(kunci_level, pref.getString(kunci_level, null));
        //user.put(kunci_kd_user, pref.getString(kunci_kd_user, null));
        //user.put(kunci_nama_lengkap, pref.getString(kunci_nama_lengkap, null));
        user.put(kunci_username, pref.getString(kunci_username, null));
        user.put(kunci_password, pref.getString(kunci_password, null));
        return user;
    }

}
