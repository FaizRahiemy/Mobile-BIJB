package com.volunteam.mobilebijb.travel;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.volunteam.mobilebijb.R;
import com.volunteam.mobilebijb.travelDetail.TravelDetailActivity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 * Created by iqbal on 5/10/2018.
 */

public class TravelAdapter extends RecyclerView.Adapter<TravelAdapter.HolderData> {

    private List<ResultTravel> travelList;

    public TravelAdapter(List<ResultTravel> travelList) {
        this.travelList = travelList;
    }

    @Override
    public HolderData onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_travel, parent, false);
        HolderData holderData = new HolderData(v);
        return holderData;
    }

    @Override
    public void onBindViewHolder(HolderData holder, int position) {
        ResultTravel travel = travelList.get(position);
        NumberFormat formatter = new DecimalFormat("#0.00");
        holder.txNamaEvent.setText(travel.getNama() + " (" + formatter.format(travel.getRadius()) + " KM)");
        holder.txDesc.setText(travel.getDescription());
        if (travel.getImage().isEmpty()){
            holder.imgPost.setImageResource(R.color.colorPrimaryDark);
        }else{
            Picasso.with(holder.itemView.getContext())
                    .load(travel.getImage())
                    .placeholder(R.color.colorPrimaryDark)
                    .error(android.R.color.transparent)
                    .into(holder.imgPost);
        }
        holder.txComment.setText(String.valueOf(travel.getKomentar()));
        holder.txReview.setText(String.valueOf(formatter.format(travel.getScore())));
        if (travel.getKategori()==1){
            holder.txHarga.setText("Rp "+travel.getHarga());
        }else {
            holder.txHarga.setVisibility(View.GONE);
        }
        if(travel.getKategori()>0){
            holder.layoutSK.setVisibility(View.GONE);
        }
        holder.id = Integer.valueOf(travel.getId());
    }

    @Override
    public int getItemCount() {
        return travelList.size();
    }

    public class HolderData extends RecyclerView.ViewHolder implements View.OnClickListener{

        int id;
        public TextView txNamaEvent;
        public TextView txTempat;
        public TextView txReview;
        public TextView txDesc;
        public TextView txHarga;
        public ImageView imgPost;
        public Context context;
        public TextView txComment;
        public LinearLayout layoutSK;

        public HolderData(View itemView) {
            super(itemView);
            context = itemView.getContext();
            itemView.setOnClickListener(this);
            txNamaEvent = (TextView) itemView.findViewById(R.id.tx_nama_event);
            txTempat = (TextView) itemView.findViewById(R.id.tx_tempat);
            txComment = (TextView) itemView.findViewById(R.id.tx_comment);
            txReview = (TextView) itemView.findViewById(R.id.tx_rate);
            txDesc = (TextView) itemView.findViewById(R.id.tx_desc);
            txHarga = (TextView) itemView.findViewById(R.id.tx_harga_event);
            imgPost = (ImageView) itemView.findViewById(R.id.image_poster);
            layoutSK = (LinearLayout) itemView.findViewById(R.id.layout_score_komen);
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            // TODO move to travel desc
            Intent intent = new Intent(context, TravelDetailActivity.class);
            intent.putExtra("idTravel", String.valueOf(id));
            context.startActivity(intent);
        }
    }

}
