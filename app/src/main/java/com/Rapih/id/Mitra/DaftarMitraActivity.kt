package com.Rapih.id.Mitra

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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.Rapih.id.AppController
import com.Rapih.id.MitraAc.LoginMitraAcActivity
import com.Rapih.id.Model.DataPart
import com.Rapih.id.R
import com.Rapih.id.util.PathUtil
import com.Rapih.id.util.VolleyMultipartRequest
import com.android.volley.*
import com.android.volley.toolbox.Volley
import io.isfaaghyth.rak.Rak
import kotlinx.android.synthetic.main.lay_daftar_mitra.*
import org.json.JSONException

import java.io.*
import java.util.HashMap

class DaftarMitraActivity : AppCompatActivity() {


    lateinit var edt_ktp: EditText
    lateinit var edt_kk: EditText
    lateinit var edt_skd: EditText


    lateinit var edt_email: EditText
    lateinit var edt_pwd: EditText
    lateinit var edt_ulangi_pwd: EditText
    lateinit var edt_nama : EditText

    private val select_file1 : Int = 1
    private val select_file2 : Int = 2
    private val select_file3 : Int = 3
    private val select_file4 : Int = 4
    private val select_file5 : Int = 5
    private val select_file6 : Int = 6


    companion object {
        private const val IMAGE_PICK_CODE = 999
        private const val READ_REQUEST_CODE: Int = 42
        private const val STORAGE_PERMISSION_CODE: Int = 42
        private const val REQUEST_TAKE_PHOTO: Int = 1
    }

    private var fileData: ByteArray? = null


    lateinit var certificateFile: File

    lateinit var bitmapktp: Bitmap
    lateinit var bitmapkk: Bitmap
    lateinit var bitmapskd: Bitmap

    lateinit var decoded:Bitmap

    val URL_reg = "http://rapih.id/api/regismitra.php"


    var filepathktp: Uri? = null

    var filepathkk: Uri? = null
    var filepathskd: Uri? = null

    lateinit var btn_daftar: Button

    lateinit var pDialog: ProgressDialog

    private val TAG = DaftarMitraActivity::class.java!!.getSimpleName()

    var tag_json_obj : String = "json_obj_req"

    internal lateinit var conMgr: ConnectivityManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_daftar_mitra)

        Rak.initialize(this)

        var requestQueue = Volley.newRequestQueue(this)


        txtMasukmitra.setOnClickListener {
            startActivity(Intent(this, LoginMitraAcActivity::class.java))
        }

        edt_email = findViewById(R.id.edtemailregmitra)
        edt_pwd = findViewById(R.id.edtPasswordMitraReg)
        edt_ulangi_pwd = findViewById(R.id.edtPasswordmitraRegUlang)
        edt_nama = findViewById(R.id.edtnamaRegmitra)

        edt_ktp = findViewById(R.id.edtktpmitra)
        edt_skd = findViewById(R.id.edtskd)
        edt_kk = findViewById(R.id.edtkk)

        edt_ktp.isFocusable = false
        edt_skd.isFocusable = false
        edt_kk.isFocusable = false

//        edt_skd.isFocusableInTouchMode = false
//        edt_kk.inputType = TYPE_NULL


        btn_daftar = findViewById(R.id.btnDaftarmitra)


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


        edt_ktp.setOnClickListener {
            openGalery(select_file1)

        }

        edt_kk.setOnClickListener {

            openGalery(select_file2)

        }

        edt_skd.setOnClickListener {
            openGalery(select_file6)

        }


        btn_daftar.setOnClickListener {

            val loading = ProgressDialog(this)
            loading.setCancelable(false)
            loading.setMessage("Menambahkan data...")
            loading.show()


            val email = edt_email.text.toString()
            val password = edt_pwd.text.toString()
            val nama = edt_nama.text.toString()
            val ulangipassword = edt_ulangi_pwd.text.toString()


//            val stringRequest = object :StringRequest(Request.Method.POST, URL_reg,
//                Response.Listener<String> { response ->
            val req = object : VolleyMultipartRequest(Request.Method.POST,
                URL_reg,
                object : Response.Listener<NetworkResponse> {
                    override fun onResponse(response: NetworkResponse) {

                        try {

                            Rak.entry("email", email)
                            Rak.entry("password", password)
                            Rak.entry("nama", nama)


//                        val obj = JSONObject(response)

                            edt_email.setText("")
                            edt_nama.setText("")
                            edt_pwd.setText("")
                            edt_ulangi_pwd.setText("")
                            Log.d("TAG", response.toString())
                            loading.dismiss()
                            Toast.makeText(
                                getApplicationContext(),
                                "Berhasil Mendaftar",
                                Toast.LENGTH_SHORT
                            ).show()

                           val i = Intent (this@DaftarMitraActivity,
                               LoginMitraAcActivity::class.java)
                            startActivity(i)

                            finish()

                        } catch (e: JSONException) {
                            Log.e("haha", e.message)
                            e.printStackTrace()
                        }
                    }
                }, object : Response.ErrorListener {
                    override fun onErrorResponse(volleyError: VolleyError) {
                        loading.dismiss()
                        Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG)
                            .show()
                    }
                }) {

                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val params = HashMap<String, String>()
                    params.put("email", email)
                    params.put("nama", nama)
                    params.put("password", password)
                    params.put("ulangipassword", password)

                    return params
                }

                @Throws(AuthFailureError::class)
                override fun getByteData(): Map<String, DataPart> {
                    val params = HashMap<String, DataPart>()
                    val imagename = System.currentTimeMillis()

                        params.put("ktp", DataPart("${imagename}.png", getFileDataFromDrawable(bitmapktp)))
                        params.put("kk", DataPart("${imagename}.png", getFileDataFromDrawable(bitmapkk)))
                        params.put("skd", DataPart("${imagename}.png", getFileDataFromDrawable(bitmapskd)))

                    return params
                }
            }

            AppController.getInstance().addToRequestQueue(req, tag_json_obj)
        }
    }

    fun getFileDataFromDrawable(bitmap: Bitmap): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }


    fun openGalery(req_code: Int) {

            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "*/*"
            }
//          startActivityForResult(intent, READ_REQUEST_CODE)
            startActivityForResult(Intent.createChooser(intent, "Select file to upload "), req_code)
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

        if (resultCode == Activity.RESULT_OK ) {
            resultData?.data?.also { uri ->


                filepathktp = resultData?.data!!
                filepathkk = resultData?.data!!
                filepathskd = resultData?.data!!

                getBitmapFromUri(uri)
                readTextFromUri(uri)

                if (requestCode == select_file1) {

                    bitmapktp = MediaStore.Images.Media.getBitmap(this@DaftarMitraActivity.getContentResolver(), filepathktp)

                    edt_ktp.setText(PathUtil.getFileName(this, filepathktp))
                }

                if (requestCode == select_file2) {
                    bitmapkk = MediaStore.Images.Media.getBitmap(this@DaftarMitraActivity.getContentResolver(), filepathkk)

                    edt_kk.setText(PathUtil.getFileName(this, filepathkk))
                }
                if (requestCode == select_file6) {
                    bitmapskd = MediaStore.Images.Media.getBitmap(this@DaftarMitraActivity.getContentResolver(), filepathskd)

                    edt_skd.setText(PathUtil.getFileName(this, filepathskd))
                }

            }
            super.onActivityResult(requestCode, resultCode, resultData)

    }

  }

    private fun showDialog() {
        if (!pDialog.isShowing)
            pDialog.show()
    }

    private fun hideDialog() {
        if (pDialog.isShowing)
            pDialog.dismiss()
    }
    private fun setFile(uri : Uri){
        certificateFile =  File(PathUtil.getPath(applicationContext, uri))

    }

    override fun onBackPressed() {
        intent = Intent(this, LoginMitraAcActivity::class.java)
        finish()
        startActivity(intent)
    }


}

