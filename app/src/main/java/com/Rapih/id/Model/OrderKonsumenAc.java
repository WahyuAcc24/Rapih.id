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
    @SerializedName("perbaikan_ac")
    private String perbaikan_ac;
    @SerializedName("bongkar_pasang_ac")
    private String bongkar_pasang_ac;
    @SerializedName("layanan_cuci_ac")
    private String layanan_cuci_ac;
    @SerializedName("freon")
    private String freon;
    @SerializedName("jumlah_ac")
    private String jumlah_ac;
    @SerializedName("lokasi_proyek")
    private String lokasi_proyek;
    @SerializedName("tanggal_pengerjaan")
    private String tanggal_pengerjaan;
    @SerializedName("deskripsi_pekerjaan")
    private String deskripsi_pekerjaan;
    @SerializedName("total_pembayaran")
    private String total_pembayaran;
    @SerializedName("status")
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFreon() {
        return freon;
    }

    public void setFreon(String freon) {
        this.freon = freon;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId_mitra_ac() {
        return id_mitra_ac;
    }

    public void setId_mitra_ac(String id_mitra_ac) {
        this.id_mitra_ac = id_mitra_ac;
    }

    public String getId_konsumen_ac() {
        return id_konsumen_ac;
    }

    public void setId_konsumen_ac(String id_konsumen_ac) {
        this.id_konsumen_ac = id_konsumen_ac;
    }

    public String getJenis_properti() {
        return jenis_properti;
    }

    public void setJenis_properti(String jenis_properti) {
        this.jenis_properti = jenis_properti;
    }

    public String getPerbaikan_ac() {
        return perbaikan_ac;
    }

    public void setPerbaikan_ac(String perbaikan_ac) {
        this.perbaikan_ac = perbaikan_ac;
    }

    public String getBongkar_pasang_ac() {
        return bongkar_pasang_ac;
    }

    public void setBongkar_pasang_ac(String bongkar_pasang_ac) {
        this.bongkar_pasang_ac = bongkar_pasang_ac;
    }

    public String getLayanan_cuci_ac() {
        return layanan_cuci_ac;
    }

    public void setLayanan_cuci_ac(String layanan_cuci_ac) {
        this.layanan_cuci_ac = layanan_cuci_ac;
    }

    public String getJumlah_ac() {
        return jumlah_ac;
    }

    public void setJumlah_ac(String jumlah_ac) {
        this.jumlah_ac = jumlah_ac;
    }

    public String getLokasi_proyek() {
        return lokasi_proyek;
    }

    public void setLokasi_proyek(String lokasi_proyek) {
        this.lokasi_proyek = lokasi_proyek;
    }

    public String getTanggal_pengerjaan() {
        return tanggal_pengerjaan;
    }

    public void setTanggal_pengerjaan(String tanggal_pengerjaan) {
        this.tanggal_pengerjaan = tanggal_pengerjaan;
    }

    public String getDeskripsi_pekerjaan() {
        return deskripsi_pekerjaan;
    }

    public void setDeskripsi_pekerjaan(String deskripsi_pekerjaan) {
        this.deskripsi_pekerjaan = deskripsi_pekerjaan;
    }

    public String getTotal_pembayaran() {
        return total_pembayaran;
    }

    public void setTotal_pembayaran(String total_pembayaran) {
        this.total_pembayaran = total_pembayaran;
    }
}
