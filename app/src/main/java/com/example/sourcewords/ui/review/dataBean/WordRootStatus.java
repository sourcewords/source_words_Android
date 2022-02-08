package com.example.sourcewords.ui.review.dataBean;

public class WordRootStatus {
    private int root_id;
    private int status;  //1完成 0未完成

    public WordRootStatus(int root_id, int status) {
        this.root_id = root_id;
        this.status = status;
    }

    public int getRoot_id() {
        return root_id;
    }

    public void setRoot_id(int root_id) {
        this.root_id = root_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
