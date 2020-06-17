package com.Rapih.id.MitraAc

import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.Intent.getIntent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.Rapih.id.AppController
import com.Rapih.id.Konsumen.BottomNav.FragmentNavPesananAc
import com.Rapih.id.Model.MitraAc
import com.Rapih.id.Model.OrderKonsumenAc
import com.Rapih.id.R
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import io.isfaaghyth.rak.Rak
import kotlinx.android.synthetic.main.item_list_order_ac.*
import org.json.JSONException
import java.util.HashMap

class DetailOrderMitraAc : AppCompatActivity() {


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


    lateinit var img_back : ImageView
    lateinit var txt_email : TextView

    lateinit var rb : RatingBar
    lateinit var edt_komen : EditText

    lateinit var btn_ok : Button

    private val TAG = DetailOrderMitraAc::class.java!!.getSimpleName()

    val URL_konf = "http://rapih.id/api/updateordermitraac.php"
    val URL_kelar = "http://rapih.id/api/updatekonfordermitraac.php"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_detail_order_mitra_ac)


        txt_pa = findViewById(R.id.txtpadetailordermitraac)
        txt_bpac = findViewById(R.id.txtbpacdetailordermitraac)
        txt_cuciac = findViewById(R.id.txtcadetailordermitraac)
        txt_freon = findViewById(R.id.txtfreondetailordermitraac)
        txt_ja = findViewById(R.id.txtjadetailordermitraac)
        txt_lokasi = findViewById(R.id.txtlokasidetailordermitraac)
        txt_tgl = findViewById(R.id.txttgldetailordermitraac)
        txt_dp = findViewById(R.id.txtdpdetailordermitraac)
        txt_total = findViewById(R.id.txttotaldetailordermitraac)
        txt_email = findViewById(R.id.txtemaildetailordermitraac)
        img_back = findViewById(R.id.imgbackdetailordermitraac)
        btn_ok = findViewById(R.id.btnokdetailordermitraac)

        txt_nohp = findViewById(R.id.txtnohpdetailordermitraac)


        txt_email.setText(Rak.grab("emailmitraac") as String)

        img_back.setOnClickListener {
            onBackPressed()
        }


        val orderkonsumenac = Gson().fromJson(getIntent().getStringExtra("datamitraac"), OrderKonsumenAc::class.java)



            Log.e("tag", getIntent().getStringExtra("datamitraac"))

         txt_pa.setText("Perbaikan Ac : " + orderkonsumenac.getPerbaikan_ac())
         txt_bpac.setText("Bongkar Pasang Ac : " + orderkonsumenac.bongkar_pasang_ac)
         txt_cuciac.setText("Cuci Ac : " + orderkonsumenac.layanan_cuci_ac)
         txt_freon.setText("Freon : " + orderkonsumenac.freon)
         txt_ja.setText("Jumlah Ac : " + orderkonsumenac.jumlah_ac)
         txt_lokasi.setText("Lokasi Anda : " + orderkonsumenac.lokasi_proyek)
         txt_tgl.setText("tanggal : " + orderkonsumenac.tanggal_pengerjaan)
         txt_dp.setText("detail pekerjaan : " + orderkonsumenac.deskripsi_pekerjaan)
         txt_total.setText("Total Pembayaran : " + orderkonsumenac.total_pembayaran)
         txt_nohp.setText("No hp : " + orderkonsumenac.no_hp)


        if (orderkonsumenac.getStatus().equals("wait")){
            btn_ok.setText("Konfirmasi pesanan")
            btn_ok.setBackgroundColor(Color.GREEN)
            btn_ok.setTextColor(Color.WHITE)
            btn_ok.isEnabled = true
            btn_ok.setOnClickListener {

                val loading = ProgressDialog(this)
                loading.setCancelable(false)
                loading.setMessage("proses konfirmasi order...")
                loading.show()

                val id_mitra_ac : String = Rak.grab("idmitraac")
                val id : String = orderkonsumenac.id
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
                        params.put("id_mitra_ac", id_mitra_ac)
                        return params
                    }
                }

                //adding request to queue
                AppController.getInstance().addToRequestQueue(stringRequest)
            }



        } else if (orderkonsumenac.status.equals("konf")){
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

                        val loading = ProgressDialog(this@DetailOrderMitraAc)
                        loading.setCancelable(false)
                        loading.setMessage("proses penyelesaian order...")
                        loading.show()
                        val id : String = orderkonsumenac.id


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

        }else if (orderkonsumenac.status.equals("done")){
            btn_ok.setText("Menunggu Ulasan Konsumen")
            btn_ok.setBackgroundColor(Color.GRAY)
            btn_ok.isEnabled = false

        } else if (orderkonsumenac.status.equals("selesai")){
            btn_ok.setText("Order Ac telah selesai")
            btn_ok.setBackgroundColor(Color.GRAY)
            btn_ok.isEnabled = false
        }


    }

}