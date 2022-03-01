package com.example.sourcewords.ui.mine.view;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sourcewords.R;
import com.example.sourcewords.ui.mine.model.databean.PlanAdapter;
import com.example.sourcewords.ui.mine.model.databean.PlanBean;
import com.example.sourcewords.ui.mine.model.databean.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class AllPlanActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<PlanBean> mList = new ArrayList<>();
    private int spaceTag = 1;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allplan);
        initList();

        recyclerView = findViewById(R.id.myplan_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(new PlanAdapter(mList));
        if(spaceTag == 1){
            recyclerView.addItemDecoration(new SpacesItemDecoration(getResources().getDimensionPixelOffset(R.dimen.dp_10), mList, this));
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().
                    setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    public void initList(){
        PlanBean plan2 = new PlanBean("六级单词", "", "xx.xx-xx.xx", 0);
        PlanBean plan3 = new PlanBean("托福单词", "", "xx.xx-xx.xx", 0);
        PlanBean plan4 = new PlanBean("雅思单词", "", "xx.xx-xx.xx", 0);

        mList.add(plan2);
        mList.add(plan3);
        mList.add(plan4);
    }
}
