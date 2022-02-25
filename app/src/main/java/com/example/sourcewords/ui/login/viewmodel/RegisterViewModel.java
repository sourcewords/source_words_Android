package com.example.sourcewords.ui.login.viewmodel;

import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sourcewords.App;
import com.example.sourcewords.ui.login.model.databean.RegisterEmail;
import com.example.sourcewords.ui.login.model.databean.RegisterPage;
import com.example.sourcewords.ui.login.model.respository.RegisterRemoteRespository;

public class RegisterViewModel extends ViewModel {

    public MutableLiveData<String> Email = new MutableLiveData<>();
    public MutableLiveData<String> VerificationCode = new MutableLiveData<>();
    public MutableLiveData<String> Pwd = new MutableLiveData<>();
    private MutableLiveData<RegisterPage> registerMutableLiveData;
    private RegisterRemoteRespository registerRemoteRespository;

    public MutableLiveData<String> getEmail() {
        return Email;
    }

    public MutableLiveData<String> getVerificationCode() {
        return VerificationCode;
    }

    public MutableLiveData<String> getPwd() {
        return Pwd;
    }

    public MutableLiveData<RegisterPage> getRegister() {
        if (registerMutableLiveData == null){
            registerMutableLiveData = new MutableLiveData<>();
        }

        return registerMutableLiveData;
    }

    public void sendCode(View view){
        if(registerRemoteRespository == null)
            registerRemoteRespository = new RegisterRemoteRespository();
        registerRemoteRespository.isRegister(new RegisterEmail(Email.getValue()));
    }

    public void register(View view){
        RegisterPage registerPage = new RegisterPage(Email.getValue(), VerificationCode.getValue(),Pwd.getValue());
        registerMutableLiveData.setValue(registerPage);
        if(VerificationCode.getValue().equals(RegisterRemoteRespository.getCode()))
            Toast.makeText(App.getAppContext(), "恭喜您注册成功！", Toast.LENGTH_SHORT).show();
    }
}
