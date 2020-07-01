package com.Rapih.id.MitraBadanUsaha

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.navigation.ui.AppBarConfiguration
import androidx.appcompat.app.AppCompatActivity
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.Rapih.id.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.isfaaghyth.rak.Rak
import androidx.fragment.app.FragmentManager
import com.Rapih.id.Konsumen.BottomNav.*
import com.Rapih.id.MitraAc.FragmentNavBantuanMitraAc
import com.Rapih.id.MitraAc.FragmentNavHomeMitraAc
import com.Rapih.id.MitraAc.FragmentNavPesananMitraAc
import com.Rapih.id.MitraAc.LoginMitraAcActivity
import com.Rapih.id.util.Preferences
import kotlinx.android.synthetic.main.activity_home_mitra_ac.*
import kotlinx.android.synthetic.main.activity_home_mitra_bu.*


class HomeMitraBuActivity : AppCompatActivity() {

    private var content: FrameLayout? = null

    private lateinit var appBarConfiguration: AppBarConfiguration

    var emails: String? = null
    var names:String? = null
    var id:String? = null

    var TAG_EMAIL : String? = "email"
    var TAG_NAMA : String? = "nama"
    var TAG_ID : String? = "id"


    var sharedPreferences : SharedPreferences? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_mitra_bu)




        bn_main_mitra_bu.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val fragmentmitrabu = FragmentNavHomeMitraBu.newInstance()
        addFragment(fragmentmitrabu)




    }




    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

            when (item.itemId) {
            R.id.home_menu_mitra_bu -> {
//                findNavController(R.id.nav_host_fragment).navigate(R.id.nav_home)
                val fragment = FragmentNavHomeMitraBu()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true

            }
            R.id.bantuan_menu_mitra_bu -> {
//                findNavController(R.id.nav_host_fragment).navigate(R.id.bantuan_bot)
                val fragment = FragmentNavBantuanMitraBu()
                addFragment(fragment)

//                showBottomSheet()
                return@OnNavigationItemSelectedListener true

            }
            R.id.pesanan_menu_mitra_bu -> {
//                findNavController(R.id.nav_host_fragment).navigate(R.id.pesanan_bot)
//                showBottomSheet()
                val fragment = FragmentNavPesananMitraBu()
                addFragment(fragment)


                return@OnNavigationItemSelectedListener true

            }
            R.id.exit_menu_mitra_bu -> {
                replaceFragment()
                Preferences.clearLoggedInNama(baseContext)
                Preferences.clearLoggedInEmail(baseContext)
                Preferences.clearLoggedInId(baseContext)
                val i = Intent(this, LoginMitraBuActivity::class.java)
                Preferences.clearLoggedInNama(baseContext)
                Preferences.clearLoggedInEmail(baseContext)
                Preferences.clearLoggedInId(baseContext)
                Preferences.setLoggedInStatus(baseContext,false)
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                Preferences.setLoggedInStatus(baseContext,false)
                startActivity(i)
                Rak.entry("loginmitrabu", false)
                Rak.removeAll(baseContext)
                finishAffinity()
            }


        }
        false
    }

    private fun addFragment(fragment: Fragment) {


//        val json = ""
//        val res = Gson().fromJson<Konsumen>(json,Konsumen::class.java)

//        var sharedPreferences  = getSharedPreferences("pref", Context.MODE_PRIVATE)

//        id = getIntent().getStringExtra(TAG_ID)


        Preferences.getLoggedInEmail(baseContext)
        Preferences.getLoggedInId(baseContext)
        Preferences.getLoggedInNama(baseContext)

        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.design_bottom_sheet_slide_in,
                R.anim.design_bottom_sheet_slide_out
            )
            .replace(R.id.f1_containermitrabu, fragment,fragment.javaClass.simpleName)
            .commit()
    }

    private fun replaceFragment(){

        Preferences.clearLoggedInNama(baseContext)
        Preferences.clearLoggedInId(baseContext)
        Preferences.clearLoggedInEmail(baseContext)
        Preferences.setLoggedInStatus(baseContext,false)
        Rak.entry("loginmitrabu",false)
        Rak.removeAll(baseContext)
        supportFragmentManager
            .popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)


    }
    private fun showBottomSheet(){




//        val bottomSheetFragment = FragmentBottomSheetPesanan()
//        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
    }

    }


