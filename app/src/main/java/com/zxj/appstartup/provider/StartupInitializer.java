package com.zxj.appstartup.provider;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.text.TextUtils;
import com.zxj.appstartup.startup.Startup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StartupInitializer {
    public static String META_VALUE = "android.startup";

    public static List<Startup<?>> discoverAndInitializ(Context context, String providerName) throws Exception {
        Map<Class<? extends Startup>, Startup<?>> startups = new HashMap<>();
        //获得manifest contentProvider中的meta-data
        ComponentName provider = new ComponentName(context, providerName);
        ProviderInfo providerInfo = context.getPackageManager().getProviderInfo(provider,
                PackageManager.GET_META_DATA);
        //得到manifest中配置的任务
        for (String key : providerInfo.metaData.keySet()) {
            String value = providerInfo.metaData.getString(key);
            if (TextUtils.equals(META_VALUE, value)) {
                Class<?> clazz = Class.forName(key);
                if (Startup.class.isAssignableFrom(clazz)) {
                    doInitialize((Startup<?>) clazz.newInstance(), startups);
                }
            }
        }

        List<Startup<?>> result = new ArrayList<>(startups.values());
        return result;
    }

    private static void doInitialize(Startup<?> startup, Map<Class<? extends Startup>, Startup<?>> startups) throws Exception {
        startups.put(startup.getClass(), startup);
        if (startup.getDependenciesCount() != 0) {
            //遍历父任务
            for (Class<? extends Startup<?>> dependency : startup.dependencies()) {
                doInitialize(dependency.newInstance(), startups);
            }
        }
    }
}
