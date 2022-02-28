package com.example.sourcewords.ui.review.view.reviewUtils;

import com.example.sourcewords.ui.review.dataBean.Word;

import java.util.Comparator;
import java.util.Objects;

public class WordSample implements Comparable<WordSample> {

    Word word;
    int status;
    String time;

    public WordSample(Word word, int status, String time) {
        this.word = word;
        this.status = status;
        this.time = time;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "WordSample{" +
                "word=" + word +
                ", status=" + status +
                ", time='" + time + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordSample that = (WordSample) o;
        return status == that.status && Objects.equals(word.getId(), that.word.getId()) && Objects.equals(time, that.time);
    }


    // 散列化，将所有单词都丢进一个缓存池（原因用户随时会更改复习的时间和状态
    // 为方便处理就不一边修改状态一边同步到数据库），最后同步到数据库和云端
    @Override
    public int hashCode() {
        return Objects.hash(word.getId(), status, time);
    }

    @Override
    public int compareTo(WordSample wordSample) {
        return this.getTime().compareTo(wordSample.getTime());
    }
}
