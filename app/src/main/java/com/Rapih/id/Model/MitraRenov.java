package com.Rapih.id.Model;

public class MitraRenov {

    private boolean status;
    private String message;
    private DataMitraAc data_mitra;

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

    public DataMitraAc getData_mitra() {
        return data_mitra;
    }

    public class DataMitraAc {
        private String id;
        private String nama;
        private String email;
        private String no_hp;
        private String images;


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
