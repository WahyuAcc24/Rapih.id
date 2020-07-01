package com.Rapih.id.MitraAc.ListOrder

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.Rapih.id.Adapter.HistoryAdapterAc
import com.Rapih.id.Adapter.HistoryAdapterCekAc
import com.Rapih.id.Adapter.ItemClickListener
import com.Rapih.id.Konsumen.DetailOrderAC.DetailOrderCekAcKonsumen
import com.Rapih.id.Konsumen.DetailOrderAC.DetailOrderCuciAcKonsumen
import com.Rapih.id.MitraAc.DetailOrder.DetailOrderMitraCuciAc
import com.Rapih.id.MitraAc.DetailOrder.DetailOrderMitraLasAc
import com.Rapih.id.Model.OrderMitraAcStatus
import com.Rapih.id.Model.OrderKonsumenAc
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
import java.lang.reflect.Type

class ListOrderMitraLasAc :AppCompatActivity(){


    private lateinit var lstHistoriac : RecyclerView

    var urlac : String? = null

    private lateinit var pglistac : ProgressBar

    var orderac : List<OrderKonsumenAc>? = null
    private var adapter : HistoryAdapterAc? = null

    private var requesQueue : RequestQueue? = null

    private var gson : Gson? = null

    lateinit var swipeRefreshLayout: SwipeRefreshLayout



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_las_ac_mitra)

        Rak.initialize(this)

        urlac = "http://rapih.id/api/las_ac/listorderlasacmitra.php"
        Log.d("TAG", urlac)

        lstHistoriac = findViewById(R.id.rvListOrderLasAcMitra) as RecyclerView

        pglistac = findViewById(R.id.progressBarLasAcMitra) as ProgressBar

        swipeRefreshLayout = findViewById(R.id.swipeorderlasacmitra)

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

                var inetErr: AlertDialog.Builder = AlertDialog.Builder(this@ListOrderMitraLasAc)
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
            var collectionType: Type = object: TypeToken<OrderMitraAcStatus<OrderKonsumenAc>>(){}.type
            var order: OrderMitraAcStatus<OrderKonsumenAc>? = Gson().fromJson(response, collectionType) as? OrderMitraAcStatus<OrderKonsumenAc>

            if (order!!.isStatus){
                try {
                    pglistac = findViewById(R.id.progressBarLasAcMitra) as ProgressBar
                    pglistac.setVisibility(View.GONE)

                    adapter = HistoryAdapterAc(order.dataKonsMitraAc)

                    adapter!!.setListener(object: ItemClickListener<OrderKonsumenAc> {
                        override fun onClicked(OrderKonsumenAc: OrderKonsumenAc?, position: Int, view: View?) {

                            val intent = Intent(this@ListOrderMitraLasAc, DetailOrderMitraLasAc::class.java)
                            intent.putExtra("datamitraac",Gson().toJson(OrderKonsumenAc))
                            startActivity(intent)

                        }
                    })
                    lstHistoriac.adapter = adapter
                }catch (ignored : Exception){

                }
            }else{
                pglistac.setVisibility(View.GONE)
                Toast.makeText(applicationContext,"Tidak Ada Data", Toast.LENGTH_SHORT).show()
            }
        }
    }


}