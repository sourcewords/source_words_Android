package com.example.sourcewords.ui.review;

import com.example.sourcewords.ui.review.dataBean.WordRoot;

public interface ReviewContract {

    public interface View {

        void setPresenter(Presenter presenter);

        void initNoneView();

        void initWordView(WordRoot wordRoot);
    }

    public interface Presenter {
        void initData();
    }
}
