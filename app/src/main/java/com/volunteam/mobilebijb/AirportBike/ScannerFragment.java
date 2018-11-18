package com.volunteam.mobilebijb.AirportBike;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.Result;
import com.volunteam.mobilebijb.AirportBike.pojo.GetBikeResponse;
import com.volunteam.mobilebijb.config.api.API;
import com.volunteam.mobilebijb.config.api.MainAPIHelper;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScannerFragment extends Fragment implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    private ScannerFragment me;
    API api;
    String code;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mScannerView = new ZXingScannerView(getActivity());
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
        me = this;

        api = MainAPIHelper.getClient("").create(API.class);

        return mScannerView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        code = String.valueOf(rawResult.getText());
        api.checkBike(MainAPIHelper.key, String.valueOf(rawResult.getText())).enqueue(new Callback<GetBikeResponse>() {
            @Override
            public void onResponse(Call<GetBikeResponse> call, Response<GetBikeResponse> response) {
                if (response.body().getStatusCode() == 1){
                    Intent intent = new Intent(getContext(), AirportBikeDetailActivity.class);
                    intent.putExtra("idBike", code);
                    getContext().startActivity(intent);
                }else{
                    Toast.makeText(getContext(), response.body().getStatus(), Toast.LENGTH_SHORT).show();
                }
                mScannerView.setResultHandler(me);
                mScannerView.startCamera();
            }

            @Override
            public void onFailure(Call<GetBikeResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Periksa koneksi internet anda!", Toast.LENGTH_SHORT).show();
                mScannerView.startCamera();
            }
        });
    }

}
