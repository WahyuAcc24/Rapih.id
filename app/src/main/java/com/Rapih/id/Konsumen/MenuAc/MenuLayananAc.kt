package com.Rapih.id.Konsumen.MenuAc

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.Rapih.id.R
import io.isfaaghyth.rak.Rak

class MenuLayananAc : AppCompatActivity() {

    lateinit var img_back_menu_ac : ImageView
    lateinit var img_kaps_ac : ImageView
    lateinit var img_cek_ac : ImageView
    lateinit var img_freon_ac : ImageView
    lateinit var img_isi_freon : ImageView
    lateinit var img_cuci_ac : ImageView
    lateinit var img_las_ac : ImageView

    lateinit var txt_email : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_pilihan_menu_ac)

        img_back_menu_ac = findViewById(R.id.imgbackpilmenuac)
        img_kaps_ac = findViewById(R.id.imgkapsac)
        img_cek_ac = findViewById(R.id.imgcekac)
        img_freon_ac = findViewById(R.id.imgfreonac)
        img_isi_freon = findViewById(R.id.imgtmbhfreonac)
        img_cuci_ac = findViewById(R.id.imgcuciac)
        img_las_ac = findViewById(R.id.imglasac)

        txt_email = findViewById(R.id.txtemailpilmenuac)


        txt_email.setText(Rak.grab("emailkonsumen") as? String)


        img_back_menu_ac.setOnClickListener {
            onBackPressed()
        }

        img_kaps_ac.setOnClickListener {
            val intent = Intent(this,KapasitorAcActivity::class.java)
            startActivity(intent)
            finish()
        }

        img_cek_ac.setOnClickListener {
            val intent = Intent(this,CekAcActivity::class.java)
            startActivity(intent)
            finish()
        }

        img_freon_ac.setOnClickListener {
            val intent = Intent(this,GantiFreonActivity::class.java)
            startActivity(intent)
            finish()
        }
        img_isi_freon.setOnClickListener {
            val intent = Intent(this,IsiUlangFreonActivity::class.java)
            startActivity(intent)
            finish()
        }
        img_las_ac.setOnClickListener {
            val intent = Intent(this,LasAcActivity::class.java)
            startActivity(intent)
            finish()
        }
        img_cuci_ac.setOnClickListener {
            val intent = Intent(this,CuciAcActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

}