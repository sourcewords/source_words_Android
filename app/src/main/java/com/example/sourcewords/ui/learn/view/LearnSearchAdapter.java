package com.example.sourcewords.ui.learn.view;

import android.annotation.SuppressLint;
import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.sourcewords.ui.SearchAdapter;
import com.example.sourcewords.ui.learn.dataBean.HistoryWordRoot;
import com.example.sourcewords.ui.learn.viewModel.HistoryWRViewModel;
import com.example.sourcewords.ui.review.dataBean.HistoryWord;
import com.example.sourcewords.ui.review.dataBean.SingleWord;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.ui.review.model.HistoryDBCallback;
import com.example.sourcewords.utils.OptimizeMeaningUtils;

import java.util.List;

public class LearnSearchAdapter extends SearchAdapter {

    LearnSearchAdapter(Application application){
        mHistoryWRViewModel = new HistoryWRViewModel(application);
    }

    public void setHWRList(List<HistoryWordRoot> list){
        HWRList = list;
        isHistory = true;
    }

    public void setWRList(List<WordRoot> list) {
        WRList = list;
        isHistory = false;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.SearchHolder holder, int position) {
        if(isHistory) {
            HistoryWordRoot historyWordRoot = HWRList.get(position);
            holder.words.setText(historyWordRoot.getWordRoot());
            if(historyWordRoot.getMeaning().length()>=10)
                holder.info.setText(" = " + historyWordRoot.getMeaning().substring(0,10) + "...");
            else
                holder.info.setText(" = " + historyWordRoot.getMeaning());
            holder.delete.setVisibility(View.VISIBLE);
            holder.mConstraintLayout.setOnClickListener(v -> {
                mAdapterCallBack.startHistory(historyWordRoot);
            });
            holder.delete.setOnClickListener(v -> {
                mHistoryWRViewModel.Delete(historyWordRoot, new HistoryDBCallback() {
                    @Override
                    public void checkIsFinish(boolean isFinish) {
                        notifyDataSetChanged();
                    }
                });
            });
        } else {
            WordRoot wordRoot = WRList.get(position);
            holder.words.setText(wordRoot.getRoot());
            if(wordRoot.getMeaning().length()>15)
                holder.info.setText(" = " + wordRoot.getMeaning().substring(0,15) + "...");
            else
                holder.info.setText(" = " + wordRoot.getMeaning());
            holder.delete.setVisibility(View.GONE);
            holder.mConstraintLayout.setOnClickListener(v -> {
                mAdapterCallBack.startSearch(wordRoot);
            });
        }
    }

    @Override
    public int getItemCount() {
        return isHistory ? HWRList.size() : WRList.size();
    }
}
