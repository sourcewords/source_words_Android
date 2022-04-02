package com.example.sourcewords.ui.mine.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.sourcewords.R;
import com.example.sourcewords.commonUtils.SPUtils;
import com.example.sourcewords.ui.login.model.UserWrapper;
import com.example.sourcewords.ui.login.view.LoginActivity;
import com.example.sourcewords.ui.login.view.LoginNavigator;
import com.example.sourcewords.ui.mine.model.Api;
import com.example.sourcewords.ui.mine.model.PlanDataResource;
import com.example.sourcewords.ui.mine.model.SigninDateSource;
import com.example.sourcewords.ui.mine.model.databean.PlanItem;
import com.example.sourcewords.ui.mine.model.databean.SigninBean;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

//TODO 我模块
public class MineFragment extends Fragment {
    private ConstraintLayout mPersonalData,mAdjustmentPlan,mUserAgreement,mSettting;
    private ImageView signin;
    private ProgressBar mine_bar;
    private TextView mine_progress;
    private Button unlogin;
    private LoginNavigator loginNavigator;
    private TextView time, day;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoginNavigator){
            loginNavigator = (LoginNavigator) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine,null);
        init(view);
        return view;
    }

    public void init(View view){
        mPersonalData = view.findViewById(R.id.personal_data);
        mPersonalData.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(),UserInfoActivity.class));
        });

        mAdjustmentPlan = view.findViewById(R.id.adjustment_plan);
        mAdjustmentPlan.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), MyPlanActivity.class));
        });

        mUserAgreement = view.findViewById(R.id.user_agreement);
        mUserAgreement.setOnClickListener(v ->{
            startActivity(new Intent(getActivity(),UserAgreementActivity.class));
        });

        mSettting = view.findViewById(R.id.setting);
        mSettting.setOnClickListener(v ->{
            startActivity(new Intent(getActivity(),SettingActivity.class));
        });

        signin = view.findViewById(R.id.sign_in);
        signin.setOnClickListener(v ->{
            startActivity(new Intent(getActivity(), SigninActivity.class));
        });

        unlogin = view.findViewById(R.id.unlogin_B);
        unlogin.setOnClickListener(v ->{
            SPUtils.getInstance(SPUtils.SP_CONFIG).clear();
            UserWrapper.getInstance().setUser(null);
            startActivity(new Intent(getActivity(), LoginActivity.class));
        });

        mine_bar = view.findViewById(R.id.mine_bar);
        mine_progress = view.findViewById(R.id.mine_progress);
        day = view.findViewById(R.id.day_tv);
        SigninDateSource.getInstance().getAllSignDate(new Api.getSignInApi() {
            @Override
            public void success(SigninBean signinBean) {
                day.setText(String.valueOf(signinBean.getData().getAll()));
            }

            @Override
            public void failed() {

            }
        });

        time = view.findViewById(R.id.time_tv);
        //time.setText(getFormatTime(SPUtils.getInstance().getLong("APP_USE_TIME", 0L)));
        time.setText(String.valueOf(SPUtils.getInstance().getLong("APP_USE_TIME", 0L)/60000));

        PlanDataResource.getInstance().getMyPlan(new Api.getPlan() {
            @SuppressLint("SetTextI18n")
            @Override

            public void success(PlanItem myplan) {
                if(myplan.getData().getPlans() != null){
                    mine_progress.setText(myplan.getData().getPlans().get(0).getPercent() + "%");
                    mine_bar.setProgress(myplan.getData().getPlans().get(0).getPercent().intValue());
                }else{
                    mine_progress.setText("0%");
                    mine_bar.setProgress(0);
                }

            }

            @Override
            public void failed() {

            }
        });
    }

    public void setLoginNavigator(LoginNavigator navigator){
        loginNavigator = navigator;
    }

    private String getFormatTime(long app_use_time) {
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        //设置时区，跳过此步骤会默认设置为"GMT+08:00" 得到的结果会多出来8个小时
        format.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));

        return format.format(app_use_time);
    }

    @Override
    public void onResume() {
        super.onResume();
        time.setText(String.valueOf(SPUtils.getInstance().getLong("APP_USE_TIME", 0L)/60000));
    }
}
