package com.example.sourcewords.ui.learn;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sourcewords.R;
import com.example.sourcewords.ui.review.dataBean.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//TODO 学模块
public class LearnFragment extends Fragment implements View.OnClickListener {
    private ImageButton imageButton;
    private VideoView videoView;
    private RecyclerView recyclerView;
    private TextView textView_learned, textView_wordRoot;
    private Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View v = inflater.inflate(R.layout.fragment_learn, null);
        initView(v);
        return v;
    }

    private void initView(View v) {
        imageButton = v.findViewById(R.id.learn_searcher);
        videoView = v.findViewById(R.id.learn_player);
        Uri uri = Uri.parse("https://stream7.iqilu.com/10339/upload_transcode/202002/18/20200218114723HDu3hhxqIT.mp4");
        videoView.setVideoURI(uri);
        MediaController controller = new MediaController(getContext());
        videoView.setMediaController(controller);
        videoView.requestFocus();

        recyclerView = v.findViewById(R.id.learn_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final WordsAdapter adapter = new WordsAdapter(Objects.requireNonNull(getContext()));
        List<Word> list = new ArrayList<>();
        list.add(new Word("abandon", "（v）放弃"));
        adapter.setList(list);//设置加载的单词列表
        recyclerView.setAdapter(adapter);
        textView_wordRoot = v.findViewById(R.id.learn_wordroot);
        textView_learned = v.findViewById(R.id.learn_AllLearned);
        textView_learned.setOnClickListener(this);
        imageButton.setOnClickListener(this);
    }

    @SuppressLint({"ResourceType", "UseCompatLoadingForDrawables", "NonConstantResourceId"})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.learn_AllLearned:
                textView_learned.setBackground(getResources().getDrawable(R.drawable.learned_selected));
                textView_learned.setTextColor(getResources().getColor(R.color.theme_green));
                break;
            case R.id.learn_searcher:
                Intent intent = new Intent(getActivity(),LearnSearchActivity.class);
                startActivity(intent);
                break;
        }
    }
}
