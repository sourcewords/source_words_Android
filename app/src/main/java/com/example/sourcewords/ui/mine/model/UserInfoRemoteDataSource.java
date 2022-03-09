package com.example.sourcewords.ui.mine.model;

import com.example.sourcewords.commonUtils.NetUtil;
import com.example.sourcewords.ui.login.model.UserWrapper;
import com.example.sourcewords.ui.mine.model.databean.UserInfoResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoRemoteDataSource implements Api.getUserInfo{

    private static UserInfoRemoteDataSource INSTANCE;
    private UserInfoResponse.DataDTO u;
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

    @Override
    public void getUserStatus(LoadUserCallBack loadUserCallBack) {
        NetUtil.getInstance().getApi().getUserInfo(token).enqueue(new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
                if(response.isSuccessful()){
                    u = response.body().getData();
                    loadUserCallBack.onUserLoaded(u);
                } else
                    loadUserCallBack.onDataNotAvailable();
            }

            @Override
            public void onFailure(Call<UserInfoResponse> call, Throwable t) {
                loadUserCallBack.onFailure();
            }
        });
    }
}
