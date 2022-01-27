package com.zxj.appstartup.manage;

import android.content.Context;
import android.os.Looper;

import com.zxj.appstartup.LogUtils;
import com.zxj.appstartup.sort.TopologySort;
import com.zxj.appstartup.startup.AndroidStartup;
import com.zxj.appstartup.startup.Result;
import com.zxj.appstartup.startup.Startup;
import com.zxj.appstartup.startup.StartupRunnable;
import com.zxj.appstartup.startup.StartupSortStore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author zuojie
 * @Date 2022/1/26
 */
public class StartupManager {

    private Context context;
    private List<Startup<?>> startupList;
    private StartupSortStore startupSortStore;
    private CountDownLatch awaitCountDownLatch;

    public StartupManager(Context context, List<Startup<?>> startupList,CountDownLatch awaitCountDownLatch) {
        this.context = context;
        this.startupList = startupList;
        this.awaitCountDownLatch = awaitCountDownLatch;
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

    public void await(){
        try {
            awaitCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void notifyChildren(Startup<?> startup) {
        if(!startup.callCreateOnMainThread() && startup.waitOnMainThread()) {
            awaitCountDownLatch.countDown();
        }
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
        private List<Startup<?>> startupList = new ArrayList<>();

        public Builder addStartup(AndroidStartup<?> startup) {
            startupList.add(startup);
            return this;
        }

        public Builder addAllStartup(List<Startup<?>> startups) {
            startupList.addAll(startups);
            return this;
        }

        public StartupManager build(Context context) {
            AtomicInteger needAwaitCount = new AtomicInteger();
            for (Startup<?> startup : startupList) {
                //记录需要主线程等待完成的异步任务
                if (!startup.callCreateOnMainThread() && startup.waitOnMainThread()) {
                    needAwaitCount.incrementAndGet();
                }
            }
            CountDownLatch awaitCountDownLatch = new CountDownLatch(needAwaitCount.get());
            return new StartupManager(context, startupList,awaitCountDownLatch);
        }
    }
}
