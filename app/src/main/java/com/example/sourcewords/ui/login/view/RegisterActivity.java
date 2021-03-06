package com.example.sourcewords.ui.login.view;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.example.sourcewords.R;
import com.example.sourcewords.databinding.ActivityLoginRegisterBinding;
import com.example.sourcewords.ui.login.model.databean.RegisterPage;
import com.example.sourcewords.ui.login.model.respository.RegisterRemoteRespository;
import com.example.sourcewords.ui.login.viewmodel.RegisterViewModel;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity{

    private RegisterViewModel registerViewModel;
    private ActivityLoginRegisterBinding binding;
    CountTimer countTimer = new CountTimer(60000, 1000);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        registerViewModel = new RegisterViewModel(RegisterRemoteRespository.getINSTANCE(),this);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_login_register);
        binding.setLifecycleOwner(this);
        binding.setRegisterViewModel(registerViewModel);
        binding.btnCaptcha.setBackgroundColor(Color.parseColor("#9CDFDD"));
        registerViewModel.onActivityCreated(new RegisterNavigator() {
            @Override
            public void onRegisterCompleted() {
                finish();
            }
        });

        registerViewModel.getRegister().observe(this, new Observer<RegisterPage>() {
            @Override
            public void onChanged(RegisterPage registerPage) {
                if(!RegisterRemoteRespository.STATUS){
                    if (TextUtils.isEmpty(Objects.requireNonNull(registerPage).getEmail())) {
                        binding.etEmail.setError("Enter a Correct Address");
                        binding.etEmail.requestFocus();
                    } else if (!registerPage.isAccountValid()) {
                        binding.etEmail.setError("Enter a Valid E-mail Address");
                        binding.etEmail.requestFocus();
                    } else{
                        countTimer.start();
                        //countTimer.onFinish();
                    }
                } else {
                    if (TextUtils.isEmpty(Objects.requireNonNull(registerPage).getEmail())) {
                        binding.etEmail.setError("Enter a Correct Address");
                        binding.etEmail.requestFocus();
                    } else if (!registerPage.isAccountValid()) {
                        binding.etEmail.setError("Enter a Valid E-mail Address");
                        binding.etEmail.requestFocus();
                    } else if (TextUtils.isEmpty(Objects.requireNonNull(registerPage).getVercode())){
                        binding.etCode.setError("Enter a Corrrct Verification Code");
                        binding.etCode.requestFocus();
                    } else if (TextUtils.isEmpty(Objects.requireNonNull(registerPage).getPwd())) {
                        binding.etPwd.setError("Enter a Password");
                        binding.etPwd.requestFocus();
                    }
                }
            }
        });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().
                    setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    /**
     * ????????????????????????
     */
    class CountTimer extends CountDownTimer {

        public CountTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        /**
         * ????????????????????????
         *
         * @param millisUntilFinished
         */
        @SuppressLint("ResourceAsColor")
        @Override
        public void onTick(long millisUntilFinished) {
            binding.btnCaptcha.setClickable(false);
            binding.btnCaptcha.setText(millisUntilFinished / 1000 + "s");
            binding.btnCaptcha.setBackgroundColor(Color.GRAY);
            binding.btnCaptcha.setTextColor(Color.WHITE);
        }

        /**
         * ????????????????????????
         */
        @SuppressLint("ResourceAsColor")
        @Override
        public void onFinish() {
            //??????????????????????????????????????????
            binding.btnCaptcha.setBackgroundColor(Color.parseColor("#9CDFDD"));
            binding.btnCaptcha.setTextColor(Color.WHITE);
            binding.btnCaptcha.setText("??????");
            binding.btnCaptcha.setClickable(true);
        }
    }
}
