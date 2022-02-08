package com.example.sourcewords.ui.mine.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sourcewords.ui.mine.model.databean.UserInfo;
import com.example.sourcewords.ui.mine.model.databean.UserInfoRemoteDataSource;

public class UserInfoViewModel extends ViewModel {

    public MutableLiveData<UserInfo> userInfo;
    UserInfoRemoteDataSource source;

    public MutableLiveData<UserInfo> getUserInfo(){
        if(userInfo == null){
            userInfo = new MutableLiveData<>();
            source.getRemoteUserInfo(userInfo);
        }
        return userInfo;
    }
}
