package com.example.sourcewords.ui.review.view;

import com.example.sourcewords.ui.review.dataBean.HistoryWord;
import com.example.sourcewords.ui.review.dataBean.SingleWord;

public interface AdapterCallBack {
    void startSearch(SingleWord singleWord);
    void startHistory(HistoryWord historyWord);
}
