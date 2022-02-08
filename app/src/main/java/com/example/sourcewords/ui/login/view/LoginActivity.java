package com.example.sourcewords.ui.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.sourcewords.R;
import com.example.sourcewords.ui.login.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private Button btn_account = null;
    private Button btn_message;
    LoginViewModel mloginViewModel;
    private Button mBtRegister;
    private EditText mAccount;
    private EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_account);

        mloginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

    }

    private void initView() {
        mBtRegister = findViewById(R.id.register_button);
        mBtRegister.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });

        mAccount = findViewById(R.id.et_account);
        mPassword = findViewById(R.id.et_password);
    }

}
