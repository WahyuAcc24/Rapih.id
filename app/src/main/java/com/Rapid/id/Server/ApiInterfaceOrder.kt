package com.Rapid.id.Server

import com.Rapid.id.Model.DefaultResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterfaceOrder {

    @FormUrlEncoded
    @POST("orderkonsumen.php")
    fun regisUser(

        @Field("nim") nim : String,
        @Field("nama") nama : String,
        @Field("pass") pass : String

    ): Call<DefaultResponse>
}