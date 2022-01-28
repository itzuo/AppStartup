package com.zxj.appstartup.run;

import android.content.Context;
import android.os.Process;

import com.zxj.appstartup.manage.StartupCacheManager;
import com.zxj.appstartup.manage.StartupManager;
import com.zxj.appstartup.startup.Result;
import com.zxj.appstartup.startup.Startup;

/**
 * @Author zuojie
 * @Date 2022/1/27
 */
public class StartupRunnable implements Runnable{

    private StartupManager startupManager;
    private Startup<?> startup;
    private Context context;

    public StartupRunnable(Context context, Startup<?> startup,
                           StartupManager startupManager) {
        this.context = context;
        this.startup = startup;
        this.startupManager = startupManager;
    }

    @Override
    public void run() {
        Process.setThreadPriority(startup.getThreadPriority());
        startup.toWait();
        Object result = startup.create(context);
        StartupCacheManager.getInstance().saveInitializedComponent(startup.getClass(),new Result(result));
        startupManager.notifyChildren(startup);
    }
}
