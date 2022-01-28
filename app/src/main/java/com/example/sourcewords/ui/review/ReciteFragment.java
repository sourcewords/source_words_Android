package com.example.sourcewords.ui.review;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sourcewords.R;
import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Iterator;
import java.util.List;

public class ReciteFragment extends Fragment {
    private List<Word> words;
    private FloatingActionButton button;
    private TextView wordEng;
    private TextView soundMark;
    private TextView difficult;
    private TextView middle;
    private TextView easy;
    private int difficultCount;
    private int middleCount;
    private int easyCount;
    Iterator<Word> iterator;

    public ReciteFragment(WordRoot wordRoot) {
        this.words = wordRoot.getList();
        iterator = words.iterator();
        difficultCount = 0;
        middleCount = 0;
        easyCount = 0;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recite,container,false);
        button = view.findViewById(R.id.next_btn);
        wordEng = view.findViewById(R.id.word);
        soundMark = view.findViewById(R.id.sound_mark);
        difficult = view.findViewById(R.id.defficult);
        middle = view.findViewById(R.id.middle);
        easy = view.findViewById(R.id.easy);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initView();
            }
        });

        initView();
        return view;
    }

    private void initView() {
        if(iterator.hasNext()) {
            Word word = iterator.next();
            wordEng.setText(word.getEnglish());
            soundMark.setText(word.getSoundMark());
            difficultCount++;
            difficult.setText(difficultCount+" ");
        }
    }
}
