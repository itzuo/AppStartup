package com.zxj.appstartup.startup;

import android.os.Process;

import com.zxj.appstartup.manage.ExecutorManager;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/**
 * @Author zuojie
 * @Date 2022/1/26
 */
public abstract class AndroidStartup<T> implements Startup<T>{
    // 根据入度数(依赖任务的个数)创建闭锁
    private CountDownLatch mWaitCountDown = new CountDownLatch(getDependenciesCount());

    @Override
    public List<Class<? extends Startup<?>>> dependencies() {
        return null;
    }

    @Override
    public int getDependenciesCount() {
        List<Class<? extends Startup<?>>> dependencies = dependencies();
        return dependencies == null ? 0 :dependencies.size();
    }

    @Override
    public Executor executor() {
        return ExecutorManager.ioExecutor;
    }

    //执行此任务时，调用toWait
    @Override
    public void toWait() {
        try {
            mWaitCountDown.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 当前无依赖的任务执行完成后，需要调用本任务的toNotify
    @Override
    public void toNotify() {
        mWaitCountDown.countDown();
    }

    @Override
    public int getThreadPriority() {
        return Process.THREAD_PRIORITY_DEFAULT;
    }
}
