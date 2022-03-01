package com.example.sourcewords.ui.review.view;

import android.annotation.SuppressLint;
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

        mSearchAdapter = new SearchAdapter(getApplication());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mSearchAdapter);

        initRV();
    }

    public void Listener(){
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Toast.makeText(getApplication(), String.valueOf(searchText.getText().toString().length()), Toast.LENGTH_SHORT).show();
                String keyWords;
                //判断是否为空
                if(!searchText.getText().toString().trim().equals("")){
                    isFold = false;
                    isHistory = false;
                    keyWords = searchText.getText().toString();
                    ReviewViewModel reviewViewModel = new ReviewViewModel(getApplication());
                    reviewViewModel.getLikelyWords(keyWords, list -> {
                        if(list.size() <= 15){
                            searchedWordsList = list;
                        } else {
                            searchedWordsList = list.subList(0,14);
                        }
                        initRV();
                    });
                } else {
                    isFold = true;
                    isHistory = true;
                }

            }
        });

        back.setOnClickListener(v -> {
            finish();
        });

        search.setOnClickListener(v->{
            //测试
            mHistoryViewModel.Insert(new HistoryWord("啊啊", "aa"), isFinish -> {
                   initRV();
            });
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
}
