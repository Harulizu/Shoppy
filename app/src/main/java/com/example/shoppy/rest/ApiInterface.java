package com.example.shoppy.rest;

import com.example.shoppy.respone.ResponseLogin;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseLogin> login(
      @Field("username") String username,
      @Field("password") String password
    );

    @FormUrlEncoded
    @POST("sign_up.php")
    Call<ResponseBody> sign_up(
            @Field("username") String username,
            @Field("password") String password,
            @Field("nama_lengkap") String nama_lengkap,
            @Field("email") String email,
            @Field("no_hp") String no_hp
    );
}
