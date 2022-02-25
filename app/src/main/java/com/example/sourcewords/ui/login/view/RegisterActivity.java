package com.example.sourcewords.ui.login.view;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.sourcewords.R;
import com.example.sourcewords.databinding.ActivityLoginRegisterBinding;
import com.example.sourcewords.ui.login.model.databean.RegisterPage;
import com.example.sourcewords.ui.login.viewmodel.RegisterViewModel;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private RegisterViewModel registerViewModel;
    private ActivityLoginRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_login_register);
        binding.setLifecycleOwner(this);
        binding.setRegisterViewModel(registerViewModel);

        registerViewModel.getRegister().observe(this, new Observer<RegisterPage>() {
            @Override
            public void onChanged(RegisterPage registerPage) {
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
                } else{

                }
            }
        });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().
                    setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
}
