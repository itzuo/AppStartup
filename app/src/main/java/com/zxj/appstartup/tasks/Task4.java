package com.zxj.appstartup.tasks;

import android.content.Context;
import android.os.Looper;
import android.os.SystemClock;

import com.zxj.appstartup.LogUtils;
import com.zxj.appstartup.startup.AndroidStartup;
import com.zxj.appstartup.startup.Startup;

import java.util.ArrayList;
import java.util.List;

public class Task4 extends AndroidStartup<Void> {

    private static List<Class<? extends Startup<?>>> depends;

    static  {
        depends = new ArrayList<>();
        depends.add(Task2.class);
    }

    @Override
    public Void create(Context context) {
        String t = Looper.myLooper() == Looper.getMainLooper()
                ? "主线程: " : "子线程: ";
        LogUtils.log(t+"Task4初始化开始");
        SystemClock.sleep(1_000);
        LogUtils.log(t+"Task4初始化结束");
        return null;
    }


    @Override
    public List<Class<? extends Startup<?>>> dependencies() {
        return depends;
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
