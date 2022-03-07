package com.example.sourcewords.ui.login.model;

import com.example.sourcewords.ui.login.model.databean.LoginUser;

public interface LoginDataSource {
    interface LoadLoginCallBack{
        void onLoginLoded();

        void onDataNotAvailable();

        void onFailure();
    }

    void getLoginStatus(LoginUser user,LoadLoginCallBack loadLoginCallBack);
}
