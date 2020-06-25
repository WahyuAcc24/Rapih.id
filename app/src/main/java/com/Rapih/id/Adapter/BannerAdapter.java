package com.Rapih.id.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.Rapih.id.Model.Banner;
import com.Rapih.id.R;

import java.util.ArrayList;
import java.util.List;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.Holder>{

    List<Banner> banners;
    Context contexts;

   public BannerAdapter (Context contexts, ArrayList<Banner> banners){
       this.contexts = contexts;
       this.banners = banners;
   }

    @Override
    public BannerAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_baner_bawah, parent, false));
    }

    @Override
    public void onBindViewHolder(BannerAdapter.Holder holder, int position) {

       Banner all = banners.get(position);
       holder.imgBnsatu.setImageResource(all.bannerId);


    }

    @Override
    public int getItemCount() {
        return banners.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        ImageView imgBnsatu;
//                ,imgBndua,imgBntiga,imgBnempat,imgBnlima,imgBnenam;

        public Holder(View itemView) {
            super(itemView);
//            imgFoto = (ImageView) itemView.findViewById(R.id.img_foto);
            imgBnsatu = itemView.findViewById(R.id.bnsatu);
//            imgBndua = (ImageView) itemView.findViewById(R.id.bndua);
//            imgBntiga = (ImageView) itemView.findViewById(R.id.bntiga);
//            imgBnempat = (ImageView) itemView.findViewById(R.id.bnempat);
//            imgBnlima = (ImageView) itemView.findViewById(R.id.bnlima);
//            imgBnenam = (ImageView) itemView.findViewById(R.id.bnenam);


        }
    }

}
