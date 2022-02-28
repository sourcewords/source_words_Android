package com.example.sourcewords.ui.learn.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.sourcewords.R;
import com.example.sourcewords.ui.learn.viewModel.WordsAdapter;
import com.example.sourcewords.ui.learn.viewModel.LearnViewModel;
import com.example.sourcewords.ui.review.dataBean.WordRoot;

public class WordRootPage extends AppCompatActivity implements View.OnClickListener {
    private static final String GET_ID = "getWordRoot_id";
    private VideoView videoView;
    private LearnViewModel viewModel;
    private static int id;
    private static WordRoot root;
    private WordsAdapter adapter;
    private TextView textView;

    public static Intent newInstance(Context mContext, int id) {
        Intent intent = new Intent(mContext, WordRootPage.class);
        intent.putExtra(GET_ID, id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_root_page);
        viewModel = ViewModelProviders.of(this).get(LearnViewModel.class);
        Intent intent = getIntent();
        id = intent.getIntExtra(GET_ID, 0);
        init();
    }

    private void init() {
        textView = findViewById(R.id.page_wordRoot);
        RecyclerView recyclerView = findViewById(R.id.page_recyclerView);
        videoView = findViewById(R.id.page_videoView);
        ImageButton back = findViewById(R.id.page_back);
        back.setOnClickListener(this);
        ImageButton search = findViewById(R.id.page_search);
        search.setOnClickListener(this);
        adapter = new WordsAdapter(this);
        recyclerView.setAdapter(adapter);
        //viewModel.getWordRoot(id, textView,adapter,videoView);
        //root = viewModel.getWordRootById(id);
        getWordRootAndUpdateUI(id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        videoView.setMediaController(new MediaController(this));
        videoView.requestFocus();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.page_back:
                onBackPressed();
                break;
            case R.id.page_search:
                Intent i = new Intent(this, LearnSearchActivity.class);
                startActivity(i);
                break;
            case R.id.page_videoView:
                if (videoView.isPlaying())
                    videoView.pause();
                else
                    videoView.start();
                break;
        }
    }

    public void getWordRootAndUpdateUI(int id) {
        Handler handler = new PageHandler();
        new Thread(() -> {
            try {
                root = viewModel.getWordRootById(id);
                handler.sendEmptyMessage(0x6666);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    class PageHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x6666) {
                textView.setText(root.getRoot());
                adapter.setList(root.getWordlist());
                final String path = root.getVideo_url();
                Log.d("能否隐藏", path + "长度为" + path.length());
                if (path.length() == 0) {
                    videoView.setVisibility(View.INVISIBLE);
                } else {
                    videoView.setVideoURI(Uri.parse(path));
                    Log.d("this", "Done");
                }
            }
        }
    }

}