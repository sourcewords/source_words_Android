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
import com.example.sourcewords.ui.mine.model.Api;
import com.example.sourcewords.ui.mine.model.PlanDataResource;
import com.example.sourcewords.ui.mine.model.databean.PlanBean;
import com.example.sourcewords.ui.mine.model.databean.PlanItem;
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
        PlanDataResource.getInstance().getMyPlan(new Api.getPlan() {
            @Override
            public void success(PlanItem planBean) {
                if(planBean.getData().getPlans() == null){
                    dataBinding.myplanRvItem.setVisibility(View.GONE);
                    Toast.makeText(MyPlanActivity.this, "现在没有进行中的计划哦，快来添加一个吧！", Toast.LENGTH_SHORT).show();
                }else{
                    if(planBean.getData().getPlans().get(0).getName().equals("四级单词")){
                        dataBinding.myPlanPic.setBackgroundResource(R.mipmap.cet4);
                    }else if(planBean.getData().getPlans().get(0).getName().equals("六级单词")){
                        dataBinding.myPlanPic.setBackgroundResource(R.mipmap.cet6);
                    }else {
                        dataBinding.myPlanPic.setBackgroundResource(R.mipmap.ielts);
                    }
                    //dataBinding.myLeastTimeTv.setText(planBean.getData().getPlans().get(0));
                    dataBinding.myBETimeTv.setText(planBean.getData().getPlans().get(0).getStart() + "-" + planBean.getData().getPlans().get(0).getEnd());
                    dataBinding.myPlanName.setText(planBean.getData().getPlans().get(0).getName());
                    dataBinding.myPlanProgressBar.setProgress(planBean.getData().getPlans().get(0).getPercent());
                    dataBinding.myPlanProgressNum.setText(planBean.getData().getPlans().get(0).getPercent()+ "%");
                }
            }

            @Override
            public void failed() {

            }
        });

        dataBinding.myplanAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddPlanActivity.class);
            startActivity(intent);
        });

        back = findViewById(R.id.my_back);
        back.setOnClickListener(v->finish());
    }


}
