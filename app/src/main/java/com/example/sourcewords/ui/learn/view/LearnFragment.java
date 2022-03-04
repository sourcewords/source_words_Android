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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sourcewords.R;
import com.example.sourcewords.commonUtils.SPUtils;
import com.example.sourcewords.ui.learn.viewModel.LearnViewModel;
import com.example.sourcewords.ui.learn.viewModel.WordsAdapter;
import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.ui.review.viewmodel.ReviewCardViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

//TODO 学模块
public class LearnFragment extends Fragment implements View.OnClickListener {
    private VideoView videoView;
    private LearnViewModel viewModel;
    private WordRoot root = null;
    private AppCompatTextView textView_wordRoot, textView_meaning, textView_source;
    private AppCompatButton button_learned;
    private WordsAdapter adapter;
    private ReviewCardViewModel reviewCardViewModel;
    private final static String KEY_TIME = "key_today_time";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View v = inflater.inflate(R.layout.fragment_learn, null);
        viewModel = ViewModelProviders.of(this.getActivity()).get(LearnViewModel.class);
        reviewCardViewModel = ViewModelProviders.of(this).get(ReviewCardViewModel.class);

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
        handlePlan();
        ImageButton imageButton = v.findViewById(R.id.learn_searcher);
        videoView = v.findViewById(R.id.learn_player);
        textView_meaning = v.findViewById(R.id.learn_meaning);
        textView_source = v.findViewById(R.id.learn_source);
        //VideoView部分
        String NULLURL = "https://stream7.iqilu.com/10339/upload_transcode/202002/18/20200218114723HDu3hhxqIT.mp4";
        Uri uri = Uri.parse(NULLURL);
        videoView.setVideoURI(uri);
        MediaController controller = new MediaController(getContext());
        controller.setVisibility(View.GONE);
        videoView.setMediaController(controller);
        videoView.requestFocus();
        videoView.setOnClickListener(this);
        //RecyclerView部分
        RecyclerView recyclerView = v.findViewById(R.id.learn_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new WordsAdapter(Objects.requireNonNull(getContext()));
        textView_wordRoot = v.findViewById(R.id.learn_wordroot);
        recyclerView.setAdapter(adapter);
        //等待算法实现
        getTodayLearn();
        initButton(v);
        viewModel.getNowDay().setValue(getNow());
        imageButton.setOnClickListener(this);
    }

    private void initButton(View v) {
        button_learned = v.findViewById(R.id.learn_AllLearned);
        button_learned.setOnClickListener(this);
        if (viewModel.getSaveFlag()){
            button_learned.setClickable(false);
            changeButtonUI();
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void changeButtonUI(){
        button_learned.setBackground(getResources().getDrawable(R.drawable.learned_selected));
        button_learned.setTextColor(getResources().getColor(R.color.theme_green));
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
        int id = viewModel.getLong();
        viewModel.getWordRootById(id).observe(getViewLifecycleOwner(), wordRoot -> {
            if (root.getRoot() != null) {
                textView_wordRoot.setText("词根：" + wordRoot.getRoot());
                textView_meaning.setText("词根" + wordRoot.getRoot() + "的意思是:" + root.getMeaning());
                textView_source.setText("词根" + wordRoot.getRoot() + "的来源与解释:" + root.getMeaning());
                viewModel.getWordsByRootID(root.getId()).observe(getViewLifecycleOwner(), new Observer<List<Word>>() {
                    @Override
                    public void onChanged(List<Word> words) {
                        adapter.setList(words);
                        adapter.notifyDataSetChanged();
                    }
                });

            }
        });
    }

    private boolean isToday(){
        return getNow() == getSaveDay();

    }

    private int getSaveDay(){
        SPUtils sp = SPUtils.getInstance(SPUtils.SP_TIME);
        return sp.getInt(SPUtils.SP_TIME);
    }


    private void saveTime() {
        //获取存储的时间
        SPUtils sp = SPUtils.getInstance(SPUtils.SP_TIME);
        sp.put(KEY_TIME, getNow());
    }

    @SuppressLint("SimpleDateFormat")
    private int getNow() {
        SimpleDateFormat sdf = new SimpleDateFormat("d");
        int day = Integer.parseInt(sdf.format(new Date()));
        sdf = new SimpleDateFormat("h");
        int hour = Integer.parseInt(sdf.format(new Date()));
        Log.d("Now","小时" + hour +"  " +  day );
        return hour < 4 ? day - 1 : day;

    }

    private void handlePlan(){
        viewModel.getNowPlan().observe(getViewLifecycleOwner(), integer -> {
            LoadPlan(integer);
            viewModel.savePlan(integer);
            //刷新
        });
    }

    private void LoadPlan(int level){
        viewModel.getAllWordRoot().observe(getViewLifecycleOwner(), list -> viewModel.initPlanRepository(list,level));
    }



    @Override
    //TODO 更新存储的系统时间
    public void onResume() {
        super.onResume();
        if (!isToday()) {
            //更新操作
            viewModel.getLearnFlag().setValue(false);
            viewModel.saveFlag(false);
            saveTime();
        }
        //refresh();
    }

    //TODO 记录离开时间
    @Override
    public void onDestroy() {
        super.onDestroy();
        saveTime();
    }
}
