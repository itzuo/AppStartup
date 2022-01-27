package com.zxj.appstartup;

import android.app.Application;

import com.zxj.appstartup.manage.StartupManager;
import com.zxj.appstartup.tasks.Task1;
import com.zxj.appstartup.tasks.Task2;
import com.zxj.appstartup.tasks.Task3;
import com.zxj.appstartup.tasks.Task4;
import com.zxj.appstartup.tasks.Task5;

/**
 * @Author zuojie
 * @Date 2022/1/26
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.log("onCreate->start");
        /*new StartupManager.Builder()
                .addStartup(new Task5())
                .addStartup(new Task4())
                .addStartup(new Task3())
                .addStartup(new Task2())
                .addStartup(new Task1())
                .build(this)
                .start().await();*/
        LogUtils.log("onCreate->end");
    }
}
