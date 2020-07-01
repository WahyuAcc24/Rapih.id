package com.Rapih.id.MitraBadanUsaha

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import io.isfaaghyth.rak.Rak
import android.content.SharedPreferences
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.Rapih.id.Adapter.BannerAdapter
import com.Rapih.id.Model.*
import com.Rapih.id.R
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_homemitraac_bottom.*
import kotlin.collections.ArrayList


class FragmentNavHomeMitraBu : Fragment() {


    lateinit var txtemailmitrabu: TextView
    lateinit var txtnamamitrabu:TextView
    lateinit var txtrating:TextView

    lateinit var img_mitrabu : ImageView
    lateinit var img_rating : ImageView

    var emails: String? = null
    var names:String? = null
    var id:String? = null

    var TAG_EMAIL : String? = "email"
    var TAG_NAMA : String? = "nama"
//    var TAG_ID : String? = "id"

    var sharedpreferences: SharedPreferences? = null

    private lateinit var lstBanner : RecyclerView
    var adapterbaner : BannerAdapter? = null
    var data : List<Banner>? = null


    var urlrate : String? = null
    private var requesQueue : RequestQueue? = null

    private var contexts: Context? = null

    lateinit var txt_rating : TextView

    var rate : List<OrderRateAc>? = null








    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(com.Rapih.id.R.layout.fragment_homemitrabu_bottom, container, false)
//        bn_main.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)


        Rak.initialize(context)

//        var bundle: Bundle? = this.arguments


        urlrate = "http://rapih.id/api/ratingmitrabu.php?id_mitra=" + Rak.grab("idmitrabu")
        requesQueue = Volley.newRequestQueue(context)




        lstBanner = getView()?.findViewById(R.id.rvbanerbawahmitrabu) as RecyclerView
        lstBanner.setHasFixedSize(true)
        var data: ArrayList<Banner> = ArrayList()

        data.add(Banner(R.drawable.bannerbawah))
        data.add(Banner(R.drawable.bannerbawahdua))
        data.add(Banner(R.drawable.bannerbawahtiga))
        data.add(Banner(R.drawable.bannerbawahempat))
        data.add(Banner(R.drawable.bannerbawahlima))
        data.add(Banner(R.drawable.bannerbawahenam))

        var lm: LinearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        lstBanner.setLayoutManager(lm)
        adapterbaner = BannerAdapter(activity, data)
        lstBanner.adapter = adapterbaner


//        var sharedPreferences  = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)

//        emails = activity?.intent?.getStringExtra(TAG_EMAIL)
//        names = activity?.intent?.getStringExtra(TAG_NAMA)

//        id = activity?.intent?.getStringExtra(TAG_ID)


        txtemailmitrabu = getView()?.findViewById(R.id.txtemailmitrabu) as TextView
        txtemailmitrabu.setText(Rak.grab("emailmitrabu") as? String)
//        txtemailkonsumen.setText(arguments?.getString("emailkons"))
//        txtemailkonsumen.setText(sharedPreferences?.getString(TAG_EMAIL,emails))
//        txtemailkonsumen.setText(Preferences.getLoggedInEmail(context))

        txtnamamitrabu = getView()?.findViewById(R.id.txtnamamitrabu) as TextView
//        txtnamakonsumen.setText(arguments?.getString("namakons"))
        txtnamamitrabu.setText(Rak.grab("namabu")as? String)
//        txtnamakonsumen.setText(Preferences.getLoggedInNama(context))
//        txtnamakonsumen.setText(id)
        txt_rating = view.findViewById(R.id.txtratingmitrabu)


        img_mitrabu = view.findViewById(R.id.imguserhome) as ImageView


        img_rating = view.findViewById(R.id.imgrating)


        val request = StringRequest(Request.Method.GET, urlrate, onPostsLoaded, object : Response.ErrorListener {
            override fun onErrorResponse(error: VolleyError?) {

                var inetErr: AlertDialog.Builder = AlertDialog.Builder(contexts!!)
                inetErr.setTitle("Terjadi Kesalahan")
                inetErr.setMessage("Periksa Kembali Koneksi Internet Anda")
                inetErr.setNegativeButton("Muat Ulang",object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {

                    }

                })
                inetErr.show()
            }
        })
        requesQueue?.add(request)
    }

    val onPostsLoaded = object: Response.Listener<String> {
        override fun onResponse(response: String) {

            val ratemitraa:OrderRateAc = Gson().fromJson(response, OrderRateAc::class.java)
            txt_rating.setText(ratemitraa.rating)


        }
    }


        companion object {
        fun newInstance(): FragmentNavHomeMitraBu {
            val fragment = FragmentNavHomeMitraBu()
            val args = Bundle()
            fragment.arguments = args

            return fragment
        }
    }



}

