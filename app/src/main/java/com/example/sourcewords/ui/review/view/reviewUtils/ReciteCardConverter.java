package com.example.sourcewords.ui.review.view.reviewUtils;


import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.dataBean.WordCardState;
import com.example.sourcewords.ui.review.dataBean.WordInfoBean;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ReciteCardConverter {
    public static GsonBuilder gsonBuilder = new GsonBuilder();
//    static {
//
//    }
//    public static Gson gson = gsonBuilder.create();
    public static String ReciteCardState2String(WordCardState wordCardState) {
        gsonBuilder.registerTypeAdapter(WordInfoBean.class, new WordInfoBeanTypeAdapter());
        gsonBuilder.registerTypeAdapter(Word.class, new WordTypeAdapter());
        gsonBuilder.registerTypeAdapter(WordSample.class, new WordSampleTypeAdapter());
        gsonBuilder.registerTypeAdapter(WordCardState.class, new WordCardStateTypeAdapter());
        Gson gson = gsonBuilder.create();
        return gson.toJson(wordCardState);
    }

    public static WordCardState String2ReciteCardState(String s) {
        gsonBuilder.registerTypeAdapter(WordInfoBean.class, new WordInfoBeanTypeAdapter());
        gsonBuilder.registerTypeAdapter(Word.class, new WordTypeAdapter());
        gsonBuilder.registerTypeAdapter(WordSample.class, new WordSampleTypeAdapter());
        gsonBuilder.registerTypeAdapter(WordCardState.class, new WordCardStateTypeAdapter());
        Gson gson = gsonBuilder.create();
        return gson.fromJson(s, WordCardState.class);
    }
}
