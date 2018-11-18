package com.volunteam.mobilebijb.merchandise;

import com.volunteam.mobilebijb.merchandise.pojo.MerchsItem;

import java.util.List;

public interface MerchandiseView {
    void setMerchandise(List<MerchsItem> merchsList);
    void onFailureGetMerchandise(String message);
}
