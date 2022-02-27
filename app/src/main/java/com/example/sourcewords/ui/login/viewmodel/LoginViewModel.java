package com.example.sourcewords.ui.login.viewmodel;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sourcewords.ui.login.model.databean.LocalPage;
import com.example.sourcewords.ui.login.model.databean.LoginUser;
import com.example.sourcewords.ui.login.model.respository.LoginRemoteRespository;

public class LoginViewModel extends ViewModel {

    public MutableLiveData<String> Account = new MutableLiveData<>();
    public MutableLiveData<String> Password = new MutableLiveData<>();
    public MutableLiveData<Boolean> checkBox = new MutableLiveData<>();
    public LoginRemoteRespository loginRemoteRespository;
    private MutableLiveData<LocalPage> userMutableLiveData;

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

    public void onClick(View view) {
        if(loginRemoteRespository == null)
            loginRemoteRespository = new LoginRemoteRespository();
        loginRemoteRespository.isLogin(new LoginUser(Account.getValue(), Password.getValue()));

        LocalPage localPage = new LocalPage(Account.getValue(), Password.getValue());
        userMutableLiveData.setValue(localPage);
    }

}
