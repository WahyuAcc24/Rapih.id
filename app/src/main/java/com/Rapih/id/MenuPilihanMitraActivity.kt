package com.Rapih.id

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.Rapih.id.MitraAc.LoginMitraAcActivity
import com.Rapih.id.MitraBadanUsaha.DaftarMitraBuActivity
import com.Rapih.id.MitraBadanUsaha.LoginMitraBuActivity
import kotlinx.android.synthetic.main.lay_pilihan_mitra.*

class MenuPilihanMitraActivity : AppCompatActivity(){



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_pilihan_mitra)



        imgmitrabu.setOnClickListener {
            startActivity(Intent(this, LoginMitraBuActivity::class.java))
//            Toast.makeText(applicationContext,"Segera hadir, jadi ditunggu ya !!",Toast.LENGTH_SHORT).show()

        }

        imgmitraac.setOnClickListener {
            startActivity(Intent(this, LoginMitraAcActivity::class.java))
        }

    }


}