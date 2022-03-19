package com.example.sourcewords.ui.mine.viewmodel;

import android.util.Base64;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sourcewords.commonUtils.NetUtil;
import com.example.sourcewords.ui.login.model.databean.LoginResponse;
import com.example.sourcewords.ui.login.model.databean.LoginUser;
import com.example.sourcewords.ui.mine.model.Api;
import com.example.sourcewords.ui.mine.model.databean.PassWord;
import com.example.sourcewords.ui.mine.model.databean.PutPwd;
import com.example.sourcewords.ui.mine.model.databean.UserWrapper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePwdViewModel extends ViewModel {

    public MutableLiveData<PassWord> password;

    public MutableLiveData<PassWord> getPassWord(){
        if(password == null){
            password = new MutableLiveData<>();
        }
        return password;
    }

    public String getOldpwd(){
        return com.example.sourcewords.ui.login.model.UserWrapper.getInstance().getUser().getPassword();
    }

    public void putChange(String newp, Api.ChangePwdApi callback){

        String encode = Base64.encodeToString(newp.getBytes(),Base64.DEFAULT);
        NetUtil.getInstance().getApi().resetPwd(new LoginUser(UserWrapper.getInstance().getName(),
                encode))
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        callback.success();
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        callback.failed();
                    }
                });
    }

}
