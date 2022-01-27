package com.zxj.appstartup.tasks;

import android.content.Context;
import android.os.SystemClock;

import androidx.annotation.Nullable;

import com.zxj.appstartup.LogUtils;
import com.zxj.appstartup.startup.AndroidStartup;
import com.zxj.appstartup.startup.Startup;

import java.util.ArrayList;
import java.util.List;

public class Task3 extends AndroidStartup<Void> {

    @Override
    public Void create(Context context) {
        LogUtils.log("Task3初始化开始");
        SystemClock.sleep(2_000);
        LogUtils.log("Task3初始化结束");
        return null;
    }


    //    执行此任务需要依赖哪些任务
    @Nullable
    @Override
    public List<Class<? extends Startup<?>>> dependencies() {
        List<Class<? extends Startup<?>>> depends = new ArrayList<>();
        //本任务依赖于任务1
        depends.add(Task1.class);
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