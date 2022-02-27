package com.example.sourcewords.utils;

import android.annotation.SuppressLint;

import com.example.sourcewords.ui.review.view.DetailActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateUtils {
    static final Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
    @SuppressLint("SimpleDateFormat")
    static SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static String getTime() {
        return s.format(c.getTime());
    }

    public static String addTime(int value, String unit){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(c.getTime());
        if(unit.equals("MINS")) calendar.add(Calendar.MINUTE,value);
        else if(unit.equals("DAYS")) calendar.add(Calendar.DATE,value);
        return s.format(calendar.getTime());
    }
}


