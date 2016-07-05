package com.ihat.pihat.circleprogress;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;

/**
 * Project: CircleProgressView
 * Author: Guoger
 * Mail: 505917699@qq.com
 * Created time: 2016/4/17/16 10:39.
 */
public class CircleProgress extends View {
    private int mMeasureHeight;
    private int mMeasureWidth;

    private Paint valuePaint;
    private String valueText;
    private int valueTextColor;
    private int valueTextSize;

    private Paint despPaint;
    private String despText;
    private int despTextColor;
    private int despTextSize;

    private Paint mCirclePaint;
    private float mCircleXY;
    private float mRadius;

    private Paint mArcPaint;
    private RectF mArcRectF;
    private float sweepValue = 20;
    private float sweepAngle;
    private int circleStrokeWidth;

    private int roundColor;
    private int roundProgressColor;
    private float max = 100; //最大值为100%
    private boolean valueTextIsDisplayable;
    private boolean despTextIsDisplayable;
    private int circleStyle;

    private ValueAnimator mValueAnimator;
    private Interpolator interpolator  = new BounceInterpolator();
    private int animDuration = Constant.DEFAULT_ANIM_DURATION;

    public CircleProgress(Context context) {
        super(context);
    }

    public CircleProgress(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgress);
        roundColor = typedArray.getColor(R.styleable.CircleProgress_roundColor, Constant.DEFAULT_ROUND_COLOR);
        roundProgressColor = typedArray.getColor(R.styleable.CircleProgress_roundProgressColor, Constant.DEFAULT_ROUND_PROGRESS_COLOR);
        circleStrokeWidth = (int) typedArray.getDimension(R.styleable.CircleProgress_circleStrokeWidth, Constant.DEFAULT_CIRCLE_STROKE_WIDTH);
        valueText = typedArray.getString(R.styleable.CircleProgress_valueText);
        valueTextColor = typedArray.getColor(R.styleable.CircleProgress_valueTextColor, Constant.DEFAULT_VALUE_TEXT_COLOR);
        valueTextSize = (int) typedArray.getDimension(R.styleable.CircleProgress_valueTextSize, Constant.DEFAULT_VALUE_TEXT_SIZE);
        despText = typedArray.getString(R.styleable.CircleProgress_despText);
        if (valueText == null || despText == null) {
            valueText = Constant.DEFAULT_VALUE_TEXT;
            despText = Constant.DEFAULT_DESP_TEXT;
        }
        despTextColor = typedArray.getColor(R.styleable.CircleProgress_despTextColor, Constant.DEFAULT_DESP_TEXT_COLOR);
        despTextSize = (int) typedArray.getDimension(R.styleable.CircleProgress_despTextSize, Constant.DEFAULT_DESP_TEXT_SIZE);
        valueTextIsDisplayable = typedArray.getBoolean(R.styleable.CircleProgress_valueTextIsDisplayable, Constant.DEFAULT_VALUE_TEXT_IS_DISPLAYABLE);
        despTextIsDisplayable = typedArray.getBoolean(R.styleable.CircleProgress_despTextIsDisplayable, Constant.DEFAULT_DESP_TEXT_IS_DISPLAYABLE);
        circleStyle = typedArray.getInt(R.styleable.CircleProgress_style, Constant.STYLE_STROKE);
        typedArray.recycle();

    }

    public CircleProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mMeasureWidth = MeasureSpec.getSize(widthMeasureSpec);
        mMeasureHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(mMeasureWidth, mMeasureHeight);

        init();
    }

    private void init() {
        float length;
        if (mMeasureHeight >= getMeasuredWidth()) {
            length = mMeasureWidth;
        } else {
            length = mMeasureHeight;
        }

        mCircleXY = length / 2;
        mRadius = length * 18 / 40;

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(roundColor);
        mCirclePaint.setStrokeWidth(circleStrokeWidth);

        mArcRectF = new RectF(
                mCircleXY - mRadius,
                mCircleXY - mRadius,
                mCircleXY + mRadius,
                mCircleXY + mRadius);
        sweepAngle = value2Angle(sweepValue);
        mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcPaint.setColor(roundProgressColor);
        mArcPaint.setStrokeWidth(circleStrokeWidth);

        //设置空心或实心
        if (circleStyle == Constant.STYLE_STROKE) {
            mArcPaint.setStyle(Paint.Style.STROKE);
            mCirclePaint.setStyle(Paint.Style.STROKE);
        } else {
            mArcPaint.setStyle(Paint.Style.FILL);
            mCirclePaint.setStyle(Paint.Style.FILL);
        }

        valuePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        valuePaint.setColor(valueTextColor);
        valuePaint.setTextSize(valueTextSize);
        valuePaint.setTextAlign(Paint.Align.CENTER);
//        valueText = Constant.DEFAULT_VALUE_TEXT;

        despPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        despPaint.setColor(despTextColor);
        despPaint.setTextSize(despTextSize);
        despPaint.setTextAlign(Paint.Align.CENTER);
//        despText = Constant.DEFAULT_DESP_TEXT;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mCircleXY, mCircleXY, mRadius, mCirclePaint);

        if (circleStyle == Constant.STYLE_STROKE) {
            canvas.drawArc(mArcRectF, 270, sweepAngle, false, mArcPaint);
        } else {
            canvas.drawArc(mArcRectF, 270, sweepAngle, true, mArcPaint);
        }

        if (valueTextIsDisplayable) {
            canvas.drawText(valueText, 0, valueText.length(),
                    mCircleXY, mCircleXY - (valueTextSize / 5), valuePaint);
        }

        if (despTextIsDisplayable) {
            canvas.drawText(despText, 0, despText.length(),
                    mCircleXY, (float) (mCircleXY + (despTextSize * 1.5)), despPaint);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        anim();
    }

    public void anim() {
        mValueAnimator = ValueAnimator.ofFloat(0, sweepValue);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                setSweepValue((Float) animation.getAnimatedValue());
            }
        });
        mValueAnimator.setInterpolator(interpolator);
        mValueAnimator.setDuration(Constant.DEFAULT_ANIM_DURATION);
        mValueAnimator.start();
    }

    public void setValueText(String value) {
        valueText = value;
        forceInvalidate();
    }

    public void setValueTextColor(int color) {
        valueTextColor = color;
        forceInvalidate();
    }

    public void setValueTextSize(int size) {
        valueTextSize = size;
        forceInvalidate();
    }

    public void setDespText(String despString) {
        despText = despString;
        forceInvalidate();
    }

    public void setDespTextSize(int despTextSize) {
        this.despTextSize = despTextSize;
        forceInvalidate();
    }

    public void setDespTextColor(int despTextColor) {
        this.despTextColor = despTextColor;
        forceInvalidate();
    }

    public void setValueTextIsDisplayable(boolean valueTextIsDisplayable) {
        this.valueTextIsDisplayable = valueTextIsDisplayable;
        forceInvalidate();
    }

    public void setDespTextIsDisplayable(boolean despTextIsDisplayable) {
        this.despTextIsDisplayable = despTextIsDisplayable;
        forceInvalidate();
    }

    public void setRoundColor(int roundColor) {
        this.roundColor = roundColor;
        forceInvalidate();
    }

    public void setRoundProgressColor(int roundProgressColor) {
        this.roundProgressColor = roundProgressColor;
        forceInvalidate();
    }


    public void setCircleStrokeWidth(int width) {
        mCirclePaint.setStrokeWidth(width);
        mArcPaint.setStrokeWidth(width);
        forceInvalidate();
    }

    public void setCircleStyle(int circleStyle) {
        this.circleStyle = circleStyle;
        forceInvalidate();
    }

    public void setSweepAngle(float sweepAngle) {
        this.sweepAngle = sweepAngle;
        forceInvalidate();
    }

    public void setInterpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
    }

    public void setAnimDuration(int animDuration) {
        this.animDuration = animDuration;
    }

    public void forceInvalidate() {
        this.invalidate();
    }

    public void setSweepValue(float sweepValue) {
        if (sweepValue > 0) {
            this.sweepValue = sweepValue;
        } else {
            sweepValue = 25;
        }
        sweepAngle = value2Angle(sweepValue);
        this.invalidate();
    }

    private float value2Angle(float value) {
        return (value / max * 360f);
    }
}
