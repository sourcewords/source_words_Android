package com.example.sourcewords.ui.learn;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sourcewords.R;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.ui.review.db.WordDatabase;

import java.util.ArrayList;
import java.util.List;

//TODO 词根搜索栏
public class LearnSearchActivity extends AppCompatActivity {
    private SearchView searchView;
    private LiveData<List<Test.DataBean>> wordRoots;
    private ViewModel viewModel;

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
        WordRootRepository repository = new WordRootRepository(this.getApplicationContext());
        viewModel = new ViewModel(this);
        wordRoots = new LiveData<List<Test.DataBean>>() {
            @Nullable
            @Override
            public List<Test.DataBean> getValue() {
                return super.getValue();
            }
        };
        wordRoots.observe(this, new Observer<List<Test.DataBean>>() {
            @Override
            public void onChanged(List<Test.DataBean> wordRoots) {
                wordRootAdapter.setWordRoots(wordRoots);
                wordRootAdapter.notifyDataSetChanged();
            }
        });
        recyclerView.setAdapter(wordRootAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //搜索时触发该方法
                List<Test.DataBean> list = viewModel.getWordList(query);
                wordRootAdapter.setWordRoots(list);
                Log.d("search","现在在搜索！！！！！！！！！！！！！！！！！！！！");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //输入内容变化时，触发该方法
                Log.d("Change","现在是changing......................................");
                return false;
            }
        });
    }

}
