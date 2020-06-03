package com.Rapid.id.Konsumen

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
import androidx.core.content.ContextCompat.startActivity
import com.Rapid.id.AppController
import com.Rapid.id.Mitra.LoginMitraActivity
import com.Rapid.id.Model.DataPart
import com.Rapid.id.Model.Konsumen
import com.Rapid.id.R
import com.Rapid.id.util.PathUtil
import com.Rapid.id.util.Preferences
import com.Rapid.id.util.VolleyMultipartRequest
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import io.isfaaghyth.rak.Rak
import kotlinx.android.synthetic.main.lay_daftar_konsumen.*
import org.json.JSONException
import java.io.*
import java.util.HashMap

class DaftarKonsumenActivity : AppCompatActivity() {


    lateinit var edt_email: EditText
    lateinit var edt_nama: EditText
    lateinit var edt_pwd: EditText
    lateinit var edt_ulangi_pwd: EditText
    lateinit var edt_alamat: EditText
    lateinit var edt_hp: EditText
    lateinit var btn_pilih_foto: Button
    lateinit var imguser: ImageView

    val URL_reg = "http://rapih.id/api/regiskonsumen.php"


    lateinit var btn_daftar: Button

    var bitmap_size: Int = 60

    var max_resolution_image : Int = 800

    lateinit var pDialog: ProgressDialog

    private val TAG = DaftarKonsumenActivity::class.java!!.getSimpleName()

    internal var tag_json_obj = "json_obj_req"

    internal lateinit var conMgr: ConnectivityManager


    private val READ_REQUEST_CODE: Int = 42
    var bitmapfoto: Bitmap? = null
    var decoded: Bitmap? = null
    var filefoto: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_daftar_konsumen)

        Rak.initialize(this)



        txtMasuk.setOnClickListener {
            startActivity(Intent(this, LoginKonsumenActivity::class.java))
        }

        edt_email = findViewById(R.id.edtemailReg)
        edt_pwd = findViewById(R.id.edtPasswordReg)
        edt_ulangi_pwd = findViewById(R.id.edtPasswordRegUlang)
        edt_nama = findViewById(R.id.edtnamaregkonsumen)
        edt_alamat = findViewById(R.id.edtalamat_rumah)
        edt_hp = findViewById(R.id.edtnohp)
        imguser = findViewById(R.id.imgUser)

        btn_pilih_foto = findViewById(R.id.btnPilihfoto)
        btn_daftar = findViewById(R.id.btnDaftarKonsumen)


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
            val alamat_rumah = edt_alamat.text.toString()
            val nohp = edt_hp.text.toString()

            val ulangipassword = edt_ulangi_pwd.text.toString()


            val req = object : VolleyMultipartRequest(Request.Method.POST,
                URL_reg,
                object : Response.Listener<NetworkResponse> {
                    override fun onResponse(response: NetworkResponse) {

                    try {

                        Rak.entry("emailkonsumen", email)
                        Rak.entry("passwordkonsumen", password)
                        Rak.entry("namakonsumen", nama)

                        loading.dismiss()

//                        val obj = JSONObject(response)

                        edt_email.setText("")
                        edt_pwd.setText("")
                        edt_ulangi_pwd.setText("")


//                        Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_SHORT).show()
                        Preferences.setRegisteredEmail(baseContext, email)
                        Preferences.setRegisteredNama(baseContext, nama)
                        Toast.makeText(
                            getApplicationContext(),
                            "Berhasil Mendaftar",
                            Toast.LENGTH_SHORT
                        ).show()
                        val i = Intent (this@DaftarKonsumenActivity, LoginKonsumenActivity::class.java)
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
                    params.put("password", password)
                    params.put("ulangipassword", password)
                    params.put("alamat_rumah", alamat_rumah)
                    params.put("no_hp", nohp)

                    return params
                }

                @Throws(AuthFailureError::class)
                override fun getByteData(): Map<String, DataPart> {
                    val params = HashMap<String, DataPart>()
                    val imagename = System.currentTimeMillis()

                    params.put("images", DataPart("${imagename}.png", getFileDataFromDrawable(bitmapfoto)))

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

                var bitmapfoto = MediaStore.Images.Media.getBitmap(this@DaftarKonsumenActivity.getContentResolver(),filefoto)
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
        intent = Intent(this, LoginKonsumenActivity::class.java)
        finish()
        startActivity(intent)
    }


}
