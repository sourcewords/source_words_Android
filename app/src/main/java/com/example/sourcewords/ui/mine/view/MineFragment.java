package com.example.sourcewords.ui.mine.view;

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
import com.example.sourcewords.ui.mine.model.databean.PlanBean;
import com.example.sourcewords.ui.mine.model.databean.SigninBean;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

//TODO 我模块
public class MineFragment extends Fragment {
    private ConstraintLayout mPersonalData,mAdjustmentPlan;
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
        mPersonalData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),UserInfoActivity.class);
                startActivity(intent);
            }
        });

        mAdjustmentPlan = view.findViewById(R.id.adjustment_plan);
        mAdjustmentPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyPlanActivity.class);
                startActivity(intent);
            }
        });

        signin = view.findViewById(R.id.sign_in);
        signin.setOnClickListener(v ->{
            Intent intent = new Intent(getActivity(), SigninActivity.class);
            startActivity(intent);
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
            @Override
            public void success(PlanBean myplan) {
                mine_progress.setText(String.valueOf(myplan.getProgress()) + "%");
                mine_bar.setProgress(myplan.getProgress());
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
