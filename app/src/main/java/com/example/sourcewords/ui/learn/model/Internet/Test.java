package com.example.sourcewords.ui.learn.model.Internet;

import com.example.sourcewords.ui.review.dataBean.WordRoot;

import java.io.Serializable;
import java.util.List;

public class Test {

    private int code;
    private String message;
    private List<WordRoot> data;

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public List<WordRoot> getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(List<WordRoot> data) {
        this.data = data;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
