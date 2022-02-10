package com.example.sourcewords.ui.review.view;

import android.app.AppComponentFactory;
import android.media.AudioManager;
import android.media.MediaExtractor;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.sourcewords.R;
import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.ui.review.viewmodel.ReviewViewModel;
import com.example.sourcewords.utils.DateUtils;
import com.example.sourcewords.utils.PreferenceUtils;

import java.io.IOException;
import java.util.Iterator;

public class DetailActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private MediaPlayer mMediaPlayer;
    private ImageButton playerButton;
    private Button again,hard,good,easy;
    private TextView wordEng, soundMark, meaning, structure, examples;
    private String url;
    private ReviewViewModel mViewModel;
    private Word mWord;
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

        playerButton.findViewById(R.id.horn_button);
        wordEng.findViewById(R.id.wordEng);
        soundMark.findViewById(R.id.soundMark);
        meaning.findViewById(R.id.meaning);
        structure.findViewById(R.id.structure);
        examples.findViewById(R.id.examples);
        again.findViewById(R.id.again);
        hard.findViewById(R.id.hard);
        good.findViewById(R.id.good);
        easy.findViewById(R.id.easy);

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
            }
        });

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWord.getWord_info().setNextTime(DateUtils.addTime(PreferenceUtils.HARD));
                mViewModel.Update(mWordRoot);
            }
        });

        good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWord.getWord_info().setNextTime(DateUtils.addTime(PreferenceUtils.GOOD));
                mViewModel.Update(mWordRoot);
            }
        });

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWord.getWord_info().setNextTime(DateUtils.addTime(PreferenceUtils.EASY));
                mViewModel.Update(mWordRoot);
            }
        });
    }

    //重写返回键以释放该播放器
    @Override
    public void onBackPressed() {
        mMediaPlayer.release();
        mMediaPlayer = null;
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        mMediaPlayer.release();
        mMediaPlayer = null;
        super.onPause();
    }

    @Override
    protected void onResume() {
        initMediaPlayer();
        super.onResume();
    }
}
