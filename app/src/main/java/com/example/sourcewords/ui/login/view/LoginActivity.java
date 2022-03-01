package com.example.sourcewords.ui.login.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.example.sourcewords.R;
import com.example.sourcewords.commonUtils.SPUtils;
import com.example.sourcewords.databinding.ActivityLoginAccountBinding;
import com.example.sourcewords.ui.login.model.databean.LocalPage;
import com.example.sourcewords.ui.login.model.respository.LoginRemoteRespository;
import com.example.sourcewords.ui.login.viewmodel.LoginViewModel;
import com.example.sourcewords.ui.main.MainActivity;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements LoginNavigator{

    private LoginViewModel loginViewModel;
    private ActivityLoginAccountBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_account);
        IsToken(SPUtils.getInstance("Token",0).getString("Token",null));

//        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        loginViewModel = new LoginViewModel(LoginRemoteRespository.getINSTANCE(),getApplicationContext());
        loginViewModel.onActivityCreated(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_account);
        binding.setLifecycleOwner(this);
        binding.setLoginViewModel(loginViewModel);

        loginViewModel.getUser().observe(this, new Observer<LocalPage>() {
            @Override
            public void onChanged(@Nullable LocalPage localPage) {

                if (TextUtils.isEmpty(Objects.requireNonNull(localPage).getName())) {
                    binding.etAccount.setError("Enter a Correct Address");
                    binding.etAccount.requestFocus();
                }
                else if (!localPage.isAccountValid()) {
                    binding.etAccount.setError("Enter a Valid E-mail Address");
                    binding.etAccount.requestFocus();
                }
                else if (TextUtils.isEmpty(Objects.requireNonNull(localPage).getPassword())) {
                    binding.etPassword.setError("Enter a Password");
                    binding.etPassword.requestFocus();
                }
//                检查密码是否大于5
//                else if (!loginUser.isPasswordLengthGreaterThan3()) {
//                    binding.etPassword.setError("Enter at least 6 Digit password");
//                    binding.etPassword.requestFocus();
//                }
//                检查是否勾选
//                else if(!localPage.isChecked()){
//                    binding.checkbox.setError("Please check");
//                    binding.checkbox.requestFocus();
//                }
                else {

                }

            }
        });

        binding.registerButton.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });

        binding.forgetpwdButton.setOnClickListener(v ->{
            startActivity(new Intent(this,ForgetPwdActivity.class));
        });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().
                    setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    public void IsToken(String token){
        if (token != null){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void onFinish() {
        this.finish();
    }
}
