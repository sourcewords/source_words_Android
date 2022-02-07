package com.example.sourcewords.ui.mine.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sourcewords.ui.mine.model.databean.UserInfo;

public class UserInfoViewModel extends ViewModel {

    public MutableLiveData<UserInfo> userInfo;

    public MutableLiveData<UserInfo> getUserInfo(){
        if(userInfo == null){
            userInfo = new MutableLiveData<>();
        }
        return userInfo;
    }
}
