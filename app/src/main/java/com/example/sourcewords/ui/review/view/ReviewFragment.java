package com.example.sourcewords.ui.review.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.sourcewords.App;
import com.example.sourcewords.R;
import com.example.sourcewords.ui.learn.viewModel.LearnViewModel;
import com.example.sourcewords.utils.DateUtils;
import com.example.sourcewords.utils.PreferencesUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


//TODO 习模块
public class ReviewFragment extends Fragment {
    private ImageView search;
    private FrameLayout frameLayout;
    private LearnViewModel viewModel;
    private MutableLiveData<Boolean> learnFlag;
    private NoneFragment noneFragment;
    private ReciteFragment reciteFragment;
    private FragmentManager fragmentManager;

    private boolean hasInit = false;
    boolean flag = false;
    private SharedPreferences sharedPreferences;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this.getActivity()).get(LearnViewModel.class);
        learnFlag = viewModel.getLearnFlag();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, null);
        frameLayout = view.findViewById(R.id.review_container);
        noneFragment = new NoneFragment();
        reciteFragment = new ReciteFragment();

        fragmentManager = getChildFragmentManager();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.getAppContext());

        String lastReviewDay = sharedPreferences.getString(PreferencesUtils.LAST_REVIEW_DAY, "2022-1-1");

        String updateTime = "4:00";
        String nowTime = DateUtils.getTime();

        initSP(lastReviewDay, updateTime, nowTime);


        flag = sharedPreferences.getBoolean(PreferencesUtils.WORD_ROOT_HAVE_LEARNED, false);
        viewModel.getLearnFlag().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean bool) {
                // 今天已经学了词根，或者今天已经学习完毕
                if (bool || flag) {
                    hasInit = true;
                    // 标识一下今天已经学过点击过学的按钮
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(PreferencesUtils.CLICK_LEARAN_BUTTON, true);
                    editor.commit();
                    initWordView(reciteFragment);

                } else if (sharedPreferences.getBoolean(PreferencesUtils.CLICK_LEARAN_BUTTON, false)) {
                    initWordView(reciteFragment);
                } else
                    initNoneView();

            }
        });

        initViewAndListener(view);

        return view;
    }

    private void initSP(String lastReviewDay,String updateTime, String nowTime) {
        // 上次复习时间是之前的日期，不是今天，且当前时间大于4：00则可以认为今日没有学习词根,且没有点击过学按钮
        if (DateUtils.compareDate(lastReviewDay, DateUtils.getDate()) > 0 && DateUtils.compareTime(updateTime,nowTime) > 0) {
            viewModel.getLearnFlag().setValue(false);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(PreferencesUtils.WORD_ROOT_HAVE_LEARNED, false);
            editor.putBoolean(PreferencesUtils.CLICK_LEARAN_BUTTON, false);
            editor.putString(PreferencesUtils.LAST_REVIEW_DAY, DateUtils.getDate());
            editor.commit();
        }
    }

    public void initViewAndListener(View view) {
        search = view.findViewById(R.id.review_search);
        search.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ReviewSearchActivity.class);
            startActivity(intent);
        });
    }


    public void initNoneView() {
        if (!noneFragment.isAdded()) {
            Log.d("fragmentManager", "1");
            fragmentManager.beginTransaction().add(R.id.review_container, noneFragment, "NoneFragment")
                    .commit();
        }
    }

    public void initWordView(ReciteFragment reciteFragment) {
        if (hasInit) {
            fragmentManager.beginTransaction().hide(noneFragment).commit();
        }
        if(!reciteFragment.isAdded())
            fragmentManager.beginTransaction().add(R.id.review_container, reciteFragment, "ReciteFragment")
                .commit();
    }

    @Override
    public void onResume() {
        String lastReviewDay = sharedPreferences.getString(PreferencesUtils.LAST_REVIEW_DAY, "2022-1-1");
        String updateTime = "4:00";
        String nowTime = DateUtils.getTime();
        initSP(lastReviewDay, updateTime, nowTime);
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        int count = fragmentManager.getBackStackEntryCount();
        for (int i = 0; i < count; ++i) {
            fragmentManager.popBackStack();
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        fragmentManager.beginTransaction().remove(noneFragment);
        fragmentManager.beginTransaction().remove(reciteFragment);
        super.onDestroy();
    }
}
