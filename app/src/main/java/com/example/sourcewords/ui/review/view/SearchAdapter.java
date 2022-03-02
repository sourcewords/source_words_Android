package com.example.sourcewords.ui.review.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.preference.Preference;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sourcewords.R;
import com.example.sourcewords.ui.review.dataBean.HistoryWord;
import com.example.sourcewords.ui.review.dataBean.SingleWord;
import com.example.sourcewords.ui.review.viewmodel.HistoryViewModel;
import com.example.sourcewords.ui.review.viewmodel.ReviewViewModel;
import com.example.sourcewords.utils.OptimizeMeaningUtils;
import com.example.sourcewords.utils.PreferencesUtils;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchHolder>{
    private List<HistoryWord> list;
    private List<SingleWord> SList;
    private AdapterCallBack mAdapterCallBack;
    private HistoryViewModel mHistoryViewModel;
    private Context mContext;
    private boolean isHistory;

    public SearchAdapter(Context mContext, HistoryViewModel historyViewModel){
        this.mContext = mContext;
        mHistoryViewModel = historyViewModel;
    }

    public void setList(List<HistoryWord> list) {
        this.list = list;
        isHistory = true;
    }

    public void setSList(List<SingleWord> SList) {
        this.SList = SList;
        isHistory = false;
    }

    public void setAdapterCallBack(AdapterCallBack adapterCallBack){
        mAdapterCallBack = adapterCallBack;
    }

    @NonNull
    @Override
    public SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_words, parent, false);
        return new SearchHolder(itemView);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull SearchHolder holder, int position) {
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

    static class SearchHolder extends RecyclerView.ViewHolder{
        TextView words, info;
        ImageView delete;
        ConstraintLayout mConstraintLayout;

        public SearchHolder(@NonNull View itemView) {
            super(itemView);
            words = itemView.findViewById(R.id.item_search_words_word);
            info = itemView.findViewById(R.id.item_search_words_details);
            delete = itemView.findViewById(R.id.item_search_words_clear);
            mConstraintLayout = itemView.findViewById(R.id.item_sw_consLayout);
        }
    }
}
