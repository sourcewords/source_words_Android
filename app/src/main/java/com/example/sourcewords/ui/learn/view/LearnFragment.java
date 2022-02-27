package com.example.sourcewords.ui.learn.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sourcewords.App;
import com.example.sourcewords.R;
import com.example.sourcewords.ui.learn.viewModel.LearnViewModel;
import com.example.sourcewords.ui.learn.viewModel.WordsAdapter;
import com.example.sourcewords.ui.review.dataBean.SingleWord;
import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.dataBean.WordInfoBean;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.ui.review.db.WordDatabase;
import com.example.sourcewords.ui.review.model.WordDataSource;
import com.example.sourcewords.ui.review.viewmodel.ReviewCardViewModel;
import com.example.sourcewords.utils.DateUtils;
import com.example.sourcewords.utils.PreferencesUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//TODO 学模块
public class LearnFragment extends Fragment implements View.OnClickListener {
    private ImageButton imageButton;
    private VideoView videoView;
    private RecyclerView recyclerView;
    private TextView textView_learned, textView_wordRoot;
    private LearnViewModel viewModel;
    private ReviewCardViewModel reviewCardViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View v = inflater.inflate(R.layout.fragment_learn, null);
        viewModel = ViewModelProviders.of(this.getActivity()).get(LearnViewModel.class);
        reviewCardViewModel = ViewModelProviders.of(this).get(ReviewCardViewModel.class);
        initView(v);
        reviewCardViewModel.getAllWord().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable final List<Word> words) {
                // Update the cached copy of the words in the adapter.
                Log.d("initDataab","" + words.size());
            }
        });
        reviewCardViewModel.getAllWordRoot().observe(this, new Observer<List<WordRoot>>() {
            @Override
            public void onChanged(List<WordRoot> wordRoots) {
                Log.d("initDataa","" + wordRoots.size());
            }
        });

        reviewCardViewModel.getAllSingleWord().observe(this, new Observer<List<SingleWord>>() {
            @Override
            public void onChanged(List<SingleWord> singleWords) {
                Log.d("initDatac","" + singleWords.size());
            }
        });
        for(int i = 1; i<3; i++) {
            SingleWord singleWord = reviewCardViewModel.getSingleWordById(i);
            singleWord.setStatus(2);
            singleWord.setNextTime(DateUtils.getData());
            reviewCardViewModel.insert(singleWord);
        }

        return v;
    }

    private void initView(View v) {
        imageButton = v.findViewById(R.id.learn_searcher);
        videoView = v.findViewById(R.id.learn_player);
        Uri uri = Uri.parse("https://stream7.iqilu.com/10339/upload_transcode/202002/18/20200218114723HDu3hhxqIT.mp4");
        videoView.setVideoURI(uri);
        MediaController controller = new MediaController(getContext());
        controller.setVisibility(View.GONE);
        videoView.setMediaController(controller);
        videoView.requestFocus();
        videoView.start();
        videoView.setOnClickListener(this);
        recyclerView = v.findViewById(R.id.learn_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final WordsAdapter adapter = new WordsAdapter(Objects.requireNonNull(getContext()));
        List<Word> list = new ArrayList<>();
        adapter.setList(list);//设置加载的单词列表
        recyclerView.setAdapter(adapter);
        textView_wordRoot = v.findViewById(R.id.learn_wordroot);
        //等待算法实现
        //textView_wordRoot.setText("词根："+ );
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
                //待实现获取每日词根的算法
                //viewModel.getWordRoot();


                SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(App.getAppContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();

                // 请在此持久化今日词根的id
                editor.putInt(PreferencesUtils.WOOD_ROOT_TODAY,1);
                editor.commit();
                // 通知习模块更新
                viewModel.getLearnFlag().setValue(true);

                textView_learned.setClickable(false);

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
}
