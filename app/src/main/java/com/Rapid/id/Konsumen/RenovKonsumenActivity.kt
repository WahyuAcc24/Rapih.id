package com.Rapid.id.Konsumen

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
import com.Rapid.id.R
import com.ngopidev.project.ngopihelpers.NgopiHelpers
import kotlinx.android.synthetic.main.fragment_slider_item.*
import kotlinx.android.synthetic.main.lay_renovrumah_konsumen.*
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest


class RenovKonsumenActivity : AppCompatActivity(),AdapterView.OnItemSelectedListener {


    lateinit var edt_tgl: EditText
    lateinit var edt_deskripsi : EditText
    lateinit var edt_anggaran: EditText
    lateinit var edt_maps : EditText




    private val current = ""

    lateinit var imgback : ImageView

    private var dateFormatter: SimpleDateFormat? = null

    var cal = Calendar.getInstance()

    lateinit var spinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_renovrumah_konsumen)

//        val ngopiHelpers = NgopiHelpers(this)

//        val hasilkonversi = ngopiHelpers.setCurrency(edt_anggaran.text.toString().toInt().toLong())


        // Here, thisActivity is the current activity




        edt_maps = findViewById(R.id.edtmaps)


        edt_tgl = findViewById(R.id.edttglproyek)
        edt_tgl.isFocusable = false

        imgback = findViewById(R.id.imgback)

        edt_anggaran = findViewById(R.id.edtuangproyek)

        edt_anggaran.addTextChangedListener(NumberTextWatcher(edt_anggaran))




        edt_maps.isFocusable = false

        edt_maps.setOnClickListener {
            val m = Intent(this,MapsActivity::class.java)
            startActivity(m)

        }

        imgback.setOnClickListener {
            startActivity(Intent(this,HomeKonsumenActivity::class.java))
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


        spinner = findViewById(R.id.spinRenov)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.properti,
            R.layout.select_item_spin
        )
        adapter.setDropDownViewResource(R.layout.item_spin_properti)
        spinner.setAdapter(adapter)
        spinner.setOnItemSelectedListener(this)
        spinner.getBackground().setColorFilter(getResources().getColor(R.color.Putih), PorterDuff.Mode.SRC_ATOP);


    }


    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {

    }
    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

