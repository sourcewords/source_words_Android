package com.example.sourcewords.ui.mine.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.example.sourcewords.R;
import com.example.sourcewords.commonUtils.SPUtils;
import com.example.sourcewords.databinding.ActivityUserinfoBinding;
import com.example.sourcewords.ui.login.view.LoginActivity;
import com.example.sourcewords.ui.mine.model.Api;
import com.example.sourcewords.ui.mine.model.databean.UserInfo;
import com.example.sourcewords.ui.mine.model.databean.UserWrapper;
import com.example.sourcewords.ui.mine.viewmodel.UserInfoViewModel;

public class UserInfoActivity extends AppCompatActivity {

    private UserInfoViewModel userInfoViewModel;
    private ActivityUserinfoBinding dataBinding;
    private UserInfo userInfo = new UserInfo();

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
        loginOut();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().
                    setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

    }
    public void initInfo(){
        userInfo.setName(dataBinding.infoName.getText().toString());
        userInfo.setBirthDay(dataBinding.infoBirthday.getText().toString());

        userInfo.setEmail(UserWrapper.getInstance().getName());
        userInfo.setLocation(dataBinding.infoZone.getText().toString());
        userInfo.setPhone(dataBinding.infoTelephone.getText().toString());
        userInfo.setSignature(dataBinding.infoSignature.getText().toString());
    }

    public void loginOut(){
        dataBinding.loginOut.setOnClickListener((v) -> {
            SPUtils.getInstance(SPUtils.SP_CONFIG).clear();
            com.example.sourcewords.ui.login.model.UserWrapper.getInstance().setUser(null);
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        });
    }

    public void girlButton(View v){
        userInfo.setGender(0);
        dataBinding.infoMale.setBackgroundColor(Color.WHITE);
        dataBinding.infoMale.setTextColor(Color.GRAY);
        dataBinding.infoFemale.setBackgroundColor(Color.parseColor("#8DD0CE"));
        dataBinding.infoFemale.setTextColor(Color.WHITE);
    }

    public void boyButton(View v){
        userInfo.setGender(1);
        dataBinding.infoMale.setBackgroundColor(Color.parseColor("#8DD0CE"));
        dataBinding.infoMale.setTextColor(Color.WHITE);
        dataBinding.infoFemale.setBackgroundColor(Color.WHITE);
        dataBinding.infoFemale.setTextColor(Color.GRAY);
    }

}
