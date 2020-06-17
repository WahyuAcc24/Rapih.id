package com.Rapih.id.MitraAc

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
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.Rapih.id.AppController
import com.Rapih.id.Model.DataPart
import com.Rapih.id.R
import com.Rapih.id.util.Preferences
import com.Rapih.id.util.VolleyMultipartRequest
import com.android.volley.*
import io.isfaaghyth.rak.Rak
import kotlinx.android.synthetic.main.lay_daftar_konsumen.*
import kotlinx.android.synthetic.main.lay_daftar_mitra.*
import kotlinx.android.synthetic.main.lay_daftar_mitraac.*
import org.json.JSONException
import java.io.*
import java.util.HashMap

class DaftarMitraAcActivity : AppCompatActivity() {


    lateinit var edt_email: EditText
    lateinit var edt_nama: EditText
    lateinit var edt_pwd: EditText
    lateinit var edt_hp: EditText
    lateinit var edt_ulangi_pwd: EditText
    lateinit var btn_pilih_foto: Button
    lateinit var imguser: ImageView

    val URL_reg = "http://rapih.id/api/regismitraac.php"


    lateinit var btn_daftar: Button

    var bitmap_size: Int = 60

    var max_resolution_image : Int = 800

    lateinit var pDialog: ProgressDialog

    private val TAG = DaftarMitraAcActivity::class.java!!.getSimpleName()

    internal var tag_json_obj = "json_obj_req"

    internal lateinit var conMgr: ConnectivityManager


    private val READ_REQUEST_CODE: Int = 42
    var bitmapfoto: Bitmap? = null
    var decoded: Bitmap? = null
    var filefoto: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_daftar_mitraac)

        Rak.initialize(this)



        txtMasukmitraac.setOnClickListener {
            startActivity(Intent(this, LoginMitraAcActivity::class.java))
        }

        edt_email = findViewById(R.id.edtemailRegmitraac)
        edt_pwd = findViewById(R.id.edtPasswordRegmitraac)
        edt_ulangi_pwd = findViewById(R.id.edtPasswordRegUlangmitraac)
        edt_hp = findViewById(R.id.edtnohpRegmitraac)
        edt_nama = findViewById(R.id.edtnamaregmitraac)
        imguser = findViewById(R.id.imgUsermitraac)

        btn_pilih_foto = findViewById(R.id.btnPilihfotomitraac)
        btn_daftar = findViewById(R.id.btnDaftarmitraac)


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


        btn_pilih_foto.setOnClickListener {
            bukaFoto()
        }



        btn_daftar.setOnClickListener {

            val loading = ProgressDialog(this)
            loading.setCancelable(false)
            loading.setMessage("Menambahkan data...")
            loading.show()

            val nama = edt_nama.text.toString()
            val email = edt_email.text.toString()
            val password = edt_pwd.text.toString()
            val ulangipassword = edt_ulangi_pwd.text.toString()
            val nohp = edt_hp.text.toString()


            val req = object : VolleyMultipartRequest(Request.Method.POST,
                URL_reg,
                object : Response.Listener<NetworkResponse> {
                    override fun onResponse(response: NetworkResponse) {

                    try {

                        Rak.entry("emailmitraac", email)
                        Rak.entry("passwordmitraac", password)
                        Rak.entry("namamitra", nama)

                        loading.dismiss()

//                        val obj = JSONObject(response)

                        edt_email.setText("")
                        edt_pwd.setText("")
                        edt_ulangi_pwd.setText("")


//                        Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_SHORT).show()
//                        Preferences.setRegisteredEmail(baseContext, email)
//                        Preferences.setRegisteredNama(baseContext, nama)
                        Toast.makeText(
                            getApplicationContext(),
                            "Berhasil Mendaftar",
                            Toast.LENGTH_SHORT
                        ).show()
                        val i = Intent (this@DaftarMitraAcActivity, LoginMitraAcActivity::class.java)
                        startActivity(i)

                        finish()

                    } catch (e: JSONException) {
                        Log.e("haha", e.message)
                        e.printStackTrace()
                    }
                   }
                },
                object : Response.ErrorListener {
                    override fun onErrorResponse(volleyError: VolleyError) {
                        loading.dismiss()
                        Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG)
                            .show()
                    }
                }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val params = HashMap<String, String>()
                    params.put("nama", nama)
                    params.put("email", email)
                    params.put("no_hp", nohp)
                    params.put("password", password)
                    params.put("ulangipassword", password)

                    return params
                }

                @Throws(AuthFailureError::class)
                override fun getByteData(): Map<String, DataPart> {
                    val params = HashMap<String, DataPart>()
                    val imagename = System.currentTimeMillis()

                    params.put("foto", DataPart("${imagename}.png", getFileDataFromDrawable(bitmapfoto)))

                    return params
                }
            }

            AppController.getInstance().addToRequestQueue(req, tag_json_obj)
        }
    }

    fun getFileDataFromDrawable(bitmap: Bitmap?): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }

    fun bukaFoto() {

        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        }
        startActivityForResult(intent, READ_REQUEST_CODE)
    }


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

        if (resultCode == Activity.RESULT_OK) {
            resultData?.data?.also { uri ->

                filefoto = resultData.data!!

                getBitmapFromUri(uri)
                readTextFromUri(uri)

                var bitmapfoto = MediaStore.Images.Media.getBitmap(this@DaftarMitraAcActivity.getContentResolver(),filefoto)
                setToImageView(getResizedBitmap(bitmapfoto, max_resolution_image))



            }

            super.onActivityResult(requestCode, resultCode, resultData)

        }

    }

    fun setToImageView(bmp: Bitmap) {
        var bytes: ByteArrayOutputStream = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes)
        decoded = BitmapFactory.decodeStream(ByteArrayInputStream(bytes.toByteArray()))

        imguser.setImageBitmap(decoded)
    }
    fun getResizedBitmap(image:Bitmap, maxSize:Int):Bitmap{

        var width : Int = image.width
        var height : Int = image.height

        var bitmapRatio : Float = width.toFloat() / height.toFloat()

        if (bitmapRatio > 1)
        {
            width = maxSize
            height  = Math.round(width / bitmapRatio)
        }
        else
        {
            height = maxSize
            width = Math.round(height * bitmapRatio)
        }
        return Bitmap.createScaledBitmap(image, width,height,true)

    }

    private fun showDialog() {
        if (!pDialog.isShowing)
            pDialog.show()
    }

    private fun hideDialog() {
        if (pDialog.isShowing)
            pDialog.dismiss()
    }

    override fun onBackPressed() {
        intent = Intent(this, LoginMitraAcActivity::class.java)
        finish()
        startActivity(intent)
    }


}
