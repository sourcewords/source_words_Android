package com.example.sourcewords.commonUtils;

import com.example.sourcewords.ui.mine.model.databean.UserInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;

public interface RetrofitApi {

    //userInfo
    @GET("user/info")
    Call<UserInfo> getUserInfo(@Header("token") String token);

//    @PUT("user/info")
//    Call<Message> putUserInfo(@Body UserInfo userInfo);
}
