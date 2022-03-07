package com.example.sourcewords.ui.mine.model.databean;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sourcewords.R;

import java.util.List;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG=SpacesItemDecoration.class.getSimpleName();

    private int space;
    private List<PlanBean> list;
    private Context context;




    public SpacesItemDecoration(int space, List<PlanBean> list, Context context) {
        this.space = space;
        this.list=list;
        this.context=context;

    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        //设置item的上下左右的margin
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;
//设置列表首个item和最后一个item的margin间距
//        if (parent.getChildAdapterPosition(view)==0){
//            int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.dp_41);
//            outRect.left=dimensionPixelSize;
//        }
//        if (parent.getChildAdapterPosition(view)==list.size()-1){
//            int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.dp_41);
//            outRect.right=dimensionPixelSize;
//        }





        // Add top margin only for the first item to avoid double space between items
//        if (parent.getChildPosition(view) == 0)
//            outRect.top = space;
    }
}
