package com.example.sourcewords.ui.login.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.example.sourcewords.R;
import com.example.sourcewords.databinding.ActivityLoginAccountBinding;
import com.example.sourcewords.ui.login.model.UserWrapper;
import com.example.sourcewords.ui.login.model.databean.LocalPage;
import com.example.sourcewords.ui.login.model.respository.LoginRemoteRespository;
import com.example.sourcewords.ui.login.viewmodel.LoginViewModel;
import com.example.sourcewords.ui.main.MainActivity;
import com.example.sourcewords.ui.mine.view.UserAgreementActivity;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements LoginNavigator{

    private LoginViewModel loginViewModel;
    private ActivityLoginAccountBinding binding;
    public static boolean isCorrect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_account);

        if (UserWrapper.getInstance().getUser()!=null){
            startActivity(new Intent(this, MainActivity.class));
            this.finish();
            return;
        }

        loginViewModel = new LoginViewModel(LoginRemoteRespository.getINSTANCE(),LoginActivity.this);
        loginViewModel.onActivityCreated(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_account);
        binding.setLifecycleOwner(this);
        binding.setLoginViewModel(loginViewModel);

        loginViewModel.getUser().observe(this, new Observer<LocalPage>() {
            @Override
            public void onChanged(@Nullable LocalPage localPage) {

                if (TextUtils.isEmpty(Objects.requireNonNull(localPage).getName())) {
                    binding.etAccount.setError("Enter a Correct Address");
                    binding.etAccount.requestFocus();
                } else if (!localPage.isAccountValid()) {
                    binding.etAccount.setError("Enter a Valid E-mail Address");
                    binding.etAccount.requestFocus();
                } else if (TextUtils.isEmpty(Objects.requireNonNull(localPage).getPassword())) {
                    binding.etPassword.setError("Enter a Password");
                    binding.etPassword.requestFocus();
                }
//                ????????????????????????5
//                else if (!loginUser.isPasswordLengthGreaterThan3()) {
//                    binding.etPassword.setError("Enter at least 6 Digit password");
//                    binding.etPassword.requestFocus();
//                }
//                ??????????????????
                else if(!localPage.isChecked()){
                    binding.checkbox.setError("Please check");
                    binding.checkbox.requestFocus();
                } else {
                    isCorrect = true;
                }

            }
        });

        binding.registerButton.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });

        binding.forgetpwdButton.setOnClickListener(v ->{
            startActivity(new Intent(this,ForgetPwdActivity.class));
        });

        String content = "????????????????????????????????????????????????????????????";
        SpannableStringBuilder spannableString = new SpannableStringBuilder(content);
        int policyBeginIndex = content.indexOf("???");
        int policyEndIndex = content.indexOf("???") + 1;
        int privateBeginIndex = content.lastIndexOf("???");
        int privateEndIndex = content.lastIndexOf("???") + 1;

        //??????????????????
        setUserPolicy(spannableString,policyBeginIndex,policyEndIndex,"policy");
        setUserPolicy(spannableString,privateBeginIndex,privateEndIndex,"private");

        //????????????????????????????????????????????????
        binding.agreementText.setMovementMethod(LinkMovementMethod.getInstance());
        //??????????????????????????????????????????????????????
        binding.agreementText.setHighlightColor(getResources().getColor(R.color.transparent));
        binding.agreementText.setText(spannableString);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().
                    setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    public void setUserPolicy(SpannableStringBuilder spannableString,int start,int end,String type){
        //??????????????????????????????
        ClickableSpan clickableSpan = new ClickableSpan() {

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                //??????????????????????????????false ???????????????????????????true?????????????????????
                ds.setUnderlineText(false);
            }

            @Override
            public void onClick(View widget) {
                showShortToast(type);
            }
        };
        spannableString.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


//        //????????????????????????
//        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#5e89ef"));

        //??????ForegroundColorSpan?????????????????????????????????
        UnderlineSpan colorSpan = new UnderlineSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(Color.parseColor("#5e89ef"));
            }
        };
        spannableString.setSpan(colorSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void showShortToast(String type) {
        if(type.equals("policy")){
            startActivity(new Intent(this, UserAgreementActivity.class));
        }else{
            Toast.makeText(this, "click"+" "+type, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onFinish() {
        this.finish();
    }
}
