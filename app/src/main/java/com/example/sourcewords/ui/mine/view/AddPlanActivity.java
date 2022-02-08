package com.example.sourcewords.ui.mine.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sourcewords.R;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

public class AddPlanActivity extends AppCompatActivity {

    MaterialCalendarView calendarView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myplan);
        calendarView = findViewById(R.id.calendar);
        //calendarView.setTileSize((int) (calendarView.getTileWidth() * 0.5));
    }
}
