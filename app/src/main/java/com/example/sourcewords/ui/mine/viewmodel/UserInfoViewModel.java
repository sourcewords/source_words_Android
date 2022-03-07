package com.example.sourcewords.ui.mine.viewmodel;

import android.content.Intent;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sourcewords.commonUtils.NetUtil;
import com.example.sourcewords.commonUtils.SPUtils;
import com.example.sourcewords.ui.login.model.UserWrapper;
import com.example.sourcewords.ui.login.model.databean.LoginResponse;
import com.example.sourcewords.ui.login.model.respository.LoginRemoteRespository;
import com.example.sourcewords.ui.login.view.LoginActivity;
import com.example.sourcewords.ui.mine.model.Api;
import com.example.sourcewords.ui.mine.model.databean.UserInfo;
import com.example.sourcewords.ui.mine.model.UserInfoRemoteDataSource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoViewModel extends ViewModel {

    public MutableLiveData<UserInfo> userInfo;
    UserInfoRemoteDataSource source;
    private String token = UserWrapper.getInstance().getToken();

    public MutableLiveData<UserInfo> getUserInfo() {
        if(userInfo == null){
            userInfo = new MutableLiveData<>();
            source.getRemoteUserInfo();

        }
        return userInfo;
    }

    public void changeUserInfo(Api.ChangeUserInfoApi api, UserInfo userInfo){
        NetUtil.getInstance().getApi().putUserInfo(userInfo, token).enqueue(new Callback<LoginResponse>() {
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
}
