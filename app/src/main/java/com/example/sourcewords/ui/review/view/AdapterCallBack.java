package com.example.sourcewords.ui.review.view;

import com.example.sourcewords.ui.review.dataBean.HistoryWord;
import com.example.sourcewords.ui.review.dataBean.SingleWord;

public interface AdapterCallBack<T, E> {
    void startSearch(T t);
    void startHistory(E e);
}
