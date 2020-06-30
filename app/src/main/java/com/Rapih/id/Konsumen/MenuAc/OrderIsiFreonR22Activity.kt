package com.Rapih.id.Konsumen.MenuAc

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.Rapih.id.Konsumen.MapsActivity
import com.Rapih.id.Model.UserLocation
import com.Rapih.id.R
import com.Rapih.id.retrofitimage.ApiConfig
import com.Rapih.id.retrofitimage.AppConfig
import io.isfaaghyth.rak.Rak

class OrderFreonR22Activity : AppCompatActivity(){

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


    val URL_orderAc = "http://rapih.id/api/orderackonsumen.php"
    private val TAG = OrderAcActivity::class.java!!.getSimpleName()


    var tag_json_obj: String = "json_obj_req"

    var progressDialog: ProgressDialog? = null

    var context: Context? = null
    internal lateinit var conMgr: ConnectivityManager

    var mApi: ApiConfig? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_konf_freon_r22)


        Rak.initialize(this)

        img_back = findViewById(R.id.imgbackkonffreonr22)

        txt_email = findViewById(R.id.txtemailkonsumenacOrderfreonr22)
        txt_sp_jp = findViewById(R.id.txtjenispropertifreonr22)
        txt_sp_freon_ac_1pk = findViewById(R.id.txtfreonr22_1pk)
        txt_sp_freon_ac_2pk = findViewById(R.id.txtfreonr22_2pk)
        txt_jmlh_ac = findViewById(R.id.txtfreonr22_1pk)
        txt_jmlh_ac2 = findViewById(R.id.txtfreonr22_2pk)
        txt_tgl = findViewById(R.id.txtWaktuPengerjaanfreonr22)
        txt_total = findViewById(R.id.txthargafreonr22)
        txt_dp = findViewById(R.id.txtDetailPekerjaanfreonr22)

        btn_req = findViewById(R.id.btnAjukanPesananfreonr22)

        edt_maps_konf = findViewById(R.id.edtmapslokasifreonr22)



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



    }




}