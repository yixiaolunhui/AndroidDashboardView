package com.wingsofts.panelview;

import android.content.Context;
import android.util.TypedValue;

/**
 * 用于px和  dp，sp的转换工具
 * Created by Administrator on 2015/12/16.
 */
public class PxUtils {

    public static int dpToPx(int dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static int spToPx(int sp,Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }


}
