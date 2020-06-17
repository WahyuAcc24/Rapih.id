package com.Rapih.id.Server

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import kotlin.random.Random


class APIorder {
    companion object {

        // base url dari end point.
        const val BASE_URL = "http://rapih.id/api/"
        const val IMAGE_URL = BASE_URL + "file/"

    }

    // ini retrofit
    private fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

        // buat sebuah instance untuk call sebuah interface dari retrofit.
        fun instance(): ApiInterface {

        return retrofit().create(ApiInterface::class.java)

    }
}

interface ApiInterface {

    @Multipart
    @POST("orderkonsumen.php")
    fun upload(
        @Part gambar_properti: MultipartBody.Part,

        @Field("jenis_properti") jenis_properti : String,
        @Field("waktu_pengerjaan") waktu_pengerjaan : String,
        @Field("domisili_proyek") domisili_proyek : String,
        @Field("lokasi_proyek") lokasi_proyek : String,
        @Field("alamat_lengkap") alamat_lengkap : String,
        @Field("detail_pekerjaan") detail_pekerjaan : String,
        @Field("anggaran_proyek") anggaran_proyek : String



    ) : Call<Random.Default> // memanggil response model 'Default'

}