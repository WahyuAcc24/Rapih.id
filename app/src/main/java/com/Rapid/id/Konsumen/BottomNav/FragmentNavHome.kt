package com.Rapid.id.Konsumen.BottomNav

import android.content.Intent
import android.content.Intent.getIntent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.Rapid.id.ImageSlider.SliderView
import com.Rapid.id.Konsumen.LoginKonsumenActivity
import com.Rapid.id.Konsumen.RenovKonsumenActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener
import io.isfaaghyth.rak.Rak
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home_bottom.*
import kotlinx.android.synthetic.main.lay_jumbotron.*
import android.content.SharedPreferences
import android.R
import android.content.Context
import com.Rapid.id.util.Preferences


class FragmentNavHome : Fragment() {


    lateinit var txtemailkonsumen: TextView
    lateinit var txtnamakonsumen:TextView
    lateinit var txtpesan:TextView

    lateinit var img_renov : ImageView
    lateinit var img_bangunrumah : ImageView
    lateinit var img_ac : ImageView
    lateinit var img_clean : ImageView

    var emails: String? = null
    var names:String? = null
    var id:String? = null

    var TAG_EMAIL : String? = "email"
    var TAG_NAMA : String? = "nama"
//    var TAG_ID : String? = "id"

    var sharedpreferences: SharedPreferences? = null


    lateinit var carouselView: CarouselView
    internal var gambarSlide = intArrayOf(com.Rapid.id.R.drawable.banersatu, com.Rapid.id.R.drawable.banerdua)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(com.Rapid.id.R.layout.fragment_home_bottom, container, false)
//        bn_main.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)

        carouselView = getView()?.findViewById(com.Rapid.id.R.id.carouselView) as CarouselView
        carouselView.setImageListener(imageListener)
        carouselView.pageCount = gambarSlide.size


        Rak.initialize(context)

//        var bundle: Bundle? = this.arguments



        var sharedPreferences  = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)

        emails = activity?.intent?.getStringExtra(TAG_EMAIL)
        names = activity?.intent?.getStringExtra(TAG_NAMA)

//        id = activity?.intent?.getStringExtra(TAG_ID)


//        txtemailkonsumen.setText(Rak.grab("emailkonsumen") as? String)
        txtemailkonsumen = getView()?.findViewById(com.Rapid.id.R.id.txtemailkonsumen) as TextView
//        txtemailkonsumen.setText(arguments?.getString("emailkons"))
//        txtemailkonsumen.setText(sharedPreferences?.getString(TAG_EMAIL,emails))
        txtemailkonsumen.setText(Preferences.getLoggedInEmail(context))

        txtnamakonsumen = getView()?.findViewById(com.Rapid.id.R.id.txtnamakonsumen) as TextView
//        txtnamakonsumen.setText(arguments?.getString("namakons"))
//        txtnamakonsumen.setText(Rak.grab("namakonsumen")as? String)
        txtnamakonsumen.setText(Preferences.getLoggedInNama(context))
//        txtnamakonsumen.setText(id)


        img_bangunrumah = getView()?.findViewById(com.Rapid.id.R.id.imgrenov) as ImageView

        img_bangunrumah.setOnClickListener {
            val intentf = Intent(context,RenovKonsumenActivity::class.java)
            context?.startActivity(intentf)

        }




    }


    companion object {
        fun newInstance(): FragmentNavHome {
            val fragment = FragmentNavHome()
            val args = Bundle()
            fragment.arguments = args

            return fragment
        }
    }


    internal var imageListener: ImageListener =
        ImageListener { position, imageView ->
            imageView.setImageResource(gambarSlide[position])
        }

}

