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

class IsiUlangFreonR32Activity : AppCompatActivity(),AdapterView.OnItemSelectedListener,
    TextWatcher {

    companion object {
        const val USER_MAP = 123
        const val KEY_MAP = "lokasi_user"
    }

    private lateinit var img_back: ImageView
    private lateinit var txt_email: TextView
    private lateinit var sp_jp: Spinner
    private lateinit var sp_freon_r32: Spinner
    private lateinit var sp_freon_r32_2pk: Spinner

    private lateinit var txt_jmlh: TextView
    private lateinit var txt_jmlh2: TextView


    private lateinit var btn_req: Button
    private lateinit var edt_tgl: EditText
    private lateinit var txt_total: TextView
    lateinit var edt_maps_ac: EditText
    private lateinit var edt_dp: EditText

    private lateinit var btn_plus: Button
    private lateinit var btn_minus: Button

    private lateinit var btn_plus2: Button
    private lateinit var btn_minus2: Button



    private var selectValueFreon1Pk: Int = 0
    private var selectValueFreon2pk: Int = 0


    private var r: Int = 0
    private var r2: Int = 0
    private var r3: Int = 0



    private var quantity: Int = 0
    private var quantity2: Int = 0



    private var values_freon = intArrayOf(0,197000)
    private var values_freon2 = intArrayOf(0,247000)


    private var jumlah_ac: Int = 0
    private var jumlah_ac2: Int = 0

    private var nama_cb : String = ""


    private var dateFormatter: SimpleDateFormat? = null

    private var cal = Calendar.getInstance()

    private var userLocation: UserLocation? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_isi_freon_r32_konsumen)


        Rak.initialize(this)


        img_back = findViewById(R.id.imgbackisifreonr32)
        txt_email = findViewById(R.id.txtemailacisifreonr32)
        sp_jp = findViewById(R.id.spinProIsiR32Ac)
        sp_freon_r32 = findViewById(R.id.spinIsiFreonAcR32)
        sp_freon_r32_2pk = findViewById(R.id.spinIsiFreonAcR32_2pk)

        txt_jmlh = findViewById(R.id.txtjumlahisifreonr32)
        txt_jmlh2 = findViewById(R.id.txtjumlahisifreonr32_2pk)



        btn_req = findViewById(R.id.btnlanjutisifreon32)
        edt_tgl = findViewById(R.id.edttglisifreon32ac)
        txt_total = findViewById(R.id.txthargaisifreon32ac)

        edt_maps_ac = findViewById(R.id.edtmapsisifreon32ac)
        edt_dp = findViewById(R.id.edtdescisifreon32ac)

        btn_minus = findViewById(R.id.btnminusisir32)
        btn_minus2 = findViewById(R.id.btnminusisir32_2pk)
        btn_plus = findViewById(R.id.btnplusisir32)
        btn_plus2 = findViewById(R.id.btnplusisiR32_2pk)

        txt_email.setText(Rak.grab("emailkonsumen") as? String)

        edt_tgl.isFocusable = false

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
                    this@IsiUlangFreonR32Activity,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        })

        dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)

        val adapterjp = ArrayAdapter.createFromResource(
            this,
            R.array.properti,
            R.layout.select_item_spin_jp
        )
        adapterjp.setDropDownViewResource(R.layout.item_spin_properti)
        sp_jp.setAdapter(adapterjp)
        sp_jp.setOnItemSelectedListener(this)
        sp_jp.getBackground()
            .setColorFilter(getResources().getColor(R.color.birulain), PorterDuff.Mode.SRC_ATOP)

        val adapterfreon1pk = ArrayAdapter.createFromResource(
            this,
            R.array.tambah_freon_r32_1pk,
            R.layout.select_item_spin_freon
        )
        adapterfreon1pk.setDropDownViewResource(R.layout.item_spin_freon)
        sp_freon_r32.setAdapter(adapterfreon1pk)
        sp_freon_r32.setOnItemSelectedListener(this)
        sp_freon_r32.getBackground()
            .setColorFilter(getResources().getColor(R.color.birulain), PorterDuff.Mode.SRC_ATOP)

        val adapterfreon2pk = ArrayAdapter.createFromResource(
            this,
            R.array.tambah_freon_r32_2pk,
            R.layout.select_item_spin_freon
        )
        adapterfreon2pk.setDropDownViewResource(R.layout.item_spin_freon)
        sp_freon_r32_2pk.setAdapter(adapterfreon2pk)
        sp_freon_r32_2pk.setOnItemSelectedListener(this)
        sp_freon_r32_2pk.getBackground()
            .setColorFilter(getResources().getColor(R.color.birulain), PorterDuff.Mode.SRC_ATOP)


        txt_jmlh.addTextChangedListener(this)
        txt_jmlh2.addTextChangedListener(this)

        btn_req.setOnClickListener {


            val intent: Intent = Intent(this@IsiUlangFreonR32Activity, OrderIsiFreonR32Activity::class.java)
            intent.putExtra("jenis_properti_freon_ac", sp_jp.selectedItem.toString())
            intent.putExtra("freon_ac_1pk", sp_freon_r32.selectedItem.toString())
            intent.putExtra("freon_ac_2pk", sp_freon_r32_2pk.selectedItem.toString())
            intent.putExtra("jumlah_ac_1pk", txt_jmlh.text.toString())
            intent.putExtra("jumlah_ac_2pk", txt_jmlh2.text.toString())
            intent.putExtra("user_location_ac", userLocation)
            intent.putExtra("tanggal", edt_tgl.text.toString())
            intent.putExtra("deskripsi_ac", edt_dp.text.toString())
            intent.putExtra("total_harga", txt_total.text.toString())
            startActivity(intent)

        }

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

        if (arg0.id == R.id.spinIsiFreonAcR32) {
            selectValueFreon1Pk = values_freon[arg2]

        };if (arg0.id == R.id.spinIsiFreonAcR32_2pk) {
            selectValueFreon2pk = values_freon2[arg2]


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
        var edt_jml_ac = txt_jmlh.text.toString()
        var edt_jml_ac2 = txt_jmlh2.text.toString()



        if (txt_jmlh.text.toString().isNotEmpty()) {
            jumlah_ac = Integer.parseInt(edt_jml_ac)
            r = (selectValueFreon1Pk * jumlah_ac)
            txt_total.setText("Rp. " + r.toString())


            Toast.makeText(applicationContext, "" + r, Toast.LENGTH_SHORT).show()

        };if (txt_jmlh2.text.toString().isNotEmpty()){
            jumlah_ac2 = Integer.parseInt(edt_jml_ac2)
            r2 = (selectValueFreon2pk * jumlah_ac2)
            txt_total.setText("Rp. " + r2.toString())

            Toast.makeText(applicationContext, "" + r2, Toast.LENGTH_SHORT).show()

        };if(txt_jmlh2.text.toString().isNotEmpty()) {
            jumlah_ac2 = Integer.parseInt(edt_jml_ac2)
            r3 = r + (selectValueFreon2pk * jumlah_ac2)
            txt_total.setText("Rp. " + r3.toString())


            Toast.makeText(applicationContext, "" + r3, Toast.LENGTH_SHORT).show()

        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }



}