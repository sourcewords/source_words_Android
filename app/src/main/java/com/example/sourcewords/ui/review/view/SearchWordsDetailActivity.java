package com.example.sourcewords.ui.review.view;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sourcewords.R;
import com.example.sourcewords.ui.review.dataBean.SingleWord;

import com.example.sourcewords.ui.review.viewmodel.ReviewViewModel;
import com.example.sourcewords.utils.OptimizeMeaningUtils;
import com.example.sourcewords.utils.PreferencesUtils;

import java.io.IOException;

public class SearchWordsDetailActivity extends AppCompatActivity {
    private int wordID;
    private MediaPlayer mMediaPlayer;
    private SingleWord mSingleWord;
    private ImageView horn;
    private TextView wordEng, soundMark, meaning, structure, examples,source;
    private ReviewViewModel mReviewViewModel;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_words_detail);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().
                    setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        wordID = getIntent().getIntExtra(PreferencesUtils.WORD_ID,0);
        initView();
        Listener();
    }

    public void initView(){
        mReviewViewModel = new ReviewViewModel(getApplication());
        mSingleWord = mReviewViewModel.getSingleWord(wordID);

        wordEng = findViewById(R.id.sw_title);
        wordEng.setText(mSingleWord.getWord());

        soundMark = findViewById(R.id.sw_soundmark);
        soundMark.setText(mSingleWord.getPhonetic());

        meaning = findViewById(R.id.sw_meaning);
        meaning.setText(OptimizeMeaningUtils.OptimizeMeaning(mSingleWord.getMeaning()));

        structure = findViewById(R.id.sw_structure);
        structure.setText(mSingleWord.getVariation());

        examples = findViewById(R.id.sw_examples);
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0; i<mSingleWord.getExample_sentences().size(); i++) {
            stringBuilder.append(mSingleWord.getExample_sentences().get(i).getEn())
                    .append("\n")
                    .append(mSingleWord.getExample_sentences().get(i).getZh())
                    .append("\n");
        }
        examples.setText(stringBuilder);

        source = findViewById(R.id.sw_source);
        source.setText(mSingleWord.getZh_source());

        horn = findViewById(R.id.sw_horn);
        url = mSingleWord.getPronunciation_url();
        initMediaPlayer();
    }

    public void Listener(){
        horn.setOnClickListener(V->{
            mMediaPlayer.start();
        });
    }

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMediaPlayer.release();
    }
}
