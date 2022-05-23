package com.example.sourcewords.ui.review.view.reviewUtils;

import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.dataBean.WordInfoBean;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class WordTypeAdapter extends TypeAdapter<Word> {
    Gson gson = new Gson();

    @Override
    public void write(JsonWriter jsonWriter, Word word) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("id").value(word.getId());
        jsonWriter.name("meaning").value(word.getMeaning());
        jsonWriter.name("word").value(word.getWord());
        jsonWriter.name("explanation").value(word.getExplanation());
        jsonWriter.name("property").value(word.getProperty());
        jsonWriter.name("root_id").value(word.getRoot_id());
        jsonWriter.name("word_info").value(gson.toJson(word.getWord_info()));
        jsonWriter.endObject();
    }

    @Override
    public Word read(JsonReader jsonReader) throws IOException {
        Word word = new Word();

        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            switch (jsonReader.nextName()) {
                case "id":
                    word.setId(jsonReader.nextInt());
                    break;
                case "meaning":
                    word.setMeaning(jsonReader.nextString());
                    break;
                case "word":
                    word.setWord(jsonReader.nextString());
                    break;
                case "explanation":
                    word.setExplanation(jsonReader.nextString());
                    break;
                case "property":
                    word.setProperty(jsonReader.nextString());
                    break;
                case "root_id":
                    word.setRoot_id(jsonReader.nextInt());
                    break;
                case "word_info":
                    word.setWord_info(gson.fromJson(jsonReader.nextString(), new TypeToken<WordInfoBean>(){}.getType()));
            }
        }

        jsonReader.endObject();
        return word;
    }
}
