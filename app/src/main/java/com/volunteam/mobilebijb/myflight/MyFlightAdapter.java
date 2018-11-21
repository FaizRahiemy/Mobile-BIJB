package com.volunteam.mobilebijb.myflight;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.volunteam.mobilebijb.R;

import java.util.List;

public class MyFlightAdapter extends RecyclerView.Adapter<MyFlightAdapter.HolderData>{

    private List<MyFlight> myFlightList;

    public MyFlightAdapter(List<MyFlight> myFlightList) {
        this.myFlightList = myFlightList;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_myflight, viewGroup, false);
        HolderData holderData = new HolderData(v);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holderData, int i) {
        MyFlight myFlight = myFlightList.get(i);
        holderData.idFlight = myFlight.getId();
        holderData.myFlight = myFlight;
        holderData.num = myFlight.getPenumpangList().size();
    }

    @Override
    public int getItemCount() {
        return myFlightList.size();
    }

    public class HolderData extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView txRute;
        public TextView txTglFlight;
        public TextView txJenisFlight;
        public TextView txStatus1, txStatus2, txStatus3;
        public String idFlight;
        public MyFlight myFlight;
        public int num;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            txRute = itemView.findViewById(R.id.tx_rute_flight);
            txTglFlight = itemView.findViewById(R.id.tx_tgl_flight);
            txJenisFlight = itemView.findViewById(R.id.tx_jenis_flight);
            txStatus1 = itemView.findViewById(R.id.tx_status_booked);
            txStatus2 = itemView.findViewById(R.id.tx_status_on);
            txStatus3 = itemView.findViewById(R.id.tx_status_done);
        }

        @Override
        public void onClick(View v) {
            // TODO to checkin page
            Intent intentDetailMyFlight = new Intent(v.getContext(), DetailMyFlightActivity.class);
            intentDetailMyFlight.putExtra("KODE_BOOKING", myFlight.getKode());
            intentDetailMyFlight.putExtra("AIRLINE", myFlight.getAirline());
            intentDetailMyFlight.putExtra("IMG_AIRLINE", myFlight.getImgAirline());
            intentDetailMyFlight.putExtra("TIME_DEPARTURE", myFlight.getTime_departure());
            intentDetailMyFlight.putExtra("TIME_ARRIVAL", myFlight.getTime_arrival());
            intentDetailMyFlight.putExtra("TGL_DEPARTURE", myFlight.getTgl_departure());
            intentDetailMyFlight.putExtra("TGL_ARRIVAL", myFlight.getTgl_arrival());
            intentDetailMyFlight.putExtra("FROM", myFlight.getFrom());
            intentDetailMyFlight.putExtra("TO", myFlight.getTo());
            intentDetailMyFlight.putExtra("STATUS", myFlight.getStatus());
            intentDetailMyFlight.putExtra("NUM", String.valueOf(num));
            v.getContext().startActivity(intentDetailMyFlight);
//            Intent intent = new Intent(v.getContext(),AddFlightActivity.class);
//            intent.putExtra("idFlight",idFlight);
//            v.getContext().startActivity(intent);
        }
    }
}
