package com.Rapih.id.MitraAc.DetailOrder

import android.app.ProgressDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.Rapih.id.AppController
import com.Rapih.id.Model.OrderKonsumenAc
import com.Rapih.id.Model.OrderKonsumenCekAc
import com.Rapih.id.R
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import io.isfaaghyth.rak.Rak

class DetailOrderMitraCuciAc : AppCompatActivity() {


    lateinit var txt_order_ac : TextView
    lateinit var txt_order_ac2 : TextView
    lateinit var txt_jp : TextView
    lateinit var txt_ja : TextView
    lateinit var txt_ja2 : TextView
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

    private val TAG = DetailOrderMitraCuciAc::class.java.getSimpleName()

    val URL_konf = "http://rapih.id/api/cuci_ac/updateordercuciacmitra.php"
    val URL_kelar = "http://rapih.id/api/cuci_ac/konfordercuciacmitra.php"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_detail_order_cuci_ac_mitra)

        txt_jp = findViewById(R.id.txtdetailjenisproperticuciacmitra)
        txt_order_ac = findViewById(R.id.txtdetailorder1pkcuciacmitra)
        txt_order_ac2 = findViewById(R.id.txtdetailorder2pkcuciacmitra)
        txt_ja = findViewById(R.id.txtja1pkdetailordercuciacmitra)
        txt_ja2 = findViewById(R.id.txtja2pkdetailordercuciacmitra)
        txt_lokasi = findViewById(R.id.txtlokasidetailordercuciacmitra)
        txt_tgl = findViewById(R.id.txttgldetailordercuciacmitra)
        txt_dp = findViewById(R.id.txtdpdetailordercuciacmitra)
        txt_total = findViewById(R.id.txttotaldetailordercuciacmitra)
        txt_email = findViewById(R.id.txtemaildetailordercuciacmitra)
        img_back = findViewById(R.id.imgbackdetailordercuciacmitra)
        btn_ok = findViewById(R.id.btnokdetailordercuciacmitra)

        txt_nohp = findViewById(R.id.txtnohpdetailordercuciacmitra)


        txt_email.setText(Rak.grab("emailmitraac") as String)

        img_back.setOnClickListener {
            onBackPressed()
        }


        val orderkonsumenac = Gson().fromJson(getIntent().getStringExtra("datamitraac"), OrderKonsumenAc::class.java)



            Log.e("tag", getIntent().getStringExtra("datamitraac"))

        txt_order_ac.setText("Jenis Order : " + orderkonsumenac.order_1pk)
        txt_order_ac2.setText("Jenis Order : " + orderkonsumenac.order_2pk)

        txt_jp.setText("Jenis Properti : " + orderkonsumenac.jenis_properti)
        txt_ja.setText("Jumlah Ac : " + orderkonsumenac.jumlah_ac_1pk)
        txt_jp.setText("Jenis Properti : " + orderkonsumenac.jenis_properti)
        txt_lokasi.setText("Lokasi Anda : " + orderkonsumenac.lokasi)
        txt_tgl.setText("tanggal : " + orderkonsumenac.tanggal)
        txt_dp.setText("detail pekerjaan : " + orderkonsumenac.detail_pekerjaan)
        txt_total.setText("Total Pembayaran : " + orderkonsumenac.harga)

//
//
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

                        val loading = ProgressDialog(this@DetailOrderMitraCuciAc)
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