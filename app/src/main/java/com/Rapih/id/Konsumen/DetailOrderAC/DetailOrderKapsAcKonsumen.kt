package com.Rapih.id.Konsumen.DetailOrderAC

import android.app.ProgressDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
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
import kotlinx.android.synthetic.main.rating.view.*
import java.util.HashMap

class DetailOrderCuciAcKonsumen : AppCompatActivity() {


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

//    lateinit var rb : RatingBar
//    lateinit var edt_komen : EditText

    lateinit var btn_ok : Button

    private val TAG = DetailOrderCuciAcKonsumen::class.java.getSimpleName()

    val URL_rate = "http://rapih.id/api/cuci_ac/updateordercuciackonsumen.php"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_detail_order_cuci_ac_konsumen)


        txt_jp = findViewById(R.id.txtdetailjenisproperticuciac)
        txt_order_ac = findViewById(R.id.txtdetailorder1pkcuciac)
        txt_order_ac2 = findViewById(R.id.txtdetailorder2pkcuciac)

        txt_ja = findViewById(R.id.txtja1pkdetailordercuciac)
        txt_ja2 = findViewById(R.id.txtja2pkdetailordercuciac)
        txt_lokasi = findViewById(R.id.txtlokasidetailorderac)
        txt_tgl = findViewById(R.id.txttgldetailordercuciac)
        txt_dp = findViewById(R.id.txtdpdetailordercuciac)
        txt_total = findViewById(R.id.txttotaldetailordercuciac)
        txt_email = findViewById(R.id.txtemaildetailordercuciac)
        img_back = findViewById(R.id.imgbackdetailordercuciac)
        btn_ok = findViewById(R.id.btnokdetailordercuciac)
//        rb = findViewById(R.id.ratingBar)
//        edt_komen = findViewById(R.id.edtKomen)

        txt_nohp = findViewById(R.id.txtnohpdetailordercuciac)



        txt_email.setText(Rak.grab("emailkonsumen") as? String)
        txt_nohp.setText("No Hp : " +Rak.grab("hpkonsumen") as? String)

        img_back.setOnClickListener {
            onBackPressed()
        }


        val orderkonsumenac = Gson().fromJson(getIntent().getStringExtra("data"), OrderKonsumenAc::class.java)



        Log.e("tag", getIntent().getStringExtra("data"))

         txt_order_ac.setText("Jenis Order : " + orderkonsumenac.order_1pk)
         txt_order_ac2.setText("Jenis Order : " + orderkonsumenac.order_2pk)

         txt_jp.setText("Jenis Properti : " + orderkonsumenac.jenis_properti)
         txt_ja.setText("Jumlah Ac : " + orderkonsumenac.jumlah_ac_1pk)
         txt_ja2.setText("Jumlah Ac : " + orderkonsumenac.jumlah_ac_2pk)
         txt_lokasi.setText("Lokasi Anda : " + orderkonsumenac.lokasi)
         txt_tgl.setText("tanggal : " + orderkonsumenac.tanggal)
         txt_dp.setText("detail pekerjaan : " + orderkonsumenac.deskripsi_pekerjaan)
         txt_total.setText("Total Pembayaran : " + orderkonsumenac.harga)


        if (orderkonsumenac.getStatus().equals("wait")) {
            btn_ok.setText("Menunggu Konfirmasi Teknisi")
            btn_ok.setBackgroundColor(Color.GRAY)
            btn_ok.setTextColor(Color.WHITE)
            btn_ok.isEnabled = false

        }else if (orderkonsumenac.getStatus().equals("konf")){
            btn_ok.setText("Teknisi akan menghubungimu")
            btn_ok.setBackgroundColor(Color.RED)
            btn_ok.setTextColor(Color.WHITE)
            btn_ok.isEnabled = false


        } else if (orderkonsumenac.status.equals("done")){
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

                    val konsumenac =
                        Gson().fromJson(getIntent().getStringExtra("data"), OrderKonsumenCekAc::class.java)

                    val id : String = konsumenac.id
                    val id_konsumen_ac: String = konsumenac.id_konsumen_ac.toString()
                    val id_mitra_ac: String = konsumenac.id_mitra_ac.toString()
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
                            params.put("id_konsumen_ac", id_konsumen_ac)
                            params.put("id_mitra_ac", id_mitra_ac)
                            params.put("rating", rating)
                            params.put("komen",komen)
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

        }else if (orderkonsumenac.status.equals("selesai")){
            btn_ok.setText("Orderan Selesai")
            btn_ok.setBackgroundColor(Color.GRAY)
            btn_ok.isEnabled = false

        }


    }
}