package com.example.sourcewords.ui.mine.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sourcewords.R;

public class MyPlanActivity extends AppCompatActivity {

    private TextView planName, leastDate, b_eTime, progress;
    private ProgressBar progressBar;
    private ImageButton add;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_myplan);
        planName = findViewById(R.id.plan_name);
        leastDate = findViewById(R.id.least_time_tv);
        b_eTime = findViewById(R.id.b_e_time_tv);
        progress = findViewById(R.id.plan_progress_num);
        progressBar = findViewById(R.id.plan_progress_bar);
        add = findViewById(R.id.myplan_add);
        add.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddPlanActivity.class);
            startActivity(intent);
        });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().
                    setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

    }
}
