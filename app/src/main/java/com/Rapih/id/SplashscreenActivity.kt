package com.Rapih.id

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView

class SplashscreenActivity : AppCompatActivity()  {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //hiding title bar of this activity


        window.requestFeature(Window.FEATURE_NO_TITLE)
        //making this activity full screen
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.lay_splashscreen)

        val animation = findViewById<LottieAnimationView>(R.id.progressBarAnimation)


        //4second splash time
        Handler().postDelayed({
            //start main activity
            startActivity(Intent(this, MenuPilihanActivity::class.java))
            //finish this activity
            finish()
        },3000)


        animation.speed = 3.0f
        animation.progress = 50f



    }


}

