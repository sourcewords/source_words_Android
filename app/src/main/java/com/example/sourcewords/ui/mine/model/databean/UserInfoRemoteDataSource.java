package com.example.sourcewords.ui.mine.model.databean;

import androidx.lifecycle.MutableLiveData;

import com.example.sourcewords.commonUtils.NetUtil;

import java.util.concurrent.Callable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoRemoteDataSource {

    private static UserInfoRemoteDataSource INSTANCE;

    private final String token = UserWrapper.getInstance().getToken();

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

    public void getRemoteUserInfo(UserInfo myUserInfo) {

        UserInfo[] userInfo = new UserInfo[1];
        NetUtil.getInstance().getApi().getUserInfo(token)
                .enqueue(new Callback<UserInfo>() {
                    @Override
                    public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                        userInfo[0] = response.body();
                    }

                    @Override
                    public void onFailure(Call<UserInfo> call, Throwable t) {

                    }
                });
        myUserInfo = userInfo[0];
    }
}
