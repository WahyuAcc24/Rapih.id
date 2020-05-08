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
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener


class HomeKonsumenActivity : AppCompatActivity() {

    private var content: FrameLayout? = null

    private lateinit var appBarConfiguration: AppBarConfiguration

//    private lateinit var mToggle : ActionBarDrawerToggle

//    private var mAdapter: SliderPagerAdapter? = null
//    private var mIndicator: SliderIndicator? = null
//
//    private val sliderView: SliderView? = null
//    private val mLinearLayout: LinearLayout? = null

//    lateinit var carouselView: CarouselView
//    internal var gambarSlide = intArrayOf(R.drawable.banersatu, R.drawable.banerdua)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

//        val toolbar: Toolbar = findViewById(R.id.toolbar)
//        setSupportActionBar(toolbar)
//        supportActionBar?.setHomeButtonEnabled(true)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        carouselView = findViewById(R.id.carouselView) as CarouselView
//        carouselView.pageCount = gambarSlide.size

//        carouselView.setImageListener(imageListener)

//        carouselView.setOnClickListener {




//        setupSlider()

//        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
//        val navView: NavigationView = findViewById(R.id.nav_view)
//        val navController = findNavController(R.id.nav_host_fragment)

//        drawerLayout.closeDrawers()

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
//                R.id.nav_tools, R.id.nav_share, R.id.nav_send
//            ), drawerLayout
//        )

//        var home = navView.getMenu()
//        home.findItem(R.id.nav_home).setVisible(false)

//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
//
//        navView.menu.getItem(0).setChecked(false)


        bn_main.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val fragment = FragmentNavHome.newInstance()
        addFragment(fragment)
    }
 //    private fun setupSlider() {
//
//        sliderView?.setDurationScroll(800)
//        val fragments = ArrayList<Fragment>()
//        fragments.add(FragmentSlider.newInstance(R.drawable.banersatu.toString()))
//        fragments.add(FragmentSlider.newInstance(R.drawable.banerdua.toString()))
//
//        mAdapter =  SliderPagerAdapter(getSupportFragmentManager(), fragments)
//        sliderView?.setAdapter(mAdapter);
//        mIndicator = sliderView?.let {
//            mLinearLayout?.let { it1 ->
//                SliderIndicator(this, it1,
//                    it, R.drawable.indicator_circle)
//            }
//        }
//        mIndicator!!.setPageCount(fragments.size)
//        mIndicator!!.show()
//    }

//    internal var imageListener: ImageListener =
//        ImageListener { position, imageView -> imageView.setImageResource(gambarSlide[position])
//        }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.home, menu)
//        return true
//    }
//
//    override fun onSupportNavigateUp(): Boolean {
//
//        val navController = findNavController(R.id.nav_host_fragment)
//        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
//    }
//
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
                startActivity(i)
                Rak.entry("loginkonsumen", false)
                Rak.removeAll(applicationContext)
                finishAffinity()
            }


        }
        false
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.design_bottom_sheet_slide_in,
                R.anim.design_bottom_sheet_slide_out
            )
            .replace(R.id.f1_container, fragment,fragment.javaClass.simpleName)
            .commit()
    }

//    override fun onBackPressed() {
//
//        if(drawer_layout.isDrawerOpen(GravityCompat.START)){
//            drawer_layout.closeDrawer(GravityCompat.START)
//        }else{
//            super.onBackPressed()
//        }

    }


