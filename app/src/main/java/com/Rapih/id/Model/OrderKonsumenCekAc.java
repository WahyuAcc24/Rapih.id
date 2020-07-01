package com.Rapih.id.Model;

import com.google.gson.annotations.SerializedName;

public class OrderKonsumenCekAc {



    @SerializedName("id")
    private String id;
    @SerializedName("id_konsumen_ac")
    private String id_konsumen_ac;
    @SerializedName("id_mitra_ac")
    private String id_mitra_ac;
    @SerializedName("no_hp")
    private String no_hp;
    @SerializedName("jenis_properti")
    private String jenis_properti;
    @SerializedName("order_ac")
    private String order_ac;
    @SerializedName("jumlah_ac")
    private String jumlah_ac;
    @SerializedName("lokasi")
    private String lokasi;
    @SerializedName("tanggal")
    private String tanggal;
    @SerializedName("deskripsi_pekerjaan")
    private String deskripsi_pekerjaan;
    @SerializedName("harga")
    private String harga;
    @SerializedName("status")
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_konsumen_ac() {
        return id_konsumen_ac;
    }

    public void setId_konsumen_ac(String id_konsumen_ac) {
        this.id_konsumen_ac = id_konsumen_ac;
    }

    public String getId_mitra_ac() {
        return id_mitra_ac;
    }

    public void setId_mitra_ac(String id_mitra_ac) {
        this.id_mitra_ac = id_mitra_ac;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getJenis_properti() {
        return jenis_properti;
    }

    public void setJenis_properti(String jenis_properti) {
        this.jenis_properti = jenis_properti;
    }

    public String getOrder_ac() {
        return order_ac;
    }

    public void setOrder_ac(String order_ac) {
        this.order_ac = order_ac;
    }

    public String getJumlah_ac() {
        return jumlah_ac;
    }

    public void setJumlah_ac(String jumlah_ac) {
        this.jumlah_ac = jumlah_ac;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getDeskripsi_pekerjaan() {
        return deskripsi_pekerjaan;
    }

    public void setDeskripsi_pekerjaan(String deskripsi_pekerjaan) {
        this.deskripsi_pekerjaan = deskripsi_pekerjaan;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
