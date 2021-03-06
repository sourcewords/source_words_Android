package com.example.sourcewords.ui.main;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.sourcewords.App;
import com.example.sourcewords.R;
import com.example.sourcewords.ui.learn.view.LearnFragment;
import com.example.sourcewords.ui.learn.view.Loading;
import com.example.sourcewords.ui.learn.viewModel.LearnViewModel;
import com.example.sourcewords.ui.mine.view.MineFragment;
import com.example.sourcewords.ui.review.view.ReviewFragment;
import com.example.sourcewords.utils.PreferencesUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    private List<Fragment> fragmentList;
    private Loading loading;
    private static final int MESSAGE1 = 0x1001;

    public static MutableLiveData<Integer> remove;//以应对3个AsyncTask都结束时要实现的复杂结构的观察者模式，我的选择是内存泄漏
    public static int num = 0;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        remove = new MutableLiveData<>(0);
    }

    @SuppressLint("NonConstantResourceId")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main,container,false);
        viewPager = view.findViewById(R.id.viewpage);
        bottomNavigationView = view.findViewById(R.id.navigation);
        initFragmentList();
        viewPager.setAdapter(new MainViewPageAdapter(getChildFragmentManager(),fragmentList));
        remove.observe(getViewLifecycleOwner(),x -> {
            if(x == 3) {
                ((ViewGroup) loading.getParent()).removeView(loading);
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.getAppContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(PreferencesUtils.isFirst,x);
                editor.apply();
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                Log.d("selected"," "+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.learn:
                    viewPager.setCurrentItem(0);
                    break;

                case R.id.review:
                    viewPager.setCurrentItem(1);
                    break;

                case R.id.mine:
                    viewPager.setCurrentItem(2);
                    break;

            }

            return false;
        });
        initLoading();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.getAppContext());
        if(preferences.getInt(PreferencesUtils.isFirst,0) == 3){
            Handler handler = new MessageHandler();
            handler.sendEmptyMessageDelayed(MESSAGE1,500);
        }
        return view;
    }

    private void initFragmentList() {
        fragmentList = new ArrayList<>(3);
        fragmentList.add(new LearnFragment());
        fragmentList.add(new ReviewFragment());
        fragmentList.add(new MineFragment());
    }

    private void initLoading() {
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        loading = new Loading(getContext());
        getActivity().addContentView(loading, lp);
        //Handler handler = new MessageHandler();
        //handler.sendEmptyMessageDelayed(MESSAGE1,1500);
    }

    @SuppressLint("HandlerLeak")
    class MessageHandler extends Handler {

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == MESSAGE1) ((ViewGroup) loading.getParent()).removeView(loading);
        }
    }



}