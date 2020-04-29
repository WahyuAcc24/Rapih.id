package com.Rapid.id.Mitra

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
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
import com.Rapid.id.Model.Konsumen
import com.Rapid.id.R
import com.Rapid.id.util.PathUtil
import com.Rapid.id.util.PathUtil.getPath
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import io.isfaaghyth.rak.Rak
import kotlinx.android.synthetic.main.lay_daftar_konsumen.*
import org.json.JSONException
import java.io.*
import java.util.HashMap

class DaftarMitraActivity : AppCompatActivity() {


    lateinit var edt_ktp: EditText
    lateinit var edt_kk: EditText
    lateinit var edt_skd: EditText
    lateinit var edt_siup: EditText
    lateinit var edt_tdp: EditText
    lateinit var edt_npwp: EditText


    lateinit var edt_email: EditText
    lateinit var edt_pwd: EditText
    lateinit var edt_ulangi_pwd: EditText

    private val select_file1 : Int = 1
    private val select_file2 : Int = 2

    companion object {
        private const val IMAGE_PICK_CODE = 999
        private const val READ_REQUEST_CODE: Int = 42
        private const val STORAGE_PERMISSION_CODE: Int = 42
        private const val REQUEST_TAKE_PHOTO: Int = 1
    }

    private var fileData: ByteArray? = null


    lateinit var certificateFile: File


    val URL_reg = "http://rapih.id/api/regismitra.php"


    var filepath: Uri? = null

    lateinit var btn_daftar: Button

    lateinit var pDialog: ProgressDialog

    private val TAG = DaftarMitraActivity::class.java!!.getSimpleName()

    internal var tag_json_obj = "json_obj_req"

    internal lateinit var conMgr: ConnectivityManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_daftar_mitra)

        Rak.initialize(this)

        var requestQueue = Volley.newRequestQueue(this)


        txtMasuk.setOnClickListener {
            startActivity(Intent(this, LoginMitraActivity::class.java))
        }

        edt_email = findViewById(R.id.edtemailReg)
        edt_pwd = findViewById(R.id.edtPasswordReg)
        edt_ulangi_pwd = findViewById(R.id.edtPasswordRegUlang)

        edt_ktp = findViewById(R.id.edtktp)
        edt_skd = findViewById(R.id.edtskd)
        edt_tdp = findViewById(R.id.edttdp)
        edt_siup = findViewById(R.id.edtsiup)
        edt_npwp = findViewById(R.id.edtnpwp)
        edt_kk = findViewById(R.id.edtkk)


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


        edt_ktp.setOnClickListener {
            openGalery()

        }

        edt_kk.setOnClickListener {

            openGalery()

        }

        edt_npwp.setOnClickListener {
        }

        edt_siup.setOnClickListener {
        }

        edt_tdp.setOnClickListener {
        }

        edt_skd.setOnClickListener {

        }


        btn_daftar.setOnClickListener {

            val loading = ProgressDialog(this)
            loading.setCancelable(false)
            loading.setMessage("Menambahkan data...")
            loading.show()


            val email = edt_email.text.toString()
            val password = edt_pwd.text.toString()
            val ulangipassword = edt_ulangi_pwd.text.toString()


            val stringRequest = object : StringRequest(Request.Method.POST, URL_reg,
                Response.Listener<String> { response ->


                    try {

                        Rak.entry("email", email)
                        Rak.entry("password", password)


                        val res = Gson().fromJson(response.toString(), Konsumen::class.java!!)
                        if (res.isStatus()) {

                            loading.dismiss()

//                        val obj = JSONObject(response)

                            edt_email.setText("")
                            edt_pwd.setText("")
                            edt_ulangi_pwd.setText("")
//                        Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_SHORT).show()
                            Toast.makeText(
                                getApplicationContext(),
                                "Berhasil Mendaftar",
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(Intent(this, LoginMitraActivity::class.java))


                        } else {
                            loading.dismiss()
                            Toast.makeText(
                                getApplicationContext(),
                                "Email sudah Terdaftar",
                                Toast.LENGTH_SHORT
                            ).show()
                        }


                    } catch (e: JSONException) {
                        Log.e("haha", e.message)
                        e.printStackTrace()
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
                    params.put("email", email)
                    params.put("password", password)
                    params.put("ulangipassword", password)
                    return params
                }
            }

            //adding request to queue
            AppController.getInstance().addToRequestQueue(stringRequest)
        }


    }

      fun openGalery() {

        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
        }
          startActivityForResult(intent, READ_REQUEST_CODE)
//          startActivityForResult(Intent.createChooser(intent,"Select file to upload "), req_code)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {

        if (requestCode == READ_REQUEST_CODE && resultCode ==Activity.RESULT_OK) {
            resultData?.data?.also { uri ->
                Log.i(TAG, "Uri: $uri")
                getBitmapFromUri(uri)
                readTextFromUri(uri)

                 edt_ktp.setText(PathUtil.getFileName(this, resultData.data))


//                edt_kk.setText(PathUtil.getFileName(this,resultData.data))
//                edt_skd.setText(PathUtil.getFileName(this,resultData.data))
//                edt_siup.setText(PathUtil.getFileName(this,resultData.data))
//                edt_npwp.setText(PathUtil.getFileName(this,resultData.data))
//                edt_tdp.setText(PathUtil.getFileName(this,resultData.data))

            }
        }
        super.onActivityResult(requestCode, resultCode, resultData)

    }

    @Throws(IOException::class)
    private fun getBitmapFromUri(uri: Uri): Bitmap {
        val parcelFileDescriptor: ParcelFileDescriptor? = contentResolver.openFileDescriptor(uri, "r")
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
//val items = arrayOf("Kamera, Galery, batal")
//        val selectedList = ArrayList<Int>()
//        val alertDialogBuilder = AlertDialog.Builder(this)
//        alertDialogBuilder.setTitle("Siliahkan Pilih..")
//
//        alertDialogBuilder.setMultiChoiceItems(items, null)
//        { dialog,item ->
//
//            if (items.equals("Kamera")){
//
//                Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
//                    takePictureIntent.resolveActivity(packageManager)?.also {
//                        startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
//                    }
//                }
//
//            }else if (items.equals("Galery")){
//
//                val intent = Intent(Intent.ACTION_GET_CONTENT)
//                intent.type = "images/*"
//                startActivityForResult(intent, READ_REQUEST_CODE)
//
//            }
