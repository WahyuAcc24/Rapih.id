package com.Rapih.id

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.Rapih.id.Konsumen.LoginKonsumenActivity
import kotlinx.android.synthetic.main.lay_pilihan.*

class MenuPilihanActivity : AppCompatActivity(){



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_pilihan)


        imgkonsumen.setOnClickListener{
            startActivity(Intent(this,LoginKonsumenActivity::class.java))
        }

        imgmitra.setOnClickListener {
            startActivity(Intent(this, MenuPilihanMitraActivity::class.java))
        }

    }


}