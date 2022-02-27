package com.example.sourcewords.commonUtils;

import androidx.lifecycle.MutableLiveData;


import com.example.sourcewords.ui.login.model.databean.LoginResponse;
import com.example.sourcewords.ui.login.model.databean.LoginUser;
import com.example.sourcewords.ui.login.model.databean.RegisterEmail;
import com.example.sourcewords.ui.login.model.databean.RegisterResponse;
import com.example.sourcewords.ui.mine.model.databean.UserInfo;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.ui.review.dataBean.WordRootStatus;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface RetrofitApi {
    //register
    @POST("user/email")
    Call<RegisterResponse> sendCode(@Body RegisterEmail email);

    @POST("user/register")
    Call<RegisterResponse> register(@Body LoginUser user);

    //login
    @POST("user/login")
    Call<LoginResponse> login (@Body LoginUser user);

    //userInfo
    @GET("user/info")
    Call<MutableLiveData<UserInfo>> getUserInfo(@Header("token") String token);

    //wordRoots
    @GET("/roots/list")
    Call<WordRoot> getWordRoot();

    @PUT("/roots/status")
    Call updateWordRootStatus(@Header("token") String token,
                              @Body WordRootStatus wordRootStatus);
//    @PUT("user/info")
//    Call<Message> putUserInfo(@Body UserInfo userInfo);

//    //chang-pwd
//    @PUT("user/reset")
//    Call<LoginResponse> changPwd(@Body )
}
