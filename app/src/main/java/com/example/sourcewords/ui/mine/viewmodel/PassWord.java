package com.example.sourcewords.ui.mine.viewmodel;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.sourcewords.BR;

public class PassWord extends BaseObservable {

    @Bindable
    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
        notifyPropertyChanged(BR.oldPwd);
    }
    @Bindable
    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
        notifyPropertyChanged(BR.newPwd);
    }
    @Bindable
    public String getAgainPwd() {
        return againPwd;
    }

    public void setAgainPwd(String againPwd) {
        this.againPwd = againPwd;
        notifyPropertyChanged(BR.againPwd);
    }

    private String oldPwd, newPwd,againPwd;

    public PassWord(String old, String newp, String again){
        this.oldPwd = old;
        this.newPwd = newp;
        this.againPwd = again;
    }

}
