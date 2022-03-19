package com.example.sourcewords.ui.mine.view;

import static com.example.sourcewords.ui.mine.viewmodel.UserInfoViewModel.flag;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.example.sourcewords.R;
import com.example.sourcewords.commonUtils.SPUtils;
import com.example.sourcewords.databinding.ActivityUserinfoBinding;
import com.example.sourcewords.ui.login.view.LoginActivity;
import com.example.sourcewords.ui.mine.model.Api;
import com.example.sourcewords.ui.mine.model.databean.UserInfo;
import com.example.sourcewords.ui.mine.model.databean.UserWrapper;
import com.example.sourcewords.ui.mine.viewmodel.UserInfoViewModel;

public class UserInfoActivity extends AppCompatActivity{

    private UserInfoViewModel userInfoViewModel;
    private ActivityUserinfoBinding dataBinding;
    private UserInfo userInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_userinfo);
        userInfoViewModel = new UserInfoViewModel();
        ImageButton back = findViewById(R.id.info_back);
        Button girl = findViewById(R.id.info_female);
        Button boy = findViewById(R.id.info_male);

        dataBinding.setLifecycleOwner(this);
        dataBinding.setUserInfoViewModel(userInfoViewModel);

        userInfoViewModel.initW();
        userInfo = userInfoViewModel.getUserInfo().getValue();
        if(userInfo == null)
            userInfo = new UserInfo("1",1,"1","1","1","1","1");

        userInfoViewModel.getUserInfo().observe(this, new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo user) {
                if(user.getGender() == 1)   girlButton();
                else boyButton();

                dataBinding.commit.setOnClickListener(v ->
                {
                    initInfo();
                    userInfoViewModel.changeUserInfo(new Api.ChangeUserInfoApi() {
                        @Override
                        public void success() {
                            Toast.makeText(UserInfoActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void failed() {
                            Toast.makeText(UserInfoActivity.this, "出错啦！请检查网络设置~", Toast.LENGTH_SHORT).show();
                        }
                    }, userInfo);
                });
            }
        });

        dataBinding.infoChangePwd.setOnClickListener(view -> {
            Intent intent = new Intent(this, ChangePwdActivity.class);
            startActivity(intent);
        });

        dataBinding.loginOut.setOnClickListener((v) -> {
            SPUtils.getInstance(SPUtils.SP_CONFIG).clear();
            com.example.sourcewords.ui.login.model.UserWrapper.getInstance().setUser(null);
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        });

        back.setOnClickListener(v ->
                finish());

        girl.setOnClickListener(v -> {
            flag = 1;
            girlButton();
        });

        boy.setOnClickListener(v -> {
            flag = 2;
            boyButton();
        });

        getWindow().getDecorView().
                setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

    }

    public void initInfo() {
        userInfo.setName(dataBinding.infoName.getText().toString());
        userInfo.setBirthDay(dataBinding.infoBirthday.getText().toString());
        userInfo.setEmail(UserWrapper.getInstance().getName());
        userInfo.setLocation(dataBinding.infoZone.getText().toString());
        userInfo.setPhone(dataBinding.infoTelephone.getText().toString());
        userInfo.setSignature(dataBinding.infoSignature.getText().toString());
        userInfo.setGender(flag);
    }

    public void girlButton() {
        dataBinding.infoMale.setBackgroundColor(Color.WHITE);
        dataBinding.infoMale.setTextColor(Color.GRAY);
        dataBinding.infoFemale.setBackgroundColor(Color.parseColor("#8DD0CE"));
        dataBinding.infoFemale.setTextColor(Color.WHITE);
    }

    public void boyButton() {
        dataBinding.infoMale.setBackgroundColor(Color.parseColor("#8DD0CE"));
        dataBinding.infoMale.setTextColor(Color.WHITE);
        dataBinding.infoFemale.setBackgroundColor(Color.WHITE);
        dataBinding.infoFemale.setTextColor(Color.GRAY);
    }
}
