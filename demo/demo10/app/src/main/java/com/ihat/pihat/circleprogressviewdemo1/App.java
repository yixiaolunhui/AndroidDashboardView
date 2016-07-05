package com.ihat.pihat.circleprogressviewdemo1;

import android.app.Application;
import android.content.Context;

/**
 * Project: CircleProgressViewDemo1
 * Package: com.ihat.pihat.circleprogressviewdemo1
 * Author: Guoger
 * Mail: 505917699@qq.com
 * Created time: 2016/4/18/16 11:22.
 */
public class App extends Application {
    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }
}
