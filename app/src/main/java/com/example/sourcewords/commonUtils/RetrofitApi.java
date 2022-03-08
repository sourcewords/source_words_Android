package com.example.sourcewords.commonUtils;

import com.example.sourcewords.ui.login.model.databean.LoginResponse;
import com.example.sourcewords.ui.login.model.databean.LoginUser;
import com.example.sourcewords.ui.login.model.databean.RegisterEmail;
import com.example.sourcewords.ui.login.model.databean.RegisterResponse;
import com.example.sourcewords.ui.mine.model.databean.AddPlanBean;
import com.example.sourcewords.ui.mine.model.databean.PlanBean;
import com.example.sourcewords.ui.mine.model.databean.PlanItem;
import com.example.sourcewords.ui.mine.model.databean.PutPwd;
import com.example.sourcewords.ui.mine.model.databean.SigninBean;
import com.example.sourcewords.ui.mine.model.databean.SigninDate;
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

    @POST("user/registerbyemail")
    Call<RegisterResponse> registerByEmail(@Body LoginUser user);

//    //login
//    @POST("user/login")
//    Call<LoginResponse> login (@Body LoginUser user);

    //loginbyemail
    @POST("user/loginbyemail")
    Call<LoginResponse> loginByEmail (@Body LoginUser user);

    //resetPwd
    @PUT("user/resetbyemail")
    Call<LoginResponse> resetPwd(@Body LoginUser user);

    //userInfo
    @GET("user/info")
    Call<UserInfo> getUserInfo(@Header("token") String token);

    @PUT("user/info")
    Call<LoginResponse> putUserInfo(@Body UserInfo userInfo, @Header("Authorization") String token);

    //plan
    @GET("user/plan")
    Call<PlanItem> getMyPlan(@Header("Authorization")String token);

    @POST("user/plan?")
    Call<LoginResponse> changePlan(@Header("Authorization") String token, @Body AddPlanBean addPlanBean);

    //signin
    @POST("date/")
    Call<LoginResponse> putTodaySignin(@Body SigninDate data, @Header("Authorization")String token);

    @GET("date")
    Call<SigninBean> getAllSigninDate(@Header("Authorization")String token);

    //wordRoots
    @GET("/roots/list")
    Call<WordRoot> getWordRoot();

    @PUT("/roots/status")
    Call updateWordRootStatus(@Header("token") String token,
                              @Body WordRootStatus wordRootStatus);
//    @PUT("user/info")
//    Call<Message> putUserInfo(@Body UserInfo userInfo);

    //chang-pwd
    @PUT("user/reset")
    Call<LoginResponse> changPwd(@Body PutPwd newpwd);
}
