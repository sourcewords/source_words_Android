package com.example.sourcewords.utils;

import androidx.room.TypeConverter;

import com.example.sourcewords.ui.review.dataBean.SingleWord;
import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.dataBean.WordInfoBean;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.ui.review.view.reviewUtils.WordSample;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

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

    @TypeConverter
    public static String HashMap2Json(HashMap<Integer, WordSample> map) {
        return gs.toJson(map);
    }

    @TypeConverter
    public static HashMap<Integer, WordSample> Json2HashMap(String json) {
        return gs.fromJson(json, new TypeToken<HashMap<Integer, WordSample>>(){
        }.getType());
    }

    @TypeConverter
    public static String Stack2Json(Stack<WordSample> stack) {
        return gs.toJson(stack);
    }

    @TypeConverter
    public static Stack<WordSample> Json2Stack(String json) {
        return gs.fromJson(json, new TypeToken<Stack<WordSample>>(){
        }.getType());
    }

    @TypeConverter
    public static String Queue2Json(Queue<WordSample> queue) {
        return gs.toJson(queue);
    }

    @TypeConverter
    public static Queue<WordSample> Json2Queue(String json) {
        return gs.fromJson(json, new TypeToken<Queue<WordSample>>(){
        }.getType());
    }

    @TypeConverter
    public static String PQ2Json(PriorityQueue<WordSample> pq) {
        return gs.toJson(pq);
    }

    @TypeConverter
    public static PriorityQueue<WordSample> Json2PQ(String json) {
        return gs.fromJson(json, new TypeToken<PriorityQueue<WordSample>>(){
        }.getType());
    }

    @TypeConverter
    public static String WordSample2Json(WordSample wordSample) {
        return gs.toJson(wordSample);
    }

    @TypeConverter
    public static WordSample Json2WordSample(String json) {
        return gs.fromJson(json, new TypeToken<WordSample>(){
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
