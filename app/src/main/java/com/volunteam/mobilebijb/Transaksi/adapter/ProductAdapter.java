package com.volunteam.mobilebijb.Transaksi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.volunteam.mobilebijb.R;
import com.volunteam.mobilebijb.Transaksi.pojo.Id.ProductsItem;
import com.volunteam.mobilebijb.detailMerchandise.DetailMerchandiseActivity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    List<ProductsItem> merchsList = new ArrayList<>();
    Context context;

    public ProductAdapter(List<ProductsItem> merchsList, Context context) {
        this.merchsList = merchsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ProductAdapter.ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_merchandise,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder viewHolder, int i) {
        viewHolder.setMerchandise(merchsList.get(i));
    }

    @Override
    public int getItemCount() {
        return merchsList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView img_produk;
        private TextView txt_nama_produk;
        private TextView txt_harga_produk;

        private ProductsItem merchItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_produk = itemView.findViewById(R.id.img_produk);
            txt_nama_produk = itemView.findViewById(R.id.txt_nama_produk);
            txt_harga_produk = itemView.findViewById(R.id.txt_harga_produk);
            itemView.setOnClickListener(this);
        }

        public void setMerchandise(ProductsItem merchItem){
            DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
            DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
            symbols.setGroupingSeparator('.');
            formatter.setDecimalFormatSymbols(symbols);

            txt_nama_produk.setText(merchItem.getName());
            txt_harga_produk.setText("Rp " + formatter.format(Float.valueOf(merchItem.getHarga())));
            if (merchItem.getImage().isEmpty()){
                Picasso.with(itemView.getContext())
                        .load(R.color.colorPrimaryDark)
                        .error(android.R.color.transparent)
                        .into(img_produk);
            }else{
                Picasso.with(itemView.getContext())
                        .load(merchItem.getImage())
                        .placeholder(R.color.colorPrimaryDark)
                        .error(android.R.color.transparent)
                        .centerCrop()
                        .fit()
                        .into(img_produk);
            }
            this.merchItem = merchItem;
        }

        @Override
        public void onClick(View v) {
            Intent intentDetailProduk = new Intent(context, DetailMerchandiseActivity.class);
            intentDetailProduk.putExtra("id", merchItem.getId());
            context.startActivity(intentDetailProduk);
        }
    }
}
