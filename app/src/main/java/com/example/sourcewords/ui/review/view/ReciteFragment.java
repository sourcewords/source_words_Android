package com.example.sourcewords.ui.review.view;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import com.example.sourcewords.App;
import com.example.sourcewords.R;
import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReciteFragment extends Fragment {
    private List<Word> newLearnedWords, haveLearnedWords, reviewWords;
    private FloatingActionButton button;
    private TextView wordEng;
    private TextView soundMark;
    private TextView newLearned,haveLearned,review;
    private int newLearnedCount,haveLearnedCount,reviewCount;

    private CardView mWordCard;
    private int cursor = 0;
    private static final int DETAIL_WORD_ACTIVITY_REQUEST_CODE_NEW = 0;
    private static final int DETAIL_WORD_ACTIVITY_REQUEST_CODE_OLD = 1;



    public ReciteFragment(WordRoot wordRoot) {
        // TODO:从viewmodel中拿到今日学过的
        this.newLearnedWords = wordRoot.getWordlist();
        this.haveLearnedWords = new ArrayList<>();

        // TODO:从viewmodel中通过日期拿到
        this.reviewWords = new ArrayList<>();

        newLearnedCount = newLearnedWords.size();
        haveLearnedCount = haveLearnedWords.size();
        reviewCount = reviewWords.size();
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

        mWordCard.setOnClickListener(v -> {
            Intent intent = new Intent(App.getAppContext(), DetailActivity.class);
//            intent.putExtra("index", cursor);
//            if(reviewWords.size() == 0)
//                startActivityForResult(intent, DETAIL_WORD_ACTIVITY_REQUEST_CODE_NEW);
//            else startActivityForResult(intent, DETAIL_WORD_ACTIVITY_REQUEST_CODE_OLD);
            intent.putExtra("indexWord", cursor);
            WordRoot mWordRoot = null;
            intent.putExtra("indexWordRoot",mWordRoot.getId());
            startActivity(intent);
        });
        button.setOnClickListener(v -> {
            getPreWord();
        });
        initView();
        return view;
    }

    private void initView() {


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DETAIL_WORD_ACTIVITY_REQUEST_CODE_NEW && resultCode == RESULT_OK) {
            getNextWord();
        }
        else if(requestCode == DETAIL_WORD_ACTIVITY_REQUEST_CODE_OLD && resultCode == RESULT_OK) {
            getNextWord();
        }
        else {
            Toast.makeText(App.getAppContext(),
                    "Something went wrong",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void getPreWord() {
        Word word = newLearnedWords.get(++cursor);
    }

    private void getNextWord() {
    }

//    private List<Word> getList() {
//
//    }
}
