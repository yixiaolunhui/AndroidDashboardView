package com.jacob.measure;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Package : com.jacob.measure
 * Author : jacob
 * Date : 15-3-3
 * Description : 这个类是用来xxx
 */
public class WeightDashBoard extends View {
    public static final float MAX_VALUE = 90.0F;

    private Bitmap mBitmapDash;
    private Bitmap mBitmapIndicator;
    private Paint mTextPaint;
    private Paint mBitmapPaint;

    private int mTextSize = dpToPx(20);
    private int mTextColor = 0xffff4546;

    private String mText;
    private int mTextWidth;
    private int mTextHeight;
    private int angle;
    private int lastAngle;
    private float lastWeight = 0;

    public WeightDashBoard(Context context) {
        this(context, null);
    }

    public WeightDashBoard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeightDashBoard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);

        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);

        mText = String.valueOf(0) + "KG";
        mTextWidth = (int) mTextPaint.measureText(mText, 0, mText.length());
        Rect rect = new Rect();
        mTextPaint.getTextBounds(mText, 0, mText.length(), rect);
        mTextHeight = rect.height();

        mBitmapDash = BitmapFactory.decodeResource(getResources(), R.mipmap.s_weight_rule);
        mBitmapIndicator = BitmapFactory.decodeResource(getResources(), R.mipmap.s_w_hand);

        setWeight(0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //整个高度设置为图片原来尺寸的一半
        setMeasuredDimension(mBitmapDash.getWidth(), mBitmapDash.getHeight() / 2);
    }


    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        canvas.save();
        Rect rectD = new Rect(0, 0, mBitmapDash.getWidth(), mBitmapDash.getHeight());
        canvas.rotate(angle, getMeasuredWidth() / 2, mBitmapDash.getHeight() / 2);
        canvas.drawBitmap(mBitmapDash, null, rectD, mBitmapPaint);
        canvas.restore();
        canvas.drawBitmap(mBitmapIndicator, null, rectD, mBitmapPaint);

        mText = formatDecimalRound(lastWeight, 1) + "KG";
        mTextWidth = (int) mTextPaint.measureText(mText, 0, mText.length());
        canvas.drawText(mText, mBitmapDash.getWidth() / 2 - mTextWidth / 2, mBitmapDash.getHeight() / 3 + mTextHeight, mTextPaint);
    }

    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    float startX = 0;


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float distance = event.getX() - startX;
                angle = lastAngle + (int) (distance * 1.0f / getMeasuredWidth() * 180);
                if (angle % 2 != 0) {
                    angle++;
                }
//                Log.e("TAG", angle + "");
                if (angle >= 0) {
                    lastWeight = MAX_VALUE * (1 - angle / 360.0f);
                } else {
                    lastWeight = MAX_VALUE * (Math.abs(angle) / 360.0f);
                }

                if (lastWeight < 0) {
                    lastWeight = MAX_VALUE + lastWeight;
                    lastAngle = 0;
                }

                if (lastWeight == MAX_VALUE) {
                    lastAngle = 0;
                    lastWeight = 0;
                }


                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                lastAngle = angle;
                startX = 0;
                break;
        }
        return true;
    }

    public void setWeight(float weight) {
        lastWeight = weight;
        angle = (int) Math.ceil(weight / MAX_VALUE * 360);
    }

    public String formatDecimalRound(double number, int digits) {
        String source = String.valueOf(number);
        double result = new BigDecimal(source).setScale(digits, BigDecimal.ROUND_HALF_UP).doubleValue();
        return formatDecimal(result, digits);
    }

    public String formatDecimal(double number, int digits) {

        StringBuffer a = new StringBuffer();
        for (int i = 0; i < digits; i++) {
            if (i == 0)
                a.append(".");
            a.append("0");
        }
        DecimalFormat nf = new DecimalFormat("###,###,###,##0" + a.toString());
        String formatted = nf.format(number);
        return formatted;
    }

    public float getWeight() {
        return lastWeight;
    }
}
