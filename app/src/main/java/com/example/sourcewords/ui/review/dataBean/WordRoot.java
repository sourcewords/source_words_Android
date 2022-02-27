package com.example.sourcewords.ui.review.dataBean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "wordRoot_table")
public class WordRoot {
    @PrimaryKey
    @ColumnInfo(name = "词根序号")
    private int id;

    /**
     * meaning : quis sunt
     * video_url : http://ityh.gu/zlokiexhl
     * id : 78
     * source : adipisicing ipsum cillum dolor
     * root : in pariatur
     */
    @ColumnInfo(name = "中文意思")
    private String meaning;
    @ColumnInfo(name = "视频地址")
    private String video_url;

    @ColumnInfo(name = "词根来源")
    private String source;
    @ColumnInfo(name = "词根")
    private String root;
    @ColumnInfo(name = "单词列表")
    private List<Word> wordlist;

    public WordRoot(String meaning, String video_url, int id, String source, String root, List<Word> wordlist) {
        this.meaning = meaning;
        this.video_url = video_url;
        this.id = id;
        this.source = source;
        this.root = root;
        this.wordlist = wordlist;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public List<Word> getWordlist() {
        return wordlist;
    }

    public void setWordlist(List<Word> wordlist) {
        this.wordlist = wordlist;
    }

}
