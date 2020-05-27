package com.Rapid.id.Model

import java.io.Serializable

data class OrderKonsumen (
    val jenis_properti : String,
    val waktu_pengerjaan : String,
    val domisili_proyek : String,
    val lokasi_proyek : String,
    val alamat_lengkap : String,
    val detail_pekerjaan : String,
    val anggaran_proyek : String,
    val gambar_properti : String

): Serializable