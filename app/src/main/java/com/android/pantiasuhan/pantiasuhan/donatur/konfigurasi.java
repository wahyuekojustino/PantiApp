package com.android.pantiasuhan.pantiasuhan.donatur;

/**
 * Created by Bismillah on 25/11/2017.
 */

public class konfigurasi {
    public static final String URL = "http://gascoding.id/rest/SIGPAK/donatur/";



    //Dibawah ini merupakan Pengalamatan dimana Lokasi Skrip CRUD PHP disimpan
    //Pada tutorial Kali ini, karena kita membuat localhost maka alamatnya tertuju ke IP komputer
    //dimana File PHP tersebut berada
    //PENTING! JANGAN LUPA GANTI IP SESUAI DENGAN IP KOMPUTER DIMANA DATA PHP BERADA
    public static final String URL_ADD="http://gascoding.id/rest/SIGPAK/pengunjung/tambahDonatur.php";
    public static final String URL_GET_ALL = "http://gascoding.id/rest/SIGPAK/donatur/tampilSemuaDonatur.php?username=";
    public static final String URL_GET_EMP = "http://gascoding.id/rest/SIGPAK/donatur/tampilDonatur.php?id_donatur=";
    public static final String URL_UPDATE_EMP = "http://gascoding.id/rest/SIGPAK/donatur/updateDonatur.php";
    public static final String URL_DELETE_EMP = "http://gascoding.id/rest/SIGPAK/donatur/hapusDonatur.php?id_donatur=";

    public static final String URL_GET_EMP_DONATUR = "http://gascoding.id/rest/SIGPAK/pengunjung/tampilDonatur.php?nik=";
    public static final String URL_UPDATE_EMP_DONATUR = "http://gascoding.id/rest/SIGPAK/pengunjung/updateDonatur.php";


    public static final String URL_ADD_DONASI="http://gascoding.id/rest/SIGPAK/pengunjung/tambahDonasi.php";
    public static final String URL_GET_ALL_DONASI = "http://gascoding.id/rest/SIGPAK/pengunjung/tampilSemuaDonasi.php?nik=";
    public static final String URL_GET_EMP_DONASI = "http://gascoding.id/rest/SIGPAK/admin/tampilDonasi.php?id_donasi=";
    public static final String URL_VALIDASI_EMP_DONASI = "http://gascoding.id/rest/SIGPAK/admin/validasiDonasi.php?id_donasi=";

    //Dibawah ini merupakan Kunci yang akan digunakan untuk mengirim permintaan ke Skrip PHP
    public static final String KEY_EMP_ID = "id_donatur";
    public static final String KEY_EMP_NIK= "nik";
    public static final String KEY_EMP_NAMA_DONATUR= "nama_donatur";
    public static final String KEY_EMP_PASSWORD= "password";
    public static final String KEY_EMP_PEKERJAAN = "pekerjaan";
    public static final String KEY_EMP_ALAMAT_DONATUR = "alamat_donatur";
    public static final String KEY_EMP_TLP = "tlp";

    public static final String SHARED_PREF_NAME="sharedPanti";
    public static final String LOGGEDIN_SHARED_PREF="loggedin";

    //JSON Tags
    public static final String JSON_ARRAY = "result";
    public static final String TAG_JSON_ARRAY="result";

    public static final String TAG_ID = "id_donatur";
    public static final String TAG_NIK = "nik";
    public static final String TAG_NAMA_DONATUR = "nama_donatur";
    public static final String TAG_PASSWORD = "password";
    public static final String TAG_PEKERJAAN = "pekerjaan";
    public static final String TAG_ALAMAT_DONATUR = "alamat_donatur";
    public static final String TAG_TLP_DONATUR = "tlp";

    public static final String TAG_ID_NIK = "nik";
    public static final String TAG_ID_DONASI = "id_donasi";
    public static final String TAG_UANG_DONASI = "uang";
    public static final String TAG_NAMA_DONASI = "nama";
    public static final String TAG_JK_DONASI = "jk";
    public static final String TAG_AGAMA_DONASI = "agama";
    public static final String TAG_ALAMAT_DONASI= "alamat";
    public static final String TAG_KEAHLIAN_DONASI = "keahlian";
    public static final String TAG_TELP_DONASI = "telp";

    public static final String TAG_NAMA_PANTI = "nama_panti";
    public static final String TAG_UANG = "uang";
    public static final String TAG_NAMA = "nama";
    public static final String TAG_JKSDM = "jk";
    public static final String TAG_AGAMASDM = "agama";
    public static final String TAG_KEAHLIAN = "keahlian";
    public static final String TAG_JUMLAHSDM = "jumlahsdm";
    //ID karyawan
    //emp itu singkatan dari Employee
    public static final String EMP_ID = "emp_id";
}
