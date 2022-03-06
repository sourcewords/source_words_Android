package com.example.sourcewords.ui.learn.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sourcewords.R;
import com.example.sourcewords.ui.learn.viewModel.LearnViewModel;
import com.example.sourcewords.ui.learn.viewModel.WordsAdapter;
import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.viewmodel.ReviewCardViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//TODO 学模块
public class LearnFragment extends Fragment implements View.OnClickListener {
    private VideoView videoView;
    private LearnViewModel viewModel;
    private AppCompatTextView textView_wordRoot, textView_meaning, textView_source;
    private AppCompatButton button_learned;
    private WordsAdapter adapter;
    private static int day;
    private static final String Param = "LearnFragment";
    private static List<Integer> list = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View v = inflater.inflate(R.layout.fragment_learn, null);
        viewModel = ViewModelProviders.of(this.getActivity()).get(LearnViewModel.class);
        if (getArguments() != null) {
            day = getArguments().getInt(Param, 1);
        }
        ReviewCardViewModel reviewCardViewModel = ViewModelProviders.of(this).get(ReviewCardViewModel.class);

        reviewCardViewModel.getAllWord().observe(getViewLifecycleOwner(), words -> {
            assert words != null;
            Log.d("initDataab", "" + words.size());
        });
        reviewCardViewModel.getAllWordRoot().observe(getViewLifecycleOwner(), wordRoots -> Log.d("initDataa", "" + wordRoots.size()));
        reviewCardViewModel.getAllSingleWord().observe(getViewLifecycleOwner()
                , singleWords -> Log.d("initDatac", "" + singleWords.size()));
        initView(v);
        return v;
    }

    private void initView(View v) {
        ImageButton imageButton = v.findViewById(R.id.learn_searcher);
        videoView = v.findViewById(R.id.learn_player);
        textView_meaning = v.findViewById(R.id.learn_meaning);
        textView_source = v.findViewById(R.id.learn_source);
        MediaController controller = new MediaController(getContext());
        controller.setVisibility(View.GONE);
        videoView.setMediaController(controller);
        videoView.requestFocus();
        videoView.setOnClickListener(this);
        //RecyclerView部分
        RecyclerView recyclerView = v.findViewById(R.id.learn_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new WordsAdapter(Objects.requireNonNull(getContext()));
        recyclerView.setAdapter(adapter);
        textView_wordRoot = v.findViewById(R.id.learn_wordroot);
        getTodayLearn();
        initButton(v);
        viewModel.getNowDay().setValue(viewModel.getNow());
        imageButton.setOnClickListener(this);
    }

    private void initButton(View v) {
        button_learned = v.findViewById(R.id.learn_AllLearned);
        button_learned.setOnClickListener(this);
        if (viewModel.getSaveFlag()) {
            button_learned.setClickable(false);
            changeButtonUI();
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void changeButtonUI() {
        button_learned.setBackground(getResources().getDrawable(R.drawable.learned_selected));
        button_learned.setTextColor(getResources().getColor(R.color.theme_green));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void changeButtonUIBack() {
        button_learned.setBackground(getResources().getDrawable(R.color.theme_green));
        button_learned.setTextColor(getResources().getColor(R.color.white));
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.learn_AllLearned:
                changeButtonUI();
                viewModel.saveFlag(true);
                viewModel.saveLong(viewModel.getLong() + 1);
                // 通知习模块更新
                viewModel.getLearnFlag().setValue(true);
                button_learned.setClickable(false);
                //通知后端
                viewModel.whatILearnedToday(list);
                break;
            case R.id.learn_searcher:
                Intent intent = new Intent(getActivity(), LearnSearchActivity.class);
                startActivity(intent);
                break;
            case R.id.learn_player:
                if (videoView.isPlaying())
                    videoView.pause();
                else
                    videoView.start();
                break;
        }
    }

    //TODO 临时的获取每日学习的词根算法
    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    private void getTodayLearn() {
        //TODO 第几天就是第几个词根
        viewModel.getWordRootById(viewModel.getLong()).observe(getViewLifecycleOwner(), wordRoot -> {
            if (wordRoot != null) {
                textView_wordRoot.setText("词根：" + wordRoot.getRoot());
                textView_meaning.setText("词根" + wordRoot.getRoot() + "的意思是:" + wordRoot.getMeaning());
                textView_source.setText("词根" + wordRoot.getRoot() + "的来源与解释:" + wordRoot.getMeaning());
                adapter.setList(wordRoot.getWordlist());
                String path = wordRoot.getVideo_url();
                if (path.length() == 0) {
                    videoView.setVisibility(View.INVISIBLE);
                } else {
                    videoView.setVideoURI(Uri.parse(path));
                }
                list = changeToInteger(wordRoot.getWordlist());
                adapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    //TODO 更新存储的系统时间
    public void onResume() {
        super.onResume();
        if (!viewModel.isToday()) {
            //更新操作
            viewModel.getLearnFlag().setValue(false);
            viewModel.saveFlag(false);
            viewModel.saveTime();
            refresh();
        }
    }

    //TODO 记录离开时间
    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.saveTime();
    }

    public void refresh() {
        getTodayLearn();
        changeButtonUIBack();
        button_learned.setClickable(true);
        Log.d("LearnFragment", "刷新拉!!!!");
    }

    private List<Integer> changeToInteger(List<Word> list) {
        List<Integer> res = new ArrayList<>();
        for (Word word : list) {
            res.add(word.getWord_info().getId());
        }
        return res;
    }

}
