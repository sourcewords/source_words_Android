package com.example.sourcewords.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateUtils {
    static final Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
    static final Calendar calendar = new GregorianCalendar();
    @SuppressLint("SimpleDateFormat")
    static SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static String getTime() {
        return s.format(c.getTime());
    }

    public static String addTime(int key){
        calendar.setTime(c.getTime());
        if(key == PreferenceUtils.AGAIN) calendar.add(Calendar.MINUTE,10);
        else if(key == PreferenceUtils.HARD) calendar.add(Calendar.DATE,2);
        else if(key == PreferenceUtils.GOOD) calendar.add(Calendar.DATE, 3);
        else if(key == PreferenceUtils.EASY) calendar.add(Calendar.DATE, 4);
        return s.format(calendar.getTime());
    }
}


