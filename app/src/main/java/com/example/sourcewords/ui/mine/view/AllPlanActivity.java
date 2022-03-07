package com.example.sourcewords.ui.mine.view;

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

    RecyclerView recyclerView;
    List<PlanBean> mList = new ArrayList<>();
    private int spaceTag = 1;
    private ImageButton back;
    private TextView myplan;
    private ConstraintLayout myplan_item;
    private TextView name, leastTime, b_eTime, progress;
    private ProgressBar bar;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allplan);


        recyclerView = findViewById(R.id.myplan_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        initList();
        if(spaceTag == 1){
            recyclerView.addItemDecoration(new SpacesItemDecoration(getResources().getDimensionPixelOffset(R.dimen.dp_10), mList, this));
        }
        myplan = findViewById(R.id.myplan);
        myplan_item = findViewById(R.id.myplan_rv_item);
        name = findViewById(R.id.all_plan_name);
        leastTime = findViewById(R.id.all_least_time_tv);
        b_eTime = findViewById(R.id.all_b_e_time_tv);
        progress = findViewById(R.id.all_plan_progress_num);
        bar = findViewById(R.id.all_plan_progress_bar);
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
                mList.add(plan1);
                mList.add(plan2);
                mList.add(plan4);
                if(planBean.getData().getPlans().size() == 0){
                    myplan.setVisibility(View.GONE);
                    myplan_item.setVisibility(View.GONE);
                }else if(planBean.getData().getPlans().get(0).getName().equals("四级")){
                    mList.remove(0);
                    name.setText(planBean.getData().getPlans().get(0).getName());
                    b_eTime.setText(planBean.getData().getPlans().get(0).getStart() + "-" + planBean.getData().getPlans().get(0).getEnd());
                    progress.setText(planBean.getData().getPlans().get(0).getPercent() + "%");
                    bar.setProgress(planBean.getData().getPlans().get(0).getPercent());
                }else if(planBean.getData().getPlans().get(0).getName().equals("六级单词")){
                    mList.remove(1);
                    name.setText(planBean.getData().getPlans().get(0).getName());
                    b_eTime.setText(planBean.getData().getPlans().get(0).getStart() + "-" + planBean.getData().getPlans().get(0).getEnd());
                    progress.setText(planBean.getData().getPlans().get(0).getPercent() + "%");
                    bar.setProgress(planBean.getData().getPlans().get(0).getPercent());
                }else if(planBean.getData().getPlans().get(0).getName().equals("雅思单词")){
                    mList.remove(2);
                    name.setText(planBean.getData().getPlans().get(0).getName());
                    b_eTime.setText(planBean.getData().getPlans().get(0).getStart() + "-" + planBean.getData().getPlans().get(0).getEnd());
                    progress.setText(planBean.getData().getPlans().get(0).getPercent() + "%");
                    bar.setProgress(planBean.getData().getPlans().get(0).getPercent());
                }
                recyclerView.setAdapter(new PlanAdapter(mList));
            }

            @Override
            public void failed() {

            }
        });

    }

}
