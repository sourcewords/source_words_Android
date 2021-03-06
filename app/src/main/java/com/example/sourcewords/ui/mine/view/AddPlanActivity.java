package com.example.sourcewords.ui.mine.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.sourcewords.R;
import com.example.sourcewords.databinding.ActivityAddplanBindingImpl;
import com.example.sourcewords.ui.learn.viewModel.LearnViewModel;
import com.example.sourcewords.ui.mine.model.Api;
import com.example.sourcewords.ui.mine.model.PlanDataResource;
import com.example.sourcewords.ui.mine.model.databean.PlanItem;
import com.example.sourcewords.ui.mine.viewmodel.AddPlanViewModel;

public class AddPlanActivity extends AppCompatActivity {

    private AddPlanViewModel addPlanViewModel;
    private ActivityAddplanBindingImpl binding;
    private ImageButton addPlan, back;
    private PlanItem item = new PlanItem();
    private LearnViewModel learnViewModel;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_addplan);

        addPlanViewModel = new AddPlanViewModel(this);
        learnViewModel = ViewModelProviders.of(this).get(LearnViewModel.class);


        binding = DataBindingUtil.setContentView(this, R.layout.activity_addplan);
        binding.setLifecycleOwner(this);
        binding.setAddPlanViewModel(addPlanViewModel);

        addPlan = findViewById(R.id.add_plan);
        addPlan.setOnClickListener(v ->{
            launcher.launch(true);
        } );
        back = findViewById(R.id.add_back);
        back.setOnClickListener(v->finish());
        getPlan();

        binding.startPlan.setOnClickListener(v -> {
            int flag = 0;
            String name = binding.addPlanName.getText().toString().substring(0,2);
            if(item.getData().getPlans() != null) {
                for(PlanItem.DataDTO.PlansDTO i : item.getData().getPlans()){
                    if(i.getName().equals(name)){
                        changePlan(i.getPlanId());
                        flag = 1;
                    }
                }
            }
            if(flag == 0){
                addPlan(name);
            }
            learnViewModel.saveMakePlan();
            int level = 0;
            switch (name){
                case "??????":
                    level = 1;
                    break;
                case "??????":
                    level = 2;
                    break;
                default:
                    level = 3;
                    break;
            }
            learnViewModel.getNowPlan().setValue(level);
        });



        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().
                    setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
    ActivityResultLauncher launcher = registerForActivityResult(new AddPlanActivity.AddResultContract(), new ActivityResultCallback<String>() {
        @Override
        public void onActivityResult(String result) {
            if(result.equals("error")) return;
            binding.addPlanName.setText(result + "??????");
        }
    });

    class AddResultContract extends ActivityResultContract<Boolean, String> {
        @NonNull
        @Override
        public Intent createIntent(@NonNull Context context, Boolean input) {
            Intent intent = new Intent(AddPlanActivity.this, AllPlanActivity.class);
            return intent;
        }

        @Override
        public String parseResult(int resultCode, @Nullable Intent intent) {
            return intent.getStringExtra("plan");
        }
    }

    public void getPlan(){
        PlanDataResource.getInstance().getMyPlan(new Api.getPlan() {
            @Override
            public void success(PlanItem planItem) {
                item = planItem;
            }

            @Override
            public void failed() {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addPlan(String name){
        addPlanViewModel.addPlan(name, new Api.changePlanApi() {
            @Override
            public void success() {
                //getPlan();
//                String name = binding.addPlanName.getText().toString().substring(0,2);
//                if(item.getData().getPlans() != null) {
//                    for(PlanItem.DataDTO.PlansDTO i : item.getData().getPlans()){
//                        if(i.getName().equals(name)){
//                            changePlan(i.getPlanId());
//                        }
//                    }
//                }

                Toast.makeText(AddPlanActivity.this, "???????????????", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void failed() {

            }

            @Override
            public void requestTime(){
                Toast.makeText(AddPlanActivity.this, "?????????????????????????????????", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void requestName(){
                Toast.makeText(AddPlanActivity.this, "?????????????????????????????????", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void changePlan(Integer id){
        addPlanViewModel.changePlan(new Api.changePlanApi() {
            @Override
            public void success() {
                Toast.makeText(AddPlanActivity.this, "???????????????", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void failed() {

            }

            @Override
            public void requestName() {

            }

            @Override
            public void requestTime() {

            }
        }, id);
    }
}