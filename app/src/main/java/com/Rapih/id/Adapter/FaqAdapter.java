package com.Rapih.id.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.Rapih.id.Model.Faq;
import com.Rapih.id.R;

import java.util.ArrayList;
import java.util.List;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.Holder>{

    List<Faq> faqs;
    Context contexts;

    private ItemClickListener listenerfaq;

    public void setListener(ItemClickListener listener) {
        this.listenerfaq = listener;
    }


    public FaqAdapter(Context contexts, ArrayList<Faq> faq){
       this.contexts = contexts;
       this.faqs = faq;
   }

    @Override
    public FaqAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_faq, parent, false));
    }

    @Override
    public void onBindViewHolder(FaqAdapter.Holder holder, final int position) {

       Faq all = faqs.get(position);
       holder.txtFaq.setText(all.judul);



    }

    @Override
    public int getItemCount() {
        return faqs.size();
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtFaq;
        LinearLayout item;

        public Holder(View itemView) {
            super(itemView);
            txtFaq = (TextView) itemView.findViewById(R.id.txtitemfaq);
            item = (LinearLayout) itemView.findViewById(R.id.lnfaq);
            contexts = itemView.getContext();
            itemView.setClickable(true);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            final Intent intent;
            switch (getAdapterPosition()){
                case 0:
                    NextFragment nextFrag= new NextFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.Layout_container, nextFrag, "findThisFragment")
                            .addToBackStack(null)
                            .commit();
            }

        }

    }

}
