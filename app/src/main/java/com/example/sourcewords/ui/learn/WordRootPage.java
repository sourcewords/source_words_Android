package com.example.sourcewords.ui.learn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.sourcewords.R;
import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.ui.review.db.WordDatabase;

import java.util.List;

public class WordRootPage extends AppCompatActivity {
    private static final String GET_ID = "getWordRoot_id";
    private TextView textView;
    private RecyclerView recyclerView;
    private VideoView videoView;
    private ImageButton back,search;
    private List<Word> list;
    private WordDatabase db;

    public static Intent newInstance(Context mContext, int id){
        Intent intent = new Intent(mContext,WordRootPage.class);
        intent.putExtra(GET_ID,id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_root_page);
        Intent intent = getIntent();
        final int id = intent.getIntExtra(GET_ID,0);
        final WordRoot root = db.getWordDao().getWordRootById(id);
        list = root.getList();
        init();
    }

    private void init(){
        textView = findViewById(R.id.page_wordRoot);
        recyclerView = findViewById(R.id.search_recyclerView);
        videoView = findViewById(R.id.page_videoView);
        back = findViewById(R.id.page_back);
        search = findViewById(R.id.page_search);
        final WordsAdapter adapter = new WordsAdapter(this);
        adapter.setList(list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}