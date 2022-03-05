package com.example.sourcewords.ui.login.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sourcewords.R;

public class ForgetPwdActivity extends AppCompatActivity {
    private Button next;
    private EditText mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);

        initWidgets();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().
                    setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    private void initWidgets(){
        mail = findViewById(R.id.mail_forget);
        next = findViewById(R.id.next_btn);
        next.setOnClickListener(v -> {
            startActivity(new Intent(this,SetPwdActivity.class));
        });
        if(!Patterns.EMAIL_ADDRESS.matcher(mail.getText()).matches()){
            mail.setError("Enter a Valid E-mail Address");
            mail.requestFocus();
        }else {

        }
    }
}
