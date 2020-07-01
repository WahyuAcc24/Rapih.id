package com.Rapih.id.Model;

import com.google.gson.annotations.SerializedName;

public class OrderKonsumenAc {



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
    @SerializedName("order_1pk")
    private String order_1pk;
    @SerializedName("jumlah_ac_1pk")
    private String jumlah_ac_1pk;
    @SerializedName("order_2pk")
    private String order_2pk;
    @SerializedName("jumlah_ac_2pk")
    private String jumlah_ac_2pk;
    @SerializedName("lokasi")
    private String lokasi;
    @SerializedName("tanggal")
    private String tanggal;
    @SerializedName("detail_pekerjaan")
    private String detail_pekerjaan;
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

    public String getOrder_1pk() {
        return order_1pk;
    }

    public void setOrder_1pk(String order_1pk) {
        this.order_1pk = order_1pk;
    }

    public String getJumlah_ac_1pk() {
        return jumlah_ac_1pk;
    }

    public void setJumlah_ac_1pk(String jumlah_ac_1pk) {
        this.jumlah_ac_1pk = jumlah_ac_1pk;
    }

    public String getOrder_2pk() {
        return order_2pk;
    }

    public void setOrder_2pk(String order_2pk) {
        this.order_2pk = order_2pk;
    }

    public String getJumlah_ac_2pk() {
        return jumlah_ac_2pk;
    }

    public void setJumlah_ac_2pk(String jumlah_ac_2pk) {
        this.jumlah_ac_2pk = jumlah_ac_2pk;
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

    public String getDetail_pekerjaan() {
        return detail_pekerjaan;
    }

    public void setDetail_pekerjaan(String detail_pekerjaan) {
        this.detail_pekerjaan = detail_pekerjaan;
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
