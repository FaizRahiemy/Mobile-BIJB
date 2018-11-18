package com.volunteam.mobilebijb.Transaksi;

import com.volunteam.mobilebijb.merchandise.pojo.MerchsItem;

import java.util.List;

public interface CartView {
    void setMerchandise(List<MerchsItem> merchsList);
    void onFailureGetMerchandise(String message);
}
