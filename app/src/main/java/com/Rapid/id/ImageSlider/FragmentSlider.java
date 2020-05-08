package com.Rapid.id.ImageSlider;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.Rapid.id.R;
import com.bumptech.glide.Glide;

public class FragmentSlider extends Fragment {
    private static final String ARG_PARAM1 = "params";

    private String imageUrls;


    public static FragmentSlider newInstance(String params) {
        FragmentSlider fragment = new FragmentSlider();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, params);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        imageUrls = getArguments().getString(ARG_PARAM1);

        View view = inflater.inflate(R.layout.fragment_slider_item, container, false);
        ImageView img = (ImageView) view.findViewById(R.id.img);


        Glide.with(getActivity())
                .load(imageUrls)
                .placeholder(R.drawable.backgroundkonsumen)
                .into(img);
        return view;

    }
}
//        Glide.with(getActivity())
//                .load(R.drawable.banersatu)
//                .load(R.drawable.banerdua)
//                .into(img);
//                return view;
//
//                }
