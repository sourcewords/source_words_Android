package com.example.sourcewords.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sourcewords.R;
import com.example.sourcewords.ui.mine.view.UserInfoActivity;

public class LoginActivity extends AppCompatActivity {

    private Button mBtRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_account);

        initView();
    }

    private void initView() {
        mBtRegister = findViewById(R.id.register_button);

        mBtRegister.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
    }

}
