package com.example.sourcewords.ui.mine.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sourcewords.commonUtils.NetUtil;
import com.example.sourcewords.ui.login.model.databean.LoginResponse;
import com.example.sourcewords.ui.mine.model.Api;
import com.example.sourcewords.ui.mine.model.databean.UserInfo;
import com.example.sourcewords.ui.mine.model.UserInfoRemoteDataSource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoViewModel extends ViewModel {

    public MutableLiveData<UserInfo> userInfo;
    UserInfoRemoteDataSource source;

    public MutableLiveData<UserInfo> getUserInfo() {
        if(userInfo == null){
            userInfo = new MutableLiveData<>();
            source.getRemoteUserInfo(userInfo.getValue());

        }
        return userInfo;
    }

    public void changeUserInfo(Api.ChangeUserInfoApi api){
        NetUtil.getInstance().getApi().putUserInfo(new UserInfo()).enqueue(new Callback<LoginResponse>() {
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
