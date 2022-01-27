package com.zxj.appstartup.manage;

import android.content.Context;
import android.os.Looper;

import com.zxj.appstartup.sort.TopologySort;
import com.zxj.appstartup.startup.AndroidStartup;
import com.zxj.appstartup.startup.Result;
import com.zxj.appstartup.startup.Startup;
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

    public StartupManager(Context context, List<AndroidStartup<?>> startupList) {
        this.context = context;
        this.startupList = startupList;
    }

    public StartupManager start() {
        if(Looper.myLooper() != Looper.getMainLooper()){
            throw new RuntimeException("请在主线程调用！");
        }
        StartupSortStore startupSortStore = TopologySort.sort(startupList);
        for (Startup<?> startup : startupSortStore.getResult()) {
            Object result = startup.create(context);
            StartupCacheManager.getInstance().saveInitializedComponent(startup.getClass(),new Result(result));
        }
        return this;
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
