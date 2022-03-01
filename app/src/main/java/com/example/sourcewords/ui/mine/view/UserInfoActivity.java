package com.example.sourcewords.ui.mine.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.sourcewords.R;
import com.example.sourcewords.databinding.ActivityUserinfoBinding;
import com.example.sourcewords.ui.mine.model.Api;
import com.example.sourcewords.ui.mine.viewmodel.UserInfoViewModel;

public class UserInfoActivity extends AppCompatActivity {

    private UserInfoViewModel userInfoViewModel;
    private ActivityUserinfoBinding dataBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_userinfo);
        userInfoViewModel = new UserInfoViewModel();
        dataBinding.setLifecycleOwner(this);
        dataBinding.setUserInfoViewModel(userInfoViewModel);

        dataBinding.infoChangePwd.setOnClickListener(view -> {
            Intent intent = new Intent(this, ChangePwdActivity.class);
            startActivity(intent);
        });

        dataBinding.commit.setOnClickListener(v -> {
            userInfoViewModel.changeUserInfo(new Api.ChangeUserInfoApi() {
                @Override
                public void success() {
                    Toast.makeText(UserInfoActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void failed() {
                    Toast.makeText(UserInfoActivity.this, "出错啦！请检查网络设置~", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
