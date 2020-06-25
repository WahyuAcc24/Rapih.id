package com.Rapih.id.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.Rapih.id.Faq.FaqActivity;
import com.Rapih.id.Faq.FaqDelapanActivity;
import com.Rapih.id.Faq.FaqDelapanBelasActivity;
import com.Rapih.id.Faq.FaqDuaActivity;
import com.Rapih.id.Faq.FaqDuaBelasActivity;
import com.Rapih.id.Faq.FaqDuaPuluhActivity;
import com.Rapih.id.Faq.FaqEmpatActivity;
import com.Rapih.id.Faq.FaqEnamActivity;
import com.Rapih.id.Faq.FaqEnamBelasActivity;
import com.Rapih.id.Faq.FaqLimaActivity;
import com.Rapih.id.Faq.FaqSebelasActivity;
import com.Rapih.id.Faq.FaqSembilanActivity;
import com.Rapih.id.Faq.FaqSembilanBelasActivity;
import com.Rapih.id.Faq.FaqSepuluhActivity;
import com.Rapih.id.Faq.FaqTigaActivity;
import com.Rapih.id.Faq.FaqTigaBelasActivity;
import com.Rapih.id.Faq.FaqTujuhActivity;
import com.Rapih.id.Faq.FaqTujuhBelasActivity;
import com.Rapih.id.Konsumen.BottomNav.FragmentNavBantuan;
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
            txtFaq = itemView.findViewById(R.id.txtitemfaq);
            item = itemView.findViewById(R.id.lnfaq);
            contexts = itemView.getContext();
            itemView.setClickable(true);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            final Intent intent;
            switch (getAdapterPosition()){
                case 0:
                    intent = new Intent(contexts, FaqActivity.class);
                    break;
                case 1:
                    intent = new Intent(contexts, FaqDuaActivity.class);
                    break;
                case 2:
                    intent = new Intent(contexts, FaqTigaActivity.class);
                    break;
                case 3:
                    intent = new Intent(contexts, FaqEmpatActivity.class);
                    break;
                case 4:
                    intent = new Intent(contexts, FaqLimaActivity.class);
                    break;
                case 5:
                    intent = new Intent(contexts, FaqEnamActivity.class);
                    break;
                case 6:
                    intent = new Intent(contexts, FaqTujuhActivity.class);
                    break;
                case 7:
                    intent = new Intent(contexts, FaqDelapanActivity.class);
                    break;
                case 8:
                    intent = new Intent(contexts, FaqSembilanActivity.class);
                    break;
                case 9:
                    intent = new Intent(contexts, FaqSepuluhActivity.class);
                    break;
                case 10:
                    intent = new Intent(contexts, FaqSebelasActivity.class);
                    break;
                case 11:
                    intent = new Intent(contexts, FaqDuaBelasActivity.class);
                    break;
                case 12:
                    intent = new Intent(contexts, FaqTigaBelasActivity.class);
                    break;
                case 13:
                    intent = new Intent(contexts, FaqEnamBelasActivity.class);
                    break;
                case 14:
                    intent = new Intent(contexts, FaqTujuhBelasActivity.class);
                    break;
                case 15:
                    intent = new Intent(contexts, FaqDelapanBelasActivity.class);
                    break;
                case 16:
                    intent = new Intent(contexts, FaqSembilanBelasActivity.class);
                    break;
                case 17:
                    intent = new Intent(contexts, FaqDuaPuluhActivity.class);
                    break;

                default:
                    intent = new Intent(contexts, FragmentNavBantuan.class);
                    break;
            }
            contexts.startActivity(intent);

        }

    }

}
