package com.ethanco.circleprogresslibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.ethanco.circleprogresslibrary.utils.GeometryUtil;

/**
 * @Description 带刻度条的圆形进度条
 * Created by YOLANDA on 2015-12-10.
 */
public class TickCircleProgress extends CircleProgress {
    protected Paint tickPaint;
    //刻度线的高度
    protected float tickMarkHeight;
    //刻度线的个数
    protected int tickMarkCount = 8;
    private PointF[] startPoints;
    private PointF[] stopPoints;
    //刻度圆弧的rect
    private RectF mTickPaintRect;
    //父类内弧边界的Rect
    private RectF mParentRect;
    private float tickWidth;
    //刻度颜色
    private int tickColor;

    public TickCircleProgress(Context context) {
        super(context);
    }

    public TickCircleProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TickCircleProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init() {
        super.init();

        initTickPaint();
    }

    @Override
    protected void initViewSize() {
        super.initViewSize();

        float dx = mStrokeWidth * 0.5F;
        mParentRect = new RectF(mPaintRectF.left + dx, mPaintRectF.top + dx,
                mPaintRectF.right - dx, mPaintRectF.bottom - dx);

        mTickPaintRect = new RectF(mParentRect.left + tickMarkHeight, mParentRect.top + tickMarkHeight,
                mParentRect.right - tickMarkHeight, mParentRect.bottom - tickMarkHeight);

        startPoints = GeometryUtil.getIntersectionPoints(mCenter, (mParentRect.right - mParentRect.left) / 2F, 0.0);
        stopPoints = GeometryUtil.getIntersectionPoints(mCenter, (mTickPaintRect.right - mTickPaintRect.left) / 2F, 0.0);
    }

    @Override
    protected void initVar(Context context, AttributeSet attrs) {
        super.initVar(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TickCircleProgress);
        tickColor = ta.getColor(R.styleable.TickCircleProgress_tickColor, Color.BLACK);
        tickWidth = ta.getDimension(R.styleable.TickCircleProgress_tickWidth, 2);
        tickMarkHeight = ta.getDimension(R.styleable.TickCircleProgress_tickMarkHeight, mStrokeWidth * 1F);
        ta.recycle();

        isSolid = false;
    }

    private void initTickPaint() {
        tickPaint = new Paint();
        tickPaint.setColor(tickColor);
        setCommonPaint(tickPaint, isSolid, false);
        tickPaint.setStrokeWidth(tickWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawArc(mTickPaintRect, 0, maxProgress, isSolid, tickPaint);

        float routeDegress = maxProgress / (tickMarkCount - 1);
        for (int i = 0; i < tickMarkCount; i++) {
            if (i != 0)
                canvas.rotate(-routeDegress, mCenter.x, mCenter.y);
            canvas.drawLine(startPoints[0].x, startPoints[0].y, stopPoints[0].x, stopPoints[0].y, tickPaint);
        }
    }
}
