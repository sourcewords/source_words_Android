package com.example.sourcewords.commonUtils;

import androidx.lifecycle.MutableLiveData;


import com.example.sourcewords.ui.login.model.LoginResponse;
import com.example.sourcewords.ui.login.model.LoginUser;
import com.example.sourcewords.ui.mine.model.databean.UserInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface RetrofitApi {
    //login
    @POST("user/login")
    Call<LoginResponse> login (@Body LoginUser user);

    //userInfo
    @GET("user/info")
    Call<MutableLiveData<UserInfo>> getUserInfo(@Header("token") String token);

//    @PUT("user/info")
//    Call<Message> putUserInfo(@Body UserInfo userInfo);
}
