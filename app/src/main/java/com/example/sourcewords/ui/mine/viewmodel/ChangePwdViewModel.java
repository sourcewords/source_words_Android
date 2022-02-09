package com.example.sourcewords.ui.mine.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sourcewords.ui.mine.model.databean.PassWord;

public class ChangePwdViewModel extends ViewModel {

    public MutableLiveData<PassWord> pwd;

    public MutableLiveData<PassWord> getPwd(){
        if(pwd == null){
            pwd = new MutableLiveData<PassWord>();
        }
        return pwd;
    }

}
