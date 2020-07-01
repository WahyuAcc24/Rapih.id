package com.Rapih.id.MitraAc

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.Rapih.id.Konsumen.HomeKonsumenActivity
import com.Rapih.id.Konsumen.ListOrderAc.*
import com.Rapih.id.MitraAc.ListOrder.*
import com.Rapih.id.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import io.isfaaghyth.rak.Rak
import kotlinx.android.synthetic.main.bottom_sheet_freon.view.*
import kotlinx.android.synthetic.main.bottom_sheet_reload_freon.view.*

class FragmentNavPesananMitraAc : Fragment(){

    lateinit var imgkaps : ImageView
    lateinit var imglas : ImageView
    lateinit var imgcuciac : ImageView
    lateinit var imgcekac : ImageView
    lateinit var imgfreon : ImageView
    lateinit var imgisifreon : ImageView
    lateinit var imgback : ImageView

    var bottomSheetDialog: BottomSheetDialog? = null

    var bottomSheetDialog2: BottomSheetDialog? = null




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.lay_pilihan_list_order_mitra_ac, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Rak.initialize(context)

        bottomSheetDialog = BottomSheetDialog(view.context)

        bottomSheetDialog2 = BottomSheetDialog(view.context)

        val view1 = layoutInflater.inflate(R.layout.bottom_sheet_freon, null)

        val view2 = layoutInflater.inflate(R.layout.bottom_sheet_reload_freon, null)

        bottomSheetDialog?.setContentView(view1)
        bottomSheetDialog2?.setContentView(view2)

        view1.textViewFreonR22.setOnClickListener {
            val intent = Intent(context, ListOrderMitraFreonR22::class.java)
            startActivity(intent)
            bottomSheetDialog?.hide()

        }

        view1.textViewFreonR32.setOnClickListener {
            val intent = Intent(context, ListOrderMitraFreonR32::class.java)
            startActivity(intent)
            bottomSheetDialog?.hide()

        }


        view2.textViewFreonR22r.setOnClickListener {
            val intent = Intent(context, ListOrderMitraIsiFreonR22::class.java)
            startActivity(intent)
            bottomSheetDialog?.hide()

        }

        view2.textViewFreonR32r.setOnClickListener {
            val intent = Intent(context, ListOrderMitraIsiFreonR32::class.java)
            startActivity(intent)
            bottomSheetDialog?.hide()

        }


        imgkaps = view.findViewById(R.id.imglistmitrakapsac)
        imglas = view.findViewById(R.id.imglistmitralasac)
        imgcuciac = view.findViewById(R.id.imglistmitracuciac)
        imgcekac = view.findViewById(R.id.imglistmitracekac)
        imgfreon = view.findViewById(R.id.imglistmitrafreonac)
        imgisifreon = view.findViewById(R.id.imglistmitratmbhfreonac)
        imgback = view.findViewById(R.id.imgbacklistmitramenuac)


        imgback.setOnClickListener {
            val intent = Intent(context, HomeMitraAcActivity::class.java)
            startActivity(intent)
        }

        imgkaps.setOnClickListener {
            val intent = Intent(context, ListOrderMitraKapasitorAc::class.java)
            startActivity(intent)
        }

        imglas.setOnClickListener {
            val intent = Intent(context, ListOrderMitraLasAc::class.java)
            startActivity(intent)
        }

        imgcuciac.setOnClickListener {
            val intent = Intent(context, ListOrderMitraCuciAc::class.java)
            startActivity(intent)
        }

        imgcekac.setOnClickListener {
            val intent = Intent(context, ListOrderMitraCekAc::class.java)
            startActivity(intent)
        }

        imgfreon.setOnClickListener {
            bottomSheetDialog?.show()
        }

        imgisifreon.setOnClickListener {
            bottomSheetDialog2?.show()
        }





    }
        companion object {
        fun newInstance(): FragmentNavPesananMitraAc{
            val fragment = FragmentNavPesananMitraAc()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }



}