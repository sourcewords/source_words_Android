package com.example.sourcewords.ui.review.view.reviewUtils;

import com.example.sourcewords.ui.review.dataBean.Word;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class WordSampleTypeAdapter extends TypeAdapter<WordSample> {
    Gson gson = new Gson();
    @Override
    public void write(JsonWriter jsonWriter, WordSample wordSample) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("status").value(wordSample.getStatus());
        jsonWriter.name("time").value(wordSample.getTime());
        jsonWriter.name("word").value(gson.toJson(wordSample.getWord()));
        jsonWriter.endObject();
    }

    @Override
    public WordSample read(JsonReader jsonReader) throws IOException {
        WordSample wordSample = new WordSample();

        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            switch (jsonReader.nextName()) {
                case "status":
                    wordSample.setStatus(jsonReader.nextInt());
                    break;
                case "time":
                    wordSample.setTime(jsonReader.nextString());
                    break;
                case "word":
                    wordSample.setWord(gson.fromJson(jsonReader.nextString(), new TypeToken<Word>(){}.getType()));
            }
        }

        jsonReader.endObject();
        return wordSample;
    }
}
