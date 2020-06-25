package com.Rapih.id.FaqPembayaran

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.Rapih.id.R
import kotlinx.android.synthetic.main.lay_faqempatbelas.*
import kotlinx.android.synthetic.main.lay_faqsatu.*

class FaqPembayaranActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_faqempatbelas)

        imgbackfaqempatbelas.setOnClickListener {
            onBackPressed()
        }
    }

}