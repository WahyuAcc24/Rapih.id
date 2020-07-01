package com.Rapih.id.MitraBadanUsaha

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

class FragmentNavBantuanMitraBu : Fragment() {


    lateinit var rvchat : RecyclerView
    var adapterChat : ChatUsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bantuan_mitraac, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        rvchat = view.findViewById(R.id.rvKontak)
        rvchat.setHasFixedSize(true)

        var datafaqchatus : ArrayList<ChatUs> = ArrayList()

        datafaqchatus.add(ChatUs("Whatsapp"))

        var lmchat : LinearLayoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL, false)

        rvchat.setLayoutManager(lmchat)
        adapterChat = ChatUsAdapter(activity,datafaqchatus)
        rvchat.adapter = adapterChat



    }

    companion object {
        fun newInstance(): FragmentNavBantuanMitraBu {
            val fragment = FragmentNavBantuanMitraBu()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
