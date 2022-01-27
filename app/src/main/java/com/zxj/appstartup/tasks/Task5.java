package com.zxj.appstartup.tasks;

import android.content.Context;
import android.os.SystemClock;

import com.zxj.appstartup.LogUtils;
import com.zxj.appstartup.startup.AndroidStartup;
import com.zxj.appstartup.startup.Startup;

import java.util.ArrayList;
import java.util.List;

public class Task5 extends AndroidStartup<Void> {

    private List<Class<? extends Startup<?>>> depends;

    public Task5(){
        depends = new ArrayList<>();
        depends.add(Task3.class);
        depends.add(Task4.class);
    }


    @Override
    public Void create(Context context) {
        LogUtils.log("Task5初始化开始");
        SystemClock.sleep(500);
        LogUtils.log("Task5初始化结束");
        return null;
    }

    //执行此任务需要依赖哪些任务
    @Override
    public List<Class<? extends Startup<?>>> dependencies() {
        return depends;
    }

}
