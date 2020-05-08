package com.Rapid.id.Konsumen.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.Rapid.id.R

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

//    private var imageModelArrayList: ArrayList<ImageModel>? = null

//    private val myImageList = intArrayOf(R.drawable.banersatu, R.drawable.banerdua)


//    lateinit var mPager : ViewPager

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        homeViewModel =ViewModelProviders.of(this).get(HomeViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        homeViewModel.text.observe(this, Observer {
        })
        return root

    }


//    private fun populateList(): ArrayList<ImageModel> {
//
//        val list = ArrayList<ImageModel>()
//
//        for (i in 0..5) {
//            val imageModel = ImageModel()
//            imageModel.setImage_drawables(myImageList[i])
//            list.add(imageModel)
//        }
//
//        return list
//    }
//    private fun init() {
//
//        mPager = view?.findViewById(R.id.pager) as ViewPager
//        mPager!!.adapter = SlideImageAdapter(this@HomeFragment, this.imageModelArrayList!!)
//
//        val indicator = view?.findViewById(R.id.indicator) as TabLayout
//
//        indicator.setViewPager(mPager)
//
//        val density = resources.displayMetrics.density
//
//        //Set circle indicator radius
//        indicator.setRadius(5 * density)
//
//        NUM_PAGES = imageModelArrayList!!.size
//
//        // Auto start of viewpager
//        val handler = Handler()
//        val Update = Runnable {
//            if (currentPage == NUM_PAGES) {
//                currentPage = 0
//            }
//            mPager!!.setCurrentItem(currentPage++, true)
//        }
//        val swipeTimer = Timer()
//        swipeTimer.schedule(object : TimerTask() {
//            override fun run() {
//                handler.post(Update)
//            }
//        }, 3000, 3000)
//
//        // Pager listener over indicator
//        indicator.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
//
//            override fun onPageSelected(position: Int) {
//                currentPage = position
//
//            }
//
//            override fun onPageScrolled(pos: Int, arg1: Float, arg2: Int) {
//
//            }
//
//            override fun onPageScrollStateChanged(pos: Int) {
//
//            }
//        })
//
//    }
//
//    companion object {
//
//        private var mPager: ViewPager? = null
//        private var currentPage = 0
//        private var NUM_PAGES = 0
//    }
//

}


