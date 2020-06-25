package com.Rapih.id.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.Rapih.id.FaqPembayaran.FaqPembayaranActivity;
import com.Rapih.id.FaqPembayaran.FaqPembayaranduaActivity;
import com.Rapih.id.Konsumen.BottomNav.FragmentNavBantuan;
import com.Rapih.id.Model.ChatUs;
import com.Rapih.id.Model.FaqPay;
import com.Rapih.id.R;

import java.util.ArrayList;
import java.util.List;

public class ChatUsAdapter extends RecyclerView.Adapter<ChatUsAdapter.Holder>{

    List<ChatUs> kontakkami;
    Context contexts;

   public ChatUsAdapter(Context contexts, ArrayList<ChatUs> kontakkamis){
       this.contexts = contexts;
       this.kontakkami = kontakkamis;
   }


    @Override
    public ChatUsAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kontak, parent, false));
    }

    @Override
    public void onBindViewHolder(ChatUsAdapter.Holder holder, int position) {

       ChatUs all = kontakkami.get(position);
       holder.txtChatUs.setText(all.jdlkontak);


    }

    @Override
    public int getItemCount() {
        return kontakkami.size();
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtChatUs;

        public Holder(View itemView) {
            super(itemView);
            txtChatUs = itemView.findViewById(R.id.txtitemkontak);
            contexts = itemView.getContext();
            itemView.setClickable(true);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            final Intent intent ;
            switch (getAdapterPosition()){
                case 0:
                    String nomor = "6281219789088";
                    String url = "https://api.whatsapp.com/send?phone="+nomor;
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.setPackage("com.whatsapp");
                    intent.setData(Uri.parse(url));
                    break;

                default:
                    intent = new Intent(contexts, FragmentNavBantuan.class);
                    break;
            }
            contexts.startActivity(intent);
        }

    }
}
