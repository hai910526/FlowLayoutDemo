package com.zly.flowlayoutdemo.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Process;
import android.view.View;

import com.zly.flowlayoutdemo.BaseApplication;

/**
 * Cerated by xiaoyehai
 * Create date : 2021/1/11 15:07
 * description :
 */
public class AppUtils {

    public static Context getContext() {
        return BaseApplication.getContext();
    }

    public static Handler getHandler() {
        return BaseApplication.getHandler();
    }

    public static int getMainThreadId() {
        return BaseApplication.getMainThreadId();
    }


    public static int dp2px(float value) {
        //设备密度
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (value * density + 0.5f);
    }

    public static int px2dp(float px) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5f);
    }

    public static int getScreenWidth() {
        return getContext().getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return getContext().getResources().getDisplayMetrics().heightPixels;
    }

    public static View inflate(int id) {
        return View.inflate(getContext(), id, null);
    }


    public static boolean isRunOnUIThread() {
        //获取当前线程id
        int myTid = Process.myTid();
        if (myTid == getMainThreadId()) {
            return true;
        }
        return false;
    }

    public static void runOnUIThread(Runnable r) {
        if (isRunOnUIThread()) {
            //在主线程，直接运行
            r.run();
        } else {
            //在子线程，借助Handler让其运行在主线程
            getHandler().post(r);
        }
    }
}
