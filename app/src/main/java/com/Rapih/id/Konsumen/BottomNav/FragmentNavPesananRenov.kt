package com.Rapih.id.Konsumen.BottomNav

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
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
import com.Rapih.id.Adapter.HistoryAdapter
import com.Rapih.id.Adapter.ItemClickListener
import com.Rapih.id.Konsumen.DetailOrderRenovKonsumen
import com.Rapih.id.Model.OrderKonsumen
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

class FragmentNavPesananRenov : Fragment(){


    lateinit var lnHistory : LinearLayout

    private lateinit var lstHistori : RecyclerView

    var url : String? = null

    private lateinit var pglist : ProgressBar

    var order : List<OrderKonsumen>? = null
    private var adapter : HistoryAdapter? = null

    private var requesQueue : RequestQueue? = null

    private var gson : Gson? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pesanan_renov_bottom, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Rak.initialize(context)

        url = "http://rapih.id/api/listorderkonsumen.php?id_konsumen= " + Rak.grab("idkonsumen")

        Log.d("TAG", url)

        lstHistori = getView()?.findViewById(R.id.rvListOrder) as RecyclerView

        pglist = getView()?.findViewById(R.id.progressBar) as ProgressBar

        requesQueue = Volley.newRequestQueue(context)

        var gsonBuilder : GsonBuilder = GsonBuilder()

        gson = gsonBuilder.create()

        lstHistori.setLayoutManager(LinearLayoutManager(context))

        order = ArrayList()

        ambilListOrder()

    }
        companion object {
        fun newInstance(): FragmentNavPesananRenov{
            val fragment = FragmentNavPesananRenov()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }


    fun ambilListOrder() {

        val request = StringRequest(Request.Method.GET, url, onPostsLoaded, object : Response.ErrorListener {
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

            var collectionType: Type = object:TypeToken<OrderStatus<OrderKonsumen>>(){}.type
            var order:OrderStatus<OrderKonsumen> = Gson().fromJson(response, collectionType)

            if (order.isStatus){
                try {
                    pglist = view?.findViewById(R.id.progressBar) as ProgressBar
                    pglist.setVisibility(View.GONE)

                    adapter = HistoryAdapter(order.dataKons)
                    lstHistori.adapter = adapter
                    adapter!!.setListener(object: ItemClickListener<OrderKonsumen> {
                        override fun onClicked(OrderKonsumen: OrderKonsumen?, position: Int, view: View?) {
                            val intent = Intent(context, DetailOrderRenovKonsumen::class.java)
                            intent.putExtra("datarenov",Gson().toJson(OrderKonsumen))
                            startActivity(intent)

                        }
                    })
                }catch (ignored : Exception){}
            }else{
                pglist.setVisibility(View.GONE)
                Toast.makeText(context,"Tidak Ada Data", Toast.LENGTH_SHORT).show()
            }
        }
    }

}