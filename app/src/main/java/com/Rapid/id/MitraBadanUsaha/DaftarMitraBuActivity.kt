package com.Rapid.id.MitraBadanUsaha

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
import com.Rapid.id.AppController
import com.Rapid.id.Mitra.LoginMitraActivity
import com.Rapid.id.Model.DataPart
import com.Rapid.id.R
import com.Rapid.id.util.PathUtil
import com.Rapid.id.util.VolleyMultipartRequest
import com.android.volley.*
import com.android.volley.toolbox.Volley
import io.isfaaghyth.rak.Rak
import kotlinx.android.synthetic.main.lay_daftar_mitra_kontraktor.*
import org.json.JSONException

import java.io.*
import java.util.HashMap

class DaftarMitraBuActivity : AppCompatActivity() {

    lateinit var edt_ktp: EditText
    lateinit var edt_siup: EditText
    lateinit var edt_tdp: EditText
    lateinit var edt_npwp: EditText


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
    lateinit var bitmaptdp: Bitmap
    lateinit var bitmapnpwp: Bitmap
    lateinit var bitmapsiup: Bitmap

    lateinit var decoded:Bitmap

    val URL_reg = "http://rapih.id/api/regismitrabu.php"

    var filepathktp: Uri? = null
    var filepathsiup: Uri? = null
    var filepathtdp: Uri? = null
    var filepathnpwp: Uri? = null

    lateinit var btn_daftar: Button

    lateinit var pDialog: ProgressDialog

    private val TAG = DaftarMitraBuActivity::class.java!!.getSimpleName()

    var tag_json_obj : String = "json_obj_req"

    internal lateinit var conMgr: ConnectivityManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_daftar_mitra_kontraktor)

        Rak.initialize(this)

        var requestQueue = Volley.newRequestQueue(this)


        txtMasukbu.setOnClickListener {
            startActivity(Intent(this, LoginMitraActivity::class.java))
        }

        edt_email = findViewById(R.id.edtemailbu)
        edt_pwd = findViewById(R.id.edtPasswordbu)
        edt_ulangi_pwd = findViewById(R.id.edtPasswordbuUlang)
        edt_nama = findViewById(R.id.edtnamabu)

        edt_ktp = findViewById(R.id.edtktpbu)
        edt_tdp = findViewById(R.id.edttdp)
        edt_siup = findViewById(R.id.edtsiup)
        edt_npwp = findViewById(R.id.edtnpwp)

        edt_ktp.isFocusable = false
        edt_tdp.isFocusable = false
        edt_siup.isFocusable = false
        edt_npwp.isFocusable = false

        btn_daftar = findViewById(R.id.btnDaftarBu)


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


        edt_npwp.setOnClickListener {
            openGalery(select_file3)
        }

        edt_siup.setOnClickListener {

            openGalery(select_file4)

        }

        edt_tdp.setOnClickListener {
            openGalery(select_file5)
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

                           val i = Intent (this@DaftarMitraBuActivity,LoginMitraActivity::class.java)
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
                        params.put("siup", DataPart("${imagename}.png", getFileDataFromDrawable(bitmapsiup)))
                        params.put("tdp", DataPart("${imagename}.png", getFileDataFromDrawable(bitmaptdp)))
                        params.put("npwp", DataPart("${imagename}.png", getFileDataFromDrawable(bitmapnpwp)))

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
                filepathsiup = resultData?.data!!
                filepathtdp = resultData?.data!!
                filepathnpwp = resultData?.data!!

                getBitmapFromUri(uri)
                readTextFromUri(uri)

                if (requestCode == select_file1) {

                    bitmapktp = MediaStore.Images.Media.getBitmap(this@DaftarMitraBuActivity.getContentResolver(), filepathktp)
                    edt_ktp.setText(PathUtil.getFileName(this, filepathktp))
                }

                if (requestCode == select_file3) {
                    bitmapnpwp = MediaStore.Images.Media.getBitmap(this@DaftarMitraBuActivity.getContentResolver(), filepathnpwp)

                    edt_npwp.setText(PathUtil.getFileName(this, filepathnpwp))
                }
                if (requestCode == select_file4) {
                    bitmapsiup = MediaStore.Images.Media.getBitmap(this@DaftarMitraBuActivity.getContentResolver(), filepathsiup)

                    edt_siup.setText(PathUtil.getFileName(this, filepathsiup))
                }
                if (requestCode == select_file5) {
                    bitmaptdp = MediaStore.Images.Media.getBitmap(this@DaftarMitraBuActivity.getContentResolver(), filepathtdp)

                    edt_tdp.setText(PathUtil.getFileName(this, filepathtdp))
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
        intent = Intent(this, LoginMitraActivity::class.java)
        finish()
        startActivity(intent)
    }

}