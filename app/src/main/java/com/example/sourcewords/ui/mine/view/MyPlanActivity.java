package com.example.sourcewords.ui.mine.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import com.example.sourcewords.R;
import com.example.sourcewords.databinding.ActivityMyplanBinding;
import com.example.sourcewords.ui.mine.model.databean.PlanBean;
import com.example.sourcewords.ui.mine.viewmodel.MyPlanViewModel;

import java.lang.invoke.ConstantCallSite;

public class MyPlanActivity extends AppCompatActivity {

    private MyPlanViewModel viewModel = new MyPlanViewModel();
    private PlanBean myPlan;
    private ActivityMyplanBinding dataBinding;
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_myplan);
        dataBinding.setLifecycleOwner(this);
        dataBinding.setMyPlanViewModel(viewModel);

        initView();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().
                    setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

    }

    private void initView() {
        myPlan = viewModel.getMyPlan().getValue();
        if(myPlan == null){
            dataBinding.myplanRvItem.setVisibility(View.GONE);
            Toast.makeText(this, "现在没有进行中的计划哦，快来添加一个吧！", Toast.LENGTH_SHORT).show();
        }else{
            dataBinding.leastTimeTv.setText(myPlan.getLeastTime());
            dataBinding.bETimeTv.setText(myPlan.getB_eTime());
            dataBinding.planName.setText(myPlan.getPlanName());
            dataBinding.planProgressBar.setProgress(myPlan.getProgress());
            dataBinding.planProgressNum.setText(myPlan.getProgress());
        }

        dataBinding.myplanAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddPlanActivity.class);
            startActivity(intent);
        });

        back = findViewById(R.id.my_back);
        back.setOnClickListener(v->finish());
    }


}
