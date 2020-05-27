package com.Rapid.id.Konsumen

import android.app.FragmentTransaction
import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.Rapid.id.Konsumen.BottomNav.FragmentNavBantuan
import com.Rapid.id.Konsumen.BottomNav.FragmentNavHome
import com.Rapid.id.Konsumen.BottomNav.FragmentNavKeluar
import com.Rapid.id.Konsumen.BottomNav.FragmentNavPesanan
import com.Rapid.id.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.isfaaghyth.rak.Rak
import kotlinx.android.synthetic.main.activity_home.*
import android.widget.LinearLayout
import com.Rapid.id.ImageSlider.FragmentSlider
import com.Rapid.id.ImageSlider.SliderView
import com.Rapid.id.ImageSlider.SliderIndicator
import com.Rapid.id.ImageSlider.SliderPagerAdapter
import com.Rapid.id.Model.Konsumen
import com.google.gson.Gson
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener


class HomeKonsumenActivity : AppCompatActivity() {

    private var content: FrameLayout? = null

    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        bn_main.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val fragment = FragmentNavHome.newInstance()
        addFragment(fragment)




    }




    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

            when (item.itemId) {
            R.id.home_menu -> {
//                findNavController(R.id.nav_host_fragment).navigate(R.id.nav_home)
                val fragment = FragmentNavHome()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true

            }
            R.id.bantuan_menu -> {
//                findNavController(R.id.nav_host_fragment).navigate(R.id.bantuan_bot)
                val fragment = FragmentNavBantuan()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true

            }
            R.id.pesanan_menu -> {
//                findNavController(R.id.nav_host_fragment).navigate(R.id.pesanan_bot)
                val fragment = FragmentNavPesanan()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true

            }
            R.id.exit_menu -> {
                val i = Intent(this, LoginKonsumenActivity::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                Rak.entry("loginkonsumen", false)
                Rak.removeAll(applicationContext)
                startActivity(i)
                finishAffinity()
            }


        }
        false
    }

    private fun addFragment(fragment: Fragment) {

//        val res = Gson().fromJson(response.toString(), Konsumen::class.java!!)


//        val json = ""
//        val res = Gson().fromJson<Konsumen>(json,Konsumen::class.java)

        val value = getIntent().getExtras()
        val email = value?.getString("emailkons")
        val nama = value?.getString("namakons")

        value?.putString("emailkons",email)
        value?.putString("namakons",nama)
        fragment.setArguments(value)
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.design_bottom_sheet_slide_in,
                R.anim.design_bottom_sheet_slide_out
            )
            .replace(R.id.f1_container, fragment,fragment.javaClass.simpleName)
            .commit()
    }


    }


