package com.volunteam.mobilebijb.myflight;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.volunteam.mobilebijb.R;

import java.util.List;

public class KursiAdapter extends RecyclerView.Adapter<KursiAdapter.HolderData> {

    private List<RowKursi> kursiList;

    public KursiAdapter(List<RowKursi> kursiList) {
        this.kursiList = kursiList;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_kursi, viewGroup, false);
        HolderData holderData = new HolderData(v);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holderData, int i) {
        RowKursi kursi = kursiList.get(i);
//        checkKursi(kursi.getKursis()[0], holderData.txC1Y, holderData.txC1N, "1");
//        checkKursi(kursi.getKursis()[1], holderData.txC2Y, holderData.txC2N, "2");
//        checkKursi(kursi.getKursis()[2], holderData.txC3Y, holderData.txC3N, "3");
//        checkKursi(kursi.getKursis()[3], holderData.txC4Y, holderData.txC4N, "4");
//        checkKursi(kursi.getKursis()[4], holderData.txC5Y, holderData.txC5N, "5");
//        checkKursi(kursi.getKursis()[5], holderData.txC6Y, holderData.txC6N, "6");
        holderData.txNum.setText(String.valueOf(i));
        holderData.pos = i;
    }

    @Override
    public int getItemCount() {
        return kursiList.size();
    }

    public class HolderData extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView txC1N, txC2N, txC3N, txC4N, txC5N, txC6N;
        public TextView txC1Y, txC2Y, txC3Y, txC4Y, txC5Y, txC6Y;
        public TextView txNum;
        public int pos;

        public HolderData(@NonNull View itemView) {
            super(itemView);
//            itemView.setOnClickListener(this);
            txNum = itemView.findViewById(R.id.tx_num_kursi);
            txC1N = itemView.findViewById(R.id.tx_c1n_kursi);
            txC2N = itemView.findViewById(R.id.tx_c2n_kursi);
            txC3N = itemView.findViewById(R.id.tx_c3n_kursi);
            txC4N = itemView.findViewById(R.id.tx_c4n_kursi);
            txC5N = itemView.findViewById(R.id.tx_c5n_kursi);
            txC6N = itemView.findViewById(R.id.tx_c6n_kursi);
            txC1Y = itemView.findViewById(R.id.tx_c1y_kursi);
            txC2Y = itemView.findViewById(R.id.tx_c2y_kursi);
            txC3Y = itemView.findViewById(R.id.tx_c3y_kursi);
            txC4Y = itemView.findViewById(R.id.tx_c4y_kursi);
            txC5Y = itemView.findViewById(R.id.tx_c5y_kursi);
            txC6Y = itemView.findViewById(R.id.tx_c6y_kursi);
            txC1N.setOnClickListener(this);
            txC2N.setOnClickListener(this);
            txC3N.setOnClickListener(this);
            txC4N.setOnClickListener(this);
            txC5N.setOnClickListener(this);
            txC6N.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tx_c1n_kursi:
                    checkKursi(kursiList.get(pos).getKursis()[0], txC1Y, txC1N, "0");
                    break;
                case R.id.tx_c2n_kursi:
                    checkKursi(kursiList.get(pos).getKursis()[1], txC2Y, txC2N, "1");
                    break;
                case R.id.tx_c3n_kursi:
                    checkKursi(kursiList.get(pos).getKursis()[2], txC3Y, txC3N, "2");
                    break;
                case R.id.tx_c4n_kursi:
                    checkKursi(kursiList.get(pos).getKursis()[3], txC4Y, txC4N, "3");
                    break;
                case R.id.tx_c5n_kursi:
                    checkKursi(kursiList.get(pos).getKursis()[4], txC5Y, txC5N, "4");
                    break;
                case R.id.tx_c6n_kursi:
                    checkKursi(kursiList.get(pos).getKursis()[5], txC6Y, txC6N, "5");
                    break;
            }
        }
    }

    private void switchStatus(TextView tx1, TextView tx2){
        tx1.setVisibility(View.VISIBLE);
        tx2.setVisibility(View.GONE);
    }

    private void checkKursi(Kursi kursi, TextView tx1, TextView tx2, String column){
        if (kursi.getColumn().equals(column)){
            if (kursi.getStatus().equals("0")){
                switchStatus(tx1,tx2);
            }
        }
    }
}
