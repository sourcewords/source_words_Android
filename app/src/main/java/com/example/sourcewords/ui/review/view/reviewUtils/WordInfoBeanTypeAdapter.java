package com.example.sourcewords.ui.review.view.reviewUtils;

import com.example.sourcewords.ui.review.dataBean.WordInfoBean;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.List;

public class WordInfoBeanTypeAdapter extends TypeAdapter<WordInfoBean> {
    Gson gson = new Gson();
    @Override
    public void write(JsonWriter jsonWriter, final WordInfoBean wordInfoBean) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("zh_source").value(wordInfoBean.getZh_source());
        jsonWriter.name("pronunciation_url").value(wordInfoBean.getPronunciation_url());
        jsonWriter.name("meaning").value(wordInfoBean.getMeaning());
        jsonWriter.name("phonetic").value(wordInfoBean.getPhonetic());
        jsonWriter.name("id").value(wordInfoBean.getId());
        jsonWriter.name("root").value(wordInfoBean.getRoot());
        jsonWriter.name("variation").value(wordInfoBean.getVariation());
        jsonWriter.name("word").value(wordInfoBean.getWord());
        jsonWriter.name("exam_grading").value(gson.toJson(wordInfoBean.getExam_grading()));
        jsonWriter.name("nextTime").value(wordInfoBean.getNextTime());
        jsonWriter.name("status").value(wordInfoBean.getStatus());
        jsonWriter.name("example_sentences").value(gson.toJson(wordInfoBean.getExample_sentences()));
        jsonWriter.endObject();
    }

    @Override
    public WordInfoBean read(JsonReader jsonReader) throws IOException {
        WordInfoBean wordInfoBean = new WordInfoBean();
        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            switch (jsonReader.nextName()) {
                case "zh_source":
                    wordInfoBean.setZh_source(jsonReader.nextString());
                    break;
                case "pronunciation_url":
                    wordInfoBean.setPronunciation_url(jsonReader.nextString());
                    break;
                case "meaning":
                    wordInfoBean.setMeaning(jsonReader.nextString());
                    break;
                case "phonetic":
                    wordInfoBean.setPhonetic(jsonReader.nextString());
                    break;
                case "id":
                    wordInfoBean.setId(jsonReader.nextInt());
                    break;
                case "root":
                    wordInfoBean.setRoot(jsonReader.nextInt());
                    break;
                case "variation":
                    wordInfoBean.setVariation(jsonReader.nextString());
                    break;
                case "word":
                    wordInfoBean.setWord(jsonReader.nextString());
                    break;
                case "exam_grading":
                    wordInfoBean.setExam_grading(gson.fromJson(jsonReader.nextString(), new TypeToken<List<Boolean>>(){}.getType()));
                    break;
                case "nextTime":
                    wordInfoBean.setNextTime(jsonReader.nextString());
                    break;
                case "status":
                    wordInfoBean.setStatus(jsonReader.nextInt());
                    break;
                case "example_sentences":
                    wordInfoBean.setExample_sentences(gson.fromJson(jsonReader.nextString(), new TypeToken<List<WordInfoBean.ExampleSentencesBean>>(){}.getType()));
            }
        }

        jsonReader.endObject();
        return wordInfoBean;
    }
}
