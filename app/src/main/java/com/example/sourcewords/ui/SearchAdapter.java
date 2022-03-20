package com.example.sourcewords.ui;

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
import com.example.sourcewords.ui.learn.dataBean.HistoryWordRoot;
import com.example.sourcewords.ui.learn.viewModel.HistoryWRViewModel;
import com.example.sourcewords.ui.review.dataBean.HistoryWord;
import com.example.sourcewords.ui.review.dataBean.SingleWord;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.ui.review.view.AdapterCallBack;
import com.example.sourcewords.ui.review.viewmodel.HistoryViewModel;

import java.util.List;

public abstract class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchHolder> {
    protected List<HistoryWord> list;
    protected List<SingleWord> SList;
    protected List<HistoryWordRoot> HWRList;
    protected List<WordRoot> WRList;

    protected AdapterCallBack mAdapterCallBack;
    protected boolean isHistory;

    protected HistoryViewModel mHistoryViewModel;
    protected HistoryWRViewModel mHistoryWRViewModel;

    public void setAdapterCallBack(AdapterCallBack adapterCallBack){
        mAdapterCallBack = adapterCallBack;
    }

    @NonNull
    @Override
    public SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_words, parent, false);
        return new SearchHolder(itemView);
    }


    public class SearchHolder extends RecyclerView.ViewHolder{
        public TextView words;
        public TextView info;
        public ImageView delete;
        public ConstraintLayout mConstraintLayout;

        public SearchHolder(@NonNull View itemView) {
            super(itemView);
            words = itemView.findViewById(R.id.item_search_words_word);
            info = itemView.findViewById(R.id.item_search_words_details);
            delete = itemView.findViewById(R.id.item_search_words_clear);
            mConstraintLayout = itemView.findViewById(R.id.item_sw_consLayout);
        }
    }
}
