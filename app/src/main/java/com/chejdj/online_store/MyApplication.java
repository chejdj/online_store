package com.chejdj.online_store;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import cn.bmob.v3.Bmob;

/**
 * <pre>
 *     author : chejdj
 *     e-mail : yangyang.zhu96@gmail.com
 *     time   : 2018/06/18
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class MyApplication extends Application {
    private RefWatcher watcher;
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this,"371703b45a8a6023b5ee8cb78527dd8b");
        watcher=LeakCanary.install(this);
    }
    public static  RefWatcher getRefWatcher(Context context){
        MyApplication application=(MyApplication)context.getApplicationContext();
        return application.watcher;
    }
}
