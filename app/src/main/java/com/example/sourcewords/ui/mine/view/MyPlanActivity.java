package com.example.sourcewords.ui.mine.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sourcewords.R;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

public class MyPlanActivity extends AppCompatActivity {

    MaterialCalendarView calendarView;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myplan);
        calendarView = findViewById(R.id.calendar);
        recyclerView = findViewById(R.id.myplan_rv);
    }
}
