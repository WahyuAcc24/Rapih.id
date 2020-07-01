package com.Rapih.id.Konsumen.MenuAc

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.Rapih.id.AppController
import com.Rapih.id.Konsumen.HomeKonsumenActivity
import com.Rapih.id.Konsumen.MapsActivity
import com.Rapih.id.Model.Konsumen
import com.Rapih.id.Model.UserLocation
import com.Rapih.id.R
import com.Rapih.id.retrofitimage.ApiConfig
import com.Rapih.id.retrofitimage.AppConfig
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import io.isfaaghyth.rak.Rak
import java.util.HashMap

class OrderFreonR32Activity : AppCompatActivity(){

    companion object {
        const val USER_MAP = 123
        const val KEY_MAP = "lokasi_user"
    }

    private lateinit var img_back: ImageView
    private lateinit var txt_email: TextView
    private lateinit var txt_sp_jp: TextView
    private lateinit var txt_sp_freon_ac_1pk: TextView
    private lateinit var txt_sp_freon_ac_2pk: TextView
    private lateinit var txt_jmlh_ac: TextView
    private lateinit var txt_jmlh_ac2: TextView
    private lateinit var btn_req: Button
    private lateinit var txt_tgl: TextView
    private lateinit var txt_total: TextView
    private lateinit var edt_maps_konf: EditText
    private lateinit var txt_dp: TextView


    val URL_freonAc = "http://rapih.id/api/freon_r32/orderfreonr32konsumen.php"
    private val TAG = OrderAcActivity::class.java.getSimpleName()


    var tag_json_obj: String = "json_obj_req"

    var progressDialog: ProgressDialog? = null

    var context: Context? = null
    internal lateinit var conMgr: ConnectivityManager

    var mApi: ApiConfig? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_konf_freon_r32)


        Rak.initialize(this)

        img_back = findViewById(R.id.imgbackkonffreonr32)

        txt_email = findViewById(R.id.txtemailkonsumenacOrderfreonr32)
        txt_sp_jp = findViewById(R.id.txtjenispropertifreonr32)
        txt_sp_freon_ac_1pk = findViewById(R.id.txtfreonr32_1pk)
        txt_sp_freon_ac_2pk = findViewById(R.id.txtfreonr32_2pk)
        txt_jmlh_ac = findViewById(R.id.txtjmlhfreonr321pk)
        txt_jmlh_ac2 = findViewById(R.id.txtjmlhfreonr32)
        txt_tgl = findViewById(R.id.txtWaktuPengerjaanfreonr32)
        txt_total = findViewById(R.id.txthargafreonr32)
        txt_dp = findViewById(R.id.txtDetailPekerjaanfreonr32)

        btn_req = findViewById(R.id.btnAjukanPesananfreonr32)

        edt_maps_konf = findViewById(R.id.edtmapslokasifreonr32)



        mApi = AppConfig.getAPIService()
        context = this


        img_back.setOnClickListener {

            onBackPressed()
        }


        txt_email.setText(Rak.grab("emailkonsumen") as? String)
        txt_sp_jp.setText(intent.getStringExtra("jenis_properti_freon_ac"))
        txt_sp_freon_ac_1pk.setText(intent.getStringExtra("freon_ac_1pk"))
        txt_sp_freon_ac_2pk.setText(intent.getStringExtra("freon_ac_2pk"))
        txt_jmlh_ac.setText(intent.getStringExtra("jumlah_ac_1pk"))
        txt_jmlh_ac2.setText(intent.getStringExtra("jumlah_ac_2pk"))
        txt_tgl.setText(intent.getStringExtra("tanggal"))
        txt_dp.setText(intent.getStringExtra("deskripsi_ac"))
        txt_total.setText(intent.getStringExtra("total_harga"))


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


            val id_kons: String = Rak.grab("idkonsumen")
            val no_hp: String = Rak.grab("hpkonsumen")
            val jp: String = txt_sp_jp.text.toString()
            val tgl: String = txt_tgl.text.toString()
            val order1pk: String = txt_sp_freon_ac_1pk.text.toString()
            val order2pk: String = txt_sp_freon_ac_2pk.text.toString()
            val jmlh: String = txt_jmlh_ac.text.toString()
            val jmlh2: String = txt_jmlh_ac2.text.toString()
            val desc: String = txt_dp.text.toString()
            val lokasi: String = edt_maps_konf.text.toString()
            val harga: String = txt_total.text.toString()

            if (lokasi.length > 0 && jp.length > 0) {
                if (conMgr.activeNetworkInfo != null
                    && conMgr.activeNetworkInfo!!.isAvailable
                    && conMgr.activeNetworkInfo!!.isConnected
                ) {
                    freonAc(
                        id_kons,
                        no_hp,
                        jp,
                        tgl,
                        order1pk,
                        order2pk,
                        jmlh,
                        jmlh2,
                        desc,
                        harga,
                        lokasi
                    )
                } else {
                    Toast.makeText(applicationContext, "tidak ada koneksi", Toast.LENGTH_LONG)
                        .show()
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    "Lokasi dan properti tidak boleh kosong",
                    Toast.LENGTH_LONG
                )
                    .show()

            }

        }

    }

    fun freonAc(id_kons : String,no_hp : String,jp : String,tgl : String,order1pk : String,order2pk : String,jmlh : String, jmlh2 : String,
                desc : String,harga : String,lokasi: String ){


        val loading = ProgressDialog(this)
        loading.setCancelable(false)
        loading.setMessage("Menambahkan Order...")
        loading.show()


        val stringRequest = object : StringRequest(
            Request.Method.POST, URL_freonAc,
            Response.Listener<String> { response ->


                Log.e(TAG, "Cuci Ac Order Response: $response")


                var res = Gson().fromJson(response.toString(), Konsumen::class.java)


                if (res.isStatus()) {

                    loading.dismiss()
                    Toast.makeText(getApplicationContext(), "Order Ac berhasil", Toast.LENGTH_SHORT).show()


                    val intent = Intent(this@OrderFreonR32Activity, HomeKonsumenActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    loading.dismiss()
                    Toast.makeText(getApplicationContext(), "Order Gagal", Toast.LENGTH_SHORT).show()
                }

            },
            object : Response.ErrorListener {
                override fun onErrorResponse(volleyError: VolleyError) {
                    loading.dismiss()
                    Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("id_konsumen_ac",id_kons)
                params.put("id_mitra_ac","0")
                params.put("no_hp",no_hp)
                params.put("jenis_properti",jp)
                params.put("tanggal",tgl)
                params.put("order_1pk",order1pk)
                params.put("order_2pk",order2pk)
                params.put("jumlah_ac_1pk",jmlh)
                params.put("jumlah_ac_2pk",jmlh2)
                params.put("detail_pekerjaan",desc)
                params.put("harga",harga)
                params.put("lokasi",lokasi)
                params.put("status","wait")


                return params
            }
        }

        //adding request to queue
        AppController.getInstance().addToRequestQueue(stringRequest)
    }



}