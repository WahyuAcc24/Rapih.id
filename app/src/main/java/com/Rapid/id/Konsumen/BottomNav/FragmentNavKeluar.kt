package com.Rapid.id.Konsumen.BottomNav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.Rapid.id.R
import com.Rapid.id.util.Preferences

class FragmentNavKeluar : Fragment(){



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_keluar_bottom, container, false)

        Preferences.clearLoggedInNama(context)
        Preferences.clearLoggedInId(context)
        Preferences.clearLoggedInEmail(context)

    }



    companion object {
        fun newInstance(): FragmentNavKeluar{
            val fragment = FragmentNavKeluar()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}