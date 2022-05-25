package com.example.sourcewords.ui.mine.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.sourcewords.R;
import com.example.sourcewords.databinding.ActivityMyplanBinding;
import com.example.sourcewords.ui.learn.viewModel.LearnViewModel;
import com.example.sourcewords.ui.mine.model.Api;
import com.example.sourcewords.ui.mine.model.PlanDataResource;
import com.example.sourcewords.ui.mine.model.databean.PlanBean;
import com.example.sourcewords.ui.mine.model.databean.PlanItem;
import com.example.sourcewords.ui.mine.viewmodel.MyPlanViewModel;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class MyPlanActivity extends AppCompatActivity {

    private MyPlanViewModel viewModel = new MyPlanViewModel();
    private PlanBean myPlan;
    private LearnViewModel learnViewModel;
    private ActivityMyplanBinding dataBinding;
    private ImageButton back;
    int syear = 0, smonth = 0, sday = 0, eyear = 0,emonth = 0,eday = 0, speed = 0;
    String startstring, endstring;
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        learnViewModel = ViewModelProviders.of(this).get(LearnViewModel.class);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_myplan);
        dataBinding.setLifecycleOwner(this);
        dataBinding.setMyPlanViewModel(viewModel);

        initView();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().
                    setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

    }

    private void initView() {
        PlanDataResource.getInstance().getMyPlan(new Api.getPlan() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void success(PlanItem planBean) {
                if (planBean.getData().getPlans() == null) {
                    dataBinding.myplanRvItem.setVisibility(View.GONE);
                    Toast.makeText(MyPlanActivity.this, "现在没有进行中的计划哦，快来添加一个吧！", Toast.LENGTH_SHORT).show();
                } else {
                    dataBinding.myplanRvItem.setVisibility(View.VISIBLE);
                    for (PlanItem.DataDTO.PlansDTO dao : planBean.getData().getPlans()) {
                        if (dao.getActive() == 1) {
                            if (dao.getName().equals("四级")) {
                                dataBinding.myPlanPic.setBackgroundResource(R.mipmap.cet4);
                            } else if (dao.getName().equals("六级")) {
                                dataBinding.myPlanPic.setBackgroundResource(R.mipmap.cet6);
                            } else {
                                dataBinding.myPlanPic.setBackgroundResource(R.mipmap.ielts);
                            }
                            startstring = dao.getStart();
                            endstring = dao.getEnd();
                            getDay();

                            LocalDate start = LocalDate.of(eyear, smonth+1, eday);
                            LocalDate today = LocalDate.now();
                            LocalDate end = LocalDate.of(eyear, emonth+1, eday);
                            long daysDiff = ChronoUnit.DAYS.between(today, end);
                            long allDay = ChronoUnit.DAYS.between(start, end);
                            try{
                                speed = (int) (486 / allDay);
                            }catch(Exception e) {
                                e.printStackTrace();
                            }
                            learnViewModel.saveSpeed(speed);
                            dataBinding.myLeastTimeTv.setText("倒计时：" + daysDiff + "天");
                            dataBinding.myBETimeTv.setText(dao.getStart() + "-" + planBean.getData().getPlans().get(0).getEnd());
                            dataBinding.myPlanName.setText(dao.getName() + "单词");
                            dataBinding.myPlanProgressBar.setProgress(dao.getPercent().intValue());
                            dataBinding.myPlanProgressNum.setText(dao.getPercent() + "%");
                        }
                    }

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
        back.setOnClickListener(v -> finish());
    }

    public void getDay(){
        syear = Integer.parseInt(startstring.substring(0,4));
        if(startstring.charAt(startstring.length()-2) == '.'){//日期一位数 2022.11.1
            sday = Integer.parseInt(startstring.substring(startstring.length()-1));
            if(startstring.charAt(startstring.length()-4) == '.'){
                smonth = Integer.parseInt(startstring.substring(startstring.length()-3, startstring.length()-2));
            }else if(startstring.charAt(startstring.length()-5) == '.'){
                smonth = Integer.parseInt(startstring.substring(startstring.length()-4, startstring.length()-2));
            }
        }else{
            sday = Integer.parseInt(startstring.substring(startstring.length()-2, startstring.length()));
            if(startstring.charAt(startstring.length()-5) == '.'){
                smonth = Integer.parseInt(startstring.substring(startstring.length()-4, startstring.length()-3));
            }else if(startstring.charAt(startstring.length()-6) == '.'){
                smonth = Integer.parseInt(startstring.substring(startstring.length()-5, startstring.length()-3));
            }
        }

        eyear = Integer.parseInt(endstring.substring(0,4));
        if(endstring.charAt(endstring.length()-2) == '.'){//日期一位数 2022.11.1
            eday = Integer.parseInt(endstring.substring(endstring.length()-1));
            if(endstring.charAt(endstring.length()-4) == '.'){
                emonth = Integer.parseInt(endstring.substring(endstring.length()-3, endstring.length()-2));
            }else if(endstring.charAt(endstring.length()-5) == '.'){
                emonth = Integer.parseInt(endstring.substring(endstring.length()-4, endstring.length()-2));
            }
        }else{
            eday = Integer.parseInt(endstring.substring(endstring.length()-2, endstring.length()));
            if(endstring.charAt(endstring.length()-5) == '.'){
                emonth = Integer.parseInt(endstring.substring(endstring.length()-4, endstring.length()-3));
            }else if(endstring.charAt(endstring.length()-6) == '.'){
                emonth = Integer.parseInt(endstring.substring(endstring.length()-5, endstring.length()-3));
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }
}
