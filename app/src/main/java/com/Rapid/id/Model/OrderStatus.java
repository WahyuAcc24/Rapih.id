package com.Rapid.id.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderStatus<M> {
    @SerializedName("status") private boolean status;
    @SerializedName("data_kons") private List<M> data_kons;

    public boolean isStatus() {
        return status;
    }

    public List<M> getDataKons() {
        return data_kons;
    }
}
