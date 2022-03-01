package com.example.sourcewords.ui.login.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sourcewords.ui.login.model.LoginDataSource;
import com.example.sourcewords.ui.login.model.databean.LocalPage;
import com.example.sourcewords.ui.login.model.databean.LoginUser;
import com.example.sourcewords.ui.login.model.respository.LoginRemoteRespository;
import com.example.sourcewords.ui.login.view.LoginNavigator;
import com.example.sourcewords.ui.main.MainActivity;

public class LoginViewModel extends ViewModel {

    public MutableLiveData<String> Account = new MutableLiveData<>();
    public MutableLiveData<String> Password = new MutableLiveData<>();
    public MutableLiveData<Boolean> checkBox = new MutableLiveData<>();
    private MutableLiveData<LocalPage> userMutableLiveData;
    private Context mContext;
    private final LoginRemoteRespository loginRemoteRespository;
    private LoginNavigator loginNavigator;

    public MutableLiveData<String> getAccount() {
        return Account;
    }

    public MutableLiveData<String> getPassword() {
        return Password;
    }

    public MutableLiveData<Boolean> getCheckBox() {
        return checkBox;
    }

    public MutableLiveData<LocalPage> getUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }

        return userMutableLiveData;
    }

    public void onActivityCreated(LoginNavigator navigator) {
        loginNavigator = navigator;
    }

    public LoginViewModel(LoginRemoteRespository repository, Context context) {
        mContext = context.getApplicationContext();
        loginRemoteRespository = repository;
    }

    public void onClick(View view) {
        loginRemoteRespository.getLoginStatus(new LoginUser(Account.getValue(), Password.getValue()), new LoginDataSource.LoadLoginCallBack() {
            @Override
            public void onLoginLoded() {
                Intent intent = new Intent(mContext, MainActivity.class);
                mContext.startActivity(intent);
                loginNavigator.onSaveToken();
            }

            @Override
            public void onDataNotAvailable() {
                Toast.makeText(mContext,"账号或密码错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure() {
                Toast.makeText(mContext,"网络连接失败",Toast.LENGTH_SHORT).show();
            }
        });

        LocalPage localPage = new LocalPage(Account.getValue(), Password.getValue());
        userMutableLiveData.setValue(localPage);
    }

}
