package com.Rapih.id.Konsumen.BottomNav

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.Rapih.id.Adapter.HistoryAdapter
import com.Rapih.id.Adapter.HistoryAdapterAc
import com.Rapih.id.Adapter.ItemClickListener
import com.Rapih.id.Konsumen.DetailOrderAcKonsumen
import com.Rapih.id.Model.OrderAcStatus
import com.Rapih.id.Model.OrderKonsumen
import com.Rapih.id.Model.OrderKonsumenAc
import com.Rapih.id.Model.OrderStatus
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
import java.util.*
import kotlin.collections.ArrayList

class FragmentNavPesananAc : Fragment(){


    lateinit var lnHistory : LinearLayout

    private lateinit var lstHistoriac : RecyclerView

    var urlac : String? = null

    private lateinit var pglistac : ProgressBar

    var orderac : List<OrderKonsumenAc>? = null
    private var adapter : HistoryAdapterAc? = null

    private var requesQueue : RequestQueue? = null

    private var gson : Gson? = null

    lateinit var swipeRefreshLayout: SwipeRefreshLayout


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pesanan_ac_bottom, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Rak.initialize(context)

        urlac = "http://rapih.id/api/listorderackonsumen.php?id_konsumen_ac= " + Rak.grab("idkonsumen")

        Log.d("TAG", urlac)

        lstHistoriac = getView()?.findViewById(R.id.rvListOrderAc) as RecyclerView

        pglistac = getView()?.findViewById(R.id.progressBarAc) as ProgressBar

        swipeRefreshLayout = view.findViewById(R.id.swipeorderac)

        requesQueue = Volley.newRequestQueue(context)

        var gsonBuilder : GsonBuilder = GsonBuilder()

        gson = gsonBuilder.create()

        lstHistoriac.setLayoutManager(LinearLayoutManager(context))

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
        companion object {
        fun newInstance(): FragmentNavPesananAc{
            val fragment = FragmentNavPesananAc()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }


    fun ambilListOrder() {

        val request = StringRequest(Request.Method.GET, urlac, onPostsLoaded, object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError?) {

                   var inetErr:AlertDialog.Builder = AlertDialog.Builder(context)
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
            var collectionType: Type = object:TypeToken<OrderAcStatus<OrderKonsumenAc>>(){}.type
            var order:OrderAcStatus<OrderKonsumenAc>? = Gson().fromJson(response, collectionType) as? OrderAcStatus<OrderKonsumenAc>

            if (order!!.isStatus){
                try {
                    pglistac = view?.findViewById(R.id.progressBarAc) as ProgressBar
                    pglistac.setVisibility(View.GONE)

                    adapter = HistoryAdapterAc(order.dataKonsAc)

                    adapter!!.setListener(object: ItemClickListener<OrderKonsumenAc> {
                        override fun onClicked(OrderKonsumenAc: OrderKonsumenAc?, position: Int, view: View?) {

                            val intent = Intent(context, DetailOrderAcKonsumen::class.java)
                            intent.putExtra("data",Gson().toJson(OrderKonsumenAc))
                            startActivity(intent)

                        }
                    })
                    lstHistoriac.adapter = adapter
                }catch (ignored : Exception){

                }
            }else{
                pglistac.setVisibility(View.GONE)
                Toast.makeText(context,"Tidak Ada Data", Toast.LENGTH_SHORT).show()
            }
        }
    }

}