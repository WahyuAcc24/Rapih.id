package com.Rapih.id.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.Rapih.id.Model.OrderKonsumen;
import com.Rapih.id.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.Holder>{

    private List<OrderKonsumen> historis;
    private ItemClickListener<OrderKonsumen> listener;

    public HistoryAdapter(List<OrderKonsumen> historis) {
        this.historis = historis;
    }

    public void setListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_order, parent, false));
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        holder.txtJp.setText(historis.get(position).getJenis_properti());
        holder.txtTgl.setText(historis.get(position).getWaktu_pengerjaan());
        holder.txtDp.setText(historis.get(position).getDetail_pekerjaan());


        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClicked(historis.get(position), position, v);
            }
        });

//        Glide.with(holder.imgFoto.getContext())
//                .load(urlFoto + votes.get(position).getImages())
//                .error(R.mipmap.logo_vote)
//                .placeholder(R.mipmap.logo_vote)
//                .into(holder.imgFoto);

    }

    @Override
    public int getItemCount() {
        return historis.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        private ImageView imgFoto;
        private TextView txtJp,  txtDp, txtTgl;
        private LinearLayout item;

        public Holder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.linearHistory);
//            imgFoto = (ImageView) itemView.findViewById(R.id.img_foto);
            txtJp = itemView.findViewById(R.id.txtjp);
            txtDp = itemView.findViewById(R.id.txtdp);
            txtTgl = itemView.findViewById(R.id.txttgl);

        }
    }




}
//class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.Holder>() {
//
//private var histories : List<OrderKonsumen>? = null
//private var listener : ItemClickListener<OrderKonsumen>? = null
//
//        fun HistoryAdapter(historis:List<OrderKonsumen>) {
//        this.histories = historis
//        }
//
//        fun setListener(listener:ItemClickListener) {
//        this.listener = listener
//        }
//
//
//
//
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.Holder {
//        return Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_order, parent, false))    }
//
//        override fun getItemCount(): Int {
//        }
//
//        override fun onBindViewHolder(holder: HistoryAdapter.Holder, position: Int) {
//
//        holder.txt_jp.setText(histories?.get(position)?.jenis_properti)
//
//
//        }
//
//class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//        var txt_jp : TextView = itemView.findViewById(R.id.txtjp)
//        var txt_dp : TextView = itemView.findViewById(R.id.txtdp)
//        var txt_wp : TextView = itemView.findViewById(R.id.txttgl)
//        var item_klik : LinearLayout = itemView.findViewById(R.id.linearHistory)
//
//
//
//        }
//
//        }