package com.example.sourcewords.ui.login.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sourcewords.commonUtils.KeyboardUtils;
import com.example.sourcewords.ui.login.model.RegisterDataSource;
import com.example.sourcewords.ui.login.model.databean.LoginUser;
import com.example.sourcewords.ui.login.model.databean.RegisterEmail;
import com.example.sourcewords.ui.login.model.databean.RegisterPage;
import com.example.sourcewords.ui.login.model.respository.RegisterRemoteRespository;
import com.example.sourcewords.ui.login.view.RegisterNavigator;

public class RegisterViewModel extends ViewModel {

    public MutableLiveData<String> Email = new MutableLiveData<>();
    public MutableLiveData<String> VerificationCode = new MutableLiveData<>();
    public MutableLiveData<String> Pwd = new MutableLiveData<>();
    private MutableLiveData<RegisterPage> registerMutableLiveData;
    private Context mContext;
    private final RegisterRemoteRespository registerRemoteRespository;
    private RegisterNavigator registerNavigator;

    public RegisterViewModel(RegisterRemoteRespository repository, Activity context){
        mContext = context;
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

    public void onActivityCreated(RegisterNavigator navigator) {
        registerNavigator = navigator;
    }

    public void sendCode(View view){
        registerRemoteRespository.STATUS = false;
        RegisterPage registerPage = new RegisterPage(Email.getValue());
        registerMutableLiveData.setValue(registerPage);
        KeyboardUtils.hideKeyboard((Activity) mContext);
        registerRemoteRespository.isSendCode(new RegisterEmail(Email.getValue()));
    }

    public void register(View view){
        registerRemoteRespository.STATUS = true;
        RegisterPage registerPage = new RegisterPage(Email.getValue(), VerificationCode.getValue(),Pwd.getValue());
        registerMutableLiveData.setValue(registerPage);
        KeyboardUtils.hideKeyboard((Activity) mContext);

        if(registerRemoteRespository.code.equals(VerificationCode.getValue()) && !TextUtils.isEmpty(Pwd.getValue())){
            String encode = Base64.encodeToString(Pwd.getValue().getBytes(),Base64.DEFAULT);
            RegisterRemoteRespository.getINSTANCE().getRegisterStatus(new LoginUser(Email.getValue(), encode), new RegisterDataSource.LoadRegisterCallBack() {
                @Override
                public void onRegisterLoaded() {
                    registerNavigator.onRegisterCompleted();
                    Toast.makeText(mContext.getApplicationContext(), "注册成功!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onDataNotAvailable() {
                    Toast.makeText(mContext.getApplicationContext(), "未知错误!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure() {
                    Toast.makeText(mContext.getApplicationContext(),"网络出问题啦!", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(!TextUtils.isEmpty(Pwd.getValue()) && !registerRemoteRespository.code.equals(VerificationCode.getValue())){
            Toast.makeText(mContext.getApplicationContext(), "请输入正确的验证码!", Toast.LENGTH_SHORT).show();
        }
    }
}
