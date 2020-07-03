package com.task.parenttechnicaltask;

import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import org.koin.android.java.KoinAndroidApplication;
import org.koin.core.KoinApplication;
import org.koin.core.context.GlobalContext;

import static com.tests.newandroid.DI.AppModuleKt.appModule;
import static org.koin.core.context.ContextFunctionsKt.startKoin;

public class AppClass extends MultiDexApplication {
    private static AppClass appClass;

    @Override
    public void onCreate() {
        super.onCreate();
        appClass = this;
        KoinApplication koin = KoinAndroidApplication.create(this)
                .modules(appModule);
        startKoin(new GlobalContext(), koin);
    }

    public static AppClass getInstance() {
        return appClass;

    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
