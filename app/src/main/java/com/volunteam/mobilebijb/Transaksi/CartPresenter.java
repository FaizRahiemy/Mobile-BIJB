package com.volunteam.mobilebijb.Transaksi;

import com.volunteam.mobilebijb.Transaksi.pojo.GetTransaksiResponse;
import com.volunteam.mobilebijb.Transaksi.pojo.ProductsItem;
import com.volunteam.mobilebijb.config.api.API;
import com.volunteam.mobilebijb.config.api.MainAPIHelper;
import com.volunteam.mobilebijb.detailMerchandise.pojo.GetMerchandiseDetailResponse;
import com.volunteam.mobilebijb.merchandise.MerchandiseView;
import com.volunteam.mobilebijb.merchandise.pojo.GetMerchandiseResponse;
import com.volunteam.mobilebijb.merchandise.pojo.MerchsItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartPresenter {
    private CartView merchandiseView;
    List<MerchsItem> merchList = new ArrayList<>();
    ProductsItem prod;

    public CartPresenter(CartView merchandiseView) {
        this.merchandiseView = merchandiseView;
    }

    public void getMerchandise(String token, String id){
        final API apiToken = MainAPIHelper.getClient(token).create(API.class);
        apiToken.getTransaksiId(MainAPIHelper.key, id).enqueue(new Callback<GetTransaksiResponse>() {
            @Override
            public void onResponse(Call<GetTransaksiResponse> call, Response<GetTransaksiResponse> response) {
                if (response.body().getStatusCode() == 1){
                    for (int i = 0; i < response.body().getTransaksi().get(0).getProducts().size(); i++){
                        prod = response.body().getTransaksi().get(0).getProducts().get(i);

                        final API api = MainAPIHelper.getClient("").create(API.class);
                        api.getMerchandise(MainAPIHelper.key).enqueue(new Callback<GetMerchandiseResponse>() {
                            @Override
                            public void onResponse(Call<GetMerchandiseResponse> call, Response<GetMerchandiseResponse> response) {
                                if (response.body().getStatusCode() == 1){
                                    for (int j = 0; j < response.body().getMerchs().size(); j++){
                                        if (response.body().getMerchs().get(j).getId() == prod.getId()){
                                            merchList.add(response.body().getMerchs().get(j));
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<GetMerchandiseResponse> call, Throwable t) {
                                merchandiseView.onFailureGetMerchandise(t.getMessage());
                            }
                        });
                    }
                    merchandiseView.setMerchandise(merchList);
                }
            }

            @Override
            public void onFailure(Call<GetTransaksiResponse> call, Throwable t) {
                merchandiseView.onFailureGetMerchandise(t.getMessage());
            }
        });
    }
}
