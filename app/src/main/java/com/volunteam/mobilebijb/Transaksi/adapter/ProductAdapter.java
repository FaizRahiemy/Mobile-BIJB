package com.volunteam.mobilebijb.Transaksi.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.volunteam.mobilebijb.Transaksi.DaftarTransaksiActivity;
import com.volunteam.mobilebijb.Transaksi.pojo.DeleteCartResponse;
import com.volunteam.mobilebijb.Transaksi.pojo.Id.ProductsItem;
import com.volunteam.mobilebijb.config.TinyDB;
import com.volunteam.mobilebijb.config.api.API;
import com.volunteam.mobilebijb.config.api.MainAPIHelper;
import com.volunteam.mobilebijb.detailMerchandise.DetailMerchandiseActivity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    List<ProductsItem> merchsList;
    Context context;
    API api;

    public ProductAdapter(List<ProductsItem> merchsList, Context context) {
        this.merchsList = merchsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ProductAdapter.ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_prod_trans,viewGroup,false));
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
        private TextView deleteCart;

        private ProductsItem merchItem;
        API api;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            img_produk = itemView.findViewById(R.id.img_produk);
            txt_nama_produk = itemView.findViewById(R.id.txt_nama_produk);
            txt_harga_produk = itemView.findViewById(R.id.txt_harga_produk);
            deleteCart = itemView.findViewById(R.id.delete_cart);

            itemView.setOnClickListener(this);
        }

        public void setMerchandise(final ProductsItem merchItem){
            TinyDB tinyDB = new TinyDB(context);
            String token = tinyDB.getString("token");
            api = MainAPIHelper.getClient(token).create(API.class);

            DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
            DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
            symbols.setGroupingSeparator('.');
            formatter.setDecimalFormatSymbols(symbols);

            txt_nama_produk.setText(merchItem.getName() + ("( x") + merchItem.getJumlah() + ")");
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

            deleteCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(itemView.getContext())
                        .setTitle("Hapus Barang?")
                        .setMessage("Yakin akan menghapus " + merchItem.getName() + " dari keranjang?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                api.deleteCart(MainAPIHelper.key, merchItem.getCartId()).enqueue(new Callback<DeleteCartResponse>() {
                                    @Override
                                    public void onResponse(Call<DeleteCartResponse> call, Response<DeleteCartResponse> response) {
                                        if (response.body().getStatusCode() == 1){
                                            Toast.makeText(context, response.body().getStatus(), Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(itemView.getContext(), DaftarTransaksiActivity.class);
                                            itemView.getContext().startActivity(intent);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<DeleteCartResponse> call, Throwable t) {
                                        Toast.makeText(context, "Periksa koneksi internet anda!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
                }
            });
        }

        @Override
        public void onClick(View v) {
            Intent intentDetailProduk = new Intent(context, DetailMerchandiseActivity.class);
            intentDetailProduk.putExtra("id", merchItem.getId());
            context.startActivity(intentDetailProduk);
        }
    }
}
