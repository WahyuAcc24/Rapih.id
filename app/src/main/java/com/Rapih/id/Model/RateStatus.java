package com.Rapih.id.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RateStatus<M> {
    @SerializedName("status") private boolean status;
    @SerializedName("data_rating") private List<M> data_rating;

    public boolean isStatus() {
        return status;
    }

    public List<M> getDataRating() {
        return data_rating;
    }
}
