package com.zxj.appstartup.manage;

import android.content.Context;
import android.os.Looper;

import com.zxj.appstartup.sort.TopologySort;
import com.zxj.appstartup.startup.AndroidStartup;
import com.zxj.appstartup.startup.Result;
import com.zxj.appstartup.startup.Startup;
import com.zxj.appstartup.startup.StartupRunnable;
import com.zxj.appstartup.startup.StartupSortStore;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author zuojie
 * @Date 2022/1/26
 */
public class StartupManager {

    private Context context;
    private List<AndroidStartup<?>> startupList;
    private StartupSortStore startupSortStore;

    public StartupManager(Context context, List<AndroidStartup<?>> startupList) {
        this.context = context;
        this.startupList = startupList;
    }

    public StartupManager start() {
        if(Looper.myLooper() != Looper.getMainLooper()){
            throw new RuntimeException("请在主线程调用！");
        }
        startupSortStore = TopologySort.sort(startupList);
        for (Startup<?> startup : startupSortStore.getResult()) {
            StartupRunnable startupRunnable = new StartupRunnable(context,startup,this);
            if(startup.callCreateOnMainThread()){
                startupRunnable.run();
            }else {
                startup.executor().execute(startupRunnable);
            }
        }
        return this;
    }

    public void notifyChildren(Startup<?> startup) {
        //获得已经完成的当前任务的所有子任务
        if(startupSortStore.getStartupChildrenMap().containsKey(startup.getClass())){
            List<Class<? extends Startup>> childStartupCls = startupSortStore.getStartupChildrenMap().get(startup.getClass());
            for (Class<? extends Startup> cls : childStartupCls) {
                // 通知子任务 startup父任务已完成
                Startup<?> childStartup = startupSortStore.getStartupMap().get(cls);
                childStartup.toNotify();
            }
        }
    }

    public static class Builder{
        private List<AndroidStartup<?>> startupList = new ArrayList<>();

        public Builder addStartup(AndroidStartup<?> startup) {
            startupList.add(startup);
            return this;
        }

        public Builder addAllStartup(List<AndroidStartup<?>> startups) {
            startupList.addAll(startups);
            return this;
        }

        public StartupManager build(Context context) {
            return new StartupManager(context, startupList);
        }
    }
}
