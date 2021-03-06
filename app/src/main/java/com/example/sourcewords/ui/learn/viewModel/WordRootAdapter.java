package com.example.sourcewords.ui.learn.viewModel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sourcewords.R;
import com.example.sourcewords.ui.learn.view.WordRootPage;
import com.example.sourcewords.ui.review.dataBean.WordRoot;

import java.util.List;

public class WordRootAdapter extends RecyclerView.Adapter<WordRootAdapter.WordRootHolder>  {
    private List<WordRoot> wordRoots;
    private final LayoutInflater mInflater;
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    public WordRootAdapter(Context mContext){
        WordRootAdapter.mContext = mContext;
        mInflater = LayoutInflater.from(mContext.getApplicationContext());
    }

    public void setWordRoots(List<WordRoot> list){
        wordRoots = list;
    }

    @NonNull
    @Override
    public WordRootHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.wordroot_cells,parent,false);
        return new WordRootHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WordRootHolder holder, int position) {
        WordRoot root = wordRoots.get(position);
        holder.bind(root);
    }

    @Override
    public int getItemCount() {
        if(wordRoots == null){
            return 0;
        }else{
            return wordRoots.size();
        }
    }

    static class WordRootHolder extends RecyclerView.ViewHolder{
        private final AppCompatTextView textView;
        private final ConstraintLayout layout;
        public WordRootHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.wordRoot_text);
            layout = itemView.findViewById(R.id.root_meaning);
        }

        @SuppressLint("SetTextI18n")
        public void bind(WordRoot wordRoot) {
            textView.setText(wordRoot.getRoot() + " " + wordRoot.getMeaning());
            layout.setOnClickListener(view -> {
                int id = wordRoot.getId();
                Intent intent = WordRootPage.newInstance(mContext,id);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.getApplicationContext().startActivity(intent);
            });
        }
    }
}
