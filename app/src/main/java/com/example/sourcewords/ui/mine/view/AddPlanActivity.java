package com.example.sourcewords.ui.mine.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.os.Build;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sourcewords.R;

public class AddPlanActivity extends AppCompatActivity {

    private ImageButton addPlan;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_addplan);
        addPlan = findViewById(R.id.add_plan);
        addPlan.setOnClickListener(v ->{
            Intent intent = new Intent(this, AllPlanActivity.class);
            startActivity(intent);
        } );

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().
                    setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
}
