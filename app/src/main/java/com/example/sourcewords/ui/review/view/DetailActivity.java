package com.example.sourcewords.ui.review.view;

import android.annotation.SuppressLint;
import android.app.AppComponentFactory;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.sourcewords.R;
import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.dataBean.WordInfoBean;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.ui.review.viewmodel.ReviewViewModel;
import com.example.sourcewords.utils.DateUtils;
import com.example.sourcewords.utils.PreferenceUtils;

import java.io.IOException;

public class DetailActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private MediaPlayer mMediaPlayer;
    private ImageView playerButton;
    private Button again,hard,good,easy;
    private TextView wordEng, soundMark, meaning, structure, examples;
    private String url;
    private ReviewViewModel mViewModel;
    private Word mWord;
    private WordInfoBean mWordInfo;
    private WordRoot mWordRoot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_words_detail);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().
                    setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        init();
        listener();
    }


    public void init(){
        mViewModel = new ReviewViewModel(getApplication());
        int wordId = getIntent().getIntExtra("indexWord", 0);
        int rootId = getIntent().getIntExtra("indexWordRoot",0);
        mWordRoot = mViewModel.getWordRootTest(1);
        mWord = mWordRoot.getWordlist().get(wordId);
        mWordInfo = mWord.getWord_info();

        playerButton = findViewById(R.id.horn_button);

        wordEng = findViewById(R.id.wordEng);
        wordEng.setText(mWordInfo.getWord());

        soundMark = findViewById(R.id.soundMark);
        soundMark.setText(mWordInfo.getPhonetic());

        meaning = findViewById(R.id.meaning);
        meaning.setText(mWordInfo.getMeaning());

        structure = findViewById(R.id.structure);
        structure.setText(mWordInfo.getZh_source());
        url = mWordInfo.getPronunciation_url();

        examples = findViewById(R.id.examples);
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0; i<mWordInfo.getExample_sentences().size(); i++) {
            stringBuilder.append(mWordInfo.getExample_sentences().get(i).getEn())
                    .append("\n")
                    .append(mWordInfo.getExample_sentences().get(i).getZh())
                    .append("\n");
        }
         examples.setText(stringBuilder);



        playerButton = findViewById(R.id.horn_button);
        wordEng = findViewById(R.id.wordEng);
        soundMark = findViewById(R.id.soundMark);
        meaning = findViewById(R.id.meaning);
        structure = findViewById(R.id.structure);
        examples = findViewById(R.id.examples);
        again = findViewById(R.id.again);
        hard = findViewById(R.id.hard);
        good = findViewById(R.id.good);
        easy = findViewById(R.id.easy);

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        initMediaPlayer();
    }

    //初始化播放器
    public void initMediaPlayer(){
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mMediaPlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.prepareAsync();
    }

    public void listener(){
        int id = mWord.getId();

        playerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMediaPlayer.start();
            }
        });

        again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWord.getWord_info().setNextTime(DateUtils.addTime(PreferenceUtils.AGAIN));
                mViewModel.Update(mWordRoot);
                Intent intent = new Intent();
                intent.putExtra("result", 0);
                DetailActivity.this.setResult(RESULT_OK, intent);
                DetailActivity.this.finish();
            }
        });

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWord.getWord_info().setNextTime(DateUtils.addTime(PreferenceUtils.HARD));
                mViewModel.Update(mWordRoot);
                Intent intent = new Intent();
                intent.putExtra("result", 1);
                DetailActivity.this.setResult(RESULT_OK, intent);
                DetailActivity.this.finish();
            }
        });

        good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWord.getWord_info().setNextTime(DateUtils.addTime(PreferenceUtils.GOOD));
                mViewModel.Update(mWordRoot);
                Intent intent = new Intent();
                intent.putExtra("result", 2);
                DetailActivity.this.setResult(RESULT_OK, intent);
                DetailActivity.this.finish();
            }
        });

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWord.getWord_info().setNextTime(DateUtils.addTime(PreferenceUtils.EASY));
                mViewModel.Update(mWordRoot);
                Intent intent = new Intent();
                intent.putExtra("result", 4);
                DetailActivity.this.setResult(RESULT_OK, intent);
                DetailActivity.this.finish();
            }
        });
    }

    //重写返回键以释放该播放器
    @Override
    public void onBackPressed() {
//        mMediaPlayer.release();
//        mMediaPlayer = null;
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
//        mMediaPlayer.release();
//        mMediaPlayer = null;
        super.onPause();
    }

    @Override
    protected void onResume() {
//        initMediaPlayer();
        super.onResume();
    }
}
