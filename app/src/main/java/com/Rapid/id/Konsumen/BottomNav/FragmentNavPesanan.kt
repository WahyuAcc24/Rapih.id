package com.Rapid.id.Konsumen.BottomNav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.Rapid.id.R

class FragmentNavPesanan : Fragment(){



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pesanan_bottom, container, false)
    }

    companion object {
        fun newInstance(): FragmentNavPesanan{
            val fragment = FragmentNavPesanan()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}