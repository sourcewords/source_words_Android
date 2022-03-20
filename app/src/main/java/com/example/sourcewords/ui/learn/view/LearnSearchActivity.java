package com.example.sourcewords.ui.learn.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sourcewords.R;
import com.example.sourcewords.ui.SearchActivity;
import com.example.sourcewords.ui.learn.dataBean.HistoryWordRoot;
import com.example.sourcewords.ui.learn.viewModel.HistoryWRViewModel;
import com.example.sourcewords.ui.learn.viewModel.LearnViewModel;
import com.example.sourcewords.ui.learn.viewModel.WordRootAdapter;
import com.example.sourcewords.ui.review.dataBean.HistoryWord;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.ui.review.view.AdapterCallBack;
import com.example.sourcewords.ui.review.view.ReviewSearchAdapter;
import com.example.sourcewords.ui.review.view.SearchWordsDetailActivity;
import com.example.sourcewords.ui.review.viewmodel.HistoryViewModel;
import com.example.sourcewords.ui.review.viewmodel.ReviewViewModel;
import com.example.sourcewords.utils.OptimizeMeaningUtils;
import com.example.sourcewords.utils.PreferencesUtils;

import java.util.ArrayList;
import java.util.List;

//TODO 词根搜索栏
public class LearnSearchActivity extends SearchActivity {
    private HistoryWRViewModel mHistoryWRViewModel;
    private LearnSearchAdapter mReviewSearchAdapter;
    private List<WordRoot> searchedWordsList;

    @Override
    public void initView() {
        super.initView();
        mHistoryWRViewModel = new HistoryWRViewModel(getApplication());

        mReviewSearchAdapter = new LearnSearchAdapter(getApplication());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mReviewSearchAdapter);

        searchText.setHint("请输入要搜索的词根~");
    }

    @Override
    public void Listener() {
        super.Listener();

        mReviewSearchAdapter.setAdapterCallBack(new AdapterCallBack<WordRoot,HistoryWordRoot>() {
            @Override
            public void startSearch(WordRoot wordRoot) {
                boolean isExist = false;
                Intent intent = new Intent(getApplicationContext(), WordRootPage.class);
                intent.putExtra(PreferencesUtils.WORD_ID, wordRoot.getId());

                for (HistoryWordRoot historyWordRoot : mHistoryWRViewModel.getList()){
                    if(wordRoot.getId() == historyWordRoot.getSaveId()){
                        isExist = true;
                        break;
                    }
                }
                if(!isExist)
                    mHistoryWRViewModel.Insert(new HistoryWordRoot(wordRoot.getRoot(),wordRoot.getId(), wordRoot.getMeaning()), isFinish -> {

                    });
                startActivity(intent);
            }

            @Override
            public void startHistory(HistoryWordRoot historyWordRoot) {
                Intent intent = new Intent(getApplicationContext(), WordRootPage.class);
                intent.putExtra(PreferencesUtils.WORD_ID, historyWordRoot.getSaveId());

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
            mHistoryWRViewModel.Clear(b -> {
                initRV();
            });
        });
    }

    void DynamicSearch(String keyWords){
        LearnViewModel learnViewModel = new LearnViewModel(getApplication());
        char c = keyWords.toCharArray()[0];
        learnViewModel.getLikelyWordRoot(keyWords, list -> {
            if(list.size() <= 15){
                searchedWordsList = list;
            } else {
                searchedWordsList = list.subList(0,14);
            }
            initRV();
        });
    }

    public void initRV(){
        if(isHistory) {
            List<HistoryWordRoot> list = mHistoryWRViewModel.getList();
            //最多显示6条记录
            List<HistoryWordRoot> maxList = new ArrayList<>();
            if (list.size() >= 6 && isFold) {
                maxList = list.subList(0,6);
                mReviewSearchAdapter.setHWRList(maxList);
                unfold.setVisibility(View.VISIBLE);
            } else {
                mReviewSearchAdapter.setHWRList(list);
                unfold.setVisibility(View.GONE);
            }

            if (!isFold) {
                mReviewSearchAdapter.setHWRList(list);
                unfold.setVisibility(View.GONE);
            }
        } else {
            mReviewSearchAdapter.setWRList(searchedWordsList);
        }
        mReviewSearchAdapter.notifyDataSetChanged();
    }

    /*
    private MutableLiveData<List<WordRoot>> wordRoots;
    private LearnViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchwordsroot);
        init();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void init() {
        SearchView searchView = findViewById(R.id.search_wordRoot);
        RecyclerView recyclerView = findViewById(R.id.search_recyclerView);
        final WordRootAdapter wordRootAdapter = new WordRootAdapter(this);
        viewModel = ViewModelProviders.of(this).get(LearnViewModel.class);
        wordRoots = new MutableLiveData<>();
        wordRoots.observe(this, wordRoots -> {
            wordRootAdapter.setWordRoots(wordRoots);
            wordRootAdapter.notifyDataSetChanged();
        });
        recyclerView.setAdapter(wordRootAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //搜索时触发该方法
                //getList(query, wordRoots);
                viewModel.getSimilarWords(query).observe(LearnSearchActivity.this, list -> wordRoots.setValue(list));
                Log.d("search", "现在在搜索！！！！！！！！！！！！！！！！！！！！");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //输入内容变化时，触发该方法
                //getList(newText, wordRoots);
                viewModel.getSimilarWords(newText).observe(LearnSearchActivity.this, list -> wordRoots.setValue(list));
                Log.d("Change", "现在是changing......................................");
                return false;
            }
        });
    }*/
}
