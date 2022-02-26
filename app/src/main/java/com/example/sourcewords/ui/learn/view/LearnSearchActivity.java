package com.example.sourcewords.ui.learn.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sourcewords.R;
import com.example.sourcewords.ui.learn.viewModel.LearnViewModel;
import com.example.sourcewords.ui.learn.viewModel.WordRootAdapter;
import com.example.sourcewords.ui.review.dataBean.WordRoot;

import java.util.List;

//TODO 词根搜索栏
public class LearnSearchActivity extends AppCompatActivity {
    private MutableLiveData<List<WordRoot>> wordRoots;
    private LearnViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchwordsroot);
        init();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void init(){
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
                viewModel.getList(query,wordRoots);
                Log.d("search","现在在搜索！！！！！！！！！！！！！！！！！！！！");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //输入内容变化时，触发该方法
                viewModel.getList(newText,wordRoots);
                Log.d("Change","现在是changing......................................");
                return false;
            }
        });
    }

}
