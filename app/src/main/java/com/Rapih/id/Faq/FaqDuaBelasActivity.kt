package com.Rapih.id.Faq

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.Rapih.id.R
import kotlinx.android.synthetic.main.lay_faqduabelas.*
import kotlinx.android.synthetic.main.lay_faqsatu.*

class FaqDuaBelasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_faqduabelas)

        imgbackfaqduabelas.setOnClickListener {
            onBackPressed()
        }
    }

}