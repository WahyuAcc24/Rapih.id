package com.Rapid.id

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.Rapid.id.Konsumen.LoginKonsumenActivity
import com.Rapid.id.Mitra.DaftarMitraActivity
import com.Rapid.id.Mitra.LoginMitraActivity
import com.Rapid.id.MitraBadanUsaha.DaftarMitraBuActivity
import kotlinx.android.synthetic.main.lay_pilihan.*
import kotlinx.android.synthetic.main.lay_pilihan_mitra.*

class MenuPilihanMitraActivity : AppCompatActivity(){



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_pilihan_mitra)


        imgmitrasingle.setOnClickListener{
            startActivity(Intent(this,DaftarMitraActivity::class.java))
        }

        imgmitrabu.setOnClickListener {
            startActivity(Intent(this, DaftarMitraBuActivity::class.java))
        }

    }


}