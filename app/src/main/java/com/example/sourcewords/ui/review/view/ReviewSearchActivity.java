package com.example.sourcewords.ui.review.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.sourcewords.ui.SearchActivity;
import com.example.sourcewords.ui.review.dataBean.HistoryWord;
import com.example.sourcewords.ui.review.dataBean.SingleWord;
import com.example.sourcewords.ui.review.viewmodel.HistoryViewModel;
import com.example.sourcewords.ui.review.viewmodel.ReviewViewModel;
import com.example.sourcewords.utils.OptimizeMeaningUtils;
import com.example.sourcewords.utils.PreferencesUtils;

import java.util.ArrayList;
import java.util.List;

public class ReviewSearchActivity extends SearchActivity {
    private List<SingleWord> searchedWordsList = new ArrayList<>();
    private HistoryViewModel mHistoryViewModel;
    private ReviewSearchAdapter mReviewSearchAdapter;

    @Override
    @SuppressLint("NotifyDataSetChanged")
    public void initView(){
        super.initView();

        mHistoryViewModel = new HistoryViewModel(getApplication());

        mReviewSearchAdapter = new ReviewSearchAdapter(getApplication());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mReviewSearchAdapter);

        initRV();
    }

    @Override
    public void Listener(){
        super.Listener();

        mReviewSearchAdapter.setAdapterCallBack(new AdapterCallBack<SingleWord, HistoryWord>(){
            @Override
            public void startSearch(SingleWord singleWord) {
                boolean isExist = false;
                Intent intent = new Intent(getApplicationContext(), SearchWordsDetailActivity.class);
                intent.putExtra(PreferencesUtils.WORD_ID, singleWord.getId());

                for (HistoryWord historyWord : mHistoryViewModel.getList()){
                    if(singleWord.getId() == historyWord.getSaveID()){
                        isExist = true;
                        break;
                    }
                }
                if(!isExist)
                    mHistoryViewModel.Insert(new HistoryWord(OptimizeMeaningUtils.OptimizeMeaning(singleWord.getMeaning()), singleWord.getWord(), singleWord.getId()), isFinish -> {
                    });
                startActivity(intent);
            }

            @Override
            public void startHistory(HistoryWord historyWord) {
                Intent intent = new Intent(getApplicationContext(), SearchWordsDetailActivity.class);
                intent.putExtra(PreferencesUtils.WORD_ID, historyWord.getSaveID());

                startActivity(intent);
            }
        });

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String keyWords;
                if(!searchText.getText().toString().trim().equals("")){
                    isFold = false;
                    isHistory = false;
                    keyWords = searchText.getText().toString();
                    DynamicSearch(keyWords);
                }
                else
                {
                    isFold = true;
                    isHistory = true;
                    initRV();
                }

            }
        });

        unfold.setOnClickListener(v->{
            isFold = false;
            initRV();
        });

        clear.setOnClickListener(v->{
            mHistoryViewModel.Clear(b -> {
                initRV();
            });
        });
    }

    void DynamicSearch(String keyWords){
        ReviewViewModel reviewViewModel = new ReviewViewModel(getApplication());
        char c = keyWords.toCharArray()[0];
        if(Character.isLowerCase(c) || Character.isUpperCase(c))
            reviewViewModel.getLikelyWords(keyWords, list -> {
                if(list.size() <= 15){
                    searchedWordsList = list;
                } else {
                    searchedWordsList = list.subList(0,14);
                }
                initRV();
            });
        else
            reviewViewModel.getLikelyMeaning(keyWords, list -> {
                if(list.size() <= 15){
                    searchedWordsList = list;
                } else {
                    searchedWordsList = list.subList(0,14);
                }
                initRV();
            });
    }

    @SuppressLint("NotifyDataSetChanged")
    public void initRV(){
        if(isHistory) {
            List<HistoryWord> list = mHistoryViewModel.getList();
            //最多显示6条记录
            List<HistoryWord> maxList = new ArrayList<>();
            if (list.size() >= 6 && isFold) {
                maxList = list.subList(0,6);
                mReviewSearchAdapter.setList(maxList);
                unfold.setVisibility(View.VISIBLE);
            } else {
                mReviewSearchAdapter.setList(list);
                unfold.setVisibility(View.GONE);
            }

            if (!isFold) {
                mReviewSearchAdapter.setList(list);
                unfold.setVisibility(View.GONE);
            }
        } else {
            mReviewSearchAdapter.setSList(searchedWordsList);
        }
        mReviewSearchAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<HistoryWord> list = mHistoryViewModel.getList();
        mReviewSearchAdapter.setList(list);
        mReviewSearchAdapter.notifyDataSetChanged();
    }
}
