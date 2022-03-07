package com.example.sourcewords.ui.mine.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.sourcewords.R;
import com.example.sourcewords.databinding.ActivityAddplanBindingImpl;
import com.example.sourcewords.ui.mine.model.Api;
import com.example.sourcewords.ui.mine.viewmodel.AddPlanViewModel;

public class AddPlanActivity extends AppCompatActivity {

    private AddPlanViewModel addPlanViewModel;
    private ActivityAddplanBindingImpl binding;
    private ImageButton addPlan, back;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_addplan);

        addPlanViewModel = new AddPlanViewModel(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_addplan);
        binding.setLifecycleOwner(this);
        binding.setAddPlanViewModel(addPlanViewModel);

        addPlan = findViewById(R.id.add_plan);
        addPlan.setOnClickListener(v ->{
            Intent intent = new Intent(this, AllPlanActivity.class);
            startActivity(intent);
        } );
        back = findViewById(R.id.add_back);
        back.setOnClickListener(v->finish());

        binding.startPlan.setOnClickListener(v -> {
            addPlanViewModel.changePlan(new Api.changePlanApi() {
                @Override
                public void success() {
                    Toast.makeText(AddPlanActivity.this, "添加成功！", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void failed() {

                }

                @Override
                public void requestTime(){
                    Toast.makeText(AddPlanActivity.this, "请选择开始和结束时间！", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void requestName(){
                    Toast.makeText(AddPlanActivity.this, "请选择想要进行的计划！", Toast.LENGTH_SHORT).show();
                }
            });
        });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().
                    setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
}
