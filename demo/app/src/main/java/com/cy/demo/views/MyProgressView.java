package com.cy.demo.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.cy.demo.R;

/**
 * @创建者 CY
 * @创建时间 2020/7/29 18:10
 * @描述 天逢门下，降魔大仙，摧魔伐恶，鹰犬当先，二将闻召，立至坛前，依律道奉令，神功帝宣，魔妖万鬼，诛专战无盖，太上圣力，浩荡无边，急急奉北帝律令
 */
public class MyProgressView extends View {
    private String TAG="MyProgressView";
    private Paint mBgPaint,mPaint;
    private Path path;

    private int startX = 0,startY = 0,endX = 0,endY = 0;
    private int progressBrBgColor;
    private int progressBrColor;

    private int progressBarHeight;

    public MyProgressView(Context context) {
        this(context,null);
    }

    public MyProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //TODO TypeArray
        init();
    }

    private void init(){
        Log.i(TAG,"init");
        mBgPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setStrokeCap(Paint.Cap.ROUND);
        mBgPaint.setColor(progressBrBgColor);
        mBgPaint.setStyle(Paint.Style.STROKE);
        mBgPaint.setStrokeWidth(progressBarHeight);

        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(progressBrColor);
        mPaint.setStrokeWidth(progressBarHeight);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        progressBrBgColor= Color.parseColor("#FF0000");
        progressBrColor= Color.parseColor("#00FF00");

        path=new Path();
//        initData();
    }

    private void initData(){
        startX = getWidth()/2;
        startY = getHeight()/2;
        endX = getWidth()/2;
        endY = getHeight()/2;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i(TAG,"onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        startX = 0;
        startY = heightSpecSize/2;
        endX = widthSpecSize;
        endY = heightSpecSize/2;
        Log.i(TAG,""+startX+"  "+startY+"  "+endX+"  "+endY);
//        radiu = (Math.min(getWidth(), getHeight())- mBgPaint.getStrokeWidth()*2)/2;
//
//        rectClickRange = new RectF(getWidth()/2-radiu,getHeight()/2-radiu,getWidth()/2+radiu,getHeight()/2+radiu);
//
//        matrix = new Matrix();
//        matrix.setTranslate(progressOffsetX, getHeight() / 2 - mBgPaint.getStrokeWidth() / 2-downloadBitmap.getHeight());
//
//        if (progress == 0){
//            progressOffsetX = 0;
//        }else {
//            progressOffsetX = (progress*(getWidth()- mBgPaint.getStrokeWidth()-loadingBitmap.getWidth())*1.0f/max);
//        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i(TAG,"onDraw");
        path.reset();
        path.moveTo(startX, startY);
        path.lineTo(endX, endY);
        canvas.drawPath(path, mBgPaint);
    }
}