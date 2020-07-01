package com.Rapih.id.Konsumen

import android.app.ProgressDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.Rapih.id.AppController
import com.Rapih.id.Model.OrderKonsumen
import com.Rapih.id.R
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import io.isfaaghyth.rak.Rak
import kotlinx.android.synthetic.main.rating.view.*
import java.util.HashMap

class DetailOrderRenovKonsumen : AppCompatActivity() {


    lateinit var txt_pa : TextView
    lateinit var txt_bpac : TextView
    lateinit var txt_cuciac : TextView
    lateinit var txt_freon : TextView
    lateinit var txt_ja : TextView
    lateinit var txt_lokasi : TextView
    lateinit var txt_tgl : TextView
    lateinit var txt_dp : TextView
    lateinit var txt_total : TextView
    lateinit var txt_nohp : TextView
    lateinit var btn_ok : Button



    lateinit var img_back : ImageView
    lateinit var txt_email : TextView

//    lateinit var rb : RatingBar
//    lateinit var edt_komen : EditText


    private val TAG = DetailOrderRenovKonsumen::class.java.getSimpleName()

    val URL_rate = "http://rapih.id/api/updateorderkonsumen.php"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_detail_order_renov_konsumen)


        txt_pa = findViewById(R.id.txtpadetailorderrenov)
        txt_bpac = findViewById(R.id.txtbpacdetailorderrenov)
        txt_cuciac = findViewById(R.id.txtcadetailorderrenov)
        txt_freon = findViewById(R.id.txtfreondetailorderrenov)
        txt_ja = findViewById(R.id.txtjadetailorderrenov)
        txt_lokasi = findViewById(R.id.txtlokasidetailorderrenov)
        txt_tgl = findViewById(R.id.txttgldetailorderrenov)
        txt_dp = findViewById(R.id.txtdpdetailorderrenov)
        txt_email = findViewById(R.id.txtemaildetailorderrenov)
        img_back = findViewById(R.id.imgbackdetailorderrenov)
        btn_ok = findViewById(R.id.btnokdetailorderrenov)
//        rb = findViewById(R.id.ratingBar)
//        edt_komen = findViewById(R.id.edtKomen)


        txt_nohp = findViewById(R.id.txtnohpdetailorderrenov)


        txt_email.setText(Rak.grab("emailkonsumen") as String)
        txt_nohp.setText("No Hp : " + Rak.grab("hpkonsumen") as String)

        img_back.setOnClickListener {
            onBackPressed()
        }


        val orderkonsumenrenov = Gson().fromJson(getIntent().getStringExtra("datarenov"), OrderKonsumen::class.java)



            Log.e("tag", getIntent().getStringExtra("datarenov"))

         txt_pa.setText("Jenis Properti : " + orderkonsumenrenov.jenis_properti)
         txt_bpac.setText("Waktu Pengerjaan : " + orderkonsumenrenov.waktu_pengerjaan)
         txt_cuciac.setText("Domisili Proyek : " + orderkonsumenrenov.domisili_proyek)
         txt_freon.setText("Lokasi Proyek : " + orderkonsumenrenov.lokasi_proyek)
         txt_ja.setText("Alamat Lengkap : " + orderkonsumenrenov.alamat_lengkap)
         txt_lokasi.setText("Detail Pekerjaan : " + orderkonsumenrenov.detail_pekerjaan)
         txt_tgl.setText("Anggaran Proyek : " + orderkonsumenrenov.anggaran_proyek)
         txt_dp.setText("detail pekerjaan : " + orderkonsumenrenov.detail_pekerjaan)

        if (orderkonsumenrenov.getStatus().equals("wait")) {
            btn_ok.setText("Menunggu Konfirmasi Teknisi")
            btn_ok.setBackgroundColor(Color.GRAY)
            btn_ok.setTextColor(Color.WHITE)
            btn_ok.isEnabled = false

        }else if (orderkonsumenrenov.getStatus().equals("konf")){
            btn_ok.setText("Teknisi akan menghubungimu")
            btn_ok.setBackgroundColor(Color.RED)
            btn_ok.setTextColor(Color.WHITE)
            btn_ok.isEnabled = false


        } else if (orderkonsumenrenov.status.equals("done")){
            btn_ok.setText("Beri Ulasan")
            btn_ok.setBackgroundColor(Color.GREEN)
            btn_ok.setTextColor(Color.WHITE)
            btn_ok.isEnabled = true
            btn_ok.setOnClickListener {
                val dialog : AlertDialog = AlertDialog.Builder(this).create()
                val mDialogView = LayoutInflater.from(this).inflate(R.layout.rating, null)
                val mBuilder = AlertDialog.Builder(this)
                    .setView(mDialogView)
                    .setTitle("Beri Ulasan")

                val mAlertDialog = mBuilder.show()



                mDialogView.btnRating.setOnClickListener {

                    mAlertDialog.dismiss()
                    val loading = ProgressDialog(this)
                    loading.setCancelable(false)
                    loading.setMessage("Mohon Menunggu...")
                    loading.show()

                    val konsumen =
                        Gson().fromJson(getIntent().getStringExtra("datarenov"), OrderKonsumen::class.java)

                    val id : String = konsumen.id
                    val id_konsumen: String = konsumen.id_konsumen.toString()
                    val id_mitra: String = konsumen.id_mitra.toString()
                    var komen: String = mDialogView.edtKomen.text.toString()
                    var rating = mDialogView.ratingBar.getRating().toString()


                    val stringRequest = object : StringRequest(
                        Request.Method.POST, URL_rate,
                        Response.Listener<String> { response ->

                            Log.e(TAG, "penyelesaian order Response: $response")

                            loading.dismiss()
                            Toast.makeText(
                                getApplicationContext(),
                                "order telah selesai",
                                Toast.LENGTH_SHORT
                            ).show()
                            onBackPressed()

                        },
                        object : Response.ErrorListener {
                            override fun onErrorResponse(volleyError: VolleyError) {
                                loading.dismiss()
                                Toast.makeText(
                                    applicationContext,
                                    volleyError.message,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        })
                    {
                        @Throws(AuthFailureError::class)
                        override fun getParams(): Map<String, String> {
                            val params = HashMap<String, String>()
                            params.put("id",id)
                            params.put("id_konsumen", id_konsumen)
                            params.put("id_mitra", id_mitra)
                            params.put("rating", rating)
                            params.put("komentar",komen)
                            return params
                        }
                    }

                    //adding request to queue
                    AppController.getInstance().addToRequestQueue(stringRequest)


                }
                mDialogView.btnCancel.setOnClickListener {
                    mAlertDialog.dismiss()
                }

                mAlertDialog.show()
            }

        }else if (orderkonsumenrenov.status.equals("selesai")){
            btn_ok.setText("Orderan Selesai")
            btn_ok.setBackgroundColor(Color.GRAY)
            btn_ok.isEnabled = false

        }


    }
}