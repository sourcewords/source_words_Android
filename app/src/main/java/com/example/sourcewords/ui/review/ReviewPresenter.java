package com.example.sourcewords.ui.review;

import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.ui.review.model.WordRepository;

public class ReviewPresenter implements ReviewContract.Presenter {

    private WordRepository wordRepository;
    private ReviewContract.View reviewView;

    private WordRoot wordRoot;

    public ReviewPresenter(WordRepository wordRepository, ReviewContract.View reviewView) {

        this.wordRepository = wordRepository;
        this.reviewView = reviewView;

        reviewView.setPresenter(this);
    }

    @Override
    public void initData() {
        // 拿到当日学到的词根，id保存在sharePreference中
        wordRoot = wordRepository.getWordRootTest(1);
        if(wordRoot == null) {
            reviewView.initNoneView();
        }
        else{
            reviewView.initWordView(wordRoot);
        }
    }
}
