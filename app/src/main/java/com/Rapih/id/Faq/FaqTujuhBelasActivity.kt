package com.Rapih.id.Faq

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.Rapih.id.R
import kotlinx.android.synthetic.main.lay_faqsatu.*
import kotlinx.android.synthetic.main.lay_faqtujuhbelas.*

class FaqTujuhBelasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_faqtujuhbelas)

        imgbackfaqtujuhbelas.setOnClickListener {
            onBackPressed()
        }
    }

}