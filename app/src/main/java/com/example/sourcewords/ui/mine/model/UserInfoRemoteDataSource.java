package com.example.sourcewords.ui.mine.model;

import androidx.lifecycle.MutableLiveData;

import com.example.sourcewords.commonUtils.NetUtil;
import com.example.sourcewords.ui.mine.model.databean.UserInfo;
import com.example.sourcewords.ui.mine.model.databean.UserWrapper;

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

    public void getRemoteUserInfo(MutableLiveData<UserInfo> myUserInfo) {

        MutableLiveData<UserInfo>[] userInfo = new MutableLiveData[1];
        NetUtil.getInstance().getApi().getUserInfo(token)
                .enqueue(new Callback<MutableLiveData<UserInfo>>() {
                    @Override
                    public void onResponse(Call<MutableLiveData<UserInfo>> call, Response<MutableLiveData<UserInfo>> response) {
                        userInfo[0] = response.body();
                    }

                    @Override
                    public void onFailure(Call<MutableLiveData<UserInfo>> call, Throwable t) {

                    }
                });
        myUserInfo = userInfo[0];
    }
}
