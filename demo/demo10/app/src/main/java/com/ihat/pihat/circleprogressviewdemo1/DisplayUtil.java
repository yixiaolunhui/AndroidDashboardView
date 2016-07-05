package com.ihat.pihat.circleprogressviewdemo1;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Project: CircleProgressViewDemo1
 * Package: com.ihat.pihat.circleprogressviewdemo1
 * Author: Guoger
 * Mail: 505917699@qq.com
 * Created time: 2016/4/18/16 11:29.
 */
public class DisplayUtil {
    public static float getDensity() {
        float density;
        WindowManager wm = (WindowManager) App.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        density = dm.density;
        return density;
    }
}
