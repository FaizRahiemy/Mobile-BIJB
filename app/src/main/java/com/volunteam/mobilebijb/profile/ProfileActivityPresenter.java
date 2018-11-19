package com.volunteam.mobilebijb.profile;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.google.common.hash.Hashing;
import com.volunteam.mobilebijb.config.api.API;
import com.volunteam.mobilebijb.config.api.MainAPIHelper;
import com.volunteam.mobilebijb.home.HomeActivity;
import com.volunteam.mobilebijb.login.LoginActivity;
import com.volunteam.mobilebijb.login.LoginResponse;
import com.volunteam.mobilebijb.register.InsertResponse;

import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivityPresenter {

    private User user;
    private View view;

    public ProfileActivityPresenter(View view, String id, String token) {
        this.user = new User(id,token);
        this.view = view;
    }

    public void getUser(){
        view.showProgressBar();
        API api = MainAPIHelper.getClient(user.getToken()).create(API.class);
        Call<LoginResponse> call = api.showUser(user.getId());
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                try{
                    if (response.body().getStatus_code()== 1) {
                        // valid
                        user.setNama(response.body().getResult_login().getNama());
                        user.setEmail(response.body().getResult_login().getEmail());
                        user.setPassword(response.body().getResult_login().getPassword());
                        user.setPin(response.body().getResult_login().getPin());
                        user.setSaldo("Rp "+toStringNoDecimal(
                                response.body().getResult_login().getSaldo()
                        ));
                        view.showUserData(user);
                        view.hideProgressBar();
                    }else{
                        // not valid
                        view.hideProgressBar();
                        view.showMessage("Error when getting user information");
                    }
                }catch (Exception e){

                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    view.hideProgressBar();
                    view.showMessage("Error, please check your internet connection");
                }
            }
        });
    }

    public void updateUser(final User userParam){
        view.showProgressBar();
        String passwordHash = "";
        String pinHash = "";
        if (userParam.getNama().equals("")){userParam.setNama(user.getNama());}
        if (userParam.getEmail().equals("")){userParam.setEmail(user.getEmail());}
        if (userParam.getPassword().equals("")){
            userParam.setPassword(user.getPassword());
        }else{
		    passwordHash = Hashing.sha256()
                .hashString(userParam.getPassword().toString(), StandardCharsets.UTF_8)
                .toString();
                userParam.setPassword(passwordHash);
        }
        if (userParam.getPin().equals("")){
            userParam.setPin(user.getPin());
        }else{
            pinHash = Hashing.sha256()
                .hashString(userParam.getPin().toString(), StandardCharsets.UTF_8)
                .toString();
                userParam.setPin(pinHash);
        }
        API api = MainAPIHelper.getClient(user.getToken()).create(API.class);
        Call<InsertResponse> call = api.updateUser(
                user.getId(),userParam.getNama(),userParam.getEmail(),userParam.getPassword(),
                userParam.getPin()
        );
        call.enqueue(new Callback<InsertResponse>() {
            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                try{
                    if (response.body().getStatus_code().equals("1")) {
                        // valid
                        view.showMessage("Update successful");
                        getUser();
                    }else{
                        // not valid
                        view.hideProgressBar();
                        view.showMessage("Error when updating user information");
                    }
                }catch (Exception e){

                }
            }
            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    view.hideProgressBar();
                    view.showMessage("Error, please check your internet connection");
                }
            }
        });
        view.showUserData(user);
    }

    private String toStringNoDecimal(String value) {
        try {
            Double dValue = Double.parseDouble(value);
            DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
            formatter .applyPattern("#,###");
            return formatter.format(dValue).replace(",",".");
        }catch (Exception e){
            return value;
        }
    }

    public interface View{
        void showUserData(User user);
        void showProgressBar();
        void hideProgressBar();
        void showMessage(String message);
    }
}
