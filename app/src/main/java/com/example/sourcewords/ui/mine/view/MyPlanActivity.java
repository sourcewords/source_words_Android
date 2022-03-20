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

import com.example.sourcewords.R;
import com.example.sourcewords.commonUtils.SPUtils;
import com.example.sourcewords.databinding.ActivityMyplanBinding;
import com.example.sourcewords.ui.mine.model.Api;
import com.example.sourcewords.ui.mine.model.PlanDataResource;
import com.example.sourcewords.ui.mine.model.databean.PlanBean;
import com.example.sourcewords.ui.mine.model.databean.PlanItem;
import com.example.sourcewords.ui.mine.viewmodel.MyPlanViewModel;

import java.time.LocalDate;

public class MyPlanActivity extends AppCompatActivity {

    private MyPlanViewModel viewModel = new MyPlanViewModel();
    private PlanBean myPlan;
    private ActivityMyplanBinding dataBinding;
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
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
                    for (PlanItem.DataDTO.PlansDTO dao : planBean.getData().getPlans()) {
                        if (dao.getActive() == 1) {
                            if (dao.getName().equals("四级")) {
                                dataBinding.myPlanPic.setBackgroundResource(R.mipmap.cet4);
                            } else if (dao.getName().equals("六级")) {
                                dataBinding.myPlanPic.setBackgroundResource(R.mipmap.cet6);
                            } else {
                                dataBinding.myPlanPic.setBackgroundResource(R.mipmap.ielts);
                            }
                            LocalDate start = LocalDate.now();
                            int year, month, day;
                            String endstring = dao.getEnd();
                            year = Integer.parseInt(endstring.substring(0, 4));
                            if (endstring.charAt(endstring.length() - 2) == '.') {
                                day = Integer.parseInt(endstring.substring(endstring.length() - 2, endstring.length() - 1));
                                if (endstring.charAt(endstring.length() - 4) == '.') {
                                    month = Integer.parseInt(endstring.substring(endstring.length() - 4, endstring.length() - 3));
                                } else if (endstring.charAt(endstring.length() - 5) == '.') {
                                    month = Integer.parseInt(endstring.substring(endstring.length() - 5, endstring.length() - 3));
                                }
                            } else {
                                day = Integer.parseInt(endstring.substring(endstring.length() - 3, endstring.length() - 2));
                                if (endstring.charAt(endstring.length() - 5) == '.') {
                                    month = Integer.parseInt(endstring.substring(endstring.length() - 5, endstring.length() - 4));
                                } else if (endstring.charAt(endstring.length() - 6) == '.') {
                                    month = Integer.parseInt(endstring.substring(endstring.length() - 6, endstring.length() - 4));
                                }
                            }

                            dataBinding.myBETimeTv.setText(dao.getStart() + "-" + planBean.getData().getPlans().get(0).getEnd());
                            dataBinding.myPlanName.setText(dao.getName() + "单词");
                            dataBinding.myPlanProgressBar.setProgress(dao.getPercent());
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

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }
}
