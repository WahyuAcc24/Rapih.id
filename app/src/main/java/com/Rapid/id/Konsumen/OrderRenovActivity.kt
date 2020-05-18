package com.Rapid.id.Konsumen

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.Rapid.id.Mitra.LoginMitraActivity
import com.Rapid.id.Model.UserLocation
import com.Rapid.id.R
import io.isfaaghyth.rak.Rak
import java.text.NumberFormat
import java.util.*


class OrderRenovActivity :AppCompatActivity() {

    lateinit var img_back : ImageView
    lateinit var txt_email : TextView
    lateinit var txt_jp : TextView
    lateinit var txt_wp : TextView
    lateinit var txt_dp : TextView
    lateinit var txt_uang : TextView
    lateinit var edt_gambar : EditText
    lateinit var btn_ap : Button
    lateinit var txt_lokasi : TextView
    lateinit var txt_da : TextView
    lateinit var edt_maps_komf : EditText

    var harga : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_komfirmasi_pesanan_konsumen)

        Rak.initialize(this)

        img_back = findViewById(R.id.imgbackorder)
        txt_email = findViewById(R.id.txtemailkonsumenrenovOrder)
        txt_jp = findViewById(R.id.txtjenisproyek)
        txt_wp = findViewById(R.id.txtWaktuPengerjaan)
        txt_dp = findViewById(R.id.txtDetailPekerjaan)
        txt_uang = findViewById(R.id.txthargaproyek)
        edt_gambar  = findViewById(R.id.edtgambarproperti)
        btn_ap = findViewById(R.id.btnAjukanProperti)
        txt_lokasi = findViewById(R.id.txtdomisili)
        txt_da = findViewById(R.id.txtdetailalamat)
        edt_maps_komf = findViewById(R.id.edtmapslokasi)


        val jp = txt_jp.text.toString()
        val wp = txt_wp.text.toString()
        val dp = txt_dp.text.toString()




        img_back.setOnClickListener {
            val intent = Intent(this, RenovKonsumenActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
//            Rak.entry("loginkonsumen", false)
//            Rak.removeAll(getApplicationContext())
            finish()
        }

        txt_jp.setText(intent.getStringExtra("properti"))
        txt_wp.setText(intent.getStringExtra("tgl_proyek"))
        txt_dp.setText(intent.getStringExtra("deskripsi"))
        txt_uang.setText("Rp." + intent.getStringExtra("anggaran"))
        txt_email.setText((Rak.grab("emailkonsumen"))as String)
        txt_lokasi.setText(intent.getStringExtra("domisili"))
        txt_da.setText(intent.getStringExtra("des_alamat"))

        val userLocation = intent.getSerializableExtra("user_location") as UserLocation
        edt_maps_komf.setText(userLocation.address)


    }
    override fun onBackPressed() {
        val intent = Intent(this, RenovKonsumenActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
//        Rak.entry("loginkonsumen", false)
//        Rak.removeAll(getApplicationContext())
        finish()

    }

}