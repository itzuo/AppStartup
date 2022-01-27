package com.zxj.appstartup.tasks;

import android.content.Context;
import android.os.SystemClock;

import com.zxj.appstartup.LogUtils;
import com.zxj.appstartup.startup.AndroidStartup;
import com.zxj.appstartup.startup.Startup;

import java.util.ArrayList;
import java.util.List;

public class Task4 extends AndroidStartup<Void> {

    private List<Class<? extends Startup<?>>> depends;

    public Task4() {
        depends = new ArrayList<>();
        depends.add(Task2.class);
    }

    @Override
    public Void create(Context context) {
        LogUtils.log("Task4初始化开始");
        SystemClock.sleep(1_000);
        LogUtils.log("Task4初始化结束");
        return null;
    }


    @Override
    public List<Class<? extends Startup<?>>> dependencies() {
        return depends;
    }
}
