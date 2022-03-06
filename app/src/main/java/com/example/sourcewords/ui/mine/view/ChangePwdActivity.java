package com.example.sourcewords.ui.mine.view;

import android.os.Build;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;

import com.example.sourcewords.R;
import com.example.sourcewords.databinding.ActivityChangepwdBinding;
import com.example.sourcewords.ui.mine.model.Api;
import com.example.sourcewords.ui.mine.model.databean.PassWord;
import com.example.sourcewords.ui.mine.viewmodel.ChangePwdViewModel;

public class ChangePwdActivity extends AppCompatActivity {

    private ChangePwdViewModel myViewModel;
    private ActivityChangepwdBinding myDataBinding;
    String old, newp, again;
    private ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        myDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_changepwd);
        myViewModel = new ChangePwdViewModel();
        PassWord passWord = new PassWord("", "", "");
        myViewModel.getPassWord().setValue(passWord);

        MutableLiveData<PassWord> pwd = myViewModel.getPassWord();
        pwd.observe(this, pwd2 -> myDataBinding.setChangePwdViewModel(myViewModel));

        myDataBinding.confirmChangepwd.setOnClickListener(v->{
            initPwd();
            if(!old.equals(myViewModel.getOldpwd())){
                Toast.makeText(this,"请输入正确的旧密码！",Toast.LENGTH_SHORT).show();
            }
            else if(myViewModel.getPassWord().getValue().getNewPwd().length() < 5){
                Toast.makeText(this, "新密码长度太短，请重新设置！",Toast.LENGTH_SHORT).show();
            }
            else if(!newp.equals(again)){
                Toast.makeText(this, "两次输入的密码不一致哦！",Toast.LENGTH_SHORT).show();
            }
            else{
                myViewModel.putChange(new Api.ChangePwdApi() {
                    @Override
                    public void success() {
                        Toast.makeText(ChangePwdActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failed() {
                        Toast.makeText(ChangePwdActivity.this, "出错啦！请检查网络连接~", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        back = findViewById(R.id.pwd_back);
        back.setOnClickListener(v -> finish());

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().
                    setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
    void initPwd(){
        old = myDataBinding.oldPassword.getText().toString();
        newp = myDataBinding.newPassword.getText().toString();
        again = myDataBinding.againPassword.getText().toString();
    }

}
