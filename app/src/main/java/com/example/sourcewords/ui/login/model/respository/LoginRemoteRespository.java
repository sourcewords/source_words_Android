package com.example.sourcewords.ui.login.model.respository;

import android.widget.Toast;

import com.example.sourcewords.App;
import com.example.sourcewords.commonUtils.NetUtil;
import com.example.sourcewords.ui.login.model.LoginDataSource;
import com.example.sourcewords.ui.login.model.databean.LoginResponse;
import com.example.sourcewords.ui.login.model.databean.LoginUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRemoteRespository implements LoginDataSource {
    private static LoginRemoteRespository INSTANCE;

    public static LoginRemoteRespository getINSTANCE(){
        if (INSTANCE == null){
            INSTANCE = new LoginRemoteRespository();
        }
        return INSTANCE;
    }

    @Override
    public void getLoginStatus(LoginUser user, LoadLoginCallBack loadLoginCallBack) {
        NetUtil.getInstance().getApi().login(user).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    //token = response.body().getData();
                    loadLoginCallBack.onLoginLoded();
                }else {
                    loadLoginCallBack.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loadLoginCallBack.onFailure();
            }
        });
    }
}
