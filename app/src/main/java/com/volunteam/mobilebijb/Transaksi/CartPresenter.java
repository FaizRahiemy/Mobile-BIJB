package com.volunteam.mobilebijb.Transaksi;

import com.volunteam.mobilebijb.Transaksi.pojo.Id.GetTransaksiIdResponse;
import com.volunteam.mobilebijb.Transaksi.pojo.Id.ProductsItem;
import com.volunteam.mobilebijb.config.api.API;
import com.volunteam.mobilebijb.config.api.MainAPIHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartPresenter {
    private CartView merchandiseView;
    List<ProductsItem> merchList = new ArrayList<>();

    public CartPresenter(CartView merchandiseView) {
        this.merchandiseView = merchandiseView;
    }

    public void getMerchandise(String token, String id){
        final API apiToken = MainAPIHelper.getClient(token).create(API.class);
        apiToken.getTransaksiId(MainAPIHelper.key, id).enqueue(new Callback<GetTransaksiIdResponse>() {
            @Override
            public void onResponse(Call<GetTransaksiIdResponse> call, Response<GetTransaksiIdResponse> response) {
                if (response.body().getStatusCode() == 1){
                    merchList.addAll(response.body().getTransaksi().getProducts());
                    merchandiseView.setMerchandise(merchList);
                }
            }

            @Override
            public void onFailure(Call<GetTransaksiIdResponse> call, Throwable t) {
                merchandiseView.onFailureGetMerchandise(t.getMessage());
            }
        });
    }
}
