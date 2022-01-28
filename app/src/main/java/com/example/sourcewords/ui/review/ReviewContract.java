package com.example.sourcewords.ui.review;

import com.example.sourcewords.ui.review.dataBean.WordRoot;

public class ReviewContract {

    interface View {

        void setPresenter(Presenter presenter);

        void initNoneView();

        void initWordView(WordRoot wordRoot);
    }

    interface Presenter {
        void initData();
    }
}
