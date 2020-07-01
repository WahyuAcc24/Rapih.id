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

class LasAcActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, TextWatcher {


    companion object {
        const val USER_MAP = 123
        const val KEY_MAP = "lokasi_user"
    }

    private lateinit var sp_jp: Spinner
    private lateinit var sp_las : Spinner
    private lateinit var sp_las2 : Spinner
    private lateinit var txt_jmlh3: TextView
    private lateinit var txt_jmlh4: TextView
    private lateinit var txt_email: TextView


    private lateinit var btn_plus3: Button
    private lateinit var btn_minus3: Button

    private lateinit var btn_plus4: Button
    private lateinit var btn_minus4: Button
    private lateinit var img_back: ImageView

    private lateinit var edt_maps_ac: EditText
    private lateinit var edt_dp: EditText

    private lateinit var edt_tgl: EditText
    private lateinit var txt_total: TextView

    private lateinit var btn_req: Button


    private var selectValueLas: Int = 0
    private var selectValueLas2: Int = 0

    private var r4: Int = 0
    private var r5: Int = 0
    private var r6: Int = 0

    private var quantity3: Int = 0
    private var quantity4: Int = 0


    private var values_las = intArrayOf(0,95000)
    private var values_las2 = intArrayOf(0,495000)

    private var jumlah_ac3: Int = 0
    private var jumlah_ac4: Int = 0

    private var las: Int = 0
    private var las2: Int = 0

    private var dateFormatter: SimpleDateFormat? = null

    private var cal = Calendar.getInstance()

    private var userLocation: UserLocation? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_las_ac_konsumen)
        Rak.initialize(this)

        txt_jmlh3 = findViewById(R.id.edtjumlahlasfreon)
        txt_jmlh4 = findViewById(R.id.edtjumlahlasfreonbocor)
        sp_jp = findViewById(R.id.spinProLasAc)
        sp_las = findViewById(R.id.spinLas)
        sp_las2 = findViewById(R.id.spinLass)
        img_back = findViewById(R.id.imgbacklas)
        btn_req = findViewById(R.id.btnlanjutlasac)


        btn_plus3 = findViewById(R.id.btnplus3)
        btn_minus3 = findViewById(R.id.btnminus3)

        btn_plus4 = findViewById(R.id.btnplus4)
        btn_minus4 = findViewById(R.id.btnminus4)

        edt_maps_ac = findViewById(R.id.edtmapslasac)
        edt_dp = findViewById(R.id.edtdesclasac)

        edt_tgl = findViewById(R.id.edttgllasac)
        txt_total = findViewById(R.id.txthargalasac)

        txt_email = findViewById(R.id.txtemaillasac)

        txt_email.setText(Rak.grab("emailkonsumen") as? String)

        edt_tgl.isFocusable = false


        btn_plus3.setOnClickListener {
            tambah3()
        }
        btn_minus3.setOnClickListener {
            kurang3()
        }

        btn_plus4.setOnClickListener {
            tambah4()
        }
        btn_minus4.setOnClickListener {
            kurang4()
        }

        img_back.setOnClickListener {
            onBackPressed()
        }


        txt_jmlh3.addTextChangedListener(this)
        txt_jmlh4.addTextChangedListener(this)

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
                    this@LasAcActivity,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        })

        dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)



        val adapterlas = ArrayAdapter.createFromResource(
            this,
            R.array.las_freon,
            R.layout.select_item_spin_pa
        )
        adapterlas.setDropDownViewResource(R.layout.item_spin_pa)
        sp_las.setAdapter(adapterlas)
        sp_las.setOnItemSelectedListener(this)
        sp_las.getBackground()
            .setColorFilter(getResources().getColor(R.color.birulain), PorterDuff.Mode.SRC_ATOP)

        val adapterlas2 = ArrayAdapter.createFromResource(
            this,
            R.array.las_freon_bocor,
            R.layout.select_item_spin_pa
        )
        adapterlas2.setDropDownViewResource(R.layout.item_spin_pa)
        sp_las2.setAdapter(adapterlas2)
        sp_las2.setOnItemSelectedListener(this)
        sp_las2.getBackground()
            .setColorFilter(getResources().getColor(R.color.birulain), PorterDuff.Mode.SRC_ATOP)

        btn_req.setOnClickListener {


            val intent: Intent = Intent(this@LasAcActivity, OrderLasAcActivity::class.java)
            intent.putExtra("jenis_properti_las_ac", sp_jp.selectedItem.toString())
            intent.putExtra("las_ac_1pk", sp_las.selectedItem.toString())
            intent.putExtra("las_ac_2pk", sp_las2.selectedItem.toString())
            intent.putExtra("jumlah_ac_1pk", txt_jmlh3.text.toString())
            intent.putExtra("jumlah_ac_2pk", txt_jmlh4.text.toString())
            intent.putExtra("user_location_ac", userLocation)
            intent.putExtra("tanggal", edt_tgl.text.toString())
            intent.putExtra("deskripsi_ac", edt_dp.text.toString())
            intent.putExtra("total_harga", txt_total.text.toString())
            startActivity(intent)

        }




    }
    private fun tambah3() {
        quantity3 = quantity3 + 1
        display3(quantity3)

    }

    private fun kurang3() {
        if (quantity3 == 0) {
            Toast.makeText(this, "pesanan minimal 1", Toast.LENGTH_SHORT).show()
            return
        }
        quantity3 = quantity3 - 1
        display3(quantity3)


    }

    private fun display3(nomor3: Int) {
        txt_jmlh3.setText("$nomor3")
    }

    private fun tambah4() {
        quantity4 = quantity4 + 1
        display4(quantity4)

    }

    private fun kurang4() {
        if (quantity4 == 0) {
            Toast.makeText(this, "pesanan minimal 1", Toast.LENGTH_SHORT).show()
            return
        }
        quantity4 = quantity4 - 1
        display4(quantity4)


    }

    private fun display4(nomor4: Int) {
        txt_jmlh4.setText("$nomor4")
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == USER_MAP && resultCode == Activity.RESULT_OK) {
            userLocation = data?.getSerializableExtra(KEY_MAP) as UserLocation
            edt_maps_ac.setText(userLocation?.address)
        }
    }
    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, arg2: Int, id: Long) {


        if (arg0.id == R.id.spinLas) {
            selectValueLas = values_las[arg2]

        };if (arg0.id == R.id.spinLass) {
            selectValueLas2 = values_las2[arg2]

        }
    }
    private fun updateDateInView() {
        val myFormat = "dd-MM-yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        edt_tgl.setText(dateFormatter?.format(cal.getTime()))
    }
    override fun afterTextChanged(s: Editable?) {

        var edt_jml_ac3 = txt_jmlh3.text.toString()
        var edt_jml_ac4 = txt_jmlh4.text.toString()

        if (txt_jmlh3.text.toString().isNotEmpty()) {
            jumlah_ac3 = Integer.parseInt(edt_jml_ac3)
            r4 = (selectValueLas * jumlah_ac3)
            txt_total.setText("Rp. " + r4.toString())


            Toast.makeText(applicationContext, "" + r4, Toast.LENGTH_SHORT).show()

        };if (txt_jmlh4.text.toString().isNotEmpty()){
            jumlah_ac4 = Integer.parseInt(edt_jml_ac4)
            r5 = (selectValueLas2 * jumlah_ac4)
            txt_total.setText("Rp. " + r5.toString())

            Toast.makeText(applicationContext, "" + r5, Toast.LENGTH_SHORT).show()

        };if(txt_jmlh4.text.toString().isNotEmpty()) {
            jumlah_ac4 = Integer.parseInt(edt_jml_ac4)
            r6 = r4 + (selectValueLas2 * jumlah_ac4)
            txt_total.setText("Rp. " + r6.toString())


            Toast.makeText(applicationContext, "" + r6, Toast.LENGTH_SHORT).show()

        }


    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

}