package com.maoyihan.www.kobe.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;

/**
 * 变色的字
 * Created by MaoYiHan on 2018/3/13 0013
 */

public class ColorTextView extends android.support.v7.widget.AppCompatTextView {
    /**
     * 左边的字体颜色的画笔
     */
    private Paint mLeftPaint;
    /**
     * 右边的字体颜色的画笔
     */
    private Paint mRightPaint;
    /**
     * 当前的进度
     */
    private float mCurrentProgress = 0.6f;
    private String mText;

    public ColorTextView(Context context) {
        this(context, null);
    }

    public ColorTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mLeftPaint = getPaintByColor(Color.BLACK);
        mRightPaint = getPaintByColor(Color.RED);
    }

    private Paint getPaintByColor(int color) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(color);
        paint.setTextSize(getTextSize());
        return paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mText = getText().toString();
        int width = getWidth();
        if (!TextUtils.isEmpty(mText)) {
            int middle = (int) (mCurrentProgress * width);
            drawLeft(canvas, middle);
            drawRight(canvas, middle);
        }
    }

    private void drawLeft(Canvas canvas, int middle) {
        drawText(canvas, 0, middle, mLeftPaint);
    }

    private void drawRight(Canvas canvas, int middle) {
        drawText(canvas, middle, getWidth(), mRightPaint);
    }

    private void drawText(Canvas canvas, int startX, int endX, Paint paint){
        canvas.save();
        // 截取绘制的内容，待会就只会绘制clipRect设置的参数部分
        canvas.clipRect(startX, 0, endX, getHeight());
        // 获取文字的范围
        Rect bounds = new Rect();
        mLeftPaint.getTextBounds(mText, 0, mText.length(), bounds);
        // 获取文字的Metrics 用来计算基线
        Paint.FontMetricsInt fontMetrics = mLeftPaint.getFontMetricsInt();
        // 获取文字的宽高
        int fontTotalHeight = fontMetrics.bottom - fontMetrics.top;
        // 计算基线到中心点的位置
        int offY = fontTotalHeight / 2 - fontMetrics.bottom;
        // 计算基线位置
        int baselineX = (getWidth() - bounds.width()) / 2 ;
        int baselineY = (getHeight() + fontTotalHeight) / 2 - offY ;
        canvas.drawText(mText, baselineX, baselineY, paint);
        canvas.restore();
    }
}
