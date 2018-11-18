package com.volunteam.mobilebijb.Travelling.TravellingActivity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.volunteam.mobilebijb.R;
import com.volunteam.mobilebijb.Travelling.PublicTransport.PublicTransportActivity;
import com.volunteam.mobilebijb.Travelling.PublicTransport.PublicTransportAdapter;
import com.volunteam.mobilebijb.Travelling.RuteActivity.RuteActivity;
import com.volunteam.mobilebijb.config.api.API;
import com.volunteam.mobilebijb.config.api.MainAPIHelper;
import com.volunteam.mobilebijb.travel.TravelActivity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class TravellingItemAdapter extends RecyclerView.Adapter<TravellingItemAdapter.HolderData> {

    private List<TravellingItemModel> postData;

    API api;

    public TravellingItemAdapter(List<TravellingItemModel> postData) {
        this.postData = postData;
    }

    @Override
    public TravellingItemAdapter.HolderData onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_travelling, parent, false);
        TravellingItemAdapter.HolderData holderData = new TravellingItemAdapter.HolderData(v);

        api = MainAPIHelper.getClient("").create(API.class);
        return holderData;
    }

    @Override
    public void onBindViewHolder(final TravellingItemAdapter.HolderData holder, int position) {

        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        formatter.setDecimalFormatSymbols(symbols);

        TravellingItemModel post = postData.get(position);
        holder.id = Integer.valueOf(post.getId());
        if (post.getImg().isEmpty()){
            Picasso.with(holder.itemView.getContext())
                    .load(R.color.colorPrimaryDark)
                    .error(android.R.color.transparent)
                    .into(holder.imgTravel);
        }else{
            Picasso.with(holder.itemView.getContext())
                    .load(post.getImg())
                    .fit()
                    .placeholder(R.color.colorPrimaryDark)
                    .error(android.R.color.transparent)
                    .into(holder.imgTravel);
        }
        holder.nameTravel.setText(post.getName());

    }

    @Override
    public int getItemCount() {
        return postData.size();
    }

    public class HolderData extends RecyclerView.ViewHolder implements View.OnClickListener{

        int id;
        ImageView imgTravel;
        TextView nameTravel;
        public Context context;

        public HolderData(View itemView) {
            super(itemView);
            context = itemView.getContext();
            itemView.setOnClickListener(this);
            imgTravel = (ImageView) itemView.findViewById(R.id.img_traveling);
            nameTravel = (TextView) itemView.findViewById(R.id.txt_nama_traveling);
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            Intent intent;
            if (id == 1){
                intent = new Intent(context, RuteActivity.class);
                context.startActivity(intent);
            }else if (id == 2){
                intent = new Intent(context, TravelActivity.class);
                context.startActivity(intent);
            }else if (id == 3){
                intent = new Intent(context, PublicTransportActivity.class);
                context.startActivity(intent);
            }
        }
    }

}

