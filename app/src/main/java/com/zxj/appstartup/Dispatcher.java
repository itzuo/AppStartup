package com.zxj.appstartup;

import java.util.concurrent.Executor;

public interface Dispatcher {
    /**
     * 是否在主线程执行
     */
    boolean callCreateOnMainThread();

    /**
     * 是否需要等待该任务执行完成
     * 只有callCreateOnMainThread方法返回false的时候才有意义
     */
    boolean waitOnMainThread();

    /**
     * 等待
     */
    void toWait();

    /**
     * 有父任务执行完毕
     * 计数器-1
     */
    void toNotify();

    /**
     * 若在子线程执行，则指定线程池
     */
    Executor executor();

    /**
     * 线程优先级
     */
    int getThreadPriority();
}
