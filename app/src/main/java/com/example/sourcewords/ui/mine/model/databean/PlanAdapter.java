package com.example.sourcewords.ui.mine.model.databean;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sourcewords.R;

import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> {
    private List<PlanBean> mList;

    public PlanAdapter(List<PlanBean> mList){
        this.mList = mList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final ImageView planPic;
        private final AppCompatTextView planName, leastTime, b_eTime, progressNum;
        private final ProgressBar progress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            planPic = itemView.findViewById(R.id.plan_pic);
            planName = itemView.findViewById(R.id.plan_name);
            leastTime = itemView.findViewById(R.id.least_time_tv);
            b_eTime = itemView.findViewById(R.id.b_e_time_tv);
            progressNum = itemView.findViewById(R.id.plan_progress_num);
            progress = itemView.findViewById(R.id.plan_progress_bar);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myplan_rv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.planName.setText(mList.get(position).getPlanName());
        holder.leastTime.setText("倒计时：" + mList.get(position).getLeastTime() + "天");
        holder.b_eTime.setText(mList.get(position).getB_eTime());
        holder.progress.setProgress(mList.get(position).getProgress());
        holder.progressNum.setText(mList.get(position).getProgress() + "%");
    }

    @Override
    public int getItemCount() {
        if(mList == null)   return 0;
        return mList.size();
    }
}