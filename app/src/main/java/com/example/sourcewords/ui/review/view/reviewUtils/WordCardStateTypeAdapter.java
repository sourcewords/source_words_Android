package com.example.sourcewords.ui.review.view.reviewUtils;

import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.dataBean.WordCardState;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class WordCardStateTypeAdapter extends TypeAdapter<WordCardState> {
    Gson gson = new Gson();
    @Override
    public void write(JsonWriter jsonWriter, WordCardState wordCardState) throws IOException {
        jsonWriter.beginObject();

        jsonWriter.name("id").value(wordCardState.getId());
        jsonWriter.name("date").value(wordCardState.getDate());
        jsonWriter.name("newLearnedWords").value(gson.toJson(wordCardState.getNewLearnedWords()));
        jsonWriter.name("haveLearnedWords").value(gson.toJson(wordCardState.getHaveLearnedWords()));
        jsonWriter.name("reviewWords").value(gson.toJson(wordCardState.getReviewWords()));
        jsonWriter.name("newLearnedCount").value(wordCardState.getNewLearnedCount());
        jsonWriter.name("haveLearnedCount").value(wordCardState.getHaveLearnedCount());
        jsonWriter.name("reviewCount").value(wordCardState.getReviewCount());
        jsonWriter.name("wordSample").value(gson.toJson(wordCardState.getWordSample()));
        jsonWriter.name("wordPool").value(gson.toJson(wordCardState.getWordPool()));
        jsonWriter.name("lastLearnTime").value(wordCardState.getLastLearnTime());
        jsonWriter.name("historyStack").value(gson.toJson(wordCardState.getHistoryStack()));
        jsonWriter.name("priorityQueue").value(gson.toJson(wordCardState.getPriorityQueue()));
        jsonWriter.name("newLearnedWordsQueue").value(gson.toJson(wordCardState.getNewLearnedWordsQueue()));
        jsonWriter.name("haveLearnedWordsQueue").value(gson.toJson(wordCardState.getHaveLearnedWordsQueue()));

        jsonWriter.endObject();
    }

    @Override
    public WordCardState read(JsonReader jsonReader) throws IOException {
        WordCardState wordCardState = new WordCardState();

        jsonReader.beginObject();

        while (jsonReader.hasNext()){
            switch (jsonReader.nextName()) {
                case "id":
                    wordCardState.setId(jsonReader.nextLong());
                    break;
                case "date":
                    wordCardState.setDate(jsonReader.nextString());
                    break;
                case "newLearnedWords":
                    wordCardState.setNewLearnedWords(gson.fromJson(jsonReader.nextString(), new TypeToken<List<Word>>(){}.getType()));
                    break;
                case "haveLearnedWords":
                    wordCardState.setHaveLearnedWords(gson.fromJson(jsonReader.nextString(), new TypeToken<List<Word>>(){}.getType()));
                    break;
                case "reviewWords":
                    wordCardState.setReviewWords(gson.fromJson(jsonReader.nextString(), new TypeToken<List<Word>>(){}.getType()));
                    break;
                case "newLearnedCount":
                    wordCardState.setNewLearnedCount(jsonReader.nextInt());
                    break;
                case "haveLearnedCount":
                    wordCardState.setHaveLearnedCount(jsonReader.nextInt());
                    break;
                case "reviewCount":
                    wordCardState.setReviewCount(jsonReader.nextInt());
                    break;
                case "wordSample":
                    wordCardState.setWordSample(gson.fromJson(jsonReader.nextString(), new TypeToken<WordSample>(){}.getType()));
                    break;
                case "wordPool":
                    wordCardState.setWordPool(gson.fromJson(jsonReader.nextString(), new TypeToken<HashMap<Integer, WordSample>>(){}.getType()));
                    break;
                case "lastLearnTime":
                    wordCardState.setLastLearnTime(jsonReader.nextString());
                    break;
                case "historyStack":
                    wordCardState.setHistoryStack(gson.fromJson(jsonReader.nextString(), new TypeToken<Stack<WordSample>>(){}.getType()));
                    break;
                case "priorityQueue":
                    wordCardState.setPriorityQueue(gson.fromJson(jsonReader.nextString(), new TypeToken<PriorityQueue<WordSample>>(){}.getType()));
                    break;
                case "newLearnedWordsQueue":
                    wordCardState.setNewLearnedWordsQueue(gson.fromJson(jsonReader.nextString(), new TypeToken<Queue<WordSample>>(){}.getType()));
                    break;
                case "haveLearnedWordsQueue":
                    wordCardState.setHaveLearnedWordsQueue(gson.fromJson(jsonReader.nextString(), new TypeToken<Queue<WordSample>>(){}.getType()));
                    break;
            }
        }

        jsonReader.endObject();
        return wordCardState;
    }
}
