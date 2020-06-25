package com.Rapih.id.FaqPembayaran

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.Rapih.id.R
import kotlinx.android.synthetic.main.lay_faqlimabelas.*
import kotlinx.android.synthetic.main.lay_faqsatu.*

class FaqPembayaranduaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_faqlimabelas)

        imgbackfaqlimabelas.setOnClickListener {
            onBackPressed()
        }
    }

}