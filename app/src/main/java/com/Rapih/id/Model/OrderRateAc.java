package com.Rapih.id.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderRateAc {


    @SerializedName("rating")
    private String rating;
    @SerializedName("status")
    private String status;
    @SerializedName("data_rating") private String data_rating;


    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
