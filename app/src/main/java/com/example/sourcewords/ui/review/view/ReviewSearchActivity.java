package com.example.sourcewords.ui.review.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sourcewords.R;
import com.example.sourcewords.ui.review.dataBean.HistoryWord;
import com.example.sourcewords.ui.review.dataBean.SingleWord;
import com.example.sourcewords.ui.review.viewmodel.HistoryViewModel;
import com.example.sourcewords.ui.review.viewmodel.ReviewViewModel;
import com.example.sourcewords.utils.OptimizeMeaningUtils;
import com.example.sourcewords.utils.PreferencesUtils;

import java.util.ArrayList;
import java.util.List;

public class ReviewSearchActivity extends AppCompatActivity {
    private List<SingleWord> searchedWordsList = new ArrayList<>();
    private EditText searchText;
    private ImageView back, search, clear;
    private TextView unfold;
    private HistoryViewModel mHistoryViewModel;
    private SearchAdapter mSearchAdapter;
    private RecyclerView mRecyclerView;
    private boolean isFold = true;
    private boolean isHistory = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_words);

        initView();
        Listener();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void initView(){
        mHistoryViewModel = new HistoryViewModel(getApplication());

        searchText = findViewById(R.id.search_word_editView);
        back = findViewById(R.id.search_word_back);
        search = findViewById(R.id.search_words);
        mRecyclerView = findViewById(R.id.searchRecyclerView);
        unfold = findViewById(R.id.unfold);
        clear = findViewById(R.id.clearHistory);

        mSearchAdapter = new SearchAdapter(getApplication(), mHistoryViewModel);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mSearchAdapter);

        initRV();
    }

    public void Listener(){
        mSearchAdapter.setAdapterCallBack(new AdapterCallBack() {
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
                else
                {
                    isFold = true;
                    isHistory = true;
                    initRV();
                }

            }
        });

        back.setOnClickListener(v -> {
            finish();
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

    @SuppressLint("NotifyDataSetChanged")
    public void initRV(){
        if(isHistory) {
            List<HistoryWord> list = mHistoryViewModel.getList();
            //最多显示6条记录
            List<HistoryWord> maxList = new ArrayList<>();
            if (list.size() >= 6 && isFold) {
                maxList = list.subList(0,6);
                mSearchAdapter.setList(maxList);
                unfold.setVisibility(View.VISIBLE);
            } else {
                mSearchAdapter.setList(list);
                unfold.setVisibility(View.GONE);
            }

            if (!isFold) {
                mSearchAdapter.setList(list);
                unfold.setVisibility(View.GONE);
            }
        } else {
            mSearchAdapter.setSList(searchedWordsList);
        }
        mSearchAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();
        isFold = true;
        isHistory = true;
        searchText.setText("");
        List<HistoryWord> list = mHistoryViewModel.getList();
        mSearchAdapter.setList(list);
        mSearchAdapter.notifyDataSetChanged();
    }
}
