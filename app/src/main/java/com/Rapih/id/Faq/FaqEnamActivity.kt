package com.Rapih.id.Faq

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.Rapih.id.R
import kotlinx.android.synthetic.main.lay_faqenam.*
import kotlinx.android.synthetic.main.lay_faqsatu.*

class FaqEnamActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_faqenam)

        imgbackfaqenam.setOnClickListener {
            onBackPressed()
        }
    }

}