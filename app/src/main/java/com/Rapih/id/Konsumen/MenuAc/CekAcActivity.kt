package com.Rapih.id.Konsumen.MenuAc

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.Rapih.id.Konsumen.HomeKonsumenActivity
import com.Rapih.id.Konsumen.MapsActivity
import com.Rapih.id.Model.UserLocation
import com.Rapih.id.R
import io.isfaaghyth.rak.Rak
import java.text.SimpleDateFormat
import java.util.*

class CekAcActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, TextWatcher {

    companion object {
        const val USER_MAP = 123
        const val KEY_MAP = "lokasi_user"
    }

    lateinit var img_back: ImageView
    lateinit var txt_email: TextView


    lateinit var sp_cekac : Spinner
    lateinit var sp_jp: Spinner

    lateinit var txt_jmlh5: TextView

    lateinit var btn_plus5: Button
    lateinit var btn_minus5: Button
    lateinit var btn_req: Button
    lateinit var edt_tgl: EditText
    lateinit var edt_maps_ac: EditText
    lateinit var edt_dp: EditText

    lateinit var txt_total: TextView

    var quantity5: Int = 0

    var selectValueCekAc: Int = 0

    var values_cek = intArrayOf(0,90000)

    var jumlah_ac5: Int = 0

    var cekac: Int = 0

    var r: Int = 0
    var r2: Int = 0



    private var dateFormatter: SimpleDateFormat? = null

    var cal = Calendar.getInstance()

    private var userLocation: UserLocation? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_cek_ac_konsumen)


        Rak.initialize(this)

        img_back = findViewById(R.id.imgbackcekac)
        txt_email = findViewById(R.id.txtemailcekac)

        txt_jmlh5 = findViewById(R.id.edtjumlahcekac)

        btn_plus5 = findViewById(R.id.btnplus5)
        btn_minus5 = findViewById(R.id.btnminus5)

        btn_req = findViewById(R.id.btnlanjutcekac)
        edt_tgl = findViewById(R.id.edttglcekac)
        txt_total = findViewById(R.id.txthargacekac)

        edt_maps_ac = findViewById(R.id.edtmapscekac)
        edt_dp = findViewById(R.id.edtdesccekac)

        sp_cekac = findViewById(R.id.spinPengecekan)
        sp_jp = findViewById(R.id.spinProCekAc)




        btn_plus5.setOnClickListener {
            tambah5()
        }

        btn_minus5.setOnClickListener {
            kurang5()
        }

        img_back.setOnClickListener {

            onBackPressed()
        }
        txt_email.setText(Rak.grab("emailkonsumen") as? String)



        txt_jmlh5.addTextChangedListener(this)

        edt_tgl.isFocusable = false


        edt_maps_ac.isFocusable = false

        edt_maps_ac.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            userLocation?.let {
                intent.putExtra("lat", it.latLong["lat"])
                intent.putExtra("lon", it.latLong["lon"])
                intent.putExtra("address", it.address)
            }
            startActivityForResult(intent,
                USER_MAP
            )
        }

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        edt_tgl.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(
                    this@CekAcActivity,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        })

        dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)




        val adaptercek = ArrayAdapter.createFromResource(
            this,
            R.array.cek_ac,
            R.layout.select_item_spin_pa
        )
        adaptercek.setDropDownViewResource(R.layout.item_spin_pa)
        sp_cekac.setAdapter(adaptercek)
        sp_cekac.setOnItemSelectedListener(this)
        sp_cekac.getBackground()
            .setColorFilter(getResources().getColor(R.color.birulain), PorterDuff.Mode.SRC_ATOP)




    }
    private fun tambah5() {
        quantity5 = quantity5 + 1
        display5(quantity5)

    }

    private fun kurang5() {
        if (quantity5 == 0) {
            Toast.makeText(this, "pesanan minimal 1", Toast.LENGTH_SHORT).show()
            return
        }
        quantity5 = quantity5 - 1
        display5(quantity5)


    }

    private fun display5(nomor5: Int) {
        txt_jmlh5.setText("$nomor5")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == USER_MAP && resultCode == Activity.RESULT_OK) {
            userLocation = data?.getSerializableExtra(KEY_MAP) as UserLocation
            edt_maps_ac.setText(userLocation?.address)
        }
    }
    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, arg2: Int, id: Long) {


        if (arg0.id == R.id.spinPengecekan) {
            selectValueCekAc = values_cek[arg2]

        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }
    private fun updateDateInView() {
        val myFormat = "dd-MM-yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        edt_tgl.setText(dateFormatter?.format(cal.getTime()))
    }

    override fun afterTextChanged(s: Editable?) {
        var edt_jml_ac = txt_jmlh5.text.toString()



        if (txt_jmlh5.text.toString().isNotEmpty()) {
            jumlah_ac5 = Integer.parseInt(edt_jml_ac)
            r = (selectValueCekAc * jumlah_ac5)
            txt_total.setText("Rp. " + r.toString())


            Toast.makeText(applicationContext, "" + r, Toast.LENGTH_SHORT).show()

        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }






}