package com.example.sourcewords.utils;

import androidx.room.TypeConverter;

import com.example.sourcewords.ui.review.dataBean.SingleWord;
import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.dataBean.WordInfoBean;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

//数据转换类
public class Converters {
    private static final Gson gs = new Gson();

    @TypeConverter
    public static List<Word> jsonToList(String json) {
        return gs.fromJson(json, new TypeToken<List<Word>>() {
        }.getType());
    }

    @TypeConverter
    public static List<WordRoot> WordRootJsonToList(String json) {
        return gs.fromJson(json, new TypeToken<List<WordRoot>>() {

        }.getType());
    }

    @TypeConverter
    public static String listToJson(List<Word> list) {
        return gs.toJson(list);
    }

    @TypeConverter
    public static String wordsInfoToJson(WordInfoBean wordsInfo) {

        return gs.toJson(wordsInfo);
    }

    @TypeConverter
    public static WordInfoBean jsonToWordInfo(String json) {
        return gs.fromJson(json, new TypeToken<WordInfoBean>() {
        }.getType());
    }

    @TypeConverter
    public static String SentenceListToJson(List<SingleWord.ExampleSentencesBean> list) {
        return gs.toJson(list);
    }

    @TypeConverter
    public static List<SingleWord.ExampleSentencesBean> JsonToSentenceList(String json) {
        return gs.fromJson(json, new TypeToken<List<SingleWord.ExampleSentencesBean>>(){
        }.getType());
    }

    @TypeConverter
    public static String Exam_gratingToJson(List<Boolean> list) {
        return gs.toJson(list);
    }

    @TypeConverter
    public static List<Boolean> JsonToExam_grating(String json) {
        return gs.fromJson(json, new TypeToken<List<Boolean>>(){
        }.getType());
    }


    public static List<WordRoot> WordRootJson2Class(String json) {
        return gs.fromJson(json, new TypeToken<List<WordRoot>>() {
        }.getType());
    }

    public static List<Word> WordJson2Class(String json) {
        return gs.fromJson(json, new TypeToken<List<Word>>(){
        }.getType());
    }

    public static List<SingleWord> SingleWordJson2Class(String json) {
        return gs.fromJson(json, new TypeToken<List<SingleWord>>(){
        }.getType());
    }
}
