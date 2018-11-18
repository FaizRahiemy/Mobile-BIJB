package com.volunteam.mobilebijb.home.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.volunteam.mobilebijb.R;
import com.volunteam.mobilebijb.detailMerchandise.DetailMerchandiseActivity;
import com.volunteam.mobilebijb.merchandise.pojo.MerchsItem;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MerchandiseHomeAdapter extends RecyclerView.Adapter<MerchandiseHomeAdapter.ViewHolder>{
    private List<MerchsItem> merchsList = new ArrayList<>();

    public MerchandiseHomeAdapter(List<MerchsItem> merchItem) {
        this.merchsList = merchItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_merchendise_home, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.setMarchendise(merchsList.get(i));
    }

    @Override
    public int getItemCount() {
        return merchsList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_product;
        private TextView txt_name_product;
        private TextView txt_price;
        private MerchsItem merchItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_product = itemView.findViewById(R.id.img_product);
            txt_name_product = itemView.findViewById(R.id.txt_name_product);
            txt_price = itemView.findViewById(R.id.txt_price);
        }

        public void setMarchendise(MerchsItem merchItems){
            DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
            DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
            symbols.setGroupingSeparator('.');
            formatter.setDecimalFormatSymbols(symbols);

            txt_name_product.setText(merchItems.getName());
            txt_price.setText("Rp " + formatter.format(Float.valueOf(merchItems.getHarga())));
            if (merchItems.getImage().isEmpty()){
                Picasso.with(itemView.getContext())
                        .load(R.color.colorPrimaryDark)
                        .error(android.R.color.transparent)
                        .into(img_product);
            }else{
                Picasso.with(itemView.getContext())
                        .load(merchItems.getImage())
                        .placeholder(R.color.colorPrimaryDark)
                        .error(android.R.color.transparent)
                        .fit()
                        .centerCrop()
                        .into(img_product);
            }
            this.merchItem = merchItems;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), DetailMerchandiseActivity.class);
                    intent.putExtra("id", merchItem.getId());
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
