package com.ldoublem.alpayRing.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lumingmin on 16/5/27.
 */
public class RingView extends View {

    private RingViewAnim mRingViewAnim;
    private int animDuration = 2500;

    private Paint ringPaint;
    private Paint ringProgerssPaint;
    private Paint scalePaint;
    private Paint scalePaint_s;

    private Paint textPaint;

    private Paint progerssPaint;

    private Paint pointPaint;


    private float startAngle = -200f;
    private float sweepAngle = 220f;

    private float pointAngle = -200f;
    private float pointAngle_show = -200f;


    private int sWidthringPaint = 0;
    private int sWidthprogerssPaint = 0;
    private int textPaintSize = 0;
    private int progerssPaintSize = 0;
    private int pointShadowLayer = 0;
    private int pointPaintSize = 0;


    private List<Integer> valueList = new ArrayList<>();
    private List<String> valueNameList = new ArrayList<>();


    private RectF fring;
    private RectF fprogerss;
    private RectF fprogerssPath;
    private Path path;
    private int mValue = 0;
    private String mShowValue = "";
    private OnProgerssChange mOnProgerssChange;
    Path pathPoint=new Path();

    public RingView(Context context) {
        this(context, null);
    }

    public RingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
//        setBackgroundColor(getResources().getColor(android.R.color.transparent));

        mRingViewAnim = new RingViewAnim();
        mRingViewAnim.setDuration(animDuration);


        progerssPaintSize = dip2px(getContext(), 50f);
        textPaintSize = dip2px(getContext(), 8f);
        sWidthringPaint = dip2px(getContext(), 1.5f);
        sWidthprogerssPaint = dip2px(getContext(), 7.0f);
        pointPaintSize = dip2px(getContext(), 2.0f);
        pointShadowLayer = dip2px(getContext(), 2.5f);
        ringPaint = new Paint();
        ringPaint.setAntiAlias(true);
        ringPaint.setStyle(Paint.Style.STROKE);
        ringPaint.setStrokeWidth(sWidthringPaint);
        ringPaint.setColor(Color.rgb(146, 204, 255));


        ringProgerssPaint = new Paint();
        ringProgerssPaint.setAntiAlias(true);
        ringProgerssPaint.setStyle(Paint.Style.STROKE);
        ringProgerssPaint.setStrokeWidth(sWidthprogerssPaint);
        ringProgerssPaint.setColor(Color.argb(66, 146, 204, 255));


        scalePaint = new Paint();
        scalePaint.setAntiAlias(true);
        scalePaint.setStyle(Paint.Style.STROKE);
        scalePaint.setStrokeWidth(sWidthprogerssPaint);
        scalePaint.setColor(Color.argb(150, 146, 204, 255));


        scalePaint_s = new Paint();
        scalePaint_s.setAntiAlias(true);
        scalePaint_s.setStyle(Paint.Style.STROKE);
        scalePaint_s.setStrokeWidth(sWidthprogerssPaint / 2);
        scalePaint_s.setColor(Color.argb(85, 146, 204, 255));


        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.argb(180, 146, 204, 255));
        textPaint.setTextSize(textPaintSize);


        progerssPaint = new Paint();
        progerssPaint.setAntiAlias(true);
        progerssPaint.setStyle(Paint.Style.FILL);
        progerssPaint.setColor(Color.WHITE);
        progerssPaint.setTextSize(progerssPaintSize);


        pointPaint = new Paint();
        pointPaint.setAntiAlias(true);
        pointPaint.setStyle(Paint.Style.FILL);
        pointPaint.setColor(Color.WHITE);


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        if (fring == null)
            fring = new RectF(sWidthringPaint + pointShadowLayer, sWidthringPaint + pointShadowLayer,
                    getMeasuredWidth() - sWidthringPaint - pointShadowLayer, getMeasuredWidth() - sWidthringPaint - pointShadowLayer);
        if (fprogerss == null)
            fprogerss = new RectF(sWidthprogerssPaint * 2, sWidthprogerssPaint * 2, getMeasuredWidth() - sWidthprogerssPaint * 2, getMeasuredWidth() - sWidthprogerssPaint * 2);

        if (fprogerssPath == null)
            fprogerssPath = new RectF(sWidthprogerssPaint * 2 + sWidthprogerssPaint,
                    sWidthprogerssPaint * 2 + sWidthprogerssPaint,
                    getMeasuredWidth() - sWidthprogerssPaint * 2 - sWidthprogerssPaint,
                    getMeasuredWidth() - sWidthprogerssPaint * 2 - sWidthprogerssPaint);
        if (path == null) {
            path = new Path();
            path.addOval(fprogerssPath, Path.Direction.CW);
        }
        canvas.drawArc(fring, startAngle, sweepAngle, false, ringPaint);
        canvas.drawArc(fprogerss, startAngle, sweepAngle, false, ringProgerssPaint);



        for (int i = 0; i < valueList.size(); i++) {
            float unit = sweepAngle / (valueList.size() - 1);
            float startAngleBig = startAngle + unit * i;
            float x = (float) (getMeasuredWidth() / 2 + (fprogerss.width() / 2 - sWidthprogerssPaint) * Math.cos(startAngleBig * Math.PI / 180));
            float y = (float) (getMeasuredWidth() / 2 + (fprogerss.width() / 2 - sWidthprogerssPaint) * Math.sin(startAngleBig * Math.PI / 180));
            canvas.drawArc(fprogerss, startAngleBig, 0.5f, false, scalePaint);
            String value = valueList.get(i) + "";
            textPaint.setColor(Color.rgb(146, 204, 255));
            canvas.drawTextOnPath(value, path,
                    (float) (Math.PI * fprogerssPath.width() * ((startAngleBig + 360) % 360) / 360) - getFontlength(textPaint, value) / 2,
                    getFontHeight(textPaint) / 2, textPaint);
            if (i <= valueNameList.size() - 1) {
                textPaint.setColor(Color.argb(180, 146, 204, 255));

                String name = valueNameList.get(i);
                canvas.drawTextOnPath(name, path,
                        (float) (Math.PI * fprogerssPath.width() * ((startAngleBig + unit / 2 + 360) % 360) / 360) - getFontlength(textPaint, name) / 2,
                        getFontHeight(textPaint) / 2, textPaint);

            }
            if (i < valueList.size() - 1) {

                float unitSmall = unit / 6;
                for (int j = 1; j < 6; j++) {

                    canvas.drawArc(fprogerss, startAngleBig + unitSmall * j, 0.2f, false, scalePaint_s);

                }
            }


        }

        if (!isPointer) {

            float xPoint = (float) (getMeasuredWidth() / 2 + (fring.width() / 2) * Math.cos(pointAngle_show * Math.PI / 180));
            float yPoint = (float) (getMeasuredWidth() / 2 + (fring.width() / 2) * Math.sin(pointAngle_show * Math.PI / 180));
//            pointPaint.setShadowLayer(pointShadowLayer, 0, 0, Color.WHITE);//设置阴影
            pointPaint.setColor(Color.argb(80,255,255,255));

            canvas.drawCircle(xPoint, yPoint, pointPaintSize+8, pointPaint);
            pointPaint.setColor(Color.WHITE);


            canvas.drawCircle(xPoint, yPoint, pointPaintSize, pointPaint);

            progerssPaint.setTextSize(progerssPaintSize);


            canvas.drawText(mShowValue, getMeasuredWidth() / 2 - getFontlength(progerssPaint, mShowValue) / 2, getMeasuredWidth() / 2, progerssPaint);
        } else {

            float xPoint = (float) (getMeasuredWidth() / 2 + (fring.width() / 3) * Math.cos(pointAngle_show * Math.PI / 180));
            float yPoint = (float) (getMeasuredWidth() / 2 + (fring.width() / 3) * Math.sin(pointAngle_show * Math.PI / 180));

            canvas.drawCircle(getMeasuredWidth() / 2,getMeasuredWidth()/2,dip2px(getContext(),5f),pointPaint);
            canvas.drawCircle(getMeasuredWidth() / 2,getMeasuredWidth()/2,dip2px(getContext(),8f),pointPaint);





            float xPoint2 = (float) (dip2px(getContext(),5f) * Math.cos((pointAngle_show+90) * Math.PI / 180));
            float yPoint2 = (float) (dip2px(getContext(),5f)* Math.sin((pointAngle_show +90)* Math.PI / 180));


            float xPoint3 = (float) (dip2px(getContext(),5f) * Math.cos((pointAngle_show-90) * Math.PI / 180));
            float yPoint3 = (float) (dip2px(getContext(),5f) * Math.sin((pointAngle_show-90) * Math.PI / 180));


            pathPoint.reset();
            pathPoint.moveTo(xPoint,yPoint);

            pathPoint.lineTo(getMeasuredWidth() / 2-xPoint2,getMeasuredWidth() / 2-yPoint2);

            pathPoint.lineTo(getMeasuredWidth() / 2-xPoint3,getMeasuredWidth() / 2-yPoint3);
            pathPoint.close();
            canvas.drawPath(pathPoint,pointPaint);
            progerssPaint.setTextSize(progerssPaintSize*2/3);

            canvas.drawText(mShowValue, getMeasuredWidth() / 2 - getFontlength(progerssPaint, mShowValue) / 2,
                    getMeasuredWidth() / 2+getFontHeight(progerssPaint)*1.5f, progerssPaint);

            pointPaint.setColor(Color.argb(80,255,255,255));
            canvas.drawCircle(getMeasuredWidth() / 2,getMeasuredWidth()/2,dip2px(getContext(),12f),pointPaint);
            pointPaint.setColor(Color.WHITE);


        }
        canvas.restore();

    }


    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public float getFontlength(Paint paint, String str) {
        return paint.measureText(str);
    }

    public float getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return fm.descent - fm.ascent;
    }


    public void setValue(int value, OnProgerssChange onProgerssChange, int duration) {

        this.animDuration = duration;
        this.mValue = value;
        this.mOnProgerssChange = onProgerssChange;
        if (mRingViewAnim != null)
            clearAnimation();

        mRingViewAnim.setDuration(animDuration);

        startAnimation(mRingViewAnim);


    }


    public void setValueList(List<Integer> list) {
        valueList.clear();
        valueList.addAll(list);

    }

    public void setValueNameList(List<String> list) {
        valueNameList.clear();
        valueNameList.addAll(list);
    }


    private class RingViewAnim extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            if (valueList.size() > 0) {
                pointAngle_show = startAngle + interpolatedTime * sweepAngle / (valueList.get(valueList.size() - 1) - valueList.get(0)) * (mValue - valueList.get(0));
                mShowValue = String.valueOf((int) (valueList.get(0) + (mValue - valueList.get(0)) * interpolatedTime));
                invalidate();
                if (mOnProgerssChange != null)
                    mOnProgerssChange.OnProgerssChange(interpolatedTime);
            }
        }
    }

    public interface OnProgerssChange {
        void OnProgerssChange(float interpolatedTime);
    }

    boolean isPointer = false;

    public void setPointer(boolean pointer) {
        this.isPointer = pointer;
    }


}
