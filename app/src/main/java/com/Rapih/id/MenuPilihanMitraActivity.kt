package com.Rapih.id

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.Rapih.id.MitraAc.LoginMitraAcActivity
import kotlinx.android.synthetic.main.lay_pilihan_mitra.*

class MenuPilihanMitraActivity : AppCompatActivity(){



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_pilihan_mitra)


        imgmitrasingle.setOnClickListener{

            Toast.makeText(applicationContext,"Segera hadir, jadi ditunggu ya !!",Toast.LENGTH_SHORT).show()

//            startActivity(Intent(this,DaftarMitraActivity::class.java))
        }

        imgmitrabu.setOnClickListener {
//            startActivity(Intent(this, DaftarMitraBuActivity::class.java))
            Toast.makeText(applicationContext,"Segera hadir, jadi ditunggu ya !!",Toast.LENGTH_SHORT).show()

        }

        imgmitraac.setOnClickListener {
            startActivity(Intent(this, LoginMitraAcActivity::class.java))
        }

    }


}