package com.example.sourcewords.ui.learn.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import com.example.sourcewords.R;
import com.example.sourcewords.ui.learn.viewModel.WordsAdapter;
import com.example.sourcewords.ui.learn.viewModel.LearnViewModel;
import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.dataBean.WordRoot;

import java.util.ArrayList;
import java.util.List;

public class WordRootPage extends AppCompatActivity implements View.OnClickListener {
    private static final String GET_ID = "getWordRoot_id";
    private TextView textView;
    private RecyclerView recyclerView;
    private VideoView videoView;
    private ImageButton back,search;
    private LearnViewModel viewModel;
    private static int id;

    public static Intent newInstance(Context mContext, int id){
        Intent intent = new Intent(mContext,WordRootPage.class);
        intent.putExtra(GET_ID,id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_root_page);
        viewModel = ViewModelProviders.of(this).get(LearnViewModel.class);
        Intent intent = getIntent();
        id = intent.getIntExtra(GET_ID,0);
        init();
    }

    private void init(){
        textView = findViewById(R.id.page_wordRoot);
        recyclerView = findViewById(R.id.page_recyclerView);
        videoView = findViewById(R.id.page_videoView);
        back = findViewById(R.id.page_back);
        search = findViewById(R.id.page_search);
        final WordsAdapter adapter = new WordsAdapter(this);
        recyclerView.setAdapter(adapter);
        viewModel.getWordRoot(id,textView,adapter,videoView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        videoView.setMediaController(new MediaController(this));
        videoView.requestFocus();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.page_back:
                break;
            case R.id.page_search:
                break;
        }
    }
}