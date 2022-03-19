package com.example.sourcewords.ui.login.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sourcewords.commonUtils.KeyboardUtils;
import com.example.sourcewords.commonUtils.SPUtils;
import com.example.sourcewords.ui.login.model.LoginDataSource;
import com.example.sourcewords.ui.login.model.User;
import com.example.sourcewords.ui.login.model.UserWrapper;
import com.example.sourcewords.ui.login.model.databean.LocalPage;
import com.example.sourcewords.ui.login.model.databean.LoginUser;
import com.example.sourcewords.ui.login.model.respository.LoginRemoteRespository;
import com.example.sourcewords.ui.login.view.LoginActivity;
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
        mContext = context;
        loginRemoteRespository = repository;
        checkBox.setValue(false);
    }

    public void onClick(View view) {
        LocalPage localPage = new LocalPage(Account.getValue(), Password.getValue(),checkBox.getValue());
        userMutableLiveData.setValue(localPage);

        if(LoginActivity.isCorrect){
            KeyboardUtils.hideKeyboard((Activity) mContext);
            String encode = Base64.encodeToString(Password.getValue().getBytes(),Base64.DEFAULT);

            loginRemoteRespository.getLoginStatus(new LoginUser(Account.getValue(), encode), new LoginDataSource.LoadLoginCallBack() {
                @Override
                public void onLoginLoded() {
                    User user=new User(Account.getValue(), Password.getValue(),loginRemoteRespository.getToken());
                    UserWrapper.getInstance().setUser(user);
                    mContext.startActivity(new Intent(mContext, MainActivity.class));
                    SPUtils.getInstance("Token").put("Token",loginRemoteRespository.getToken());
                    loginNavigator.onFinish();
                }

                @Override
                public void onDataNotAvailable() {
                    Toast.makeText(mContext,"邮箱或密码错误", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure() {
                    Toast.makeText(mContext,"网络连接失败",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
