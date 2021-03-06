package com.Rapih.id.Konsumen

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.Rapih.id.Model.UserLocation
import com.Rapih.id.R
import com.Rapih.id.retrofitimage.ApiConfig
import com.Rapih.id.retrofitimage.AppConfig
import com.Rapih.id.util.PathUtil
import com.android.volley.toolbox.Volley
import io.isfaaghyth.rak.Rak

import okhttp3.MultipartBody
import java.io.*


class OrderRenovRetroActivity :AppCompatActivity() {

    companion object {
        const val USER_MAP = 123

    }

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
    lateinit var image : MultipartBody.Part

    val URL_order = "http://rapih.id/api/orderkonsumen.php"


    var tag_json_obj : String = "json_obj_req"

    var progressDialog : ProgressDialog? = null

    var context : Context? = null




    private val TAKE_PHOTO = 1
    private val CHOOSE_PHOTO = 2

    private val READ_REQUEST_CODE: Int = 42
    private val REQUEST_TAKE_PHOTO: Int = 1

    private val TAG = OrderRenovRetroActivity::class.java.getSimpleName()


    internal lateinit var conMgr: ConnectivityManager

    var bitmapbluprint: Bitmap? = null
    var filebluprint: Uri? = null

    var harga : Int = 0

    var mApi : ApiConfig? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_konfirmasi_pesanan_konsumen)

        var requestQueue = Volley.newRequestQueue(this)

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

        mApi = AppConfig.getAPIService()

        context = this




        img_back.setOnClickListener {
            val intent = Intent(this, RenovKonsumenActivity::class.java)
            startActivity(intent)
            finish()
        }

        txt_jp.setText(intent.getStringExtra("properti"))
        txt_wp.setText(intent.getStringExtra("tgl_proyek"))
        txt_dp.setText(intent.getStringExtra("deskripsi"))
        txt_uang.setText("Rp." + intent.getStringExtra("anggaran"))
//        txt_email.setText(Preferences.getLoggedInEmail(baseContext))
        txt_email.setText(Rak.grab("emailkonsumen") as? String)

        txt_lokasi.setText(intent.getStringExtra("domisili"))
        txt_da.setText(intent.getStringExtra("des_alamat"))



        val userLocation = intent.getSerializableExtra("user_location") as? UserLocation
        edt_maps_komf.setText(userLocation?.address)

        edt_maps_komf.isFocusable = false
        edt_maps_komf.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            userLocation?.let {
                intent.putExtra("lat", it.latLong["lat"])
                intent.putExtra("lon", it.latLong["lon"])
                intent.putExtra("address", it.address)
            }
            startActivityForResult(intent, OrderRenovActivity.USER_MAP)

        }

        conMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        run {
            if (conMgr.activeNetworkInfo != null
                && conMgr.activeNetworkInfo!!.isAvailable
                && conMgr.activeNetworkInfo!!.isConnected
            ) {
            } else {
                Toast.makeText(
                    applicationContext, " Silahkan Cek Lagi Koneksi Anda ",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        edt_gambar.isFocusable = false
        edt_gambar.setOnClickListener {

            bukaGalery()
        }

        btn_ap.setOnClickListener {
            progressDialog = ProgressDialog.show(context, null, "Harap Tunggu...", true, false)
            regisform()

        }

    }

    fun regisform(){


//        mApi?.orderReq(txt_email.getText().toString(),
//            txt_jp.getText().toString(),
//            txt_wp.getText().toString(),
//            txt_lokasi.getText().toString(),
//            edt_maps_komf.getText().toString(),
//            txt_da.getText().toString(),
//            txt_dp.getText().toString(),
//            txt_uang.getText().toString()
//
//        )
//        ?.enqueue (object : Callback<ResponseBody>{
//            override fun onResponse(
//                call: Call<ResponseBody>?,
//                response: retrofit2.Response<ResponseBody>?) {
//
//                    if (response?.isSuccessful!!){
//
//                        Log.i("DEBUG", "onResponse : Berhasil")
//                        Toast.makeText(applicationContext,"Berhasil Order",Toast.LENGTH_SHORT).show()
//
//                        progressDialog?.dismiss()
//                        val i = Intent(this@OrderRenovRetroActivity,HomeKonsumenActivity::class.java)
//                        startActivity(i)
//                        finish()
//
//                        try {
//                            val jsonRESULT: JSONObject = JSONObject(response.body().string())
//
//                            if (jsonRESULT.getString("error").equals("false")) {
//                                Toast.makeText(applicationContext,"Berhasil Order",Toast.LENGTH_SHORT).show()
//
//                            } else {
//                                val error_message: String = jsonRESULT.getString("error_msg")
//                                Toast.makeText(applicationContext,error_message, Toast.LENGTH_SHORT).show()
//                            }
//                        }catch (e : JSONException){
//                            e.printStackTrace()
//                        }catch (e : IOException){
//                            e.printStackTrace()
//                        }
//                    }else {
//                        Log.i("debug", "onResponse : Tidak Berhasil")
//                        progressDialog?.dismiss()
//                    }
//
//            }
//
//            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
//                Log.e("debug", "onFailure: ERROR > " + t?.message)
//                Toast.makeText(applicationContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show()
//                progressDialog?.dismiss()
//            }
//
//        })

    }

    override fun onBackPressed() {
        val intent = Intent(this, RenovKonsumenActivity::class.java)
        startActivity(intent)
        finish()

    }
    fun getFileDataFromDrawable(bitmap: Bitmap?): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }

    fun bukaGalery() {

        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
        }
        startActivityForResult(intent, READ_REQUEST_CODE)
    }


//    private fun takePhotoFromCamera(){
//        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        startActivityForResult(cameraIntent, REQUEST_TAKE_PHOTO)
//    }


    @Throws(IOException::class)
    private fun getBitmapFromUri(uri: Uri): Bitmap {
        val parcelFileDescriptor: ParcelFileDescriptor? =
            contentResolver.openFileDescriptor(uri, "r")
        val fileDescriptor: FileDescriptor = parcelFileDescriptor!!.fileDescriptor
        val image: Bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor.close()
        return image
    }

    @Throws(IOException::class)
    private fun readTextFromUri(uri: Uri): String {
        val stringBuilder = StringBuilder()
        contentResolver.openInputStream(uri)?.use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                var line: String? = reader.readLine()
                while (line != null) {
                    stringBuilder.append(line)
                    line = reader.readLine()
                }
            }
        }
        return stringBuilder.toString()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {

        if (resultCode == Activity.RESULT_OK ) {
            resultData?.data?.also { uri ->

                filebluprint = resultData?.data!!

                getBitmapFromUri(uri)
                readTextFromUri(uri)

                    bitmapbluprint = MediaStore.Images.Media.getBitmap(this@OrderRenovRetroActivity.getContentResolver(), filebluprint)
                    edt_gambar.setText(PathUtil.getFileName(this, filebluprint))



            }

            super.onActivityResult(requestCode, resultCode, resultData)


        }
    }
}