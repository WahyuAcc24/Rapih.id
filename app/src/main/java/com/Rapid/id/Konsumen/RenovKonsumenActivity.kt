package com.Rapid.id.Konsumen

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.Rapid.id.R
import kotlinx.android.synthetic.main.fragment_slider_item.*
import kotlinx.android.synthetic.main.lay_renovrumah_konsumen.*
import java.text.SimpleDateFormat
import java.util.*






class RenovKonsumenActivity : AppCompatActivity(),AdapterView.OnItemSelectedListener {


    lateinit var edt_tgl: EditText
    lateinit var edt_deskripsi : EditText
    lateinit var edt_anggaran: EditText

    lateinit var imgback : ImageView

    private var dateFormatter: SimpleDateFormat? = null

    var cal = Calendar.getInstance()

    lateinit var spinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_renovrumah_konsumen)

        edt_tgl = findViewById(R.id.edttglproyek)
        edt_tgl.isFocusable = false

        imgback = findViewById(R.id.imgback)

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