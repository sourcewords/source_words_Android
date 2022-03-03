package com.example.sourcewords.ui.mine.model.databean;

import android.graphics.drawable.Drawable;
import android.text.style.RelativeSizeSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Date;

public class Decorator implements DayViewDecorator {

    private Drawable drawable;
    private Date date;

    public Decorator(Drawable drawable, Date date){
        this.date = date;
        this.drawable = drawable;
    }
    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return date != null && day.equals(date);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new RelativeSizeSpan(3.0f));
    }
}
