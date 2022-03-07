package com.example.sourcewords.ui.login.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sourcewords.R;
import com.example.sourcewords.commonUtils.NetUtil;
import com.example.sourcewords.ui.login.model.databean.LoginResponse;
import com.example.sourcewords.ui.login.model.databean.LoginUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SetPwdActivity extends AppCompatActivity {
    private String s;
    private EditText oldPwd;
    private EditText newPwd;
    private Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pwd);

        initWidgets();

        Intent intent = getIntent();
        s = intent.getStringExtra("e-mail");

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().
                    setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    private void initWidgets() {
        oldPwd = findViewById(R.id.old_password);
        newPwd = findViewById(R.id.new_password);
        confirm = findViewById(R.id.confirm_button);

        confirm.setOnClickListener((v) -> {
            if(!oldPwd.getText().toString().equals(newPwd.getText().toString())){
                Toast.makeText(this,"两次密码不相同！",Toast.LENGTH_SHORT).show();
            }else{
                request(s);
            }
        });
    }

    private void request(String str){

        NetUtil.getInstance().getApi().resetPwd(new LoginUser(str,newPwd.getText().toString())).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                setResult(RESULT_OK, new Intent().putExtra("code", "successful"));
                Toast.makeText(SetPwdActivity.this,"修改成功！",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(SetPwdActivity.this,"失败！",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
