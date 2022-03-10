package com.example.sourcewords.ui.learn.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.sourcewords.R;
import com.example.sourcewords.ui.learn.viewModel.LearnViewModel;
import com.example.sourcewords.ui.learn.viewModel.RollInterface;
import com.example.sourcewords.ui.main.MainViewPageAdapter;

import com.example.sourcewords.ui.review.viewmodel.ReviewCardViewModel;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//TODO 学模块
public class LearnFragment extends Fragment implements RollInterface {
    private int index = 0;//对于ViewPager中的list的下标
    private LearnViewModel viewModel;
    private MainViewPageAdapter adapter;
    private ViewPager viewPager;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_learn_new, container, false);
        viewModel = ViewModelProviders.of(this).get(LearnViewModel.class);
        ReviewCardViewModel reviewCardViewModel = ViewModelProviders.of(this).get(ReviewCardViewModel.class);
        reviewCardViewModel.getAllWord().observe(getViewLifecycleOwner(), words -> {
            assert words != null;
            Log.d("initDataab", "" + words.size());
        });
        reviewCardViewModel.getAllWordRoot().observe(getViewLifecycleOwner(), wordRoots -> Log.d("initDataa", "" + wordRoots.size()));
        reviewCardViewModel.getAllSingleWord().observe(getViewLifecycleOwner()
                , singleWords -> Log.d("initDatac", "" + singleWords.size()));
        //id = viewModel.getYesterdayPlan() + 1;
        initView(v);
        return v;
    }

    private void initView(View v) {
        viewPager = v.findViewById(R.id.learn_viewPager);
        adapter = new MainViewPageAdapter(getChildFragmentManager(), initFragmentList());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(index);
    }

    private List<Fragment> initFragmentList() {
        int today = viewModel.getSaveDay();
        int yesterday = viewModel.getYesterdayPlan();
        List<Fragment> ans = new ArrayList<>();
        /*
        for (int i = yesterday + 1; i <= today + 5; i++) {
            LearnWordRootFragment fragment = LearnWordRootFragment.newInstance(i);
            fragment.setRollCallBack(this);
            ans.add(fragment);
        }*/
        Random random = new Random();
        for(int i = 1; i <= 10 ;i++){
            LearnWordRootFragment fragment = new LearnWordRootFragment(i * (random.nextInt(5) + 1));
            fragment.setRollCallBack(this);
            ans.add(fragment);
        }
        return ans;
    }

    //TODO 向后翻页
    @Override
    public void next() {
        if(index < 10)
            viewPager.setCurrentItem(++index);
        else
            Toast.makeText(getContext(),"您已经完成今天的任务了!下班吧",Toast.LENGTH_SHORT).show();
    }

    //TODO 向前翻页
    @Override
    public void perform() {
        if(index > 0)
            viewPager.setCurrentItem(--index);
        else
            Toast.makeText(getContext(),"这就是今天开始的地方",Toast.LENGTH_SHORT).show();
    }


    //TODO 更新存储的系统时间
    public void onResume() {
        super.onResume();
        if (!viewModel.isToday()) {
            //更新操作
            int yesterday = viewModel.getSaveDay();
            viewModel.saveYesterday(yesterday);
            ////TODO 进过算法处理，储存当天的进度
            viewModel.saveLong(yesterday+1);
            viewModel.getLearnFlag().setValue(false);
            viewModel.saveFlag(false);
            viewModel.saveTime();
            refresh();
        }
    }

    private void refresh(){
        //TODO 算法处理
        adapter.setFragments(initFragmentList());
        adapter.notifyDataSetChanged();
    }

    // 记录离开时间
    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.saveTime();
    }

}
