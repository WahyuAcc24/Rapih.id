package com.Rapih.id.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderAcStatus<N> {
    @SerializedName("status") private boolean status;
    @SerializedName("data_kons_ac") private List<N> data_kons_ac;

    public boolean isStatus() {
        return status;
    }

    public List<N> getDataKonsAc() {
        return data_kons_ac;
    }
}
