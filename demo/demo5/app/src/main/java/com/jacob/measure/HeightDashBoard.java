package com.jacob.measure;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Package : com.jacob.measure
 * Author : jacob
 * Date : 15-3-3
 * Description : 这个类是用来xxx
 */
public class HeightDashBoard extends View {
    private Bitmap mBitmapRule;
    private Bitmap mBitmapIndicator;
    private Paint mPaint;
    public static final int START_HEIGHT = 175;
    public static final float MEASURE_HEIGHT = 200.0f;
    public static final float ALL_HEIGHT = 202.5f;
    private int height = START_HEIGHT;

    float starY = 0;
    float distance = 0;
    float lastDistance = 0;

    float realHeight = 0;
    float paddingHeight = 0;

    public HeightDashBoard(Context context) {
        this(context, null);
    }

    public HeightDashBoard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeightDashBoard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBitmapRule = BitmapFactory.decodeResource(getResources(), R.mipmap.s_height_rule);
        mBitmapIndicator = BitmapFactory.decodeResource(getResources(), R.mipmap.s_h_hand);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        realHeight = (float) Math.ceil(mBitmapRule.getHeight() * (MEASURE_HEIGHT / ALL_HEIGHT));
        paddingHeight = (float) Math.ceil(mBitmapRule.getHeight() * (2.5f / ALL_HEIGHT));

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mBitmapRule.getWidth() + 20, mBitmapRule.getHeight() / 4);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        canvas.save();
        Rect rect = new Rect(0, 0, mBitmapRule.getWidth(), mBitmapRule.getHeight());
        canvas.translate(0, distance);
        canvas.drawBitmap(mBitmapRule, null, rect, mPaint);
        canvas.restore();
        canvas.drawBitmap(mBitmapIndicator, getMeasuredWidth() - mBitmapIndicator.getWidth(),
                getMeasuredHeight() / 2 - paddingHeight / 4, mPaint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                starY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                distance = lastDistance + event.getY() - starY;
                height = (int) Math.ceil(START_HEIGHT + distance * 1.0f / (realHeight) * MEASURE_HEIGHT);
                if (height <= 0) {
                    height = 0;
                    distance = -1 * START_HEIGHT * (realHeight) / MEASURE_HEIGHT;
                }

                if (height >= MEASURE_HEIGHT) {
                    height = (int) MEASURE_HEIGHT;
                    distance = (MEASURE_HEIGHT - START_HEIGHT) * realHeight / MEASURE_HEIGHT;
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                lastDistance = distance;
                height = (int) Math.ceil(START_HEIGHT + lastDistance * 1.0f / (realHeight) * MEASURE_HEIGHT);
                break;
        }
        return true;
    }

    public int getScaleHeight() {
        return height;
    }

}
