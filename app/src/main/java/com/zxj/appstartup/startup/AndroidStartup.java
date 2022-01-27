package com.zxj.appstartup.startup;

import java.util.List;

/**
 * @Author zuojie
 * @Date 2022/1/26
 */
public abstract class AndroidStartup<T> implements Startup<T>{

    @Override
    public List<Class<? extends Startup<?>>> dependencies() {
        return null;
    }

    @Override
    public int getDependenciesCount() {
        List<Class<? extends Startup<?>>> dependencies = dependencies();
        return dependencies == null ? 0 :dependencies.size();
    }
}
