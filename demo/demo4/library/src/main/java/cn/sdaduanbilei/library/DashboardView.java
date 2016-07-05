package cn.sdaduanbilei.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


/**
 * Created by scorpio on 14-12-10.
 */
public class DashboardView extends View {

    /**
     * 画笔应用对象
     */
    private Paint paint;

    /**
     * 仪表盘底色
     */
    private int dashColor;

    /**
     * 仪表盘 进度配色
     */
    private int dasProColor;


    /**
     * 仪表板 底圆 宽度
     */
    private float dashWidth;

    /**
     * 仪表板 进度条 宽度
     */
    private float dashProWidth;

    /**
     * 最大值
     */
    private float dashProMax;

    /**
     * 进度
     */
    private float dashProgress;

    /**
     * Dash 图标
     */
    private int dashIcon;

    private String dashTitle;

    /**
     * Dash 文字大小
     */
    private float dashTitleSize;

    /**
     * Dash 文章颜色
     */
    private int dashTitleColor;

    /**
     * Dash style
     */
    private int dashStyle;


    public DashboardView(Context context) {
        this(context, null);
    }

    public DashboardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public DashboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs
                , R.styleable.DashboardView);
        //获取自定义属性和默认值
        dashColor = typedArray.getColor(R.styleable.DashboardView_dashColor, getResources().getColor(R.color.material_blue_grey_800));
        dasProColor = typedArray.getColor(R.styleable.DashboardView_dashProColor, getResources().getColor(R.color.material_blue_grey_800));
        dashWidth = typedArray.getDimension(R.styleable.DashboardView_dashWidth, 10);
        dashProWidth = typedArray.getDimension(R.styleable.DashboardView_dashProWidth, 40);
        dashProMax = typedArray.getFloat(R.styleable.DashboardView_dashProMax, 100);
        dashProgress = typedArray.getFloat(R.styleable.DashboardView_dashProgress, 0);
        dashIcon = typedArray.getResourceId(R.styleable.DashboardView_dashIcon, R.drawable.ic_launcher);
        dashTitleSize = typedArray.getDimension(R.styleable.DashboardView_dashTitleSize, 80);
        dashTitleColor = typedArray.getResourceId(R.styleable.DashboardView_dashTitlColor, getResources().getColor(R.color.material_blue_grey_800));
        dashTitle = typedArray.getNonResourceString(R.styleable.DashboardView_dashTitle);
        dashStyle = typedArray.getInteger(R.styleable.DashboardView_dashStyle, DashBoard.NOMAL);
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 画笔
         */
        paint = new Paint();
        switch (dashStyle) {
            case DashBoard.RING:
                Canvas_Ring(canvas);
                break;
            case DashBoard.NOMAL:
                Canvas_Nomal(canvas);
                break;
        }


    }

    /**
     * Nomal style
     *
     * @param canvas
     */
    private void Canvas_Nomal(Canvas canvas) {

        /**
         * 画一个圆环 底板
         */
        int center = getWidth() / 2;//圆心
        int radius = center - 32; // 圆环半径

        /**
         * 最外层的一个 圆形括弧
         */
        paint.setColor(dashColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dashWidth);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND); // paint  加上圆角

        RectF rectF = new RectF(center - radius, center - radius, center + radius, center + radius);
        canvas.drawArc(rectF, 150, 240, false, paint);


        /**
         *  进度地板颜色
         */
        /**
         * 里面一层的 间隔
         */
        paint.setColor(dashColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dashProWidth);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.BUTT);
//        paint.setStrokeMiter(2);
        // 虚线
        PathEffect pathEffect = new DashPathEffect(new float[]{16, 16, 16, 16}, 4);
        paint.setPathEffect(pathEffect);
        rectF = new RectF(center - radius + dashProWidth, center - radius + dashProWidth, center + radius - dashProWidth, center + radius - dashProWidth);
        canvas.drawArc(rectF, 152, 240, false, paint);

        /**
         * 里面一层的 进度
         */
        double warning = dashProMax * 0.8;
        Log.e("TAG",warning  + "==" +dashProgress);
        if (dashProgress > warning) {
            paint.setColor(getResources().getColor(R.color.lava));
        } else {
            paint.setColor(dasProColor);
        }
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dashProWidth);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.BUTT);
//        paint.setStrokeMiter(2);
        // 虚线
        pathEffect = new DashPathEffect(new float[]{16, 16, 16, 16}, 4);
        paint.setPathEffect(pathEffect);
        if (dashProgress>0) {
            rectF = new RectF(center - radius + dashProWidth, center - radius + dashProWidth, center + radius - dashProWidth, center + radius - dashProWidth);
            canvas.drawArc(rectF, 152, 245 * (dashProgress / dashProMax), false, paint);
        }
        /**
         * 下面的文子
         */
        String str = dashTitle + "";
        paint.setPathEffect(null);
        paint.setStrokeWidth(0);
        paint.setTextSize(dashTitleSize);
        paint.setTypeface(Typeface.SANS_SERIF); //设置字体
        paint.setColor(dashTitleColor);
        // 测量 字体宽度
        float textWidth = paint.measureText(str);
        canvas.drawText(str, center - textWidth / 2, radius * 2, paint);


        /**
         * 画中心的一个点
         */
        paint.setColor(getResources().getColor(R.color.background_floating_material_dark));
        paint.setStyle(Paint.Style.FILL);// 实心
        paint.setAntiAlias(true);
        canvas.drawCircle(center, center, 8, paint);

        /**
         * 中心画一个小圆
         */
        paint.setColor(getResources().getColor(android.R.color.darker_gray));// 设置画笔颜色
        paint.setStyle(Paint.Style.STROKE);// 设置空心
        paint.setStrokeWidth(2);// 圆环宽度
        paint.setAntiAlias(true); // 消除锯齿
        canvas.drawCircle(center, center, radius / 5, paint);//绘制图形

        /**
         * 画一个指针
         */
        paint.setColor(getResources().getColor(R.color.background_floating_material_dark));
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), dashIcon);

        Log.e("TAG", dashIcon + "");
        Matrix matrix = new Matrix();
        if (dashProgress > 0) {
            float rotate = 245 * (dashProgress / dashProMax);
            Log.e("TAG", rotate + "");
            if (dashProgress < 120) {
                matrix.setRotate(-120 + rotate, center , center );
//            }else{
//                matrix.setRotate();
//            }
            }
        } else {
            matrix.setRotate(-120, center , center);
        }
        canvas.concat(matrix);
        canvas.drawBitmap(bitmap, center - bitmap.getWidth() /2, center - bitmap.getHeight()-2, null);
//        invalidate();


    }

    /**
     * Ring style
     *
     * @param canvas
     */
    private void Canvas_Ring(Canvas canvas) {
        /**
         * 画一个圆环 底板
         */
        int center = getWidth() / 2;//圆心
        int radius = center - 32; // 圆环半径
        paint.setColor(dashColor);// 设置画笔颜色
        paint.setStyle(Paint.Style.STROKE);// 设置空心
        paint.setStrokeWidth(dashWidth);// 圆环宽度
        paint.setAntiAlias(true); // 消除锯齿
        canvas.drawCircle(center, center, radius, paint);//绘制图形

        /**
         * 中心点画一个 图标
         */
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), dashIcon);
        // bitmap 宽度
        int bitmap_width = bitmap.getWidth();
        canvas.drawBitmap(bitmap, center - bitmap_width / 2, radius - (dashProWidth * 3), paint);

        /**
         * 中心 画个textview  速度
         *
         */
        String str = dashTitle + "";
        paint.setStrokeWidth(0);
        paint.setTextSize(dashTitleSize);
        paint.setTypeface(Typeface.SANS_SERIF); //设置字体
        paint.setColor(dashTitleColor);
        // 测量 字体宽度
        float textWidth = paint.measureText(str);
        canvas.drawText(str, center - textWidth / 2, center + 80, paint);


        /**
         * 在画一个大圆环 进度条
         */
        // 警告值
        double warning = dashProMax * 0.8;
        if (dashProgress > warning) {
            paint.setColor(getResources().getColor(R.color.lava));
        } else {
            paint.setColor(dasProColor);
        }
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dashProWidth);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND); // paint  加上圆角

        RectF rectF = new RectF(center - radius, center - radius, center + radius, center + radius);
        canvas.drawArc(rectF, 90, 360 * (dashProgress / dashProMax), false, paint);

        /**
         * 画一个进度的岂止点
         */
        paint.setColor(getResources().getColor(R.color.background_floating_material_dark));
        paint.setStyle(Paint.Style.FILL);// 实心
        paint.setAntiAlias(true);
        canvas.drawCircle(center, getHeight() - 32, 10, paint);
    }


    public int getDashColor() {
        return dashColor;
    }

    public void setDashColor(int dashColor) {
        this.dashColor = dashColor;
    }

    public int getDasProColor() {
        return dasProColor;
    }

    public void setDasProColor(int dasProColor) {
        this.dasProColor = dasProColor;
    }

    public float getDashWidth() {
        return dashWidth;
    }

    public void setDashWidth(float dashWidth) {
        this.dashWidth = dashWidth;
    }

    public float getDashProWidth() {
        return dashProWidth;
    }

    public void setDashProWidth(float dashProWidth) {
        this.dashProWidth = dashProWidth;
    }


    public synchronized float getDashProgress() {
        return dashProgress;
    }


    /**
     * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步
     * 刷新界面调用postInvalidate()能在非UI线程刷新
     *
     * @param dashProgress
     */
    public synchronized void setDashProgress(float dashProgress) {
        if (dashProgress > dashProMax) {
            this.dashProgress = dashProMax;
        } else {
            this.dashProgress = dashProgress;
            postInvalidate();
        }
    }

    public int getDashIcon() {
        return dashIcon;
    }

    public void setDashIcon(int dashIcon) {
        this.dashIcon = dashIcon;
    }

    public float getDashTitleSize() {
        return dashTitleSize;
    }

    public void setDashTitleSize(float dashTitleSize) {
        this.dashTitleSize = dashTitleSize;
    }

    public int getDashTitleColor() {
        return dashTitleColor;
    }

    public void setDashTitleColor(int dashTitleColor) {
        this.dashTitleColor = dashTitleColor;
    }

    public float getDashProMax() {
        return dashProMax;
    }

    public void setDashProMax(float dashProMax) {
        this.dashProMax = dashProMax;
    }

    public synchronized String getDashTitle() {
        return dashTitle;
    }

    public synchronized void setDashTitle(String dashTitle) {
        if (!ToolsUtil.isEmpty(dashTitle)) {
            this.dashTitle = dashTitle;
        }
    }

    public int getDashStyle() {
        return dashStyle;
    }

    public void setDashStyle(int dashStyle) {
        this.dashStyle = dashStyle;
    }
}
