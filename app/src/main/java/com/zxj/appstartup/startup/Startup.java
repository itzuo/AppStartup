package com.zxj.appstartup.startup;

import android.content.Context;

import com.zxj.appstartup.Dispatcher;

import java.util.List;

/**
 * @Author zuojie
 * @Date 2022/1/26
 */
public interface Startup<T> extends Dispatcher {

    /**
     * 执行初始化任务
      */
    T create(Context context);

    /**
     * 本任务依赖哪些任务
     */
    List<Class<? extends Startup<?>>> dependencies();

    /**
     * 依赖任务的个数(入度数)
      */
    int getDependenciesCount();
}
