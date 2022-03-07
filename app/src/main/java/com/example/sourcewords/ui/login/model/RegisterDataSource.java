package com.example.sourcewords.ui.login.model;

import com.example.sourcewords.ui.login.model.databean.LoginUser;

public interface RegisterDataSource {
    interface LoadRegisterCallBack{
        void onRegisterLoaded();

        void onDataNotAvailable();

        void onFailure();
    }

    void getRegisterStatus(LoginUser user, LoadRegisterCallBack loadRegisterCallBack);
}
