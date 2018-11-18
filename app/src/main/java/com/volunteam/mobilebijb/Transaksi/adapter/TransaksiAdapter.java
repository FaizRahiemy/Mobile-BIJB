package com.volunteam.mobilebijb.Transaksi.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.volunteam.mobilebijb.R;
import com.volunteam.mobilebijb.Transaksi.CartActivity;
import com.volunteam.mobilebijb.Transaksi.pojo.TransaksiItem;

import java.util.List;

public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.HolderData> {

    private List<TransaksiItem> listTransaksi;

    public TransaksiAdapter(List<TransaksiItem> listTransaksi) {
        this.listTransaksi = listTransaksi;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_transaksi, viewGroup, false);
        HolderData holderData = new HolderData(v);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holderData, int i) {
        TransaksiItem transaksi = listTransaksi.get(i);
        holderData.id = transaksi.getId();
        holderData.txNama.setText("Transaksi " + transaksi.getId());
        holderData.txTgl.setText(transaksi.getDate());
        holderData.txJumlah.setText(transaksi.getProducts().size() + " Jenis Barang");
        if (transaksi.getStatus().equals("0")){
            holderData.txStatus2.setVisibility(View.INVISIBLE);
            holderData.txStatus3.setVisibility(View.INVISIBLE);
        }else if (transaksi.getStatus().equals("1")){
            holderData.txStatus1.setVisibility(View.INVISIBLE);
            holderData.txStatus3.setVisibility(View.INVISIBLE);
        }else{
            holderData.txStatus1.setVisibility(View.INVISIBLE);
            holderData.txStatus2.setVisibility(View.INVISIBLE);
        }
        holderData.transaksi = listTransaksi.get(i);
    }

    @Override
    public int getItemCount() {
        return listTransaksi.size();
    }

    public class HolderData extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView txNama;
        public TextView txTgl;
        public TextView txJumlah;
        public TextView txStatus1, txStatus2, txStatus3;
        public String id;
        public TransaksiItem transaksi;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            txNama = itemView.findViewById(R.id.tx_nama_transaksi);
            txTgl = itemView.findViewById(R.id.tx_tgl_transaksi);
            txJumlah = itemView.findViewById(R.id.tx_jumlah_barang_transaksi);
            txStatus1 = itemView.findViewById(R.id.tx_status_checkout_transaksi);
            txStatus2 = itemView.findViewById(R.id.tx_status_payment_transaksi);
            txStatus3 = itemView.findViewById(R.id.tx_status_success_transaksi);
        }

        @Override
        public void onClick(View v) {
            // TODO to checkin page
            Intent intent = new Intent(v.getContext(), CartActivity.class);
            intent.putExtra("id", id);
            v.getContext().startActivity(intent);
        }
    }
}
