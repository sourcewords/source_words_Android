package com.example.sourcewords.ui.review.view;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sourcewords.R;
import com.example.sourcewords.ui.SearchAdapter;
import com.example.sourcewords.ui.learn.dataBean.HistoryWordRoot;
import com.example.sourcewords.ui.review.dataBean.HistoryWord;
import com.example.sourcewords.ui.review.dataBean.SingleWord;
import com.example.sourcewords.ui.review.viewmodel.HistoryViewModel;
import com.example.sourcewords.utils.OptimizeMeaningUtils;

import java.util.List;

public class ReviewSearchAdapter extends SearchAdapter {

    public ReviewSearchAdapter(Application application){
        mHistoryViewModel = new HistoryViewModel(application);
    }

    public void setList(List<HistoryWord> list) {
        this.list = list;
        isHistory = true;
    }

    public void setSList(List<SingleWord> SList) {
        this.SList = SList;
        isHistory = false;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.SearchHolder holder, int position) {
        if(isHistory) {
            HistoryWord historyWord = list.get(position);
            holder.words.setText(historyWord.getRoot());
            holder.info.setText(historyWord.getMeaning());
            holder.delete.setVisibility(View.VISIBLE);
            holder.mConstraintLayout.setOnClickListener(v -> {
                mAdapterCallBack.startHistory(historyWord);
            });
            holder.delete.setOnClickListener(v -> {
                mHistoryViewModel.Delete(historyWord);
                notifyDataSetChanged();
            });
        } else {
            SingleWord singleWord = SList.get(position);
            holder.words.setText(singleWord.getWord());
            holder.info.setText(OptimizeMeaningUtils.OptimizeMeaning(singleWord.getMeaning()));
            holder.delete.setVisibility(View.GONE);
            holder.mConstraintLayout.setOnClickListener(v -> {
                mAdapterCallBack.startSearch(singleWord);
            });
        }
    }

    @Override
    public int getItemCount() {
        return isHistory ? list.size() : SList.size();
    }
}
