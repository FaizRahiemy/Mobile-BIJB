package com.volunteam.mobilebijb.config.api;

import com.volunteam.mobilebijb.AirportBike.pojo.GetBikeResponse;
import com.volunteam.mobilebijb.BookTicket.model.GetBandaraResponse;
import com.volunteam.mobilebijb.Transaksi.pojo.DeleteCartResponse;
import com.volunteam.mobilebijb.Transaksi.pojo.Id.GetTransaksiIdResponse;
import com.volunteam.mobilebijb.Transaksi.pojo.TambahCartResponse;
import com.volunteam.mobilebijb.Transaksi.pojo.User.GetTransaksiUserResponse;
import com.volunteam.mobilebijb.Travelling.PublicTransport.pojo.Public.PublicTransportResponse;
import com.volunteam.mobilebijb.Travelling.PublicTransport.pojo.detail.TransportDetailResponse;
import com.volunteam.mobilebijb.detailMerchandise.pojo.GetMerchandiseDetailResponse;
import com.volunteam.mobilebijb.entertainment.models.news.ResponseNewsApi;
import com.volunteam.mobilebijb.entertainment.models.movie.ResponseNowPlayingApi;
import com.volunteam.mobilebijb.entertainment.models.movie.ResponsePopularMovieApi;
import com.volunteam.mobilebijb.entertainment.models.trailer.ResponseTrailerMovieApi;
import com.volunteam.mobilebijb.merchandise.pojo.GetMerchandiseResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

import com.volunteam.mobilebijb.login.LoginResponse;
import com.volunteam.mobilebijb.parking.HisrotyResponse;
import com.volunteam.mobilebijb.register.InsertResponse;
import com.volunteam.mobilebijb.travel.TravelResponse;
import com.volunteam.mobilebijb.travelDetail.TravelDetailResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface API {
//    NEWS
    @GET("top-headlines")
    Call<ResponseNewsApi> getHeadlines(@Query("country") String country, @Query("apiKey") String apiKey);

//    MOVIES
    @GET("movie/popular")
    Call<ResponsePopularMovieApi> getPopularMovies(@Query("api_key") String api_key, @Query("language") String language, @Query("page") String page);

    @GET("movie/now_playing")
    Call<ResponseNowPlayingApi> getNowPlayingMovies(@Query("api_key") String api_key, @Query("language") String language, @Query("page") String page);

    @GET
    Call<ResponseTrailerMovieApi> getDetailMovie(@Url String url, @Query("api_key") String api_key);

//    MAIN
    @GET("getPublicTransport.php")
    Call<PublicTransportResponse> getPublicTransport(@Query("key") String key);

    @GET("getTransportDetail.php")
    Call<TransportDetailResponse> getTransportDetail(@Query("key") String key, @Query("id") String id);

    @GET("getMerchandise.php")
    Call<GetMerchandiseResponse> getMerchandise(@Query("key") String key);

    @GET("getMerchandiseDetail.php")
    Call<GetMerchandiseDetailResponse> getMerchandiseDetail(@Query("key") String key, @Query("id") String id);

    @GET("getBike.php")
    Call<GetBikeResponse> getBike(@Query("key") String key, @Query("code") String code);

    @GET("checkBike.php")
    Call<GetBikeResponse> checkBike(@Query("key") String key, @Query("code") String code);

    @GET("getBandara.php")
    Call<GetBandaraResponse> getBandara(@Query("key") String key);

    @FormUrlEncoded
    @POST("tambahCart.php")
    Call<TambahCartResponse> tambahCart(@Query("key") String key,
                                        @Field("user") String user,
                                        @Field("produk") String produk);

    @FormUrlEncoded
    @POST("deleteCart.php")
    Call<DeleteCartResponse> deleteCart(@Query("key") String key,
                                        @Field("id") String id);

    @FormUrlEncoded
    @POST("getTransaksiUser.php")
    Call<GetTransaksiUserResponse> getTransaksiUser(@Query("key") String key,
                                                    @Field("user") String user);

    @FormUrlEncoded
    @POST("getTransaksiId.php")
    Call<GetTransaksiIdResponse> getTransaksiId(@Query("key") String key,
                                                @Field("id") String id);

    @FormUrlEncoded
    @POST("register.php")
    Call<InsertResponse> register(@Field("nama") String nama,
                                  @Field("email") String email,
                                  @Field("password") String password);

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> login(@Field("email") String email,
                              @Field("password") String password);

    @FormUrlEncoded
    @POST("showUser.php")
    Call<LoginResponse> showUser(@Field("id") String id);

    @FormUrlEncoded
    @POST("updateUser.php")
    Call<InsertResponse> updateUser(@Field("id") String id,
                                    @Field("nama") String nama,
                                    @Field("email") String email,
                                    @Field("password") String password,
                                    @Field("pin") String pin);

    @FormUrlEncoded
    @POST("bookParking.php")
    Call<InsertResponse> bookParking(@Field("id") String id,
                                    @Field("tanggal_masuk") String tanggal_masuk,
                                    @Field("kendaraan") String kendaraan,
                                    @Field("parkir") String parkir,
                                    @Field("plat") String plat);

    @FormUrlEncoded
    @POST("showParking.php")
    Call<HisrotyResponse> showParking(@Field("id") String id);

    @FormUrlEncoded
    @POST("showTravel.php")
    Call<TravelResponse> showTravel(@Field("id") String id);

    @FormUrlEncoded
    @POST("getTraverDetail.php")
    Call<TravelDetailResponse> getTraverDetail(@Field("id") String id);
}
