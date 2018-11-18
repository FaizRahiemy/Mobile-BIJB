package com.volunteam.mobilebijb.home.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.volunteam.mobilebijb.R;
import com.volunteam.mobilebijb.home.model.DummyFlightInfo;

import java.util.ArrayList;
import java.util.List;

public class MyFlightInfoAdapter extends RecyclerView.Adapter<MyFlightInfoAdapter.ViewHolder> {

    private List<DummyFlightInfo> dummyFlightInfoList = new ArrayList<>();

    public MyFlightInfoAdapter(List<DummyFlightInfo> dummyFlightInfoList) {
        this.dummyFlightInfoList = dummyFlightInfoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_flight_info, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.setFlightInfo(dummyFlightInfoList.get(i));
    }

    @Override
    public int getItemCount() {
        return dummyFlightInfoList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txt_schedule, txt_airlines, txt_flight_no, txt_status;
        private ImageView img_airlines;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_schedule = itemView.findViewById(R.id.txt_schedule);
            txt_airlines = itemView.findViewById(R.id.txt_airlines);
            txt_flight_no = itemView.findViewById(R.id.txt_flight_no);
            txt_status = itemView.findViewById(R.id.txt_status);
            img_airlines = itemView.findViewById(R.id.img_airlines);
        }

        public void setFlightInfo(DummyFlightInfo dummyFlightInfo){
            txt_schedule.setText(dummyFlightInfo.getSchedule());
            txt_airlines.setText(dummyFlightInfo.getAirlines());
            txt_flight_no.setText(dummyFlightInfo.getFlightNo());
            txt_status.setText(dummyFlightInfo.getStatus());
        }

        @Override
        public void onClick(View v) {

        }
    }
}
