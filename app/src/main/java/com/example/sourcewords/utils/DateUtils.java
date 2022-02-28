package com.example.sourcewords.utils;

import android.annotation.SuppressLint;

import androidx.annotation.StringDef;

import com.example.sourcewords.ui.review.view.DetailActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateUtils {
    static final Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
    @SuppressLint("SimpleDateFormat")
    static SimpleDateFormat data = new SimpleDateFormat("yyyy-MM-dd");
    @SuppressLint("SimpleDateFormat")
    static SimpleDateFormat time = new SimpleDateFormat("HH:mm");

    // 获得当前日期 年-月-日 格式
    public static String getData() {
        return data.format(c.getTime());
    }

    // 获得当前时间 时：分 格式
    public static String getTime() {
        return time.format(c.getTime());
    }

    // 给当前日期加时间，unit表示分钟还是日期
    public static String addTime(int value, String unit){
        Calendar calendar = new GregorianCalendar();
        if(unit.equals("MINS")) {
            calendar.setTime(c.getTime());
            calendar.add(Calendar.MINUTE,value);
            return time.format(calendar.getTime());
        }
        else if(unit.equals("DAYS")) {
            calendar.add(Calendar.DATE, value);
            return data.format(calendar.getTime());
        }
        else return time.format(c.getTime());
    }
}


