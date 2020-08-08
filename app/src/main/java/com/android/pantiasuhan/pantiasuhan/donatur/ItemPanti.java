package com.android.pantiasuhan.pantiasuhan.donatur;

public class ItemPanti {
    private String id_panti;
    private String nama_panti;

    public ItemPanti(String id_panti, String nama_panti) {
        this.id_panti = id_panti;
        this.nama_panti = nama_panti;
    }

    public String getId_panti() {
        return id_panti;
    }

    public void setId_panti(String id_panti) {
        this.id_panti = id_panti;
    }

    public String getNama_panti() {
        return nama_panti;
    }

    public void setNama_panti(String nama_panti) {
        this.nama_panti = nama_panti;
    }

    //to display object as a string in spinner
    @Override
    public String toString() {
        return nama_panti;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ItemPanti){
            ItemPanti c = (ItemPanti ) obj;
            if(c.getNama_panti().equals(nama_panti) && c.getId_panti().equals(id_panti) ) return true;
        }

        return false;
    }


}
