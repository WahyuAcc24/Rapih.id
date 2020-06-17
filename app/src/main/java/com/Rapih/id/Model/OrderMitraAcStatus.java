package com.Rapih.id.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderMitraAcStatus<N> {
    @SerializedName("status") private boolean status;
    @SerializedName("data_kons_mitraac") private List<N> data_kons_mitraac;

    public boolean isStatus() {
        return status;
    }

    public List<N> getDataKonsMitraAc() {
        return data_kons_mitraac;
    }
}
