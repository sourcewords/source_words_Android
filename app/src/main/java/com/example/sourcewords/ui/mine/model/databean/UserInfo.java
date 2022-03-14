package com.example.sourcewords.ui.mine.model.databean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.sourcewords.BR;

public class UserInfo extends BaseObservable {
    private String phone;
    private String signature;
    private String email;
    private Integer gender;
    private String location;
    private String name;
    private String birth_day;

    public UserInfo(String email,Integer gender,String phone, String signature, String location, String name, String birthDay) {
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.signature = signature;
        this.location = location;
        this.name = name;
        this.birth_day = birthDay;
    }

    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);
    }
    @Bindable
    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
        notifyPropertyChanged(BR.signature);
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    @Bindable
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
        notifyPropertyChanged(BR.location);
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getBirthDay() {
        return birth_day;
    }

    public void setBirthDay(String birthDay) {
        this.birth_day = birthDay;
        notifyPropertyChanged(BR.birthDay);
    }

}
