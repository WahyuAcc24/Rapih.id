package com.Rapih.id.Konsumen.MenuAc

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.Rapih.id.Konsumen.HomeKonsumenActivity
import com.Rapih.id.Konsumen.MapsActivity
import com.Rapih.id.Model.UserLocation
import com.Rapih.id.R
import com.Rapih.id.retrofitimage.ApiConfig
import com.Rapih.id.retrofitimage.AppConfig
import io.isfaaghyth.rak.Rak
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderAcActivity : AppCompatActivity() {

    companion object {
        const val USER_MAP = 123
        const val KEY_MAP = "lokasi_user"
    }

    lateinit var img_back: ImageView
    lateinit var txt_email: TextView
    lateinit var txt_sp_jp: TextView
    lateinit var txt_sp_pa: TextView
    lateinit var txt_sp_bp_ac: TextView
    lateinit var txt_sp_cuci_ac: TextView
    lateinit var txt_sp_freon : TextView
    lateinit var txt_jmlh: TextView
    lateinit var btn_req: Button
    lateinit var txt_tgl: TextView
    lateinit var txt_total: TextView
    lateinit var edt_maps_konf: EditText
    lateinit var txt_dp: TextView


    val URL_orderAc = "http://rapih.id/api/orderackonsumen.php"
    private val TAG = OrderAcActivity::class.java!!.getSimpleName()


    var tag_json_obj: String = "json_obj_req"

    var progressDialog: ProgressDialog? = null

    var context: Context? = null
    internal lateinit var conMgr: ConnectivityManager

    var mApi: ApiConfig? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_konfirmasi_ac)

        Rak.initialize(this)



        txt_sp_jp = findViewById(R.id.txtjenispropertiac)
        txt_sp_pa = findViewById(R.id.txtperbaikanac)
        txt_sp_bp_ac = findViewById(R.id.txtbpac)
        txt_sp_cuci_ac = findViewById(R.id.txtcuciac)
        txt_sp_freon = findViewById(R.id.txtfreon)


        txt_jmlh = findViewById(R.id.txtjmlac)
        edt_maps_konf = findViewById(R.id.edtmapslokasiac)
        txt_tgl = findViewById(R.id.txtWaktuPengerjaanac)
        txt_dp = findViewById(R.id.txtDetailPekerjaanac)

        txt_email = findViewById(R.id.txtemailkonsumenacOrderac)
        txt_total = findViewById(R.id.txthargaac)

        img_back = findViewById(R.id.imgbackackonf)
        btn_req = findViewById(R.id.btnAjukanPesananAc)

        mApi = AppConfig.getAPIService()
        context = this

        img_back.setOnClickListener {
            val intent = Intent(this, AcKonsumenActivity::class.java)
            startActivity(intent)
            finish()
        }

        txt_email.setText(Rak.grab("emailkonsumen") as? String)
        txt_sp_jp.setText(intent.getStringExtra("jenis_properti_ac"))
        txt_sp_pa.setText(intent.getStringExtra("perbaikan_ac"))
        txt_sp_bp_ac.setText(intent.getStringExtra("bongkar_ac"))
        txt_sp_cuci_ac.setText(intent.getStringExtra("cuci_ac"))
        txt_sp_freon.setText(intent.getStringExtra("freon_ac"))
        txt_jmlh.setText(intent.getStringExtra("jumlah_ac"))
        txt_tgl.setText(intent.getStringExtra("tanggal"))
        txt_dp.setText(intent.getStringExtra("deskripsi_ac"))
        txt_total.setText(intent.getStringExtra("total_harga"))

//        txt_sp_jp.text.toString()
//        txt_sp_pa.text.toString()
//        txt_sp_bp_ac.text.toString()
//        txt_sp_cuci_ac.text.toString()
//        txt_jmlh.text.toString()
//        edt_maps_konf.text.toString()
//        txt_tgl.text.toString()
//        txt_dp.text.toString()
//        txt_total.text.toString()


        val userLocation = intent.getSerializableExtra("user_location_ac") as? UserLocation
        edt_maps_konf.setText(userLocation?.address)

        edt_maps_konf.isFocusable = false
        edt_maps_konf.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            userLocation?.let {
                intent.putExtra("lat", it.latLong["lat"])
                intent.putExtra("lon", it.latLong["lon"])
                intent.putExtra("address", it.address)
            }
            startActivityForResult(intent,
                USER_MAP
            )

        }

        conMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        run {
            if (conMgr.activeNetworkInfo != null
                && conMgr.activeNetworkInfo!!.isAvailable
                && conMgr.activeNetworkInfo!!.isConnected
            ) {
            } else {
                Toast.makeText(
                    applicationContext, " Silahkan Cek Lagi Koneksi Anda ",
                    Toast.LENGTH_LONG
                ).show()
            }
        }


        btn_req.setOnClickListener {

//            val loading = ProgressDialog(this)
//            loading.setCancelable(false)
//            loading.setMessage("Menambahkan Order...")
//            loading.show()
            progressDialog = ProgressDialog.show(context, null, "Harap Tunggu...", true, false)

            val jp = txt_sp_jp.text.toString()

            if (jp.trim().length > 0) {
                if (conMgr.activeNetworkInfo != null
                    && conMgr.activeNetworkInfo!!.isAvailable
                    && conMgr.activeNetworkInfo!!.isConnected
                ) {

                } else {
                    Toast.makeText(applicationContext, "tidak ada koneksi", Toast.LENGTH_LONG)
                        .show()
                }
            } else {
                Toast.makeText(applicationContext, "jenis properti tidak boleh kosong", Toast.LENGTH_LONG)
                    .show()

            }
            reqOrderAc()


        }
    }
    fun reqOrderAc(){

        val id_konsumen_ac : String = Rak.grab("idkonsumen")
        val no_hp : String = Rak.grab("hpkonsumen")

        mApi?.orderAcReq(id_konsumen_ac,
            "0",
            no_hp,
            txt_sp_jp.text.toString(),
            txt_sp_pa.text.toString(),
            txt_sp_bp_ac.text.toString(),
            txt_sp_cuci_ac.text.toString(),
            txt_sp_freon.text.toString(),
            txt_jmlh.text.toString(),
            edt_maps_konf.getText().toString(),
            txt_tgl.text.toString(),
            txt_dp.text.toString(),
            txt_total.text.toString(),
            "wait")

            ?.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,response: Response<ResponseBody> ){

                     if (response.isSuccessful()) {

                         Log.i("DEBUG", "onResponse : Order Berhasil")
                         progressDialog?.dismiss()
                         Toast.makeText(applicationContext, "Berhasil Order ", Toast.LENGTH_SHORT).show()
                         val i = Intent(this@OrderAcActivity, HomeKonsumenActivity::class.java)
                         startActivity(i)
                         finish()

                        } else {
                            Log.i("debug", "onResponse : Tidak Berhasil")
                         Toast.makeText(applicationContext, "Gagal Order gan", Toast.LENGTH_SHORT).show()
                         progressDialog?.dismiss()
                        }
                    }

                override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                    Log.e("debug", "onFailure: ERROR > " + t?.message)
                    Toast.makeText(
                        applicationContext,
                        "Koneksi Internet Bermasalah",
                        Toast.LENGTH_SHORT
                    ).show()
//                    progressDialog?.dismiss()
                }

            })
    }

}




//val jp: String = txt_sp_jp.text.toString()
//val bpac: String = txt_sp_bp_ac.text.toString()
//val cuciac: String = txt_sp_cuci_ac.text.toString()
//val pa: String = txt_sp_pa.text.toString()
//val lokasi :String  = edt_maps_konf.text.toString()
//val jml_ac : String = txt_jmlh.text.toString()
//val tgl: String = txt_tgl.text.toString()
//val des_ac: String = txt_dp.text.toString()
//val total: String = txt_total.text.toString()
//val id_konsumen = Rak.grab("idkonsumen") as String
//
//
//val stringRequest = object : StringRequest(
//    Request.Method.POST, URL_orderAc,
//    Response.Listener<String> { response ->
//        Log.e(TAG, "Order Ac Response: $response")
//        try {
//
//
//
//            loading.dismiss()
//            Toast.makeText(getApplicationContext(), "Order Ac berhasil", Toast.LENGTH_SHORT).show()
//
//            val intent = Intent(this@OrderAcActivity,HomeKonsumenActivity::class.java)
//            startActivity(intent)
//
//        } catch (e: JSONException) {
//            Log.e("haha", e.message)
//            e.printStackTrace()
//        }
//
//    },
//    object : Response.ErrorListener {
//        override fun onErrorResponse(volleyError: VolleyError) {
//            Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show()
//            loading.dismiss()
//        }
//    }) {
//    @Throws(AuthFailureError::class)
//    override fun getParams(): Map<String, String> {
//        val params = HashMap<String, String>()
//        params.put("id_konsumen_ac", id_konsumen)
//        params.put("jenis_properti", jp)
//        params.put("perbaikan_ac", pa)
//        params.put("bongkar_pasang_ac", bpac)
//        params.put("layanan_cuci_ac", cuciac)
//        params.put("jumlah_ac", jml_ac)
//        params.put("lokasi_proyek", lokasi)
//        params.put("tanggal_pengerjaan", tgl)
//        params.put("deskripsi_pekerjaan", des_ac)
//        params.put("total_pembayaran", total)
//
//        return params
//    }
//}
//
////adding request to queue
//AppController.getInstance().addToRequestQueue(stringRequest,tag_json_obj)
//}




