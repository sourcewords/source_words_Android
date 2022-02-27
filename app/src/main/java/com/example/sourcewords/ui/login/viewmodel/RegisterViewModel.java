package com.example.sourcewords.ui.login.viewmodel;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sourcewords.App;
import com.example.sourcewords.ui.login.model.RegisterDataSource;
import com.example.sourcewords.ui.login.model.databean.LoginUser;
import com.example.sourcewords.ui.login.model.databean.RegisterEmail;
import com.example.sourcewords.ui.login.model.databean.RegisterPage;
import com.example.sourcewords.ui.login.model.respository.LoginRemoteRespository;
import com.example.sourcewords.ui.login.model.respository.RegisterRemoteRespository;

public class RegisterViewModel extends ViewModel {

    public MutableLiveData<String> Email = new MutableLiveData<>();
    public MutableLiveData<String> VerificationCode = new MutableLiveData<>();
    public MutableLiveData<String> Pwd = new MutableLiveData<>();
    private MutableLiveData<RegisterPage> registerMutableLiveData;
    private Context mContext;
    private final RegisterRemoteRespository registerRemoteRespository;

    public RegisterViewModel(RegisterRemoteRespository repository, Context context){
        mContext = context.getApplicationContext();
        registerRemoteRespository = repository;
    }

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
        registerRemoteRespository.STATUS = false;
        RegisterPage registerPage = new RegisterPage(Email.getValue());
        registerMutableLiveData.setValue(registerPage);
        registerRemoteRespository.isSendCode(new RegisterEmail(Email.getValue()));
    }

    public void register(View view){
        registerRemoteRespository.STATUS = true;
        RegisterPage registerPage = new RegisterPage(Email.getValue(), VerificationCode.getValue(),Pwd.getValue());
        registerMutableLiveData.setValue(registerPage);
        if(registerRemoteRespository.code.equals(VerificationCode.getValue()) && !TextUtils.isEmpty(Pwd.getValue())){
            RegisterRemoteRespository.getINSTANCE().getRegisterStatus(new LoginUser(Email.getValue(), Pwd.getValue()), new RegisterDataSource.LoadRegisterCallBack() {
                @Override
                public void onRegisterLoaded() {

                }

                @Override
                public void onDataNotAvailable() {

                }

                @Override
                public void onFailure() {

                }
            });
        }
    }
}
