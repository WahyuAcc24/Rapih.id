package com.Rapid.id.Konsumen

import android.app.Activity
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.Rapid.id.Model.UserLocation
import com.Rapid.id.R
import com.Rapid.id.retrofitimage.ApiConfig
import com.airbnb.lottie.parser.IntegerParser
import com.android.volley.toolbox.Volley
import io.isfaaghyth.rak.Rak
import java.lang.Exception
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class AcKonsumenActivity : AppCompatActivity(),AdapterView.OnItemSelectedListener,TextWatcher {


    companion object {
        const val USER_MAP = 123
        const val KEY_MAP = "lokasi_user"
    }

    lateinit var img_back: ImageView
    lateinit var txt_email: TextView
    lateinit var sp_jp: Spinner
    lateinit var sp_pa: Spinner
    lateinit var sp_bp_ac: Spinner
    lateinit var sp_cuci_ac: Spinner
    lateinit var edt_jmlh: EditText
    lateinit var btn_req: Button
    lateinit var edt_tgl: EditText
    lateinit var txt_total: TextView
    lateinit var edt_maps_ac: EditText
    lateinit var edt_dp: EditText


    var selectValuePerbaikan: Int? = null
    var selectValueBongkar: Int? = null
    var selectValueCuciAc: Int? = null

    var r: Int? = null

    var values_perbaikan = intArrayOf(0, 225000, 295000, 95000, 495000)
    var values_bongkar = intArrayOf(0, 45000, 90000, 100000, 245000, 320000)
    var values_cuciac = intArrayOf(0, 65000, 70000)

    var jumlah_ac: Int? = null


    private var dateFormatter: SimpleDateFormat? = null

    var cal = Calendar.getInstance()

    private var userLocation: UserLocation? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_pesanan_ac)

        var requestQueue = Volley.newRequestQueue(this)

        Rak.initialize(this)

        sp_jp = findViewById(R.id.spinProAc)
        sp_pa = findViewById(R.id.spinServisAc)
        sp_bp_ac = findViewById(R.id.spinBongkarAc)
        sp_cuci_ac = findViewById(R.id.spinCuciAc)


        edt_jmlh = findViewById(R.id.edtjumlahac)
        edt_maps_ac = findViewById(R.id.edtmapsac)
        edt_tgl = findViewById(R.id.edttglac)
        edt_dp = findViewById(R.id.edtdescac)

        txt_email = findViewById(R.id.txtemailkonsumenac)
        txt_total = findViewById(R.id.txthargaac)

        img_back = findViewById(R.id.imgbackac)
        btn_req = findViewById(R.id.btnlanjutac)

        txt_email.setText(Rak.grab("emailkonsumen") as? String)

        edt_tgl.isFocusable = false



        img_back.setOnClickListener {
            startActivity(Intent(this, HomeKonsumenActivity::class.java))
            finish()
        }


        edt_maps_ac.isFocusable = false

        edt_maps_ac.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            userLocation?.let {
                intent.putExtra("lat", it.latLong["lat"])
                intent.putExtra("lon", it.latLong["lon"])
                intent.putExtra("address", it.address)
            }
            startActivityForResult(intent, USER_MAP)
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
                    this@AcKonsumenActivity,
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


        val adapterbpac = ArrayAdapter.createFromResource(
            this,
            R.array.bongkar_ac,
            R.layout.select_item_spin_bpac
        )
        adapterbpac.setDropDownViewResource(R.layout.item_spin_ba_ac)
        sp_bp_ac.setAdapter(adapterbpac)
        sp_bp_ac.setOnItemSelectedListener(this)
        sp_bp_ac.getBackground()
            .setColorFilter(getResources().getColor(R.color.birulain), PorterDuff.Mode.SRC_ATOP)


        val adapterpa = ArrayAdapter.createFromResource(
            this,
            R.array.perbaikan_ac,
            R.layout.select_item_spin_pa
        )
        adapterpa.setDropDownViewResource(R.layout.item_spin_pa)
        sp_pa.setAdapter(adapterpa)
        sp_pa.setOnItemSelectedListener(this)
        sp_pa.getBackground()
            .setColorFilter(getResources().getColor(R.color.birulain), PorterDuff.Mode.SRC_ATOP)


        val adaptercuciac = ArrayAdapter.createFromResource(
            this,
            R.array.cuci_ac,
            R.layout.select_item_spin_cuciac
        )
        adaptercuciac.setDropDownViewResource(R.layout.item_spin_cuciac)
        sp_cuci_ac.setAdapter(adaptercuciac)
        sp_cuci_ac.setOnItemSelectedListener(this)
        sp_cuci_ac.getBackground()
            .setColorFilter(getResources().getColor(R.color.birulain), PorterDuff.Mode.SRC_ATOP)


        val jp: String = sp_jp.selectedItem.toString()
        val bpac: String = sp_bp_ac.selectedItem.toString()
        val cuciac: String = sp_cuci_ac.selectedItem.toString()
        val pa: String = sp_pa.selectedItem.toString()
        val edt_lokasi = edt_maps_ac.text.toString()
        var edt_jml_ac = edt_jmlh.text.toString()
        val tgl: String = edt_tgl.text.toString()
        val des_ac: String = edt_dp.text.toString()
        val total: String = txt_total.text.toString()



        edt_jmlh.addTextChangedListener(this)


        btn_req.setOnClickListener {

            val intent: Intent = Intent(this@AcKonsumenActivity, OrderAcActivity::class.java)
            intent.putExtra("jenis_properti_ac", sp_jp.selectedItem.toString())
            intent.putExtra("bongkar_ac", sp_bp_ac.selectedItem.toString())
            intent.putExtra("cuci_ac", sp_cuci_ac.selectedItem.toString())
            intent.putExtra("perbaikan_ac", sp_pa.selectedItem.toString())
            intent.putExtra("jumlah_ac", edt_jmlh.text.toString())
            intent.putExtra("user_location_ac", userLocation)
            intent.putExtra("tanggal", edt_tgl.text.toString())
            intent.putExtra("deskripsi_ac", edt_dp.text.toString())
            intent.putExtra("total_harga", txt_total.text.toString())
            startActivity(intent)

        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == USER_MAP && resultCode == Activity.RESULT_OK) {
            userLocation = data?.getSerializableExtra(KEY_MAP) as UserLocation
            edt_maps_ac.setText(userLocation?.address)
        }
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, arg2: Int, id: Long) {

        if (arg0.id == R.id.spinServisAc) {
            selectValuePerbaikan = values_perbaikan[arg2]
        }; if (arg0.id == R.id.spinBongkarAc) {
            selectValueBongkar = values_bongkar[arg2]
        };if (arg0.id == R.id.spinCuciAc) {
            selectValueCuciAc = values_cuciac[arg2]

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

        setTips()

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    data class TotalEntry(var total : Double, var spinbongkar : Double,var spinperbaikan : Double,var spincuci : Double){
        val total_seluruh : Double get() = total * (spinbongkar + spinperbaikan + spincuci)
        val totalbayar : Double get() = total + total_seluruh
    }


    fun setTips() {

        var edt_jml_ac = edt_jmlh.text.toString()


            if (edt_jmlh.text.toString().isNotEmpty()) {
                jumlah_ac = Integer.parseInt(edt_jml_ac)
                r = (selectValuePerbaikan!! + selectValueBongkar!! + selectValueCuciAc!!) * jumlah_ac!!

                txt_total.setText("Rp." + r.toString())


            Toast.makeText(applicationContext, "" + r, Toast.LENGTH_SHORT).show()
        }

    }
}

