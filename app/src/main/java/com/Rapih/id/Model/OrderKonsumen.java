package com.Rapih.id.Model;

import com.google.gson.annotations.SerializedName;

public class OrderKonsumen {



    @SerializedName("id")
    private String id;
    @SerializedName("id_konsumen")
    private String id_konsumen;
    @SerializedName("no_hp")
    private String no_hp;
    @SerializedName("jenis_properti")
    private String jenis_properti;
    @SerializedName("waktu_pengerjaan")
    private String waktu_pengerjaan;
    @SerializedName("domisili_proyek")
    private String domisili_proyek;
    @SerializedName("lokasi_proyek")
    private String lokasi_proyek;
    @SerializedName("alamat_lengkap")
    private String alamat_lengkap;
    @SerializedName("detail_pekerjaan")
    private String detail_pekerjaan;
    @SerializedName("anggaran_proyek")
    private String anggaran_proyek;
    @SerializedName("gambar_properti")
    private String gambar_properti;
    @SerializedName("status")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_konsumen() {
        return id_konsumen;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public void setId_konsumen(String id_konsumen) {
        this.id_konsumen = id_konsumen;
    }

    public String getJenis_properti() {
        return jenis_properti;
    }

    public void setJenis_properti(String jenis_properti) {
        this.jenis_properti = jenis_properti;
    }

    public String getWaktu_pengerjaan() {
        return waktu_pengerjaan;
    }

    public void setWaktu_pengerjaan(String waktu_pengerjaan) {
        this.waktu_pengerjaan = waktu_pengerjaan;
    }

    public String getDomisili_proyek() {
        return domisili_proyek;
    }

    public void setDomisili_proyek(String domisili_proyek) {
        this.domisili_proyek = domisili_proyek;
    }

    public String getLokasi_proyek() {
        return lokasi_proyek;
    }

    public void setLokasi_proyek(String lokasi_proyek) {
        this.lokasi_proyek = lokasi_proyek;
    }

    public String getAlamat_lengkap() {
        return alamat_lengkap;
    }

    public void setAlamat_lengkap(String alamat_lengkap) {
        this.alamat_lengkap = alamat_lengkap;
    }

    public String getDetail_pekerjaan() {
        return detail_pekerjaan;
    }

    public void setDetail_pekerjaan(String detail_pekerjaan) {
        this.detail_pekerjaan = detail_pekerjaan;
    }

    public String getAnggaran_proyek() {
        return anggaran_proyek;
    }

    public void setAnggaran_proyek(String anggaran_proyek) {
        this.anggaran_proyek = anggaran_proyek;
    }

    public String getGambar_properti() {
        return gambar_properti;
    }

    public void setGambar_properti(String gambar_properti) {
        this.gambar_properti = gambar_properti;
    }
}
