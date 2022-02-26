package com.example.sourcewords.ui.review.view;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sourcewords.R;

public class ReviewSearchActivity extends AppCompatActivity {
    private ImageView back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_words);

        initView();
        Listener();
    }

    public void initView(){
        back = findViewById(R.id.search_word_back);
    }

    public void Listener(){
        back.setOnClickListener(v -> {
            finish();
        });
    }
}
