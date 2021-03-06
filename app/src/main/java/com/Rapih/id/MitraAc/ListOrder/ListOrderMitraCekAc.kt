package com.Rapih.id.MitraAc.ListOrder

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.Rapih.id.Adapter.HistoryAdapterCekAc
import com.Rapih.id.Adapter.ItemClickListener
import com.Rapih.id.MitraAc.DetailOrder.DetailOrderMitraCekAc
import com.Rapih.id.Model.OrderMitraAcStatus
import com.Rapih.id.Model.OrderKonsumenCekAc
import com.Rapih.id.R
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.isfaaghyth.rak.Rak
import java.lang.Exception
import java.lang.reflect.Type

class ListOrderMitraCekAc :AppCompatActivity(){


    private lateinit var lstHistoriac : RecyclerView

    var urlac : String? = null

    private lateinit var pglistac : ProgressBar

    var orderac : List<OrderKonsumenCekAc>? = null
    private var adapterCek : HistoryAdapterCekAc? = null

    private var requesQueue : RequestQueue? = null

    private var gson : Gson? = null

    lateinit var swipeRefreshLayout: SwipeRefreshLayout



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_cek_ac)

        Rak.initialize(this)

        urlac = "http://rapih.id/api/cek_ac/listordermitracekac.php"

        Log.d("TAG", urlac)

        lstHistoriac = findViewById(R.id.rvListOrderAc) as RecyclerView

        pglistac = findViewById(R.id.progressBarAc) as ProgressBar

        swipeRefreshLayout = findViewById(R.id.swipeorderac)

        requesQueue = Volley.newRequestQueue(this)

        var gsonBuilder : GsonBuilder = GsonBuilder()

        gson = gsonBuilder.create()

        lstHistoriac.setLayoutManager(LinearLayoutManager(this))

        orderac = ArrayList()

        ambilListOrder()
        swipeRefreshLayout.setColorSchemeResources(R.color.birulain, R.color.Hijau, R.color.merah)


        swipeRefreshLayout.setOnRefreshListener(object:SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                // Handler untuk menjalankan jeda selama 5 detik
                Handler().postDelayed(object:Runnable {
                    override fun run() {
                        // Berhenti berputar/refreshing
                        swipeRefreshLayout.setRefreshing(false)
                        ambilListOrder()
                        // fungsi-fungsi lain yang dijalankan saat refresh berhenti

                    }
                }, 5000)
            }
        })

    }
    fun ambilListOrder() {

        val request = StringRequest(Request.Method.GET, urlac, onPostsLoaded, object : Response.ErrorListener {
            override fun onErrorResponse(error: VolleyError?) {

                var inetErr: AlertDialog.Builder = AlertDialog.Builder(this@ListOrderMitraCekAc)
                inetErr.setTitle("Terjadi Kesalahan")
                inetErr.setMessage("Periksa Kembali Koneksi Internet Anda")
                inetErr.setNegativeButton("Muat Ulang",object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        ambilListOrder()

                    }

                })
                inetErr.show()
            }
        })
        requesQueue?.add(request)
    }

    val onPostsLoaded = object: Response.Listener<String> {
        override fun onResponse(response:String) {
            Log.e("TAG", response)
            var collectionType: Type = object: TypeToken<OrderMitraAcStatus<OrderKonsumenCekAc>>(){}.type
            var orderCek: OrderMitraAcStatus<OrderKonsumenCekAc>? = Gson().fromJson(response, collectionType) as? OrderMitraAcStatus<OrderKonsumenCekAc>

            if (orderCek!!.isStatus){
                try {
                    pglistac = findViewById(R.id.progressBarAc) as ProgressBar
                    pglistac.setVisibility(View.GONE)

                    adapterCek = HistoryAdapterCekAc(orderCek.dataKonsMitraAc)

                    adapterCek!!.setListener(object: ItemClickListener<OrderKonsumenCekAc> {
                        override fun onClicked(OrderKonsumenCekAc: OrderKonsumenCekAc?, position: Int, view: View?) {

                            val intent = Intent(this@ListOrderMitraCekAc, DetailOrderMitraCekAc::class.java)
                            intent.putExtra("datamitraac",Gson().toJson(OrderKonsumenCekAc))
                            startActivity(intent)

                        }
                    })
                    lstHistoriac.adapter = adapterCek
                }catch (ignored : Exception){

                }
            }else{
                pglistac.setVisibility(View.GONE)
                Toast.makeText(applicationContext,"Tidak Ada Data", Toast.LENGTH_SHORT).show()
            }
        }
    }


}