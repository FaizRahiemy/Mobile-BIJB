package com.volunteam.mobilebijb.parking;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.volunteam.mobilebijb.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryParkingAdapter extends RecyclerView.Adapter<HistoryParkingAdapter.HolderData>{

    private ArrayList<Parkir> parkirList;

    public HistoryParkingAdapter(ArrayList<Parkir> parkirList) {
        this.parkirList = parkirList;
    }

    @Override
    public HolderData onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parking, parent, false);
        HolderData holderData = new HolderData(v);
        return holderData;
    }

    @Override
    public void onBindViewHolder(HolderData holder, int position) {
        Parkir parkir = parkirList.get(position);
        holder.txTanggal.setText(parkir.getTglMParkir());
        holder.txKendaraan.setText(parkir.getKendaraan());
        holder.txDurasi.setText(parkir.getDurasi());
        if (parkir.getStatus().equals("0")){
            holder.txStatus1.setVisibility(View.VISIBLE);
            holder.txStatus2.setVisibility(View.GONE);
            holder.txStatus3.setVisibility(View.GONE);
        }else if (parkir.getStatus().equals("1")){
            holder.txStatus1.setVisibility(View.GONE);
            holder.txStatus2.setVisibility(View.VISIBLE);
            holder.txStatus3.setVisibility(View.GONE);
        }else{
            holder.txStatus1.setVisibility(View.GONE);
            holder.txStatus2.setVisibility(View.GONE);
            holder.txStatus3.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return parkirList.size();
    }

    public class HolderData extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txTanggal, txKendaraan, txDurasi, txStatus1, txStatus2, txStatus3;

        public HolderData(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            txTanggal = (TextView)itemView.findViewById(R.id.tx_tgl_parking);
            txKendaraan = (TextView)itemView.findViewById(R.id.tx_kendaraan_parking);
            txDurasi = (TextView)itemView.findViewById(R.id.tx_durasi_parking);
            txStatus1 = (TextView)itemView.findViewById(R.id.tx_status_booked);
            txStatus2 = (TextView)itemView.findViewById(R.id.tx_status_on);
            txStatus3 = (TextView)itemView.findViewById(R.id.tx_status_done);
        }

        @Override
        public void onClick(View v) {

        }
    }

}
