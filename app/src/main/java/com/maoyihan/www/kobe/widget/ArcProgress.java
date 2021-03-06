package com.maoyihan.www.kobe.widget;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.maoyihan.www.kobe.R;


/**
 * 圆弧进度条
 * Created by MaoYiHan on 2018/3/12
 */

public class ArcProgress extends View {
    private Paint mPaint;
    /**
     * 进度条进度
     */
    private float mProgress;
    /**
     * 进度条当前进度
     */
    private float mCurrentProgress;
    /**
     * 当前步数文字
     */
    private String mStep;
    private static final int RECT = 1;
    private static final int CIRCLE = 2;
    private static final int TRIANGLE= 3;
    /**
     * 当前形状类型
     */
    private int mCurrentShape = 1;
    private Path mPath;
    private RectF mRectF;

    public ArcProgress(Context context) {
        this(context, null);
    }

    public ArcProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArcProgress);
        mProgress = typedArray.getFloat(R.styleable.ArcProgress_progress,100);
        typedArray.recycle();
        init();
    }

    private void init() {
        mPaint = new Paint();
        mRectF = new RectF(-200, -200, 200, 200);
        mPath = new Path();
        mPath.moveTo(-100,100);
        mPath.lineTo(0,-100);
        mPath.lineTo(100,100);
        mPath.close();
//        progressAnimation();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(canvas.getWidth() / 2, canvas.getHeight() / 2);
        drawVarious(canvas);
//        drawBg(canvas);
//        drawProgress(canvas);
//        drawText(canvas);
    }

    private void drawBg(Canvas canvas) {
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        //笔头为圆
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(30);
        canvas.drawArc(mRectF, 0, 360, false, mPaint);
    }

    private void drawProgress(Canvas canvas) {
        mPaint.reset();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(30);
        canvas.drawArc(mRectF, 0, mCurrentProgress, false, mPaint);
        
    }

    /**
     * 问题：文字不同，怎么居中？答：测量文字的宽高，根据宽来确定
     */

    private void drawText(Canvas canvas) {
        mPaint.reset();
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(50);
        // 测量文字的宽高
        Rect textBounds = new Rect();
        mPaint.getTextBounds(mStep, 0, mStep.length(), textBounds);
        // 文字宽的一半
        int dx = (textBounds.width()) / 2;
        canvas.drawText(mStep,-dx,0,mPaint);
    }

    private void progressAnimation(){
        ValueAnimator barAnimator = ValueAnimator.ofFloat(0,(mProgress / 100) * 360);
        barAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        barAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentProgress = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        ValueAnimator txtAnimator = ValueAnimator.ofInt(0,2018);
        txtAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        txtAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mStep = animation.getAnimatedValue().toString();
                invalidate();
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(barAnimator,txtAnimator);
        animatorSet.setDuration(3000);
        animatorSet.start();
    }

    private void drawVarious(Canvas canvas){
        switch (mCurrentShape){
            case RECT:
                mPaint.setColor(Color.RED);
                canvas.drawRect(mRectF,mPaint);
                break;
            case CIRCLE:
                mPaint.setColor(Color.BLUE);
                canvas.drawCircle(0,0,100,mPaint);
                break;
            case TRIANGLE:
                mPaint.setColor(Color.BLACK);
                mPaint.setStyle(Paint.Style.FILL);
                mPaint.setStrokeWidth(15);
                canvas.drawPath(mPath,mPaint);
                break;
            default:
                break;
        }
    }

    public void changeShape(){
        switch (mCurrentShape){
            case RECT:
                mCurrentShape = CIRCLE;
                break;
            case CIRCLE:
                mCurrentShape = TRIANGLE;
                break;
            case TRIANGLE:
                mCurrentShape = RECT;
                break;
            default:
                break;
        }
        invalidate();
    }

}
