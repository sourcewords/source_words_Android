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

        //day.setText(String.valueOf(SigninDateSource.getInstance().getSigninBean().getData().getAll()));
    }



    public void setLoginNavigator(LoginNavigator navigator){
        loginNavigator = navigator;
    }
}
