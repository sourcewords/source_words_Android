package com.example.sourcewords.ui.login.viewmodel;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sourcewords.ui.login.model.LocalPage;

public class LoginViewModel extends ViewModel {

    public MutableLiveData<String> Account = new MutableLiveData<>();
    public MutableLiveData<String> Password = new MutableLiveData<>();
    public MutableLiveData<Boolean> checkBox = new MutableLiveData<>();

    private MutableLiveData<LocalPage> userMutableLiveData;

    public MutableLiveData<LocalPage> getUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }

    public void onClick(View view) {

        LocalPage localPage = new LocalPage(Account.getValue(), Password.getValue(),checkBox.getValue());

        userMutableLiveData.setValue(localPage);
    }

}
