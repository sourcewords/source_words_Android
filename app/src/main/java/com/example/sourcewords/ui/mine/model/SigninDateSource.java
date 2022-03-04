package com.example.sourcewords.ui.mine.model;


import com.example.sourcewords.commonUtils.NetUtil;
import com.example.sourcewords.ui.login.model.databean.LoginResponse;
import com.example.sourcewords.ui.mine.model.databean.SigninBean;
import com.example.sourcewords.ui.mine.model.databean.UserWrapper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SigninDateSource {
    private static SigninDateSource INSTANCE;
    private SigninBean signinBean;


    private final String token = UserWrapper.getInstance().getToken();

    public static SigninDateSource getInstance() {
        if(INSTANCE == null){
            synchronized (SigninDateSource.class) {
                if( INSTANCE == null){
                    INSTANCE = new SigninDateSource();
                }
            }
        }
        return INSTANCE;
    }

    private SigninDateSource (){}

    public void getAllSignDate(){
        NetUtil.getInstance().getApi().getAllSigninDate(token).enqueue(new Callback<SigninBean>() {
            @Override
            public void onResponse(Call<SigninBean> call, Response<SigninBean> response) {
                if(response.body()!= null){
                    signinBean = response.body();
                }
            }

            @Override
            public void onFailure(Call<SigninBean> call, Throwable t) {

            }
        });
    }

    public void putSignInDate(String date, Api.SignInApi api){
        NetUtil.getInstance().getApi().putTodaySignin(date).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                api.success();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                api.failed();
            }
        });
    }


    public SigninBean getSigninBean(){
        getAllSignDate();
        return signinBean;
    }
}
