package com.Rapih.id.Konsumen.BottomNav

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.Rapih.id.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_pesanan.*

class FragmentBottomSheetPesanan : BottomSheetDialogFragment() {

    private var fragmentView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentView = inflater.inflate(R.layout.bottom_sheet_pesanan, container, false)
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        textViewHome.setOnClickListener {
            val intent = Intent(context,FragmentNavPesananRenov::class.java)
            startActivity(intent)
        }
        textViewAc.setOnClickListener {
            val intents = Intent(context,FragmentNavPesananAc::class.java)
            startActivity(intents)
        }
    }
}