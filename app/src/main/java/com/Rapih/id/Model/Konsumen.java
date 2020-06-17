package com.Rapih.id.Model;

public class Konsumen {

    private boolean status;
    private String message;
    private DataKons data_kons;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataKons getData_kons() {
        return data_kons;
    }

    public class DataKons {
        private String id;
        private String nama;
        private String email;
        private String alamat_rumah;
        private String no_hp;
        private String images;


        public String getAlamat_rumah() {
            return alamat_rumah;
        }

        public void setAlamat_rumah(String alamat_rumah) {
            this.alamat_rumah = alamat_rumah;
        }

        public String getNo_hp() {
            return no_hp;
        }

        public void setNo_hp(String no_hp) {
            this.no_hp = no_hp;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getId() {
            return id;
        }

        public String getNama() {
            return nama;
        }

        public String getEmail() {
            return email;
        }

    }

}
