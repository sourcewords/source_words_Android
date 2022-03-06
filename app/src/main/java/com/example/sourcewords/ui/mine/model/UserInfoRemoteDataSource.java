package com.example.sourcewords.ui.mine.model;

import androidx.lifecycle.MutableLiveData;

import com.example.sourcewords.commonUtils.NetUtil;
import com.example.sourcewords.ui.login.model.respository.LoginRemoteRespository;
import com.example.sourcewords.ui.mine.model.databean.UserInfo;
import com.example.sourcewords.ui.mine.model.databean.UserWrapper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoRemoteDataSource {

    private static UserInfoRemoteDataSource INSTANCE;
    private UserInfo userInfo = new UserInfo();
    private final String token = LoginRemoteRespository.getINSTANCE().getToken();

    public static UserInfoRemoteDataSource getInstance(){
        if(INSTANCE == null){
            synchronized (UserInfoRemoteDataSource.class){
                if(INSTANCE == null){
                    INSTANCE = new UserInfoRemoteDataSource();
                }
            }
        }
        return INSTANCE;
    }

    private UserInfoRemoteDataSource(){};

    public void getRemoteUserInfo() {
        NetUtil.getInstance().getApi().getUserInfo(token)
                .enqueue(new Callback<UserInfo>() {
                    @Override
                    public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                        if(response.body() != null){
                            userInfo = response.body();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserInfo> call, Throwable t) {

                    }
                });
    }
}
