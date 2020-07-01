package com.Rapih.id.MitraBadanUsaha

import android.app.ProgressDialog
import android.content.DialogInterface
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

class DetailOrderRenovMitra : AppCompatActivity() {


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


    private val TAG = DetailOrderRenovMitra::class.java.getSimpleName()

    val URL_konf = "http://rapih.id/api/updateordermitrabu.php"
    val URL_kelar = "http://rapih.id/api/updatekonfordermitrabu.php"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_detail_order_renov_mitra)


        txt_pa = findViewById(R.id.txtpadetailorderrenovmitra)
        txt_bpac = findViewById(R.id.txtbpacdetailorderrenovmitra)
        txt_cuciac = findViewById(R.id.txtcadetailorderrenovmitra)
        txt_freon = findViewById(R.id.txtfreondetailorderrenovmitra)
        txt_ja = findViewById(R.id.txtjadetailorderrenovmitra)
        txt_lokasi = findViewById(R.id.txtlokasidetailorderrenovmitra)
        txt_tgl = findViewById(R.id.txttgldetailorderrenovmitra)
        txt_dp = findViewById(R.id.txtdpdetailorderrenovmitra)
        txt_email = findViewById(R.id.txtemaildetailorderrenovmitra)
        img_back = findViewById(R.id.imgbackdetailorderrenovmitra)
        btn_ok = findViewById(R.id.btnokdetailorderrenovmitra)
//        rb = findViewById(R.id.ratingBar)
//        edt_komen = findViewById(R.id.edtKomen)


        txt_nohp = findViewById(R.id.txtnohpdetailorderrenovmitra)


        txt_email.setText(Rak.grab("emailmitrabu") as String)

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

        if (orderkonsumenrenov.getStatus().equals("wait")){
            btn_ok.setText("Konfirmasi pesanan")
            btn_ok.setBackgroundColor(Color.GREEN)
            btn_ok.setTextColor(Color.WHITE)
            btn_ok.isEnabled = true
            btn_ok.setOnClickListener {

                val loading = ProgressDialog(this)
                loading.setCancelable(false)
                loading.setMessage("proses konfirmasi order...")
                loading.show()

                val id_mitra : String = Rak.grab("idmitrabu")
                val id : String = orderkonsumenrenov.id
                val stringRequest = object : StringRequest(
                    Request.Method.POST, URL_konf,
                    Response.Listener<String> { response ->

                        Log.e(TAG, "Konfirmasi order Response: $response")

                        loading.dismiss()
                        Toast.makeText(getApplicationContext(), "konfirmasi berhasil", Toast.LENGTH_SHORT).show()
                        onBackPressed()

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
                        params.put("id",id)
                        params.put("id_mitra", id_mitra)
                        return params
                    }
                }

                //adding request to queue
                AppController.getInstance().addToRequestQueue(stringRequest)
            }



        } else if (orderkonsumenrenov.status.equals("konf")){
            btn_ok.setText("Selesai Bekerja")
            btn_ok.setBackgroundColor(Color.RED)
            btn_ok.setTextColor(Color.WHITE)
            btn_ok.isEnabled = true
            btn_ok.setOnClickListener {

                AlertDialog.Builder(this)
                    .setTitle("Rapih Ac")
                    .setMessage("Apakah Pekerjaan Anda Sudah Selesai?.")
                    .setPositiveButton("iya", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {

                            val loading = ProgressDialog(this@DetailOrderRenovMitra)
                            loading.setCancelable(false)
                            loading.setMessage("proses penyelesaian order...")
                            loading.show()
                            val id : String = orderkonsumenrenov.id


                            val stringRequest = object : StringRequest(
                                Request.Method.POST, URL_kelar,
                                Response.Listener<String> { response ->

                                    Log.e(TAG, "penyelesaian order Response: $response")

                                    loading.dismiss()
                                    Toast.makeText(getApplicationContext(), "order telah selesai", Toast.LENGTH_SHORT).show()
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
                                }) {
                                @Throws(AuthFailureError::class)
                                override fun getParams(): Map<String, String> {
                                    val params = HashMap<String, String>()
                                    params.put("id", id)
                                    return params
                                }
                            }

                            //adding request to queue
                            AppController.getInstance().addToRequestQueue(stringRequest)

                        }

                    })
                    .setNegativeButton("tidak", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            onBackPressed()
                        }
                    }) .show()
            }

        }else if (orderkonsumenrenov.status.equals("done")){
            btn_ok.setText("Menunggu Ulasan Konsumen")
            btn_ok.setBackgroundColor(Color.GRAY)
            btn_ok.isEnabled = false

        } else if (orderkonsumenrenov.status.equals("selesai")){
            btn_ok.setText("Order Ac telah selesai")
            btn_ok.setBackgroundColor(Color.GRAY)
            btn_ok.isEnabled = false
        }



    }
}