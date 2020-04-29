package com.Rapid.id.Konsumen

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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
import kotlinx.android.synthetic.main.lay_login_konsumen.*
import org.json.JSONException
import java.util.HashMap

class LoginKonsumenActivity : AppCompatActivity() {


    val URL_login = "http://rapih.id/api/loginkonsumen.php"

    lateinit var btn_login : Button

    lateinit var edt_emaillogin : EditText
    lateinit var edt_passwordlogin : EditText



    lateinit var pDialog: ProgressDialog

    private val TAG = LoginKonsumenActivity::class.java!!.getSimpleName()

    internal var tag_json_obj = "json_obj_req"

    internal lateinit var conMgr: ConnectivityManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_login_konsumen)

        Rak.initialize(this)


        edt_emaillogin = findViewById(R.id.edtemailLog)
        edt_passwordlogin = findViewById(R.id.edtPasswordLog)

        edt_emaillogin.text
        edt_passwordlogin.text

        btn_login = findViewById(R.id.btnMasukKonsumen)

        txtDaftar.setOnClickListener {
            startActivity(Intent(this, DaftarKonsumenActivity::class.java))

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

        btn_login.setOnClickListener {

            val email = edt_emaillogin.text.toString()
            val password = edt_passwordlogin.text.toString()

            if (email.length > 0 && password.length > 0) {
                if (conMgr.activeNetworkInfo != null
                    && conMgr.activeNetworkInfo!!.isAvailable
                    && conMgr.activeNetworkInfo!!.isConnected
                ) {
                    ceklogin(email, password)
                } else {
                    Toast.makeText(applicationContext, "tidak ada koneksi", Toast.LENGTH_LONG)
                        .show()
                }
            } else {
                Toast.makeText(applicationContext, "kolom tidak boleh kosong", Toast.LENGTH_LONG)
                    .show()

            }

        }
        }

    private fun ceklogin(email: String, password: String) {

        val loading = ProgressDialog(this)
        loading.setCancelable(false)
        loading.setMessage("Mohon Menunggu...")
        loading.show()

        val stringRequest = object : StringRequest(
            Request.Method.POST, URL_login,
            Response.Listener<String> { response ->

                Log.e(TAG, "Login Response: $response")

                try {

                    Rak.entry("email", email)
                    Rak.entry("password", password)

                    val res = Gson().fromJson(response.toString(), Konsumen::class.java!!)

                    if (res.isStatus()) {

                        loading.dismiss()

//                        val obj = JSONObject(response)

                        edt_emaillogin.setText("")
                        edt_passwordlogin.setText("")
 //                        Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_SHORT).show()
                        Toast.makeText(getApplicationContext(), "Login berhasil", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, Home::class.java))

                    }else{
                        loading.dismiss()
                        Toast.makeText(getApplicationContext(), "Login Gagal", Toast.LENGTH_SHORT).show()
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
                return params
            }
        }

        //adding request to queue
        AppController.getInstance().addToRequestQueue(stringRequest)
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
//        intent = Intent(this, LoginKonsumenActivity::class.java)
        finish()
//        startActivity(intent)
    }
}





