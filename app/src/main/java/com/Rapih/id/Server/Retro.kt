package com.Rapih.id.Server

import android.util.Base64
import okhttp3.OkHttpClient

object Retro {

    private val AUTH = "Basic "+ Base64.encodeToString("WR15".toByteArray(), Base64.NO_WRAP)
    val BASE_URL = "http://rapih.id/api/"


    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()

            val requestBuilder = original.newBuilder()
                .addHeader("Authorization", AUTH)
                .method(original.method(), original.body())

            val request = requestBuilder.build()
            chain.proceed(request)
        }.build()



}