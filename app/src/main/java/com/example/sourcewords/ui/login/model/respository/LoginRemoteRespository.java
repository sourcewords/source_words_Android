package com.example.sourcewords.ui.login.model.respository;

import android.content.Intent;
import android.widget.Toast;

import com.example.sourcewords.App;
import com.example.sourcewords.commonUtils.NetUtil;
import com.example.sourcewords.ui.login.model.databean.LoginResponse;
import com.example.sourcewords.ui.login.model.databean.LoginUser;
import com.example.sourcewords.ui.login.view.LoginActivity;
import com.example.sourcewords.ui.main.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRemoteRespository {
    public void isLogin(LoginUser user){
        NetUtil.getInstance().getApi().login(user).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    //token = response.body().getData();
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent);
                }else {
                    Toast.makeText(App.getAppContext(),"账号或密码错误", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(App.getAppContext(),"网络连接失败",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
