package com.cy.demo.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * @创建者 CY
 * @创建时间 2020/9/1 17:22
 * @描述 天逢门下，降魔大仙，摧魔伐恶，鹰犬当先，二将闻召，立至坛前，依律道奉令，神功帝宣，魔妖万鬼，诛专战无盖，太上圣力，浩荡无边，急急奉北帝律令
 */
public class MyView extends View {

    private Paint paint,textPaint,linePaint;

    private Path path;

    private Random random=new Random();

    private SparseArray<POINT> data;

    private POINT currentPoint=null;

    private float pointX,pointY;


    public MyView(Context context) {
        this(context,null);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setClickable(true);
        path=new Path();
        data=new SparseArray<>();
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#FF0000"));

        textPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(30f);
        textPaint.setColor(Color.parseColor("#000000"));

        linePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.parseColor("#000000"));
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(10);
    }


    @Override
    protected void onDraw(Canvas canvas) {

        for (int i=0;i<POINT.m;i++){
            POINT s=data.get(i);
            canvas.drawCircle(s.x,s.y,50,paint);
            float size=textPaint.measureText(""+s.index);
            canvas.drawText(""+s.index,s.x-size/2,s.y+size/2,textPaint);
            if (s==currentPoint){
                path.reset();
                path.moveTo(s.x,s.y);
                path.lineTo(pointX,pointY);
                canvas.drawPath(path,linePaint);
                Log.i("MyView","onDraw");
            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        pointX=event.getX();
        pointY=event.getY();
        if (event.getAction()==MotionEvent.ACTION_DOWN){
            POINT p=new POINT();
            p.index=POINT.m++;
            p.x=event.getX();
            p.y=event.getY();
            for (int i=0;i<POINT.m-1;i++){
                POINT point=data.get(i);
                float distance=random.nextInt(100);
                p.map.put(point.index,distance);
                point.map.put(p.index,distance);
            }
            data.put(p.index,p);
            currentPoint=p;
        }else if (event.getAction()==MotionEvent.ACTION_MOVE){

        }else if (event.getAction()==MotionEvent.ACTION_UP){
            currentPoint=null;
        }
        invalidate();
        return super.onTouchEvent(event);
    }


    static class POINT{
        static int m=0;
        int index;
        float x;
        float y;
        float radius=50;
        int num;
        List<POINT> ps=new ArrayList<>();
        SparseArray<Float> map=new SparseArray<>();
        void setNum(){

        }
    }
}