package com.volunteam.mobilebijb.merchandise;

import com.volunteam.mobilebijb.config.api.API;
import com.volunteam.mobilebijb.config.api.MainAPIHelper;
import com.volunteam.mobilebijb.merchandise.pojo.GetMerchandiseResponse;
import com.volunteam.mobilebijb.merchandise.pojo.MerchsItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MerchandisePresenter {
    private MerchandiseView merchandiseView;

    public MerchandisePresenter(MerchandiseView merchandiseView) {
        this.merchandiseView = merchandiseView;
    }

    public void getMerchandise(){
        final API api = MainAPIHelper.getClient("").create(API.class);
        api.getMerchandise(MainAPIHelper.key).enqueue(new Callback<GetMerchandiseResponse>() {
            @Override
            public void onResponse(Call<GetMerchandiseResponse> call, Response<GetMerchandiseResponse> response) {
                List<MerchsItem> merchList = new ArrayList<>();
                if (response.body().getStatusCode() == 1){
                    for (int i = 0; i < response.body().getMerchs().size(); i++){
                        merchList.add(response.body().getMerchs().get(i));
                    }
                    merchandiseView.setMerchandise(merchList);
                }
            }

            @Override
            public void onFailure(Call<GetMerchandiseResponse> call, Throwable t) {
                merchandiseView.onFailureGetMerchandise(t.getMessage());
            }
        });
    }
}
