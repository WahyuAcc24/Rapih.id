package com.Rapih.id.Faq

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.Rapih.id.R
import kotlinx.android.synthetic.main.lay_faqdua.*
import kotlinx.android.synthetic.main.lay_faqsatu.*

class FaqDuaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_faqdua)

        imgbackfaqdua.setOnClickListener {
            onBackPressed()
        }
    }

}