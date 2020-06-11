package com.Rapid.id.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.Rapid.id.Model.Banner;
import com.Rapid.id.Model.Faq;
import com.Rapid.id.R;

import java.util.ArrayList;
import java.util.List;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.Holder>{

    List<Faq> faqs;
    Context contexts;

   public FaqAdapter(Context contexts, ArrayList<Faq> faq){
       this.contexts = contexts;
       this.faqs = faq;
   }

    @Override
    public FaqAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_faq, parent, false));
    }

    @Override
    public void onBindViewHolder(FaqAdapter.Holder holder, int position) {

       Faq all = faqs.get(position);
       holder.txtFaq.setText(all.judul);


    }

    @Override
    public int getItemCount() {
        return faqs.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        TextView txtFaq;

        public Holder(View itemView) {
            super(itemView);
            txtFaq = (TextView) itemView.findViewById(R.id.txtitemfaq);


        }
    }

}
