package com.Rapih.id.Konsumen.BottomNav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.Rapih.id.Adapter.ChatUsAdapter
import com.Rapih.id.Adapter.FaqAdapter
import com.Rapih.id.Adapter.FaqPayAdapter
import com.Rapih.id.Model.ChatUs
import com.Rapih.id.Model.Faq
import com.Rapih.id.Model.FaqPay
import com.Rapih.id.R

class FragmentNavBantuan : Fragment() {

   lateinit var rvfaq : RecyclerView
    var adapterfaq : FaqAdapter? = null
    var datafaq : List<Faq>? = null

    lateinit var rvfaqpay : RecyclerView
    var adapterFaqPay : FaqPayAdapter? = null
    var datafaqpay : List<FaqPay>? = null

    lateinit var rvchat : RecyclerView
    var adapterChat : ChatUsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bantuan_bottom, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        rvfaq = view.findViewById(R.id.rvfaq)
        rvfaq.setHasFixedSize(true)

        rvfaqpay = view.findViewById(R.id.rvPembayaran)
        rvfaqpay.setHasFixedSize(true)

        rvchat = view.findViewById(R.id.rvKontak)
        rvchat.setHasFixedSize(true)


        var datafaq : ArrayList<Faq> = ArrayList()

        datafaq.add(Faq("Pekerjaan seperti apa yang bisa Anda dapatkan melalui aplikasi rapih.id ?"))
        datafaq.add(Faq("Siapa yang melakukan pengerjaan pemesanan pekerjaan anda ?"))
        datafaq.add(Faq("Apa Perbedaan Kontraktor & Pelaksana Proyek?"))
        datafaq.add(Faq("Berapa durasi jam kerja di rapih.id ? "))
        datafaq.add(Faq("Bagaimana cara pemesanan pekerjaan di aplikasi rapih.id ?"))
        datafaq.add(Faq("Mengapa harus ada proses Survey ?"))
        datafaq.add(Faq("Setelah anda berhasil mengajukan pemesanan pekerjaan "))
        datafaq.add(Faq("Bagaimana jika Mitra tidak dapat dihubungi / tidak merespon Order anda ?"))
        datafaq.add(Faq("Setelah waktu survei Mitra sudah terjadwal"))
        datafaq.add(Faq("Bagaimana jika saya membutuhkan lebih dari satu service ?"))
        datafaq.add(Faq("Bagaimana cara melihat Riwayat Pekerjaan yang telah dipesan ?"))
        datafaq.add(Faq("Tambahan Pekerjaan"))
        datafaq.add(Faq("Perlengkapan & Material"))
        datafaq.add(Faq("Masa Garansi"))
        datafaq.add(Faq("Garansi Pemesanan Pekejaan rapih quick cool’s & quick clean’s"))
        datafaq.add(Faq("Penilaian / Rate"))
        datafaq.add(Faq("Perizinan dan Keamanan untuk layanan Renovasi dan Bangun Rumah"))
        datafaq.add(Faq("Kecelakaan Kerja"))




        var lm : LinearLayoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL, false)

        rvfaq.setLayoutManager(lm)
        adapterfaq = FaqAdapter(activity,datafaq)
        rvfaq.adapter = adapterfaq


        var datafaqpay : ArrayList<FaqPay> = ArrayList()

        datafaqpay.add(FaqPay("Metode pembayaran"))
        datafaqpay.add(FaqPay("Sistem pembayaran khusus Renovasi Rumah dan Bangun Rumah"))

        var lmfaq : LinearLayoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL, false)

        rvfaqpay.setLayoutManager(lmfaq)
        adapterFaqPay = FaqPayAdapter(activity,datafaqpay)
        rvfaqpay.adapter = adapterFaqPay

        var datafaqchatus : ArrayList<ChatUs> = ArrayList()

        datafaqchatus.add(ChatUs("Whatsapp"))

        var lmchat : LinearLayoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL, false)

        rvchat.setLayoutManager(lmchat)
        adapterChat = ChatUsAdapter(activity,datafaqchatus)
        rvchat.adapter = adapterChat



    }

    companion object {
        fun newInstance(): FragmentNavBantuan {
            val fragment = FragmentNavBantuan()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
