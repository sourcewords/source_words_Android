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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.sourcewords.R;
import com.example.sourcewords.ui.learn.view.LearnSearchActivity;
import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.dataBean.WordInfoBean;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.ui.review.viewmodel.ReviewViewModel;
import com.example.sourcewords.utils.DateUtils;

import java.io.IOException;

public class DetailActivity extends AppCompatActivity {
    public static final int AGAIN = 1;
    public static final int HARD = 2;
    public static final int GOOD = 3;
    public static final int EASY = 4;
    private int code;
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
        code = getIntent().getIntExtra("code",0);

        mViewModel = new ReviewViewModel(getApplication());
        int wordId = getIntent().getIntExtra("wordId", 0);

        mWord = mViewModel.search(wordId);
        Log.d("wordid","" + mWord.getId());
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

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.detail_back:
                finish();
                break;
            case R.id.search:
                Intent intent = new Intent(this, LearnSearchActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
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
                mWord.getWord_info().setNextTime(DateUtils.addTime(againTime.getValue(),againTime.getUnit()));
//                mViewModel.Update(mWordRoot);
                Intent intent = new Intent();
                intent.putExtra("result", 0);
                DetailActivity.this.setResult(RESULT_OK, intent);
                DetailActivity.this.finish();
            }
        });

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWord.getWord_info().setNextTime(DateUtils.addTime(againTime.getValue(),againTime.getUnit()));
//                mViewModel.Update(mWordRoot);
                Intent intent = new Intent();
                intent.putExtra("result", 1);
                DetailActivity.this.setResult(RESULT_OK, intent);
                DetailActivity.this.finish();
            }
        });

        good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWord.getWord_info().setNextTime(DateUtils.addTime(againTime.getValue(),againTime.getUnit()));
//                mViewModel.Update(mWordRoot);
                Intent intent = new Intent();
                intent.putExtra("result", 2);
                DetailActivity.this.setResult(RESULT_OK, intent);
                DetailActivity.this.finish();
            }
        });

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWord.getWord_info().setNextTime(DateUtils.addTime(againTime.getValue(),againTime.getUnit()));
//                mViewModel.Update(mWordRoot);
                Intent intent = new Intent();
                intent.putExtra("result", 3);
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
