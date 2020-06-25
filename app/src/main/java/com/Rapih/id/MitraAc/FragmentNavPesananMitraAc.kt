package com.Rapih.id.MitraAc

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
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
import com.Rapih.id.Model.*
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

class FragmentNavPesananMitraAc : Fragment(){


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
        return inflater.inflate(R.layout.fragment_pesanan_mitra_ac, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Rak.initialize(context)

        urlac = "http://rapih.id/api/listordermitraac.php"

        Log.d("TAG", urlac)

        lstHistoriac = getView()?.findViewById(R.id.rvListOrderMitraAc) as RecyclerView

        pglistac = getView()?.findViewById(R.id.progressBarMitraAc) as ProgressBar

        swipeRefreshLayout = view.findViewById(R.id.swipeordermitraac)
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
        fun newInstance(): FragmentNavPesananMitraAc{
            val fragment = FragmentNavPesananMitraAc()
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

            var collectionType: Type = object:TypeToken<OrderMitraAcStatus<OrderKonsumenAc>>(){}.type
            var order : OrderMitraAcStatus<OrderKonsumenAc>? = Gson().fromJson(response, collectionType) as? OrderMitraAcStatus<OrderKonsumenAc>

            if (order!!.isStatus){
                try {
                    pglistac = view?.findViewById(R.id.progressBarMitraAc) as ProgressBar
                    pglistac.setVisibility(View.GONE)

                    adapter = HistoryAdapterAc(order.dataKonsMitraAc)

                    adapter!!.setListener(object: ItemClickListener<OrderKonsumenAc> {
                        override fun onClicked(OrderKonsumenAc: OrderKonsumenAc?, position: Int, view: View?) {

                            val intent = Intent(context, DetailOrderMitraAc::class.java)
                            intent.putExtra("datamitraac",Gson().toJson(OrderKonsumenAc))
                            startActivity(intent)

                        }
                    })
                    lstHistoriac.adapter = adapter
                }catch (ignored : Exception){}
            }else{
                pglistac.setVisibility(View.GONE)
                Toast.makeText(context,"Tidak Ada Data", Toast.LENGTH_SHORT).show()
            }
        }
    }

}