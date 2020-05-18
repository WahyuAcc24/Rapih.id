package com.Rapid.id.Konsumen

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Editable
import android.widget.EditText

import android.text.Selection.setSelection
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.text.toHtml
import com.Rapid.id.Model.NumberTextWatcher
import com.Rapid.id.Model.UserLocation
import com.Rapid.id.R
import com.ngopidev.project.ngopihelpers.NgopiHelpers
import io.isfaaghyth.rak.Rak
import kotlinx.android.synthetic.main.fragment_slider_item.*
import kotlinx.android.synthetic.main.lay_renovrumah_konsumen.*
import java.math.BigDecimal
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest


class RenovKonsumenActivity : AppCompatActivity(),AdapterView.OnItemSelectedListener {

    companion object {
        const val USER_MAP = 123
        const val KEY_MAP = "lokasi_user"
    }

    lateinit var edt_tgl: EditText
    lateinit var edt_deskripsi : EditText
    lateinit var edt_anggaran: EditText
    lateinit var edt_maps : EditText
    lateinit var edt_deskripsi_alamat : EditText
    lateinit var btn_lanjut : Button
    lateinit var txt_email : TextView

    private var current = ""

    lateinit var imgback : ImageView

    private var dateFormatter: SimpleDateFormat? = null

    var cal = Calendar.getInstance()

    lateinit var sp_properti: Spinner

    lateinit var sp_domisili: Spinner

    private var userLocation: UserLocation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_renovrumah_konsumen)

        Rak.initialize(this)

//        val ngopiHelpers = NgopiHelpers(this)

//       val hasilkonversi = ngopiHelpers.setCurrency(edt_anggaran.text.toString().toInt().toLong())


        // Here, thisActivity is the current activity

        txt_email = findViewById(R.id.txtemailkonsumenrenov)

        txt_email.setText((Rak.grab("emailkonsumen")) as String)

        edt_deskripsi = findViewById(R.id.edtdeskripsikerjaan)
        edt_deskripsi_alamat = findViewById(R.id.edtdeskripsilokasi)


        edt_maps = findViewById(R.id.edtmaps)


        edt_tgl = findViewById(R.id.edttglproyek)
        edt_tgl.isFocusable = false

        imgback = findViewById(R.id.imgback)

        edt_anggaran = findViewById(R.id.edtuangproyek)

        edt_anggaran.addTextChangedListener(NumberTextWatcher(edt_anggaran))


        edt_maps.isFocusable = false

        edt_maps.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            userLocation?.let {
                intent.putExtra("lat", it.latLong["lat"])
                intent.putExtra("lon", it.latLong["lon"])
                intent.putExtra("address", it.address)
            }
            startActivityForResult(intent, USER_MAP)
        }

        imgback.setOnClickListener {
            startActivity(Intent(this, HomeKonsumenActivity::class.java))
            finish()
        }


        // create an OnDateSetListener
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
                    this@RenovKonsumenActivity,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        })

        dateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.US)


        sp_properti = findViewById(R.id.spinRenov)
        sp_domisili = findViewById(R.id.spinLokasi)


        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.properti,
            R.layout.select_item_spin
        )
        adapter.setDropDownViewResource(R.layout.item_spin_properti)
        sp_properti.setAdapter(adapter)
        sp_properti.setOnItemSelectedListener(this)
        sp_properti.getBackground()
            .setColorFilter(getResources().getColor(R.color.Putih), PorterDuff.Mode.SRC_ATOP)


        val adapterdomisili = ArrayAdapter.createFromResource(
            this,
            R.array.domisili,
            R.layout.select_item_spin_domisili
        )
        adapterdomisili.setDropDownViewResource(R.layout.item_spin_domisili)
        sp_domisili.setAdapter(adapterdomisili)
        sp_domisili.setOnItemSelectedListener(this)
        sp_domisili.getBackground()
            .setColorFilter(getResources().getColor(R.color.Putih), PorterDuff.Mode.SRC_ATOP)


        val desc_pekerjaan = edt_deskripsi.text.toString()
        val tgl_proyek = edt_tgl.text.toString()
        val anggaran = edt_anggaran.text.toString()
        val des_alamat = edt_deskripsi_alamat.text.toString()
        val properti = sp_properti.selectedItem.toString()
        val domisili = sp_domisili.selectedItem.toString()

        btn_lanjut = findViewById(R.id.btnlanjut)
        btn_lanjut.setOnClickListener {

            val intent = Intent(this, OrderRenovActivity::class.java)
            intent.putExtra("deskripsi", edt_deskripsi.text.toString())
            intent.putExtra("tgl_proyek", edt_tgl.text.toString())
            intent.putExtra("anggaran", edt_anggaran.text.toString())
            intent.putExtra("des_alamat", edt_deskripsi_alamat.text.toString())
            intent.putExtra("properti", sp_properti.selectedItem.toString())
            intent.putExtra("domisili", sp_domisili.selectedItem.toString())
            intent.putExtra("user_location", userLocation)
            startActivity(intent)


//            Rak.entry("desc_pekerjaan", desc_pekerjaan)
//            Rak.entry("tgl_proyek", tgl_proyek)
//            Rak.entry("anggaran", anggaran)
//            Rak.entry("des_alamat", des_alamat)
//            Rak.entry("properti", properti)
//            Rak.entry("domisili", domisili)

        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == USER_MAP && resultCode == Activity.RESULT_OK) {
            userLocation = data?.getSerializableExtra(KEY_MAP) as UserLocation
            edt_maps.setText(userLocation?.address)
        }
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {

    }
    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        edt_tgl.setText(dateFormatter?.format(cal.getTime()))
    }

}
//        val format = NumberFormat.getCurrencyInstance(Locale.ENGLISH)

//        val localeID = Locale("in", "ID")

//        edt_anggaran.addTextChangedListener( object : TextWatcher {
//
//
//            private var current : String = ""
//         override fun afterTextChanged(s : Editable){
//
//             var initial: String = s.toString()
//
//             if (edt_anggaran == null) return
//
//             if(initial.isEmpty())return
//
//             var cleanString : String = initial.replace(".","")
//
//             var localeID = Locale("in", "ID")
//
//
//             val nf = NumberFormat.getCurrencyInstance(Locale.ENGLISH)
//
//             nf.isGroupingUsed
//
//             val myNumber : Double = Double(cleanString)
//             current = nf.format(myNumber)
//
//
//
//
//            }
//         override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after:Int){
//
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                if (!s.toString().equals(current)){
//                    edt_anggaran.removeTextChangedListener(this)
//
//                    var localeID = Locale("in", "ID")
//
//                    val replaceable : String = String.format(
//                        "[%s,.\\s]",
//                        NumberFormat.getCurrencyInstance(localeID).currency!!.symbol)
//
//                    var cleanString : String = s.toString().replace(replaceable, "")
//                    val parsed: Double
//
//                    parsed = java.lang.Double.parseDouble(cleanString)
//
//                    val formatter = NumberFormat.getCurrencyInstance(localeID)
//                    formatter.maximumFractionDigits = 1000
//                    val formatted = formatter.format(parsed)
//
//                    current = formatted
//                    edt_anggaran.setText(formatted)
//                    edt_anggaran.setSelection(formatted.length)
//                    edt_anggaran.addTextChangedListener(this)


//                }
//                }
//            })

