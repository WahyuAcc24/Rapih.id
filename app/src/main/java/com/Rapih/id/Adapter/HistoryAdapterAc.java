package com.Rapih.id.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.Rapih.id.Model.OrderKonsumen;
import com.Rapih.id.Model.OrderKonsumenAc;
import com.Rapih.id.R;

import java.util.List;

public class HistoryAdapterAc extends RecyclerView.Adapter<HistoryAdapterAc.Holder>{

    private List<OrderKonsumenAc> historiac;
    private ItemClickListener<OrderKonsumenAc> listenerac;

    public HistoryAdapterAc(List<OrderKonsumenAc> historiac) {
        this.historiac = historiac;
    }

    public void setListener(ItemClickListener listener) {
        this.listenerac = listener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_order_ac, parent, false));
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        holder.txtJpAc.setText(historiac.get(position).getJenis_properti());
        holder.txtTglAc.setText(historiac.get(position).getTanggal_pengerjaan());
        holder.txtDpAc.setText(historiac.get(position).getDeskripsi_pekerjaan());
        holder.txtStatus.setText(historiac.get(position).getStatus());


        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerac.onClicked(historiac.get(position), position, v);
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
        return historiac.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        private TextView txtJpAc,  txtDpAc, txtTglAc, txtStatus;
        private LinearLayout item;

        public Holder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.linearHistoryac);
//            imgFoto = (ImageView) itemView.findViewById(R.id.img_foto);
            txtJpAc = itemView.findViewById(R.id.txtjpAc);
            txtDpAc = itemView.findViewById(R.id.txtdpAc);
            txtTglAc = itemView.findViewById(R.id.txttglac);
            txtStatus = itemView.findViewById(R.id.txtstatuskonsac);

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