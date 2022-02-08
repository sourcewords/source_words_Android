package com.example.sourcewords.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class DateUtils {
    static final Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
    @SuppressLint("SimpleDateFormat")
    static SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd");

    public static String getTime() {
        return s.format(c.getTime());
    }
}


