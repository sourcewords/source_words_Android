package com.example.sourcewords.ui.login.model.respository;

import android.widget.Toast;

import com.example.sourcewords.App;
import com.example.sourcewords.commonUtils.NetUtil;
import com.example.sourcewords.ui.login.model.databean.RegisterEmail;
import com.example.sourcewords.ui.login.model.databean.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterRemoteRespository {
    public static String code = " ";
    private static RegisterRemoteRespository INSTANCE;
    public static Boolean STATUS = false;

    public static RegisterRemoteRespository getINSTANCE(){
        if (INSTANCE == null){
            INSTANCE = new RegisterRemoteRespository();
        }
        return INSTANCE;
    }

    public void isRegister(RegisterEmail email){
        NetUtil.getInstance().getApi().register(email).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if(response.isSuccessful()){
                    code = String.valueOf(response.body().getData());
                }else{
                    Toast.makeText(App.getAppContext(), "邮箱输入错误!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(App.getAppContext(),"网络出问题啦!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
