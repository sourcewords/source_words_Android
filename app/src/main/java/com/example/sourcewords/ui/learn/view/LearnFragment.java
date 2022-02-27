package com.example.sourcewords.ui.learn.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sourcewords.R;
import com.example.sourcewords.ui.learn.viewModel.LearnViewModel;
import com.example.sourcewords.ui.learn.viewModel.WordsAdapter;
import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.dataBean.WordRoot;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Random;

//TODO 学模块
public class LearnFragment extends Fragment implements View.OnClickListener {
    private VideoView videoView;
    private LearnViewModel viewModel;
    private static WordRoot root;
    private TextView textView_wordRoot, textView_meaning, textView_source;
    private AppCompatButton button_learned;
    private final static int WORDROOTMESSAGE = 0x10001;//用于更新UI
    private final static int UPDATETIME = 0x9999;//用于获取当前时间
    private final String Learned = "I have learned them Today";
    private final String Name_Learn = "I should learn";
    private WordsAdapter adapter;
    private final String NULLURL = "https://stream7.iqilu.com/10339/upload_transcode/202002/18/20200218114723HDu3hhxqIT.mp4";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View v = inflater.inflate(R.layout.fragment_learn, null);
        viewModel = ViewModelProviders.of(this).get(LearnViewModel.class);
        initView(v);
        return v;
    }

    private void initView(View v) {
        ImageButton imageButton = v.findViewById(R.id.learn_searcher);
        videoView = v.findViewById(R.id.learn_player);
        textView_meaning = v.findViewById(R.id.learn_meaning);
        textView_source = v.findViewById(R.id.learn_source);
        //VideoView部分
        Uri uri = Uri.parse(NULLURL);
        videoView.setVideoURI(uri);
        MediaController controller = new MediaController(getContext());
        controller.setVisibility(View.GONE);
        videoView.setMediaController(controller);
        videoView.requestFocus();
        //videoView.start();
        videoView.setOnClickListener(this);
        //RecyclerView部分
        RecyclerView recyclerView = v.findViewById(R.id.learn_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new WordsAdapter(Objects.requireNonNull(getContext()));
        recyclerView.setAdapter(adapter);
        textView_wordRoot = v.findViewById(R.id.learn_wordroot);
        //等待算法实现
        getTodayLearn();
        //textView_wordRoot.setText("词根："+ );
        initButton(v);
        upDataTime();
        imageButton.setOnClickListener(this);
    }

    private void initButton(View v) {
        button_learned = v.findViewById(R.id.learn_AllLearned);
        button_learned.setOnClickListener(this);
        SharedPreferences button = Objects.requireNonNull(getContext()).getSharedPreferences(Name_Learn, Context.MODE_PRIVATE);
        boolean flag = button.getBoolean(Learned, false);
        if (flag) {
            button_learned.performClick();
        } else {
            viewModel.saveWhatLearnedToday(root.getId());
        }
    }

    //TODO 用于记录系统时间，以便判断UI的更新
    private void upDataTime() {
        final Handler handler = new WordRootHandler();
        long sysTime = System.currentTimeMillis();//获取系统时间
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(sysTime);
        final int[] mHour = {calendar.get(Calendar.HOUR)};
        final int[] minute = {calendar.get(Calendar.MINUTE)};
        final int[] second = {calendar.get(Calendar.SECOND)};
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    while (mHour[0] < 24) {
                        second[0]++;
                        if (second[0] == 60) {
                            minute[0]++;
                            second[0] = 0;
                        }
                        if (minute[0] == 60) {
                            minute[0] = 0;
                            mHour[0]++;
                        }
                        Thread.sleep(1000);
                    }
                    handler.sendEmptyMessage(UPDATETIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //Activity页面数据刷新
        };

    }


    @SuppressLint({"ResourceType", "UseCompatLoadingForDrawables", "NonConstantResourceId"})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.learn_AllLearned:
                button_learned.setBackground(getResources().getDrawable(R.drawable.learned_selected));
                button_learned.setTextColor(getResources().getColor(R.color.theme_green));
                SharedPreferences sharedPreferences = getContext().getSharedPreferences(Name_Learn, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(Learned, true);
                editor.apply();
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
    private void getTodayLearn() {
        Random random = new Random();
        final int id = random.nextInt(25);
        Handler handler = new WordRootHandler();
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    root = viewModel.getWordRootById(id);
                    handler.sendEmptyMessage(WORDROOTMESSAGE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

@SuppressLint("HandlerLeak")
class WordRootHandler extends Handler {
    @SuppressLint("SetTextI18n")
    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        if (msg.what == WORDROOTMESSAGE) {
            textView_wordRoot.setText("词根：" + root.getRoot());
            textView_meaning.setText("词根" + root.getRoot() + "的意思是:" + root.getMeaning());
            textView_source.setText("词根" + root.getRoot() + "的来源与解释:" + root.getMeaning());
            List<Word> words = root.getWordlist();
            adapter.setList(words);
                /*
                final String path = root.getVideo_url();
                if(path.length() == 0){
                    videoView.setVisibility(View.INVISIBLE);
                }else{
                    videoView.setVideoURI(Uri.parse(path));
                }*/
        } else if (msg.what == UPDATETIME) {
            SharedPreferences sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences(Name_Learn, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(Learned, false);
            editor.apply();
            refresh();
        }
    }

}

    private void refresh() {
        onCreate(null);
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }
}
