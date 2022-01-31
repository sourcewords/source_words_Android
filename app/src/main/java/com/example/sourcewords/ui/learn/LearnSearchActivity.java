package com.example.sourcewords.ui.learn;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sourcewords.R;
import java.util.ArrayList;

//TODO 词根搜索栏
public class LearnSearchActivity extends AppCompatActivity {
    private SearchView searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchwordsroot);
        init();
    }

    private void init(){
        searchView = findViewById(R.id.search_wordRoot);
        RecyclerView recyclerView = findViewById(R.id.search_recyclerView);
        final WordRootAdapter wordRootAdapter = new WordRootAdapter(this);
        wordRootAdapter.setWordRoots(new ArrayList<>());
        recyclerView.setAdapter(wordRootAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
