package com.example.sourcewords.ui.review.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sourcewords.R;
import com.example.sourcewords.ui.learn.view.LearnSearchActivity;
import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.dataBean.WordInfoBean;
import com.example.sourcewords.ui.review.view.reviewUtils.ContextUtils;
import com.example.sourcewords.ui.review.view.reviewUtils.WordSample;
import com.example.sourcewords.ui.review.viewmodel.ReviewCardViewModel;
import com.example.sourcewords.ui.review.viewmodel.ReviewViewModel;
import com.example.sourcewords.utils.DateUtils;

import java.io.IOException;

public class DetailActivity extends AppCompatActivity {
    private ImageView back, search;
    private int code;
    private int count;
    private Toolbar mToolbar;
    private MediaPlayer mMediaPlayer;
    private ImageView playerButton;
    private Button again,hard,good,easy;
    private Time againTime, hardTime, goodTime, easyTime;
    private TextView wordEng, soundMark, meaning, structure, examples;
    private String url;
    private ReviewViewModel mViewModel;
    private Word mWord;
    private WordInfoBean mWordInfo;
    private ReviewCardViewModel reviewCardViewModel;
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
        initButton();
        listener();
    }

    @SuppressLint("SetTextI18n")
    public void initButton(){
        if(code == 0){
            againTime = new Time(1,"MINS");
            hardTime = new Time(5,"MINS");
            goodTime = new Time(10, "MINS");
            easyTime = new Time(4,"DAYS");
        } else if(code == 1){
            againTime = new Time(5, "MINS");
            hardTime = new Time(10,"MINS");
            goodTime = new Time(1,"DAYS");
            easyTime = new Time(2,"DAYS");
        } else if(code == 2){
            againTime = new Time(10,"MINS");
            hardTime = new Time(1,"DAYS");
            goodTime = new Time(2,"DAYS");
            easyTime = new Time(4,"DAYS");
        }
        again.setText(againTime.toString()+"\nagain");
        hard.setText(hardTime.toString()+"\nhard");
        good.setText(goodTime.toString()+"\ngood");
        easy.setText(easyTime.toString()+"\neasy");
    }


    public void init(){
        back = findViewById(R.id.detail_back);
        search = findViewById(R.id.search);

        code = getIntent().getIntExtra("code",0);
        count = getIntent().getIntExtra("count", 0);

        mViewModel = new ReviewViewModel(getApplication());
        reviewCardViewModel = ViewModelProviders.of((FragmentActivity) ContextUtils.getContext()).get(ReviewCardViewModel.class);
        int wordId = getIntent().getIntExtra("wordId", 0);

        mWord = mViewModel.search(wordId);

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

        back.setOnClickListener(v -> {
            finish();
        });

        search.setOnClickListener(v -> {
            Intent intent = new Intent(DetailActivity.this, ReviewSearchActivity.class);
            startActivity(intent);
        });

        playerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMediaPlayer.start();
            }
        });

        again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nextTime = DateUtils.addTime(againTime.getValue(), againTime.getUnit());
                int status = code;
                WordSample wordSample = new WordSample(mWord, status, nextTime);


                if(againTime.getUnit() == "DAYS") {
                    wordSample.setStatus(2);
                    reviewCardViewModel.getWordPool().put(id, wordSample);
                    reviewCardViewModel.getHistoryStack().push(wordSample);
                }

                else {
                    wordSample.setStatus(1);
                    reviewCardViewModel.getPriorityQueue().offer(wordSample);
                    reviewCardViewModel.getHistoryStack().push(wordSample);
                }
                switch (code) {
                    case 0:
                        reviewCardViewModel.getNewLearnedCount().setValue(--count);
                        reviewCardViewModel.getReviewCount().setValue(reviewCardViewModel.getPriorityQueue().size());

                        break;
                    case 1:
                        reviewCardViewModel.getReviewCount().setValue(--count);
                        reviewCardViewModel.getReviewCount().setValue(reviewCardViewModel.getPriorityQueue().size());

                        break;
                    case 2:
                        reviewCardViewModel.getHaveLearnedCount().setValue(--count);
                        reviewCardViewModel.getReviewCount().setValue(reviewCardViewModel.getPriorityQueue().size());

                        break;
                }
                Intent intent = new Intent();
                DetailActivity.this.setResult(RESULT_OK, intent);
                DetailActivity.this.finish();
            }
        });

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nextTime = DateUtils.addTime(hardTime.getValue(), hardTime.getUnit());
                int status = code;
                WordSample wordSample = new WordSample(mWord, status, nextTime);

                if(hardTime.getUnit() == "DAYS") {
                    wordSample.setStatus(2);
                    reviewCardViewModel.getWordPool().put(id, wordSample);
                }
                else {
                    wordSample.setStatus(1);
                    reviewCardViewModel.getPriorityQueue().offer(wordSample);
                }
                Log.d("pre", wordSample.toString() + "inner" + wordSample.getWord().getWord_info().getStatus());

                reviewCardViewModel.getHistoryStack().push(wordSample);
                switch (code) {
                    case 0:
                        reviewCardViewModel.getNewLearnedCount().setValue(--count);
                        reviewCardViewModel.getReviewCount().setValue(reviewCardViewModel.getPriorityQueue().size());

                        break;
                    case 1:
                        reviewCardViewModel.getReviewCount().setValue(--count);
                        reviewCardViewModel.getReviewCount().setValue(reviewCardViewModel.getPriorityQueue().size());

                        break;
                    case 2:
                        reviewCardViewModel.getHaveLearnedCount().setValue(--count);
                        reviewCardViewModel.getReviewCount().setValue(reviewCardViewModel.getPriorityQueue().size());

                        break;
                }
                Intent intent = new Intent();
                DetailActivity.this.setResult(RESULT_OK, intent);
                DetailActivity.this.finish();
            }
        });

        good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nextTime = DateUtils.addTime(goodTime.getValue(), goodTime.getUnit());
                int status = code;
                WordSample wordSample = new WordSample(mWord, status, nextTime);
                if(goodTime.getUnit() == "DAYS") {
                    wordSample.setStatus(2);
                    reviewCardViewModel.getWordPool().put(id, wordSample);
                }
                else {
                    wordSample.setStatus(1);
                    reviewCardViewModel.getPriorityQueue().offer(wordSample);
                }
                Log.d("pre", wordSample.toString() + "inner" + wordSample.getWord().getWord_info().getStatus());
                reviewCardViewModel.getHistoryStack().push(wordSample);
                switch (code) {
                    case 0:
                        reviewCardViewModel.getNewLearnedCount().setValue(--count);
                        reviewCardViewModel.getReviewCount().setValue(reviewCardViewModel.getPriorityQueue().size());
                        break;
                    case 1:
                        reviewCardViewModel.getReviewCount().setValue(--count);
                        reviewCardViewModel.getReviewCount().setValue(reviewCardViewModel.getPriorityQueue().size());
                        break;
                    case 2:
                        reviewCardViewModel.getHaveLearnedCount().setValue(--count);
                        reviewCardViewModel.getReviewCount().setValue(reviewCardViewModel.getPriorityQueue().size());

                        break;
                }
                Intent intent = new Intent();
                DetailActivity.this.setResult(RESULT_OK, intent);
                DetailActivity.this.finish();
            }
        });

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nextTime = DateUtils.addTime(easyTime.getValue(), easyTime.getUnit());
                int status = code;
                WordSample wordSample = new WordSample(mWord, status, nextTime);
                if(easyTime.getUnit() == "DAYS") {
                    wordSample.setStatus(2);
                    reviewCardViewModel.getWordPool().put(id, wordSample);
                }
                else {
                    wordSample.setStatus(1);
                    reviewCardViewModel.getPriorityQueue().offer(wordSample);
                }
                Log.d("pre", wordSample.toString() + "inner" + wordSample.getWord().getWord_info().getStatus());
                reviewCardViewModel.getHistoryStack().push(wordSample);
                switch (code) {
                    case 0:
                        reviewCardViewModel.getNewLearnedCount().setValue(--count);
                        reviewCardViewModel.getReviewCount().setValue(reviewCardViewModel.getPriorityQueue().size());
                        break;
                    case 1:
                        reviewCardViewModel.getReviewCount().setValue(--count);
                        reviewCardViewModel.getReviewCount().setValue(reviewCardViewModel.getPriorityQueue().size());
                        break;
                    case 2:
                        reviewCardViewModel.getHaveLearnedCount().setValue(--count);
                        reviewCardViewModel.getReviewCount().setValue(reviewCardViewModel.getPriorityQueue().size());
                        break;
                }
                Intent intent = new Intent();
                DetailActivity.this.setResult(RESULT_OK, intent);
                DetailActivity.this.finish();
            }
        });
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

    static class Time{
        int value;
        String unit;

        public Time(int value, String unit) {
            this.value = value;
            this.unit = unit;
        }

        public int getValue() {
            return value;
        }

        public String getUnit() {
            return unit;
        }

        @Override
        public String toString() {
            return value+" "+unit;
        }
    }
}
