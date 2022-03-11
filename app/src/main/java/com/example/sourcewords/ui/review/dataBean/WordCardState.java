package com.example.sourcewords.ui.review.dataBean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.sourcewords.ui.review.view.reviewUtils.WordSample;
import com.example.sourcewords.ui.review.viewmodel.ReviewCardViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

@Entity(tableName = "word_card_state")
public class WordCardState {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private long id;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "new_learned_words")
    private List<Word> newLearnedWords;

    @ColumnInfo(name = "have_Learned_words")
    private List<Word> haveLearnedWords;

    @ColumnInfo(name = "review_words")
    private List<Word> reviewWords;

    @ColumnInfo(name = "new_learned_count")
    private int newLearnedCount;

    @ColumnInfo(name = "have_learned_count")
    private int haveLearnedCount;

    @ColumnInfo(name = "review_count")
    private int reviewCount;

    @ColumnInfo(name = "word_sample")
    private WordSample wordSample;

    @ColumnInfo(name = "word_pool")
    private HashMap<Integer, WordSample> wordPool;

    @ColumnInfo(name = "last_learn_time")
    private String lastLearnTime;

    @ColumnInfo(name = "history_stack")
    private Stack<WordSample> historyStack;

    @ColumnInfo(name = "priority_queue")
    private PriorityQueue<WordSample> priorityQueue;

    @ColumnInfo(name = "new_learned_words_queue")
    private Queue<WordSample> newLearnedWordsQueue;

    @ColumnInfo(name = "have_learned_words_queue")
    private Queue<WordSample> haveLearnedWordsQueue;

    public WordCardState(String date, List<Word> newLearnedWords, List<Word> haveLearnedWords, List<Word> reviewWords, int newLearnedCount, int haveLearnedCount, int reviewCount, WordSample wordSample, HashMap<Integer, WordSample> wordPool, String lastLearnTime, Stack<WordSample> historyStack, PriorityQueue<WordSample> priorityQueue, Queue<WordSample> newLearnedWordsQueue, Queue<WordSample> haveLearnedWordsQueue) {
        this.date = date;
        this.newLearnedWords = newLearnedWords;
        this.haveLearnedWords = haveLearnedWords;
        this.reviewWords = reviewWords;
        this.newLearnedCount = newLearnedCount;
        this.haveLearnedCount = haveLearnedCount;
        this.reviewCount = reviewCount;
        this.wordSample = wordSample;
        this.wordPool = wordPool;
        this.lastLearnTime = lastLearnTime;
        this.historyStack = historyStack;
        this.priorityQueue = priorityQueue;
        this.newLearnedWordsQueue = newLearnedWordsQueue;
        this.haveLearnedWordsQueue = haveLearnedWordsQueue;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public List<Word> getNewLearnedWords() {
        return newLearnedWords;
    }

    public void setNewLearnedWords(List<Word> newLearnedWords) {
        this.newLearnedWords = newLearnedWords;
    }

    public List<Word> getHaveLearnedWords() {
        return haveLearnedWords;
    }

    public void setHaveLearnedWords(List<Word> haveLearnedWords) {
        this.haveLearnedWords = haveLearnedWords;
    }

    public List<Word> getReviewWords() {
        return reviewWords;
    }

    public void setReviewWords(List<Word> reviewWords) {
        this.reviewWords = reviewWords;
    }

    public int getNewLearnedCount() {
        return newLearnedCount;
    }

    public void setNewLearnedCount(int newLearnedCount) {
        this.newLearnedCount = newLearnedCount;
    }

    public int getHaveLearnedCount() {
        return haveLearnedCount;
    }

    public void setHaveLearnedCount(int haveLearnedCount) {
        this.haveLearnedCount = haveLearnedCount;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public WordSample getWordSample() {
        return wordSample;
    }

    public void setWordSample(WordSample wordSample) {
        this.wordSample = wordSample;
    }

    public HashMap<Integer, WordSample> getWordPool() {
        return wordPool;
    }

    public void setWordPool(HashMap<Integer, WordSample> wordPool) {
        this.wordPool = wordPool;
    }

    public String getLastLearnTime() {
        return lastLearnTime;
    }

    public void setLastLearnTime(String lastLearnTime) {
        this.lastLearnTime = lastLearnTime;
    }

    public Stack<WordSample> getHistoryStack() {
        return historyStack;
    }

    public void setHistoryStack(Stack<WordSample> historyStack) {
        this.historyStack = historyStack;
    }

    public PriorityQueue<WordSample> getPriorityQueue() {
        return priorityQueue;
    }

    public void setPriorityQueue(PriorityQueue<WordSample> priorityQueue) {
        this.priorityQueue = priorityQueue;
    }

    public Queue<WordSample> getNewLearnedWordsQueue() {
        return newLearnedWordsQueue;
    }

    public void setNewLearnedWordsQueue(Queue<WordSample> newLearnedWordsQueue) {
        this.newLearnedWordsQueue = newLearnedWordsQueue;
    }

    public Queue<WordSample> getHaveLearnedWordsQueue() {
        return haveLearnedWordsQueue;
    }

    public void setHaveLearnedWordsQueue(Queue<WordSample> haveLearnedWordsQueue) {
        this.haveLearnedWordsQueue = haveLearnedWordsQueue;
    }
}
