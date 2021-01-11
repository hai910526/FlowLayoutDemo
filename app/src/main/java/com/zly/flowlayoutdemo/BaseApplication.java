package com.zly.flowlayoutdemo;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

/**
 * Cerated by xiaoyehai
 * Create date : 2019/11/8 16:56
 * description :
 */
public class BaseApplication extends Application {

    private static Context mContext;
    private static Handler mHandler;
    private static int mMainThreadId;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();

        mHandler = new Handler();

        //获取当前线程的id,此处是主线程id
        mMainThreadId = Process.myTid();

    }

    public static Context getContext() {
        return mContext;
    }

    public static Handler getHandler() {
        return mHandler;
    }

    public static int getMainThreadId() {
        return mMainThreadId;
    }

}

