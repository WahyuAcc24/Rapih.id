package com.Rapih.id.MitraBadanUsaha

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.Rapih.id.Adapter.HistoryAdapter
import com.Rapih.id.Adapter.HistoryAdapterCekAc
import com.Rapih.id.Adapter.ItemClickListener
import com.Rapih.id.Konsumen.DetailOrderRenovKonsumen
import com.Rapih.id.Konsumen.HomeKonsumenActivity
import com.Rapih.id.Konsumen.ListOrderAc.*
import com.Rapih.id.MitraAc.HomeMitraAcActivity
import com.Rapih.id.MitraAc.ListOrder.*
import com.Rapih.id.Model.OrderKonsumen
import com.Rapih.id.Model.OrderKonsumenCekAc
import com.Rapih.id.Model.OrderStatus
import com.Rapih.id.R
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.isfaaghyth.rak.Rak
import kotlinx.android.synthetic.main.bottom_sheet_freon.view.*
import kotlinx.android.synthetic.main.bottom_sheet_reload_freon.view.*
import kotlinx.android.synthetic.main.lay_pilihan_menu_ac.*
import kotlinx.android.synthetic.main.lay_renovrumah_konsumen.*
import java.lang.Exception
import java.lang.reflect.Type

class FragmentNavPesananMitraBu : Fragment(){

    private lateinit var lstHistori : RecyclerView

    var url : String? = null

    private lateinit var pglist : ProgressBar

    var order : List<OrderKonsumen>? = null
    private var adapter : HistoryAdapter? = null

    private var requesQueue : RequestQueue? = null

    private var gson : Gson? = null

    lateinit var swipeRefreshLayout: SwipeRefreshLayout




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pesanan_renov_mitra, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Rak.initialize(context)

        url = "http://rapih.id/api/listorderkonsumen.php?id_konsumen= " + Rak.grab("idkonsumen")

        Log.d("TAG", url)

        lstHistori = getView()?.findViewById(R.id.rvListOrderRenovmitra) as RecyclerView

        pglist = getView()?.findViewById(R.id.progressBarRenovmitra) as ProgressBar

        swipeRefreshLayout = view.findViewById(R.id.swipehistoryrenovmitra)

        requesQueue = Volley.newRequestQueue(context)

        var gsonBuilder : GsonBuilder = GsonBuilder()

        gson = gsonBuilder.create()

        lstHistori.setLayoutManager(LinearLayoutManager(context))

        order = ArrayList()

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
        fun newInstance(): FragmentNavPesananMitraBu{
            val fragment = FragmentNavPesananMitraBu()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    fun ambilListOrder() {

        val request = StringRequest(Request.Method.GET, url, onPostsLoaded, object : Response.ErrorListener {
            override fun onErrorResponse(error: VolleyError?) {

                var inetErr: AlertDialog.Builder = AlertDialog.Builder(context)
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
        override fun onResponse(response: String) {
            Log.e("TAG", response)
            var collectionType: Type = object : TypeToken<OrderStatus<OrderKonsumen>>() {}.type
            var order: OrderStatus<OrderKonsumen> =
                Gson().fromJson(response, collectionType) as OrderStatus<OrderKonsumen>

            if (order.isStatus) {
                try {
                    pglist = view?.findViewById(R.id.progressBarRenovmitra) as ProgressBar
                    pglist.setVisibility(View.GONE)

                    adapter = HistoryAdapter(order.dataKons)

                    adapter!!.setListener(object : ItemClickListener<OrderKonsumen> {
                        override fun onClicked(
                            OrderKonsumen: OrderKonsumen?,
                            position: Int,
                            view: View?
                        ) {
                            val intent = Intent(context, DetailOrderRenovMitra::class.java)
                            intent.putExtra("datarenov", Gson().toJson(OrderKonsumen))
                            startActivity(intent)

                        }
                    })
                    lstHistori.adapter = adapter
                } catch (ignored: Exception) {

                }
            } else {
                pglist.setVisibility(View.GONE)
                Toast.makeText(context, "Tidak Ada Data", Toast.LENGTH_SHORT).show()
            }
        }

    }
}