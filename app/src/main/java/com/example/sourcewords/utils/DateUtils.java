package com.example.sourcewords.utils;

import android.annotation.SuppressLint;

import androidx.annotation.StringDef;

import com.example.sourcewords.ui.review.view.DetailActivity;

import java.text.ParseException;
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
    public static String getDate() {
        return data.format(System.currentTimeMillis());
    }

    // 获得当前时间 时：分 格式
    public static String getTime() {
        return time.format(System.currentTimeMillis());
    }

    // 给当前日期加时间，unit表示分钟还是日期
    public static String addTime(int value, String unit){
        Calendar calendar = new GregorianCalendar();
        c.setTime(new Date(System.currentTimeMillis()));
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

    /**
     * 以日期的形式比较两个字符串，比较时间早晚
     * @param s1
     * @param s2
     * @return 如果s1比较早 结果>0
     */
    public static long compareDate(String s1, String s2) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = null;
        try {
            d1 = sdf.parse(s1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date d2 = null;
        try {
            d2 = sdf.parse(s2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert d2 != null;
        assert d1 != null;
        return d2.getTime() - d1.getTime();

    }

    /**
     * 以时间的形式比较两个字符串，比较时间早晚
     * @param s1
     * @param s2
     * @return 如果s1比较早 结果>0
     */
    public static long compareTime(String s1, String s2) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date d1 = null;
        try {
            d1 = sdf.parse(s1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date d2 = null;
        try {
            d2 = sdf.parse(s2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert d2 != null;
        assert d1 != null;
        return d2.getTime() - d1.getTime();
    }
}


