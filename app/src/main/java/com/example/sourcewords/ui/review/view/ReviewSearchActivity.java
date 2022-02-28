package com.example.sourcewords.ui.review.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sourcewords.R;
import com.example.sourcewords.ui.review.dataBean.HistoryWord;
import com.example.sourcewords.ui.review.model.HistoryDBCallback;
import com.example.sourcewords.ui.review.viewmodel.HistoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class ReviewSearchActivity extends AppCompatActivity {
    private ImageView back, search, clear;
    private TextView unfold;
    private HistoryViewModel mHistoryViewModel;
    private SearchAdapter mSearchAdapter;
    private RecyclerView mRecyclerView;
    private boolean isFold = true;

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
        List<HistoryWord> list = mHistoryViewModel.getList();
        //最多显示6条记录
        List<HistoryWord> maxList = new ArrayList<>();
        if(list.size()>=6 && isFold){
            for(int i=0; i<6; i++){
                maxList.add(list.get(i));
            }
            mSearchAdapter.setList(maxList);
        } else {
            mSearchAdapter.setList(list);
        }
        if(!isFold){
            mSearchAdapter.setList(list);
            unfold.setVisibility(View.GONE);
        }
        mSearchAdapter.notifyDataSetChanged();
    }
}
