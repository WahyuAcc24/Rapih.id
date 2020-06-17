package com.Rapih.id.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.Rapih.id.Model.FaqPay;
import com.Rapih.id.R;

import java.util.ArrayList;
import java.util.List;

public class FaqPayAdapter extends RecyclerView.Adapter<FaqPayAdapter.Holder>{

    List<FaqPay> faqpays;
    Context contexts;

   public FaqPayAdapter(Context contexts, ArrayList<FaqPay> faqpays){
       this.contexts = contexts;
       this.faqpays = faqpays;
   }

    @Override
    public FaqPayAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_faq_pay, parent, false));
    }

    @Override
    public void onBindViewHolder(FaqPayAdapter.Holder holder, int position) {

       FaqPay all = faqpays.get(position);
       holder.txtFaqPay.setText(all.jdlpembayaran);


    }

    @Override
    public int getItemCount() {
        return faqpays.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        TextView txtFaqPay;

        public Holder(View itemView) {
            super(itemView);
            txtFaqPay = (TextView) itemView.findViewById(R.id.txtitempay);


        }
    }

}
