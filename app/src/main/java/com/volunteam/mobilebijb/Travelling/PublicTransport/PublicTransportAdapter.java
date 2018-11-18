package com.volunteam.mobilebijb.Travelling.PublicTransport;

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
import com.volunteam.mobilebijb.Travelling.PublicTransport.pojo.Public.TransportItem;
import com.volunteam.mobilebijb.config.api.API;
import com.volunteam.mobilebijb.config.api.MainAPIHelper;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class PublicTransportAdapter extends RecyclerView.Adapter<PublicTransportAdapter.HolderData> {

    private List<TransportItem> postData;

    API api;

    public PublicTransportAdapter(List<TransportItem> postData) {
        this.postData = postData;
    }

    @Override
    public HolderData onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_public_transport, parent, false);
        HolderData holderData = new HolderData(v);

        api = MainAPIHelper.getClient("").create(API.class);
        return holderData;
    }

    @Override
    public void onBindViewHolder(final HolderData holder, int position) {

        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        formatter.setDecimalFormatSymbols(symbols);

        TransportItem post = postData.get(position);
        holder.id = Integer.valueOf(post.getId());
        if (post.getImages().isEmpty()){
            Picasso.with(holder.itemView.getContext())
                    .load(R.color.colorPrimaryDark)
                    .error(android.R.color.transparent)
                    .into(holder.imgTransport);
        }else{
            Picasso.with(holder.itemView.getContext())
                    .load(post.getImages())
                    .fit()
                    .placeholder(R.color.colorPrimaryDark)
                    .error(android.R.color.transparent)
                    .into(holder.imgTransport);
        }
        if (post.getName().length() > 17){
            String nama = "";
            for (int i = 0; i < 10; i++){
                nama += post.getName().charAt(i);
            }
            holder.nameTransport.setText(nama + "...");
        }else{
            holder.nameTransport.setText(post.getName());
        }

    }

    @Override
    public int getItemCount() {
        return postData.size();
    }

    public class HolderData extends RecyclerView.ViewHolder implements View.OnClickListener{

        int id;
        ImageView imgTransport;
        TextView nameTransport;
        public Context context;

        public HolderData(View itemView) {
            super(itemView);
            context = itemView.getContext();
            itemView.setOnClickListener(this);
            imgTransport = (ImageView) itemView.findViewById(R.id.img_transport);
            nameTransport = (TextView) itemView.findViewById(R.id.txt_nama_transport);
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            Intent intent = new Intent(context, TransportDetailActivity.class);
            intent.putExtra("id", String.valueOf(id));
            context.startActivity(intent);
        }
    }

}
