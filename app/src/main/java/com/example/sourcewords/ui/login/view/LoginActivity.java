package com.example.sourcewords.ui.login.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.sourcewords.R;
import com.example.sourcewords.commonUtils.NetUtil;
import com.example.sourcewords.databinding.ActivityLoginAccountBinding;
import com.example.sourcewords.ui.login.model.LocalPage;
import com.example.sourcewords.ui.login.model.LoginResponse;
import com.example.sourcewords.ui.login.model.LoginUser;
import com.example.sourcewords.ui.login.viewmodel.LoginViewModel;
import com.example.sourcewords.ui.main.MainActivity;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginAccountBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_account);

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

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
//                else if (!loginUser.isAccountValid()) {
//                    binding.etAccount.setError("Enter a Valid E-mail Address");
//                    binding.etAccount.requestFocus();
//                }
                else if (TextUtils.isEmpty(Objects.requireNonNull(localPage).getPassword())) {
                    binding.etPassword.setError("Enter a Password");
                    binding.etPassword.requestFocus();
                }
//                else if (!loginUser.isPasswordLengthGreaterThan3()) {
//                    binding.etPassword.setError("Enter at least 6 Digit password");
//                    binding.etPassword.requestFocus();
//                }
                else if(!localPage.isChecked()){
                    binding.checkbox.setError("Please check");
                    binding.checkbox.requestFocus();
                }
                else {
                    rq(new LoginUser(localPage.getName(),localPage.getPassword()));
                }

            }
        });


        binding.registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().
                    setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    private void rq(LoginUser user){
        NetUtil.getInstance().getApi().login(user).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    //token = response.body().getData();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(LoginActivity.this,"账号或密码错误", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"网络连接失败",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
