package com.volunteam.mobilebijb.Transaksi;

import com.volunteam.mobilebijb.Transaksi.pojo.Id.ProductsItem;

import java.util.List;

public interface CartView {
    void setMerchandise(List<ProductsItem> merchsList);
    void onFailureGetMerchandise(String message);
}
