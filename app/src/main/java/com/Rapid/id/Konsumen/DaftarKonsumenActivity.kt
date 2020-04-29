package com.Rapid.id.Konsumen

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.Rapid.id.AppController
import com.Rapid.id.Model.Konsumen
import com.Rapid.id.R
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import io.isfaaghyth.rak.Rak
import kotlinx.android.synthetic.main.lay_daftar_konsumen.*
import org.json.JSONException
import java.util.HashMap

class DaftarKonsumenActivity : AppCompatActivity() {


    lateinit var edt_email: EditText
    lateinit var edt_pwd: EditText
    lateinit var edt_ulangi_pwd: EditText

    val URL_reg = "http://rapih.id/api/regiskonsumen.php"


    lateinit var btn_daftar: Button

    lateinit var pDialog: ProgressDialog

    private val TAG = DaftarKonsumenActivity::class.java!!.getSimpleName()

    internal var tag_json_obj = "json_obj_req"

    internal lateinit var conMgr: ConnectivityManager


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
                        Toast.makeText(getApplicationContext(), "Berhasil Mendaftar", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,LoginKonsumenActivity::class.java))


                    }else{
                        loading.dismiss()
                        Toast.makeText(getApplicationContext(), "Email sudah Terdaftar", Toast.LENGTH_SHORT).show()
                    }


                } catch (e: JSONException) {
                    Log.e("haha", e.message)
                    e.printStackTrace()
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(volleyError: VolleyError) {
                    loading.dismiss()
                    Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("email", email)
                params.put("password", password)
                params.put("ulangipassword",password)
                return params
            }
        }

        //adding request to queue
        AppController.getInstance().addToRequestQueue(stringRequest)
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

    override fun onBackPressed() {
        intent = Intent(this, LoginKonsumenActivity::class.java)
        finish()
        startActivity(intent)
    }
}
