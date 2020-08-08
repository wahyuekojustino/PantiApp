package com.android.pantiasuhan.pantiasuhan.Pengunjung;

/**
 * Created by Bismillah on 25/11/2017.
 */

public class konfigurasi {

    //Dibawah ini merupakan Pengalamatan dimana Lokasi Skrip CRUD PHP disimpan
    //Pada tutorial Kali ini, karena kita membuat localhost maka alamatnya tertuju ke IP komputer
    //dimana File PHP tersebut berada
    //PENTING! JANGAN LUPA GANTI IP SESUAI DENGAN IP KOMPUTER DIMANA DATA PHP BERADA
    public static final String URL_ADD="http://gascoding.id/rest/SIGPAK/panti/tambahPanti.php";
    public static final String URL_KEC="http://gascoding.id/rest/SIGPAK/pengunjung/tampilKecamatan.php";
    public static final String URL_GET_ALL = "http://gascoding.id/rest/SIGPAK/pengunjung/tampilSemuaPanti.php";
    public static final String URL_GET_ALL2 = "http://gascoding.id/rest/SIGPAK/pengunjung/tampilSemuaPanti2.php";


    public static final String URL_GET_JML_PENGURUS = "http://gascoding.id/rest/SIGPAK/pengunjung/jumlahPengurus.php?id_panti=";
    public static final String URL_GET_JML_ANAK = "http://gascoding.id/rest/SIGPAK/pengunjung/jumlahAnak.php?id_panti=";

    public static final String URL_GET_PANTI_KEC = "http://gascoding.id/rest/SIGPAK/pengunjung/tampilPantiKec.php?kecamatan=";
    public static final String URL_GET_PANTI_KEC2 = "http://gascoding.id/rest/SIGPAK/pengunjung/tampilPantiKec2.php?kecamatan=";
    public static final String URL_GET_EMP = "http://gascoding.id/rest/SIGPAK/pengunjung/tampilPanti.php?id_panti=";
    public static final String URL_VALIDASI_EMP = "http://gascoding.id/rest/SIGPAK/pengunjung/validasiPanti.php?id_panti=";
    public static final String URL_DELETE_EMP = "http://gascoding.id/rest/SIGPAK/pengunjung/hapusPanti.php?id_panti=";

    public static final String URL_GET_ALL_ANAK = "http://gascoding.id/rest/SIGPAK/pengunjung/tampilSemuaAnak.php?id_panti=";
    public static final String URL_GET_EMP_ANAK = "http://gascoding.id/rest/SIGPAK/pengunjung/tampilAnak.php?id_anak=";
    public static final String URL_UPDATE_EMP_ANAK = "http://gascoding.id/rest/SIGPAK/anak/updateAnak.php";
    public static final String URL_VALIDASI_EMP_ANAK = "http://gascoding.id/rest/SIGPAK/pengunjung/validasiAnak.php?id_anak=";

    public static final String URL_GET_ALL_PENGURUS = "http://gascoding.id/rest/SIGPAK/pengunjung/tampilSemuaPengurus.php?id_panti=";
    public static final String URL_GET_EMP_PENGURUS = "http://gascoding.id/rest/SIGPAK/pengunjung/tampilPengurus.php?id_pengurus=";
    public static final String URL_UPDATE_EMP_PENGURUS = "http://gascoding.id/rest/SIGPAK/pengunjung/updatePengurus.php";
    public static final String URL_VALIDASI_EMP_PENGURUS = "http://gascoding.id/rest/SIGPAK/pengunjung/validasiPengurus.php?id_pengurus=";

    public static final String URL_GET_ALL_DONATUR = "http://gascoding.id/rest/SIGPAK/pengunjung/tampilSemuaDonatur.php?id_panti=";
    public static final String URL_GET_EMP_DONATUR = "http://gascoding.id/rest/SIGPAK/pengunjung/tampilDonatur.php?id_donatur=";
    public static final String URL_UPDATE_EMP_DONATUR = "http://gascoding.id/rest/SIGPAK/pengunjung/updateDonatur.php";
    public static final String URL_VALIDASI_EMP_DONATUR = "http://gascoding.id/rest/SIGPAK/pengunjung/validasiDonatur.php?id_donatur=";

    public static final String URL_GET_ALL_KEBUTUHAN = "http://gascoding.id/rest/SIGPAK/pengunjung/tampilSemuaKebutuhan.php?id_panti=";
    public static final String URL_GET_PANTI = "http://gascoding.id/rest/SIGPAK/panti/dataPanti.php";
    public static final String URL_GET_EMP_KEBUTUHAN = "http://gascoding.id/rest/SIGPAK/pengunjung/tampilKebutuhan.php?id_kebutuhan=";
    public static final String URL_GET_VALIDASI_EMP_KEBUTUHAN = "http://gascoding.id/rest/SIGPAK/pengunjung/tampilKebutuhan.php?id_kebutuhan=";

    public static final String URL_GET_ALL_KEGIATAN = "http://gascoding.id/rest/SIGPAK/pengunjung/tampilSemuaKegiatan.php?id_panti=";
    public static final String URL_GET_EMP_KEGIATAN = "http://gascoding.id/rest/SIGPAK/pengunjung/tampilKegiatan.php?id_kegiatan=";

    //Dibawah ini merupakan Kunci yang akan digunakan untuk mengirim permintaan ke Skrip PHP
    public static final String KEY_EMP_ID = "id_panti";
    public static final String KEY_EMP_NAMA_PANTI= "nama_panti";
    public static final String KEY_EMP_ALAMAT_PANTI = "alamat_panti";
    public static final String KEY_EMP_BERDIRI = "berdiri";
    public static final String KEY_EMP_TLP = "tlp";
    public static final String KEY_EMP_LATI = "lati";
    public static final String KEY_EMP_LONGI = "longi";



    //JSON Tags
    public static final String JSON_ARRAY = "result";
    public static final String TAG_JSON_ARRAY="result";

    public static final String SHARED_PREF_NAME="sharedPanti";
    public static final String LOGGEDIN_SHARED_PREF="loggedin";

    public static final String TAG_ID = "id_panti";
    public static final String TAG_NAMA_PANTI = "nama_panti";
    public static final String TAG_USERNAME = "username";
    public static final String TAG_PASSWORD = "password";
    public static final String TAG_KECAMATAN = "kecamatan";
    public static final String TAG_ALAMAT_PANTI = "alamat_panti";
    public static final String TAG_BERDIRI = "berdiri";
    public static final String TAG_SK = "sk";
    public static final String TAG_BANK = "bank";
    public static final String TAG_AKUN = "akun";
    public static final String TAG_REKENING = "rekening";
    public static final String TAG_TLP = "tlp";
    public static final String TAG_LATI = "lati";
    public static final String TAG_LONGI = "longi";
    public static final String TAG_JML_LK = "laki";
    public static final String TAG_JML_PR = "perempuan";





    public static final String KEY_EMP_ID_ANAK = "id_anak";
    public static final String KEY_EMP_NAMA_ANAK= "nama_anak";
    public static final String KEY_EMP_TTL = "ttl";
    public static final String KEY_EMP_JK = "jk";
    public static final String KEY_EMP_IBU= "ibu";
    public static final String KEY_EMP_AYAH = "ayah";
    public static final String KEY_EMP_ALAMAT = "alamat_anak";
    public static final String KEY_EMP_TLP_ANAK = "tlp";
    public static final String KEY_EMP_STATUS = "status";


    public static final String TAG_ID_ANAK = "id_anak";
    public static final String TAG_NAMA_ANAK= "nama_anak";
    public static final String TAG_TTL = "tempat_lahir";
    public static final String TAG_TGLL = "tgl_lahir";
    public static final String TAG_JK = "jk";
    public static final String TAG_IBU= "ibu";
    public static final String TAG_AYAH = "ayah";
    public static final String TAG_ALAMAT = "alamat_anak";
    public static final String TAG_TLP_ANAK = "tlp";
    public static final String TAG_STATUS = "status";
    public static final String TAG_AGAMA = "agama";

    //ID karyawan
    //emp itu singkatan dari Employee
    public static final String EMP_ID = "emp_id";

    public static final String KEY_EMP_ID_PENGURUS = "id_pengurus";
    public static final String KEY_EMP_NAMA_PENGURUS= "nama_pengurus";
    public static final String KEY_EMP_JABATAN = "jabatan";
    public static final String KEY_EMP_ALAMAT_PENGURUS = "alamat_pengurus";
    public static final String KEY_EMP_TLP_PENGURUS = "tlp";

    public static final String TAG_ID_PENGURUS = "id_pengurus";
    public static final String TAG_NAMA_PENGURUS = "nama_pengurus";
    public static final String TAG_JABATAN = "jabatan";
    public static final String TAG_ALAMAT_PENGURUS = "alamat_pengurus";
    public static final String TAG_TLP_PENGURUS = "tlp";


    public static final String KEY_EMP_ID_DONATUR = "id_donatur";
    public static final String KEY_EMP_NAMA_DONATUR= "nama_donatur";
    public static final String KEY_EMP_PEKERJAAN = "pekerjaan";
    public static final String KEY_EMP_ALAMAT_DONATUR = "alamat_donatur";
    public static final String KEY_EMP_TLP_DONATUR = "tlp";

    public static final String TAG_ID_DONATUR = "id_donatur";
    public static final String TAG_NAMA_DONATUR = "nama_donatur";
    public static final String TAG_PEKERJAAN = "pekerjaan";
    public static final String TAG_ALAMAT_DONATUR = "alamat_donatur";
    public static final String TAG_TLP_DONATUR = "tlp";

    public static final String KEY_EMP_ID_KEBUTUHAN = "id_kebutuhan";
    public static final String KEY_EMP_KEBUTUHAN= "kebutuhan";

    public static final String TAG_ID_KEBUTUHAN = "id_kebutuhan";
    public static final String TAG_KEBUTUHAN = "kebutuhan";
    public static final String TAG_UANG = "uang";
    public static final String TAG_KEPERLUAN= "keperluan";
    public static final String TAG_BARANG = "barang";
    public static final String TAG_JUMLAH = "jumlah";
    public static final String TAG_NAMA = "nama";
    public static final String TAG_JKSDM = "jk";
    public static final String TAG_AGAMASDM = "agama";
    public static final String TAG_KEAHLIAN = "keahlian";
    public static final String TAG_JUMLAHSDM = "jumlahsdm";

    public static final String KEY_EMP_ID_KEGITAN = "id_kegiatan";
    public static final String KEY_EMP_TGL= "tgl";
    public static final String KEY_EMP_KETERANGAN= "keterangan";

    public static final String TAG_ID_KEGIATAN = "id_kegiatan";
    public static final String TAG_TGL = "tgl";
    public static final String TAG_KETERANGAN = "keterangan";

}
