package com.zxj.appstartup.manage;

import com.zxj.appstartup.startup.Result;
import com.zxj.appstartup.startup.Startup;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存类(缓存每个任务执行的结果)
 */
public class StartupCacheManager {
    // 这里的ConcurrentHashMap范型为啥用的是Result类？是为了防止空指针异常，因为ConcurrentHashMap的put方法的key和value都不能为null
    private ConcurrentHashMap<Class<? extends Startup>, Result> mInitializedComponents = new ConcurrentHashMap();

    private static StartupCacheManager mInstance;

    private StartupCacheManager() {

    }

    public static StartupCacheManager getInstance() {
        if (mInstance == null) {
            synchronized (StartupCacheManager.class) {
                if (mInstance == null) {
                    mInstance = new StartupCacheManager();
                }
            }
        }
        return mInstance;
    }

    public void saveInitializedComponent(Class<? extends Startup> zClass, Result result) {
        mInitializedComponents.put(zClass, result);
    }

    public boolean hadInitialized(Class<? extends Startup> zClass) {
        return mInitializedComponents.containsKey(zClass);
    }

    public <T> Result<T> obtainInitializedResult(Class<? extends Startup<T>> zClass) {
        return mInitializedComponents.get(zClass);
    }


    public void remove(Class<? extends Startup> zClass) {
        mInitializedComponents.remove(zClass);
    }

    public void clear() {
        mInitializedComponents.clear();
    }
}
