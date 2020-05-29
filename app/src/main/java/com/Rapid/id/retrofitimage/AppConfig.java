package com.Rapid.id.retrofitimage;

import com.Rapid.id.Server.Retro;

public class AppConfig {

    public static String BASE_URL = "http://rapih.id/api/";


    public static ApiConfig getAPIService(){
        return RetrofitClient.getClient(BASE_URL).create(ApiConfig.class);
    }

    //    public static Retrofit getRetrofit() {
//
//        return new Retrofit.Builder()
//                .baseUrl(AppConfig.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//    }

}
