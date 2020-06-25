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
import com.Rapih.id.Konsumen.MapsActivity
import com.Rapih.id.Model.UserLocation
import com.Rapih.id.R
import io.isfaaghyth.rak.Rak
import java.text.SimpleDateFormat
import java.util.*

class CuciAcActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, TextWatcher {

    companion object {
        const val USER_MAP = 123
        const val KEY_MAP = "lokasi_user"
    }

    lateinit var sp_jp: Spinner
    lateinit var sp_ca : Spinner
    lateinit var sp_caa : Spinner
    lateinit var txt_jmlh: TextView
    lateinit var txt_jmlh2: TextView

    lateinit var txt_email: TextView


    lateinit var btn_plus: Button
    lateinit var btn_minus: Button

    lateinit var btn_plus2: Button
    lateinit var btn_minus2: Button
    lateinit var img_back: ImageView

    lateinit var edt_maps_ac: EditText
    lateinit var edt_dp: EditText

    lateinit var edt_tgl: EditText
    lateinit var txt_total: TextView

    lateinit var btn_req: Button


    var selectValueCuciAc: Int = 0
    var selectValueCuciAc2: Int = 0

    var r: Int = 0
    var r2: Int = 0
    var r3: Int = 0

    var quantity: Int = 0
    var quantity2: Int = 0


    var values_cuci = intArrayOf(0,65000)
    var values_cuci2 = intArrayOf(0,70000)

    var jumlah_ac: Int = 0
    var jumlah_ac2: Int = 0

    

    private var dateFormatter: SimpleDateFormat? = null

    var cal = Calendar.getInstance()

    private var userLocation: UserLocation? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_cuci_ac_konsumen)

        txt_jmlh = findViewById(R.id.edtjumlahcuciac)
        txt_jmlh2 = findViewById(R.id.edtjumlahcuciacc)
        sp_jp = findViewById(R.id.spinProCuciAc)
        sp_ca = findViewById(R.id.spincuciac)
        sp_caa = findViewById(R.id.spincuciacc)
        img_back = findViewById(R.id.imgbackcuciac)
        btn_req = findViewById(R.id.btnlanjutcuciac)


        btn_plus = findViewById(R.id.btnpluscuciac)
        btn_minus = findViewById(R.id.btnminuscuciac)

        btn_plus2 = findViewById(R.id.btnpluscuciac2)
        btn_minus2 = findViewById(R.id.btnminuscuciac2)

        edt_maps_ac = findViewById(R.id.edtmapscuciac)
        edt_dp = findViewById(R.id.edtdesccuciac)

        edt_tgl = findViewById(R.id.edttglcuciac)
        txt_total = findViewById(R.id.txthargacuciac)

        txt_email = findViewById(R.id.txtemailcuciac)

        txt_email.setText(Rak.grab("emailkonsumen") as? String)



        btn_plus.setOnClickListener {
            tambah()
        }
        btn_minus.setOnClickListener {
            kurang()
        }

        btn_plus2.setOnClickListener {
            tambah2()
        }
        btn_minus2.setOnClickListener {
            kurang2()
        }

        img_back.setOnClickListener {
            onBackPressed()
        }
        txt_jmlh.addTextChangedListener(this)
        txt_jmlh2.addTextChangedListener(this)

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
                    this@CuciAcActivity,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        })

        dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)



        val adaptercuciac = ArrayAdapter.createFromResource(
            this,
            R.array.cuciac,
            R.layout.select_item_spin_cuciac
        )
        adaptercuciac.setDropDownViewResource(R.layout.item_spin_cuciac)
        sp_ca.setAdapter(adaptercuciac)
        sp_ca.setOnItemSelectedListener(this)
        sp_ca.getBackground()
            .setColorFilter(getResources().getColor(R.color.birulain), PorterDuff.Mode.SRC_ATOP)

        val adaptercuciac2 = ArrayAdapter.createFromResource(
            this,
            R.array.cuciacc,
            R.layout.select_item_spin_cuciac
        )
        adaptercuciac2.setDropDownViewResource(R.layout.item_spin_cuciac)
        sp_caa.setAdapter(adaptercuciac2)
        sp_caa.setOnItemSelectedListener(this)
        sp_caa.getBackground()
            .setColorFilter(getResources().getColor(R.color.birulain), PorterDuff.Mode.SRC_ATOP)

    }
    private fun tambah() {
        quantity = quantity + 1
        display(quantity)

    }

    private fun kurang() {
        if (quantity == 0) {
            Toast.makeText(this, "pesanan minimal 1", Toast.LENGTH_SHORT).show()
            return
        }
        quantity = quantity - 1
        display(quantity)


    }

    private fun display(nomor: Int) {
        txt_jmlh.setText("$nomor")
    }

    private fun tambah2() {
        quantity2 = quantity2 + 1
        display2(quantity2)

    }

    private fun kurang2() {
        if (quantity2 == 0) {
            Toast.makeText(this, "pesanan minimal 1", Toast.LENGTH_SHORT).show()
            return
        }
        quantity2 = quantity2 - 1
        display2(quantity2)


    }

    private fun display2(nomor2: Int) {
        txt_jmlh2.setText("$nomor2")
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == USER_MAP && resultCode == Activity.RESULT_OK) {
            userLocation = data?.getSerializableExtra(KEY_MAP) as UserLocation
            edt_maps_ac.setText(userLocation?.address)
        }
    }
    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, arg2: Int, id: Long) {


        if (arg0.id == R.id.spincuciac) {
            selectValueCuciAc = values_cuci[arg2]

        };if (arg0.id == R.id.spincuciacc) {
            selectValueCuciAc2 = values_cuci2[arg2]

        }
    }
    private fun updateDateInView() {
        val myFormat = "dd-MM-yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        edt_tgl.setText(dateFormatter?.format(cal.getTime()))
    }
    override fun afterTextChanged(s: Editable?) {

        var edt_jml_ac = txt_jmlh.text.toString()
        var edt_jml_ac2 = txt_jmlh2.text.toString()

        if (txt_jmlh.text.toString().isNotEmpty()) {
            jumlah_ac = Integer.parseInt(edt_jml_ac)
            r = (selectValueCuciAc * jumlah_ac)
            txt_total.setText("Rp. " + r.toString())


            Toast.makeText(applicationContext, "" + r, Toast.LENGTH_SHORT).show()

        };if (txt_jmlh2.text.toString().isNotEmpty()){
            jumlah_ac2 = Integer.parseInt(edt_jml_ac2)
            r2 = (selectValueCuciAc2 * jumlah_ac2)
            txt_total.setText("Rp. " + r2.toString())

            Toast.makeText(applicationContext, "" + r2, Toast.LENGTH_SHORT).show()

        };if(txt_jmlh2.text.toString().isNotEmpty()) {
            jumlah_ac2 = Integer.parseInt(edt_jml_ac2)
            r3 = r + (selectValueCuciAc2 * jumlah_ac2)
            txt_total.setText("Rp. " + r3.toString())


            Toast.makeText(applicationContext, "" + r3, Toast.LENGTH_SHORT).show()

        }


    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
    override fun onNothingSelected(parent: AdapterView<*>?) {
    }


}

