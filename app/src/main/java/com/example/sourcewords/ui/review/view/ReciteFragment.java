package com.example.sourcewords.ui.review.view;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.sourcewords.App;
import com.example.sourcewords.R;
import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.dataBean.WordInfoBean;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.ui.review.viewmodel.ReviewCardViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReciteFragment extends Fragment {
    private Word word;
    private FloatingActionButton button;
    private TextView wordEng;
    private TextView soundMark;
    private TextView newLearned,haveLearned,review;
    private int newLearnedCount,haveLearnedCount,reviewCount;
    private ReviewCardViewModel reviewCardViewModel;

    private ImageView newLearnedBkg, haveLearnedBkg, reviewBkg;

    private CardView mWordCard;
    private int flag;
    /**
     * 刚刚进入 习 这个板块的新单词 状态为 0
     * 今天已经复习过的单词但今天仍需复习的状态为 1
     * 过去学过、复习过，今天要复习的的状态为 2
     */
    public static final int WORD_NEW = 0;
    public static final int WORD_TODAY_REVIEW_AGAIN = 1;
    public static final int WORD_PAST_REVIEWED = 2;
    private ViewStub stub;


    public ReciteFragment() {
    }

    private void initData() {
        this.newLearnedCount = reviewCardViewModel.getNewLearnedCount();
        this.haveLearnedCount = reviewCardViewModel.getHaveLearnedCount();
        this.reviewCount = reviewCardViewModel.getReviewCount();
        this.word = reviewCardViewModel.getNextWords();
        this.flag = reviewCardViewModel.currentFlag();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recite,container,false);
        button = view.findViewById(R.id.next_btn);
        wordEng = view.findViewById(R.id.word);
        soundMark = view.findViewById(R.id.sound_mark);
        newLearned = view.findViewById(R.id.defficult);
        haveLearned = view.findViewById(R.id.middle);
        review = view.findViewById(R.id.easy);
        mWordCard = view.findViewById(R.id.card);
        stub = view.findViewById(R.id.viewstub_done);

        newLearnedBkg = view.findViewById(R.id.difficult_bkg);
        haveLearnedBkg = view.findViewById(R.id.middle_bkg);
        reviewBkg = view.findViewById(R.id.easy_bkg);


        this.reviewCardViewModel = ViewModelProviders.of(this).get(ReviewCardViewModel.class);
        initData();

        mWordCard.setOnClickListener(v -> {
            //放一个临时变量
            WordInfoBean wordInfo = word.getWord_info();
            Intent intent = new Intent(App.getAppContext(), DetailActivity.class);

            intent.putExtra("wordId", word.getId());
            //TODO:根据单词的状态选择request code
            if(wordInfo.getStatus() == WORD_NEW) {
                intent.putExtra("code", WORD_NEW);
                startActivityForResult(intent, WORD_NEW);
            }
            else if(wordInfo.getStatus() == WORD_TODAY_REVIEW_AGAIN){
                intent.putExtra("code", WORD_TODAY_REVIEW_AGAIN);
                startActivityForResult(intent, WORD_TODAY_REVIEW_AGAIN);
            }
            else if(wordInfo.getStatus() == WORD_PAST_REVIEWED){
                intent.putExtra("code", WORD_PAST_REVIEWED);
                startActivityForResult(intent, WORD_PAST_REVIEWED);
            }
        });
        button.setOnClickListener(v -> {
            getPreWord();
        });
        initView();
        return view;
    }

    private void initView() {
        if(flag == 0) {
            newLearnedBkg.setVisibility(View.VISIBLE);
            haveLearnedBkg.setVisibility(View.INVISIBLE);
            reviewBkg.setVisibility(View.INVISIBLE);
        }
        else if(flag == 1) {
            newLearnedBkg.setVisibility(View.INVISIBLE);
            haveLearnedBkg.setVisibility(View.VISIBLE);
            reviewBkg.setVisibility(View.INVISIBLE);
        }
        else {
            newLearnedBkg.setVisibility(View.INVISIBLE);
            haveLearnedBkg.setVisibility(View.INVISIBLE);
            reviewBkg.setVisibility(View.VISIBLE);
        }

        WordInfoBean mWordInfo = word.getWord_info();
        wordEng.setText(mWordInfo.getWord());
        soundMark.setText(mWordInfo.getPhonetic());
        newLearned.setText(newLearnedCount + "");
        haveLearned.setText(haveLearnedCount + "");
        review.setText(reviewCount + "");


        if(newLearnedCount == 0 && haveLearnedCount == 0 && reviewCount == 0) {
            stub.inflate();
            mWordCard.setClickable(false);
            button.setClickable(false);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int result = -1;
        if(data != null)
            result = data.getIntExtra("result", -1);
        if (requestCode == WORD_NEW && resultCode == RESULT_OK && (result == 2 || result == 3 || result == 1 || result == 0)) {
            initData();
            initView();
        }
        else {
            Toast.makeText(App.getAppContext(),
                    "Please make a choice",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void getPreWord() {
        Word preWord = reviewCardViewModel.getPreWord(word, flag);
        if(word == preWord) return;
        this.newLearnedCount = reviewCardViewModel.getNewLearnedCount();
        this.haveLearnedCount = reviewCardViewModel.getHaveLearnedCount();
        this.reviewCount = reviewCardViewModel.getReviewCount();
        this.flag = reviewCardViewModel.currentFlag();
        initView();
    }


}
