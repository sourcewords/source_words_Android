package com.example.sourcewords.ui.login.view;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sourcewords.R;
import com.example.sourcewords.ui.main.MainActivity;

public class ForgetPwdActivity extends AppCompatActivity {
    private Button next;
    private EditText mail;

    ActivityResultLauncher launcher = registerForActivityResult(new ResultContract(), new ActivityResultCallback<String>() {
        @Override
        public void onActivityResult(String result) {
            Toast.makeText(ForgetPwdActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    });

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
            if(!Patterns.EMAIL_ADDRESS.matcher(mail.getText()).matches()){
                mail.setError("Enter a Valid E-mail Address");
                mail.requestFocus();
            }else {
                launcher.launch(true);
            }
        });
    }

    class ResultContract extends ActivityResultContract<Boolean, String> {
        @NonNull
        @Override
        public Intent createIntent(@NonNull Context context, Boolean input) {
            Intent intent = new Intent(ForgetPwdActivity.this, SetPwdActivity.class);
            intent.putExtra("b", input);
            return intent;
        }

        @Override
        public String parseResult(int resultCode, @Nullable Intent intent) {
            return intent.getStringExtra("s");
        }
    }
}
