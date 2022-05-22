package com.example.sourcewords.ui.learn.viewModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sourcewords.R;
import com.example.sourcewords.ui.review.dataBean.Word;

import java.util.List;

//学模块的recyclerView的适配器
public class WordsAdapter extends RecyclerView.Adapter<WordsAdapter.WordsHolder> {
    private List<Word> list;
    private final LayoutInflater mInflater;

    public WordsAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void setList(List<Word> list){
        this.list = list;
    }

    @NonNull
    @Override
    public WordsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.words_cells, parent, false);
        return new WordsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WordsHolder holder, int position) {
        Word word = list.get(position);
        holder.bind(word);
    }

    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();
        else
            return 0;
    }

    static class WordsHolder extends RecyclerView.ViewHolder {

        private TextView english, chinese, explian;

        public WordsHolder(@NonNull View itemView) {
            super(itemView);
            init();
        }

        private void init() {
            english = itemView.findViewById(R.id.cell_English);
            chinese = itemView.findViewById(R.id.cell_Chinese);
            explian = itemView.findViewById(R.id.cell_explain);
        }

        public void bind(Word word) {
            english.setText(word.getWord());
            chinese.setText(word.getMeaning());
            explian.setText(word.getExplanation());
        }
    }
}
