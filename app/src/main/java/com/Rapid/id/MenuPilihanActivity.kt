package com.Rapid.id

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.Rapid.id.Konsumen.LoginKonsumenActivity
import com.Rapid.id.Mitra.LoginMitraActivity
import kotlinx.android.synthetic.main.lay_pilihan.*

class MenuPilihanActivity : AppCompatActivity(){



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_pilihan)


        imgkonsumen.setOnClickListener{
            startActivity(Intent(this,LoginKonsumenActivity::class.java))
        }

        imgmitra.setOnClickListener {
            startActivity(Intent(this, LoginMitraActivity::class.java))
        }

    }


}