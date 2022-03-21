package com.example.sourcewords.ui.learn.view;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import androidx.viewpager.widget.ViewPager;

import com.example.sourcewords.App;
import com.example.sourcewords.R;
import com.example.sourcewords.ui.learn.viewModel.LearnViewModel;
import com.example.sourcewords.ui.learn.viewModel.RollInterface;
import com.example.sourcewords.ui.main.MainViewPageAdapter;

import com.example.sourcewords.ui.review.viewmodel.ReviewCardViewModel;
import com.example.sourcewords.utils.PreferencesUtils;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

//TODO 学模块
public class LearnFragment extends Fragment implements RollInterface {
    private int index = 0;//对于ViewPager中的list的下标
    private LearnViewModel viewModel;
    private MainViewPageAdapter adapter;
    private ViewPager viewPager;
    private static int size;



    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_learn_new, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(LearnViewModel.class);
        ReviewCardViewModel reviewCardViewModel = ViewModelProviders.of(getActivity()).get(ReviewCardViewModel.class);
        reviewCardViewModel.getAllWord().observe(getViewLifecycleOwner(), words -> {
            assert words != null;
            Log.d("initDataab", "" + words.size());
        });
        reviewCardViewModel.getAllWordRoot().observe(getViewLifecycleOwner(), wordRoots -> Log.d("initDataa", "" + wordRoots.size()));
        reviewCardViewModel.getAllSingleWord().observe(getViewLifecycleOwner()
                , singleWords -> Log.d("initDatac", "" + singleWords.size()));
        size = viewModel.getSpeed();
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
        int date = viewModel.HowLongPlan();
        Log.d("已学习天数", String.valueOf(date));
        List<Fragment> ans = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            LearnWordRootFragment fragment = LearnWordRootFragment.newInstance(i + date * size);
            fragment.setRollCallBack(this);
            ans.add(fragment);
        }

        /*
        Random random = new Random();
        for(int i = 1; i <= 10 ;i++){
            LearnWordRootFragment fragment = new LearnWordRootFragment(i * (random.nextInt(5) + 1));
            fragment.setRollCallBack(this);
            ans.add(fragment);
        }

         */
        return ans;
    }

    //TODO 向后翻页
    @Override
    public void next() {
        if (index < size - 1)
            viewPager.setCurrentItem(++index);
        else
            Toast.makeText(getContext(), "您已经完成今天的任务了!下班吧", Toast.LENGTH_SHORT).show();
    }

    //TODO 向前翻页
    @Override
    public void perform() {
        if (index > 0)
            viewPager.setCurrentItem(--index);
        else
            Toast.makeText(getContext(), "这就是今天开始的地方", Toast.LENGTH_SHORT).show();
    }


    //TODO 更新存储的系统时间
    public void onResume() {
        super.onResume();
        if (!viewModel.isToday()) {
            //更新操作
            //TODO 进过算法处理，储存当天的进度
            viewModel.saveLong(viewModel.HowLongPlan() * size + 1);
            viewModel.getLearnFlag().setValue(false);
            viewModel.saveFlag(false);
            viewModel.saveTime();

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.getAppContext());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(PreferencesUtils.WORD_ROOT_HAVE_LEARNED, false);
            editor.apply();
        }
        if (isPlanChanged()){
            dealWithPlanChanged();
        }
        refresh();
    }

    private void refresh() {
        //TODO 算法处理
        adapter.setFragments(initFragmentList());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
        //Pass_Wordroot_ID(viewModel.HowLongPlan() * viewModel.getSpeed() + 1,viewModel.getLong());
    }

    // 记录离开时间
    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.saveTime();
    }

    //TODO 判断计划判断以及处理计划更换
    private boolean isPlanChanged(){
        final boolean[] isChanged = {false};
        viewModel.getNowPlan().observe(getViewLifecycleOwner(), integer -> {
            isChanged[0] = (integer != viewModel.getNow());
            viewModel.savePlan(integer);
        });
        return isChanged[0];
    }

    private void dealWithPlanChanged(){
        index = 0;
        viewModel.saveLong(viewModel.HowLongPlan() * size + 1);
    }

    /*y应某个**的要求写的
    public void search(){
        viewModel.getAll().observe(getViewLifecycleOwner(), new Observer<List<WordRoot>>() {
            @Override
            public void onChanged(List<WordRoot> list) {
                for(WordRoot root : list){
                    Log.d("today","........");
                    if(root.getVideo_url().length() != 0){
                        Log.d("视频", String.valueOf(root.getId()));
                    }
                }
            }
        });
    }

     */

    //TODO 传递今日所学的id
    private void Pass_Wordroot_ID(int start,int end){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.getAppContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        JSONArray jsonArray = new JSONArray();
        for(int i = start ; i < end ; i++){
            jsonArray.put(i);
        }
        Log.d("targetSource", jsonArray.toString());
        editor.putString(PreferencesUtils.WORD_ROOT_TODAY, jsonArray.toString());
        editor.apply();
    }

}


