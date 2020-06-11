package com.Rapid.id.retrofitimage;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface ApiConfig {

    @Multipart
    @POST("regismitra.php")
    Call<ServerResponse> upload(
            @Header("Authorization") String authorization,
            @PartMap Map<String, RequestBody> map
    );

//    @FormUrlEncoded
//    @POST("orderkonsumen.php")
//    Call<ResponseBody> orderReq(@Field("email_konsumen") String email_konsumen,
//                                @Field("jenis_properti") String jenis_properti,
//                                @Field("waktu_pengerjaan") String waktu_pengerjaan,
//                                @Field("domisili_proyek") String domisili_proyek,
//                                @Field("lokasi_proyek") String lokasi_proyek,
//                                @Field("alamat_lengkap") String alamat_lengkap,
//                                @Field("detail_pekerjaan") String detail_pekerjaan,
//                                @Field("anggaran_proyek") String anggaran_proyek
//                                );

    @FormUrlEncoded
    @POST("orderackonsumen.php")
    Call<ResponseBody> orderAcReq(@Field("id_konsumen_ac") String id_konsumen_ac,
                                  @Field("jenis_properti") String jenis_properti,
                                  @Field("perbaikan_ac") String perbaikan_ac,
                                  @Field("bongkar_pasang_ac") String bongkar_pasang_ac,
                                  @Field("layanan_cuci_ac") String layanan_cuci_ac,
                                  @Field("jumlah_ac") String jumlah_ac,
                                  @Field("lokasi_proyek") String lokasi_proyek,
                                  @Field("tanggal_pengerjaan") String tanggal_pengerjaan,
                                  @Field("deskripsi_pekerjaan") String deskripsi_pekerjaan,
                                  @Field("total_pembayaran") String total_pembayaran
    );


}
