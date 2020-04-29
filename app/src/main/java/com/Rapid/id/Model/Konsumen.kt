package com.Rapid.id.Model

class Konsumen {

    private var status: Boolean = false
    private var message: String? = null
    private val data_konsumen: Datakonsumen? = null


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
    }
}