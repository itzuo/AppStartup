package com.zxj.appstartup.tasks;

import android.content.Context;
import android.os.SystemClock;

import com.zxj.appstartup.LogUtils;
import com.zxj.appstartup.startup.AndroidStartup;
import com.zxj.appstartup.startup.Startup;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author zuojie
 * @Date 2022/1/26
 */
public class Task2 extends AndroidStartup<Void> {

    private List<Class<? extends Startup<?>>> depends;

    public Task2(){
        depends = new ArrayList<>();
        //本任务依赖于任务1
        depends.add(Task1.class);
    }

    @Override
    public Void create(Context context) {
        LogUtils.log("Task2初始化开始");
        SystemClock.sleep(800);
        LogUtils.log("Task2初始化结束");
        return null;
    }

    @Override
    public List<Class<? extends Startup<?>>> dependencies() {
        return depends;
    }
}
