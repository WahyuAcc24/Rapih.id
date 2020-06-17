package com.Rapih.id.MitraAc

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.Rapih.id.Konsumen.RenovKonsumenActivity
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener
import io.isfaaghyth.rak.Rak
import android.content.SharedPreferences
import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.Rapih.id.Adapter.BannerAdapter
import com.Rapih.id.Konsumen.AcKonsumenActivity
import com.Rapih.id.Model.Banner
import com.Rapih.id.R
import kotlinx.android.synthetic.main.fragment_home_bottom.*


class FragmentNavHomeMitraAc : Fragment() {


    lateinit var txtemailmitraac: TextView
    lateinit var txtnamamitraac:TextView
    lateinit var txtrating:TextView

    lateinit var img_mitraac : ImageView
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






    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(com.Rapih.id.R.layout.fragment_homemitraac_bottom, container, false)
//        bn_main.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)


        Rak.initialize(context)

//        var bundle: Bundle? = this.arguments


        lstBanner =  getView()?.findViewById(R.id.rvbanerbawahmitraac) as RecyclerView
        lstBanner.setHasFixedSize(true)
        var data : ArrayList<Banner> = ArrayList()

        data.add(Banner(R.drawable.bannerbawah))
        data.add(Banner(R.drawable.bannerbawahdua))
        data.add(Banner(R.drawable.bannerbawahtiga))
        data.add(Banner(R.drawable.bannerbawahempat))
        data.add(Banner(R.drawable.bannerbawahlima))
        data.add(Banner(R.drawable.bannerbawahenam))

        var lm : LinearLayoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
        lstBanner.setLayoutManager(lm)
        adapterbaner = BannerAdapter(activity,data)
        lstBanner.adapter = adapterbaner



//        var sharedPreferences  = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)

//        emails = activity?.intent?.getStringExtra(TAG_EMAIL)
//        names = activity?.intent?.getStringExtra(TAG_NAMA)

//        id = activity?.intent?.getStringExtra(TAG_ID)


        txtemailmitraac = getView()?.findViewById(R.id.txtemailmitraac) as TextView
        txtemailmitraac.setText(Rak.grab("emailmitraac") as? String)
//        txtemailkonsumen.setText(arguments?.getString("emailkons"))
//        txtemailkonsumen.setText(sharedPreferences?.getString(TAG_EMAIL,emails))
//        txtemailkonsumen.setText(Preferences.getLoggedInEmail(context))

        txtnamamitraac = getView()?.findViewById(R.id.txtnamamitraac) as TextView
//        txtnamakonsumen.setText(arguments?.getString("namakons"))
        txtnamamitraac.setText(Rak.grab("namamitraac")as? String)
//        txtnamakonsumen.setText(Preferences.getLoggedInNama(context))
//        txtnamakonsumen.setText(id)


        img_mitraac = view.findViewById(R.id.imguserhome) as ImageView


        img_rating = view.findViewById(R.id.imgrating)




    }


    companion object {
        fun newInstance(): FragmentNavHomeMitraAc {
            val fragment = FragmentNavHomeMitraAc()
            val args = Bundle()
            fragment.arguments = args

            return fragment
        }
    }



}

