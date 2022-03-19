package com.example.sourcewords.ui.mine.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sourcewords.R;
import com.example.sourcewords.ui.mine.model.Api;
import com.example.sourcewords.ui.mine.model.PlanDataResource;
import com.example.sourcewords.ui.mine.model.databean.PlanAdapter;
import com.example.sourcewords.ui.mine.model.databean.PlanBean;
import com.example.sourcewords.ui.mine.model.databean.PlanItem;
import com.example.sourcewords.ui.mine.model.databean.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class AllPlanActivity extends AppCompatActivity {

    RecyclerView rvCan, rvHave;
    List<PlanBean> can, have, mList = new ArrayList<>();
    private int spaceTag = 1;
    private ImageButton back;
    private TextView myplan;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allplan);
        Intent intent = getIntent();
        setResult(RESULT_OK, new Intent().putExtra("plan", "error"));
        rvCan = findViewById(R.id.myplan_rv_can);
        rvCan.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rvHave = findViewById(R.id.myplan_rv_have);
        rvHave.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        initList();
        if(spaceTag == 1){
            rvCan.addItemDecoration(new SpacesItemDecoration(getResources().getDimensionPixelOffset(R.dimen.dp_10), mList, this));
        }
        myplan = findViewById(R.id.myplan);
        back = findViewById(R.id.all_back);
        back.setOnClickListener(v->finish());

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().
                    setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    public void initList(){

        PlanDataResource.getInstance().getMyPlan(new Api.getPlan() {
            @Override
            public void success(PlanItem planBean) {
                PlanBean plan1 = new PlanBean("四级单词", "", "xx.xx-xx.xx", 0, 1);
                PlanBean plan2 = new PlanBean("六级单词", "", "xx.xx-xx.xx", 0, 2);
                PlanBean plan4 = new PlanBean("雅思单词", "", "xx.xx-xx.xx", 0, 3);
                can.add(plan1);
                can.add(plan2);
                can.add(plan4);
                if(planBean.getData().getPlans() == null){
                    myplan.setVisibility(View.GONE);
                    rvHave.setVisibility(View.GONE);
                }else{
                    for(PlanItem.DataDTO.PlansDTO dao : planBean.getData().getPlans()){
                        int t = 0;
                        switch (dao.getName()) {
                            case "四级":
                                t = 1;
                                can.remove(plan1);
                                break;
                            case "六级":
                                t = 2;
                                can.remove(plan2);
                                break;
                            case "雅思":
                                t = 3;
                                can.remove(plan4);
                                break;
                        }
                        have.add(new PlanBean(dao.getName(), "", dao.getStart()+ "-" + dao.getEnd(), dao.getPercent(), t));
                    }
                }
                rvCan.setAdapter(new PlanAdapter(can, new Api.addPlan() {
                    @Override
                    public void success(String name) {
                        setResult(RESULT_OK, new Intent().putExtra("plan", name));
                        finish();
                    }

                    @Override
                    public void failed() {

                    }
                }));

                rvHave.setAdapter(new PlanAdapter(have, new Api.addPlan() {
                    @Override
                    public void success(String name) {
                        setResult(RESULT_OK, new Intent().putExtra("plan", name));
                        finish();
                    }

                    @Override
                    public void failed() {

                    }
                }));
            }

            @Override
            public void failed() {

            }
        });

    }

}
