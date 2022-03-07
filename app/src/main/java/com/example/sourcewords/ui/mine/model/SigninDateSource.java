package com.example.sourcewords.ui.mine.model;


import com.example.sourcewords.commonUtils.NetUtil;
import com.example.sourcewords.ui.login.model.UserWrapper;
import com.example.sourcewords.ui.login.model.databean.LoginResponse;
import com.example.sourcewords.ui.mine.model.databean.SigninBean;
import com.example.sourcewords.ui.mine.model.databean.SigninDate;

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

    public void getAllSignDate(Api.getSignInApi api){
        NetUtil.getInstance().getApi().getAllSigninDate(token).enqueue(new Callback<SigninBean>() {
            @Override
            public void onResponse(Call<SigninBean> call, Response<SigninBean> response) {
                if(response.body()!= null){
                    signinBean = response.body();
                    api.success(signinBean);
                }
            }

            @Override
            public void onFailure(Call<SigninBean> call, Throwable t) {
                api.failed();
            }
        });
    }

    public void putSignInDate(SigninDate date, Api.putSignInApi api){
        NetUtil.getInstance().getApi().putTodaySignin(date, token).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
          //      if(response.code() == )
                api.success();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                api.failed();
            }
        });
    }
}
