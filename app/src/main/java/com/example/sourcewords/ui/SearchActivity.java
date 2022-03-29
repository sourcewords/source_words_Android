package com.example.sourcewords.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sourcewords.R;

public abstract class SearchActivity extends AppCompatActivity {
    protected EditText searchText;
    protected ImageView back, search, clear;
    protected TextView unfold;
    protected RecyclerView mRecyclerView;
    protected boolean isFold = true;
    protected boolean isHistory = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_words);

        initView();
        Listener();
    }

    protected void initView() {
        searchText = findViewById(R.id.search_word_editView);
        back = findViewById(R.id.search_word_back);
        search = findViewById(R.id.search_words);
        mRecyclerView = findViewById(R.id.searchRecyclerView);
        unfold = findViewById(R.id.unfold);
        clear = findViewById(R.id.clearHistory);
    }

    protected void Listener() {
        back.setOnClickListener(v -> {
            finish();
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();
        isFold = true;
        isHistory = true;
        searchText.setText("");
    }
}
