package com.Rapid.id.Model

import com.google.gson.annotations.SerializedName



class Konsumen {

    private var status: Boolean = false
    private var message: String? = null
    private val data_konsumen: Datakonsumen? = null

    @SerializedName("id")
    private val id: String? = null
    @SerializedName("email")
    private val email: String? = null
    @SerializedName("nama")
    private val nama: String? = null



    fun getEmail() : String?{
        return email
    }

    fun setEmail() : String?{
        return email
    }

    fun getNama() : String?{
        return nama
    }
    fun setNama() : String?{
        return nama
    }


    fun getId() : String?{
        return id
    }

    fun isStatus(): Boolean {
        return status
    }

    fun setStatus(status: Boolean) {
        this.status = status
    }

    fun getMessage(): String? {
        return message
    }



    fun setMessage(message: String) {
        this.message = message
    }

    fun getData_konsumen(): Datakonsumen? {
        return data_konsumen
    }




    class Datakonsumen {
        val id: String? = null
        val email: String? = null
        val password: String? = null
        val nama : String? = null
    }
}