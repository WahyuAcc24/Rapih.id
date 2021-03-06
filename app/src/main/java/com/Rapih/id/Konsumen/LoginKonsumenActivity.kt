package com.Rapih.id.Konsumen

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
import com.Rapih.id.AppController
import com.Rapih.id.Model.Konsumen
import com.Rapih.id.R
import com.Rapih.id.util.Preferences
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import io.isfaaghyth.rak.Rak
import kotlinx.android.synthetic.main.lay_login_konsumen.*
import java.util.HashMap

class LoginKonsumenActivity : AppCompatActivity() {


    val URL_login = "http://rapih.id/api/loginkonsumen.php"

    lateinit var btn_login : Button

    lateinit var edt_emaillogin : EditText
    lateinit var edt_passwordlogin : EditText



    lateinit var pDialog: ProgressDialog

    private val TAG = LoginKonsumenActivity::class.java.getSimpleName()

    internal var tag_json_obj = "json_obj_req"

    internal lateinit var conMgr: ConnectivityManager


    var TAG_ID : String? = null
    var id : String? = null






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


        if (Rak.isExist("login")) {
            if (Rak.grab("login")) {
                startActivity(Intent(this, HomeKonsumenActivity::class.java))
                finish()
            }
        }


        if (Preferences.getLoggedInStatus(baseContext)) {

            startActivity(Intent(baseContext,HomeKonsumenActivity::class.java))
            finish()
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
            Request.Method.POST, URL_login+"?email="+email+"&password="+password,
            Response.Listener<String> { response ->

                Log.e(TAG, "Login Response: $response")


                    var res = Gson().fromJson(response.toString(), Konsumen::class.java!!)

//                    var job : JSONObject = JSONObject(response)

                    if (res.isStatus()) {


                        if(res.getData_kons()?.getId() != null) Rak.entry("idkonsumen", res.getData_kons()?.getId())
                        if (res.getData_kons()?.getNama() != null) Rak.entry("namakonsumen",res.getData_kons()?.getNama())
                        if (res.getData_kons()?.getEmail() != null) Rak.entry("emailkonsumen", res.getData_kons()?.getEmail())
                        if (res.getData_kons()?.getEmail() != null) Rak.entry("hpkonsumen", res.getData_kons()?.no_hp)

                        Rak.entry("login", true)



//                        id = job.getString(TAG_ID)

                        loading.dismiss()
                        edt_emaillogin.setText("")
                        edt_passwordlogin.setText("")


//                        Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_SHORT).show()
                        Toast.makeText(getApplicationContext(), "Login berhasil", Toast.LENGTH_SHORT).show()


                        Preferences.setLoggedInNama(baseContext,Preferences.getRegisteredNama(baseContext))
                        Preferences.setLoggedInEmail(baseContext,Preferences.getRegisteredEmail(baseContext))
                        Preferences.setLoggedInId(baseContext,Preferences.getRegisteredId(baseContext))
                        Preferences.setLoggedInStatus(baseContext,true)
                        val intent = Intent(this@LoginKonsumenActivity,HomeKonsumenActivity::class.java)
//                        intent.putExtras(bundle)
                        startActivity(intent)
                    }else{
                        loading.dismiss()
                        Toast.makeText(getApplicationContext(), "Login Gagal", Toast.LENGTH_SHORT).show()
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





