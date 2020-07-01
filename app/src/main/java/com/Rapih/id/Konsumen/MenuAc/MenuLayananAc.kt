package com.Rapih.id.Konsumen.MenuAc

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.Rapih.id.Konsumen.BottomNav.FragmentNavPesananAc
import com.Rapih.id.Konsumen.BottomNav.FragmentNavPesananRenov
import com.Rapih.id.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import io.isfaaghyth.rak.Rak
import kotlinx.android.synthetic.main.bottom_sheet_freon.view.*
import kotlinx.android.synthetic.main.bottom_sheet_pesanan.view.*
import kotlinx.android.synthetic.main.bottom_sheet_reload_freon.view.*

class MenuLayananAc : AppCompatActivity() {

    lateinit var img_back_menu_ac : ImageView
    lateinit var img_kaps_ac : ImageView
    lateinit var img_cek_ac : ImageView
    lateinit var img_freon_ac : ImageView
    lateinit var img_isi_freon : ImageView
    lateinit var img_cuci_ac : ImageView
    lateinit var img_las_ac : ImageView

    lateinit var txt_email : TextView


    var bottomSheetDialog: BottomSheetDialog? = null

    var bottomSheetDialog2: BottomSheetDialog? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_pilihan_menu_ac)

        bottomSheetDialog = BottomSheetDialog(this)

        bottomSheetDialog2 = BottomSheetDialog(this)

        val view = layoutInflater.inflate(R.layout.bottom_sheet_freon, null)

        val view2 = layoutInflater.inflate(R.layout.bottom_sheet_reload_freon, null)


        bottomSheetDialog?.setContentView(view)
        bottomSheetDialog2?.setContentView(view2)

        img_back_menu_ac = findViewById(R.id.imgbackpilmenuac)
        img_kaps_ac = findViewById(R.id.imgkapsac)
        img_cek_ac = findViewById(R.id.imgcekac)
        img_freon_ac = findViewById(R.id.imgfreonac)
        img_isi_freon = findViewById(R.id.imgtmbhfreonac)
        img_cuci_ac = findViewById(R.id.imgcuciac)
        img_las_ac = findViewById(R.id.imglasac)

        txt_email = findViewById(R.id.txtemailpilmenuac)


        txt_email.setText(Rak.grab("emailkonsumen") as? String)


        view.textViewFreonR22.setOnClickListener {
            val intent = Intent(this,GantiFreonR22Activity::class.java)
            startActivity(intent)
            bottomSheetDialog?.hide()

        }

        view.textViewFreonR32.setOnClickListener {
            val intent = Intent(this,GantiFreonR32Activity::class.java)
            startActivity(intent)
            bottomSheetDialog?.hide()

        }


        view2.textViewFreonR22r.setOnClickListener {
            val intent = Intent(this,IsiUlangFreonR22Activity::class.java)
            startActivity(intent)
            bottomSheetDialog?.hide()

        }

        view2.textViewFreonR32r.setOnClickListener {
            val intent = Intent(this,IsiUlangFreonR32Activity::class.java)
            startActivity(intent)
            bottomSheetDialog?.hide()

        }




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
            bottomSheetDialog?.show()
        }

        img_isi_freon.setOnClickListener {
            bottomSheetDialog2?.show()
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