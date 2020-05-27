package com.Rapid.id.Konsumen

import android.Manifest.permission.CAMERA
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.startActivity
import com.Rapid.id.AppController
import com.Rapid.id.Mitra.LoginMitraActivity
import com.Rapid.id.Model.DataPart
import com.Rapid.id.Model.UserLocation
import com.Rapid.id.R
import com.Rapid.id.Server.APIorder
import com.Rapid.id.Server.ApiInterface
import com.Rapid.id.retrofitimage.ApiConfig
import com.Rapid.id.util.PathUtil
import com.Rapid.id.util.VolleyMultipartRequest
import com.android.volley.AuthFailureError
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.VolleyError
import io.isfaaghyth.rak.Rak
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.text.NumberFormat
import java.util.*
import kotlin.random.Random


class OrderRenovActivity :AppCompatActivity() {

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



    private val TAKE_PHOTO = 1
    private val CHOOSE_PHOTO = 2

    private val READ_REQUEST_CODE: Int = 42
    private val REQUEST_TAKE_PHOTO: Int = 1

    private val TAG = LoginKonsumenActivity::class.java!!.getSimpleName()



    lateinit var bitmapbluprint: Bitmap
    var filebluprint: Uri? = null

    var harga : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_komfirmasi_pesanan_konsumen)



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



        img_back.setOnClickListener {
            val intent = Intent(this, RenovKonsumenActivity::class.java)
            startActivity(intent)
            finish()
        }

        txt_jp.setText(intent.getStringExtra("properti"))
        txt_wp.setText(intent.getStringExtra("tgl_proyek"))
        txt_dp.setText(intent.getStringExtra("deskripsi"))
        txt_uang.setText("Rp." + intent.getStringExtra("anggaran"))
        txt_email.setText(Rak.grab("emailkonsumen")as String)
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


        edt_gambar.isFocusable = false
        edt_gambar.setOnClickListener {

            bukaGalery()
        }

        btn_ap.setOnClickListener {

            var jp = txt_jp.text.toString()
            var wp = txt_wp.text.toString()
            var domisili = txt_lokasi.text.toString()
            var lp = txt_da.text.toString()
            var dp = txt_dp.text.toString()
            var ap = txt_uang.text.toString()
            var map = edt_maps_komf.toString()
            var id_kons = Rak.grab("id_kons") as? String


            val req = object : VolleyMultipartRequest(
                Request.Method.POST,
                URL_order,
                object : com.android.volley.Response.Listener<NetworkResponse> {
                    override fun onResponse(response: NetworkResponse) {

                        try {

                            Log.d(TAG, response.toString())
//                            loading.dismiss()

                            Toast.makeText(
                                getApplicationContext(),
                                "Berhasil Order",
                                Toast.LENGTH_SHORT
                            ).show()

                            val i = Intent (this@OrderRenovActivity,HomeKonsumenActivity::class.java)
                            startActivity(i)

                            finish()

                        } catch (e: JSONException) {
                            Log.e("haha", e.message)
                            e.printStackTrace()
                        }
                    }
                }, object : com.android.volley.Response.ErrorListener {
                    override fun onErrorResponse(volleyError: VolleyError) {
//                        loading.dismiss()
                        Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG)
                            .show()
                    }
                }) {

                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val params = HashMap<String, String>()
                    params.put("id_konsumen", id_kons!!)
                    params.put("jenis_properti", jp)
                    params.put("waktu_pengerjaan", wp)
                    params.put("domisili_proyek", domisili)
                    params.put("lokasi_proyek", map)
                    params.put("alamat_lengkap", lp)
                    params.put("detail_pekerjaan", dp)
                    params.put("anggaran_proyek", ap)


                    return params
                }

                @Throws(AuthFailureError::class)
                override fun getByteData(): Map<String, DataPart> {
                    val params = HashMap<String, DataPart>()
                    val imagename = System.currentTimeMillis()

                    params.put("gambar_properti", DataPart("${imagename}.png", getFileDataFromDrawable(bitmapbluprint)))

                    return params
                }
            }

            AppController.getInstance().addToRequestQueue(req, tag_json_obj)
        }


    }
    override fun onBackPressed() {
        val intent = Intent(this, RenovKonsumenActivity::class.java)
        startActivity(intent)
        finish()

    }
    fun getFileDataFromDrawable(bitmap: Bitmap): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream)
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

                    bitmapbluprint = MediaStore.Images.Media.getBitmap(this@OrderRenovActivity.getContentResolver(), filebluprint)
                    edt_gambar.setText(PathUtil.getFileName(this, filebluprint))



            }

            super.onActivityResult(requestCode, resultCode, resultData)


        }
    }
}