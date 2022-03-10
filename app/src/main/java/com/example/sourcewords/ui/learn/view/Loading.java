package com.example.sourcewords.ui.learn.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.sourcewords.R;

public class Loading extends View {
    private Paint paint;
    private float mHeight , mWidth;
    public Loading(Context context) {
        super(context);
        init();
    }
    public Loading(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public Loading(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight = getMeasuredHeight();
        mWidth = getMinimumWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawColor(Color.MAGENTA);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        Matrix matrix = new Matrix();
        canvas.drawBitmap(bitmap,matrix,paint);
    }

    private void init(){
        paint = new Paint();
        paint.setStrokeWidth(2);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
    }

}
