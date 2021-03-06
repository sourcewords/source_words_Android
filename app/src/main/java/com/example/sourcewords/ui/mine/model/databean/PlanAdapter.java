package com.example.sourcewords.ui.mine.model.databean;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sourcewords.R;
import com.example.sourcewords.ui.login.view.ForgetPwdActivity;
import com.example.sourcewords.ui.login.view.SetPwdActivity;
import com.example.sourcewords.ui.mine.model.Api;
import com.example.sourcewords.ui.mine.view.AddPlanActivity;
import com.example.sourcewords.ui.mine.viewmodel.AddPlanViewModel;

import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> {
    private List<PlanBean> mList;
    Api.addPlan api;
    public PlanAdapter(List<PlanBean> mList, Api.addPlan api){
        this.mList = mList;
        this.api = api;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final ImageView planPic;
        private final AppCompatTextView planName, leastTime, b_eTime, progressNum;
        private final ProgressBar progress;
        private final ConstraintLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.myplan_rv_item);
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
        holder.layout.setOnClickListener(v->{
            api.success(mList.get(position).getPlanName());
        });
        holder.planName.setText(mList.get(position).getPlanName() + "??????");
        holder.leastTime.setText("????????????" + mList.get(position).getLeastTime() + "???");
        holder.b_eTime.setText(mList.get(position).getB_eTime());
        holder.progress.setProgress((int) mList.get(position).getProgress());
        holder.progressNum.setText(mList.get(position).getProgress() + "%");
        if(mList.get(position).getType() == 1){
            holder.planPic.setBackgroundResource(R.mipmap.cet4);
        }else if(mList.get(position).getType() == 2){
            holder.planPic.setBackgroundResource(R.mipmap.cet6);
        }else {
            holder.planPic.setBackgroundResource(R.mipmap.ielts);
        }
    }

    @Override
    public int getItemCount() {
        if(mList == null)   return 0;
        return mList.size();
    }
}
