package com.example.sourcewords.ui.mine.viewmodel;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sourcewords.commonUtils.NetUtil;
import com.example.sourcewords.ui.login.model.databean.LoginResponse;
import com.example.sourcewords.ui.mine.model.Api;
import com.example.sourcewords.ui.mine.model.PasswordDataSource;
import com.example.sourcewords.ui.mine.model.databean.PassWord;
import com.example.sourcewords.ui.mine.model.databean.PutPwd;
import com.example.sourcewords.ui.mine.model.databean.UserWrapper;

import java.util.Objects;

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
        return PasswordDataSource.getInstance().getPwd();
    }

    public void putChange(Api.ChangePwdApi callback){
        String name = UserWrapper.getInstance().getName();
        NetUtil.getInstance().getApi().changPwd(new PutPwd(UserWrapper.getInstance().getName(),
                Objects.requireNonNull(getPassWord().getValue()).getAgainPwd()))
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
