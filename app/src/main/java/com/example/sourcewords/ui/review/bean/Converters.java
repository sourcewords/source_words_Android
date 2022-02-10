package com.example.sourcewords.ui.review.bean;

import androidx.room.TypeConverter;

import com.example.sourcewords.ui.review.bean.Word;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

//数据转换类
public class Converters {
    public static Gson gs = new Gson();
    @TypeConverter
    public static List<Word> jsonToWordList(String json) {
        return gs.fromJson(json, new TypeToken<List<String>>() {
        }.getType());
    }

    @TypeConverter
    public static String wordListToJson(List<Word> list) {
        return gs.toJson(list);
    }

    @TypeConverter
    public static List<String> jsonToList(String json){
        return gs.fromJson(json,new TypeToken<List<String>>(){}.getType());
    }
    @TypeConverter
    public static String listToJson(List<String> list){
        return gs.toJson(list);
    }
}
