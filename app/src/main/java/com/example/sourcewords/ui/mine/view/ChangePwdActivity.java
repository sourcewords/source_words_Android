package com.example.sourcewords.ui.mine.view;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;

import com.example.sourcewords.R;
import com.example.sourcewords.databinding.ActivityChangepwdBinding;
import com.example.sourcewords.ui.mine.viewmodel.ChangePwdViewModel;
import com.example.sourcewords.ui.mine.model.databean.PassWord;

public class ChangePwdActivity extends AppCompatActivity {

    private ChangePwdViewModel myViewModel;
    private ActivityChangepwdBinding myDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        myDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_changepwd);
        myViewModel = new ChangePwdViewModel();
        PassWord pwd= new PassWord("****", "****","****");
        myViewModel.getPwd().setValue(pwd);
        MutableLiveData<PassWord> pwd1 = myViewModel.getPwd();
        pwd1.observe(this, pwd2 -> myDataBinding.setChangePwdViewModel(myViewModel));

        myDataBinding.confirmChangepwd.setOnClickListener(v->{
            //TODO:判断密码是否为空；判断新旧密码是否相同；判断两次输入是否一致
            //进行网络请求put新密码
        });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().
                    setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
}
