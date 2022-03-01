package com.example.sourcewords.ui.mine.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

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
    }
}
