package com.example.sourcewords.ui.review.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sourcewords.R;
import com.example.sourcewords.ui.review.dataBean.HistoryWord;
import com.example.sourcewords.ui.review.dataBean.SingleWord;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchHolder>{
    private List<HistoryWord> list;
    private List<SingleWord> SList;
    private Context mContext;
    private boolean isHistory;

    public SearchAdapter(Context mContext){
        this.mContext = mContext;
    }

    public void setList(List<HistoryWord> list) {
        this.list = list;
        isHistory = true;
    }

    public void setSList(List<SingleWord> SList) {
        this.SList = SList;
        isHistory = false;
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

            holder.delete.setOnClickListener(v -> {
                list.remove(position);
                notifyDataSetChanged();
            });
        } else {
            SingleWord singleWord = SList.get(position);
            holder.words.setText(singleWord.getWord());
            holder.info.setText(singleWord.getMeaning());
            holder.delete.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return isHistory ? list.size() : SList.size();
    }

    static class SearchHolder extends RecyclerView.ViewHolder{
        TextView words, info;
        ImageView delete;

        public SearchHolder(@NonNull View itemView) {
            super(itemView);
            words = itemView.findViewById(R.id.item_search_words_word);
            info = itemView.findViewById(R.id.item_search_words_details);
            delete = itemView.findViewById(R.id.item_search_words_clear);
        }
    }
}
