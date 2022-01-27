package com.zxj.appstartup.tasks;

import android.content.Context;
import android.os.Looper;
import android.os.SystemClock;

import com.zxj.appstartup.LogUtils;
import com.zxj.appstartup.startup.AndroidStartup;
import com.zxj.appstartup.startup.Startup;

import java.util.List;

/**
 * @Author zuojie
 * @Date 2022/1/26
 */
public class Task1 extends AndroidStartup<String> {

    @Override
    public String create(Context context) {
        String t = Looper.myLooper() == Looper.getMainLooper()
                ? "主线程: " : "子线程: ";
        LogUtils.log(t+"Task1初始化开始");
        SystemClock.sleep(3_000);
        LogUtils.log(t+"Task1初始化结束");
        return null;
    }

    @Override
    public List<Class<? extends Startup<?>>> dependencies() {
        return super.dependencies();
    }

    @Override
    public boolean callCreateOnMainThread() {
        return false;
    }

    @Override
    public boolean waitOnMainThread() {
        return false;
    }
}
