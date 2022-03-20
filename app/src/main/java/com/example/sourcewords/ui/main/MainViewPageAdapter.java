package com.example.sourcewords.ui.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class MainViewPageAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;

    public MainViewPageAdapter(@NonNull FragmentManager fm, @NonNull List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    public MainViewPageAdapter(FragmentManager fm){
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public void setFragments(List<Fragment> fragments){
        this.list = fragments;
    }
}
