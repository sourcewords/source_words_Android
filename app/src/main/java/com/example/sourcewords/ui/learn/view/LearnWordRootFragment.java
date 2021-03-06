package com.example.sourcewords.ui.learn.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sourcewords.App;
import com.example.sourcewords.R;
import com.example.sourcewords.ui.learn.viewModel.LearnViewModel;
import com.example.sourcewords.ui.learn.viewModel.RollInterface;
import com.example.sourcewords.ui.learn.viewModel.WordsAdapter;
import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.viewmodel.ReviewCardViewModel;
import com.example.sourcewords.utils.PreferencesUtils;

import java.util.ArrayList;
import java.util.List;

//TODO 此页面代替原来的LearnFragment，传入词根的id
public class LearnWordRootFragment extends Fragment implements View.OnClickListener {
    private int root_id;
    private VideoView videoView;
    private LearnViewModel viewModel;
    private AppCompatTextView textView_wordRoot, textView_meaning, textView_source;
    private AppCompatButton button_learned;
    private RollInterface rollInterface;
    private WordsAdapter adapter;
    private List<Integer> list = new ArrayList<>();
    private static final String Param = "LearnRootFragment";
    private ReviewCardViewModel reviewCardViewModel;

    public static LearnWordRootFragment newInstance(int id) {
        LearnWordRootFragment fragment = new LearnWordRootFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Param, id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View v = inflater.inflate(R.layout.fragment_learn, null);
        viewModel = new ViewModelProvider(this.getActivity()).get(LearnViewModel.class);
        reviewCardViewModel = new ViewModelProvider(this.getActivity()).get(ReviewCardViewModel.class);
        initView(v);
        Bundle bundle = getArguments();
        assert bundle != null;
        root_id = bundle.getInt(Param, 0);
        Log.d("词根页的id是：", String.valueOf(root_id));
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
        adapter = new WordsAdapter(getContext());
        recyclerView.setAdapter(adapter);
        textView_wordRoot = v.findViewById(R.id.learn_wordroot);
        getTodayLearn();
        initButton(v);
        viewModel.getNowDay().setValue(viewModel.getNow());
        imageButton.setOnClickListener(this);
        AppCompatButton button_next = v.findViewById(R.id.learn_next);
        button_next.setOnClickListener(this);
        AppCompatButton button_perform = v.findViewById(R.id.learn_per);
        button_perform.setOnClickListener(this);
    }

    private void initButton(View v) {
        button_learned = v.findViewById(R.id.learn_AllLearned);
        button_learned.setOnClickListener(this);
        if (viewModel.getLong() > root_id) {
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
        button_learned.setBackground(getResources().getDrawable(R.drawable.learned_normal));
        button_learned.setTextColor(getResources().getColor(R.color.white));
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.learn_AllLearned:
                if (viewModel.getLong() == root_id) {
                    changeButtonUI();
                    button_learned.setClickable(false);
                    viewModel.saveLong(viewModel.getLong() + 1);
                    viewModel.whatILearnedToday(list);
                    rollInterface.next();
                    viewModel.saveFlag(true);
                    viewModel.getLearnFlag().setValue(true);
                    reviewCardViewModel.initData(root_id);
                } else {
                    Toast.makeText(getContext(), "您还没学到这个", Toast.LENGTH_SHORT).show();
                }
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
            case R.id.learn_per:
                rollInterface.perform();
                break;
            case R.id.learn_next:
                rollInterface.next();
                break;
        }


    }

    //TODO 临时的获取每日学习的词根算法
    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    private void getTodayLearn() {
        viewModel.getWordRootById(root_id).observe(getViewLifecycleOwner(), wordRoot -> {
            if (wordRoot != null) {
                textView_wordRoot.setText("词根详情：" + wordRoot.getRoot());
                textView_meaning.setText(handleWords("词根" + wordRoot.getRoot() + "的意思是:" + wordRoot.getMeaning()));
                textView_source.setText(handleWords("词根" + wordRoot.getRoot() + "的来源与解释:" + wordRoot.getMeaning()));
                adapter.setList(getPlanList(wordRoot.getWordlist()));
                String path = wordRoot.getVideo_url();
                if (path.length() == 0) {
                    videoView.setVisibility(View.GONE);
                } else {
                    videoView.setVideoURI(Uri.parse(path));
                }
                list = changeToInteger(wordRoot.getWordlist());
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void refresh() {
        getTodayLearn();
        if (viewModel.getLong() > root_id) {
            button_learned.setClickable(false);
            changeButtonUI();
        } else {
            button_learned.setClickable(true);
            changeButtonUIBack();
        }
        Log.d("LearnFragment", "刷新拉!!!!");
    }

    private List<Integer> changeToInteger(List<Word> list) {
        List<Integer> res = new ArrayList<>();
        for (Word word : list) {
            res.add(word.getWord_info().getId());
        }
        return res;
    }

    private List<Word> getPlanList(List<Word> list) {
        int level = viewModel.getPlan();
        List<Word> res = new ArrayList<>();
        for (Word word : list) {
            if (word.getWord_info().getExam_grading().get(level - 1)) {
                res.add(word);
            }
        }
        return res;
    }

    private SpannableStringBuilder handleWords(String s) {
        int len = s.indexOf(":");
        SpannableStringBuilder ans = new SpannableStringBuilder(s);
        ans.setSpan(new ForegroundColorSpan(Color.parseColor("#64BEBC")), 0, len, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        return ans;
    }

    public void setRollCallBack(LearnFragment fragment) {
        rollInterface = fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }


    //TODO 传递今日所学的id**临时性
    private void Pass_Wordroot_ID(int root_id) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.getAppContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
//        JSONArray jsonArray = new JSONArray();
//        jsonArray.put(viewModel.HowLongPlan() * viewModel.getSpeed() + 1);
//        Log.d("targetSource", jsonArray.toString());
//        editor.putString(PreferencesUtils.WORD_ROOT_TODAY, jsonArray.toString());
        editor.putInt(PreferencesUtils.WORD_ROOT_TODAY, root_id);
        editor.apply();
    }

}
