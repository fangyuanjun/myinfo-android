package com.kecq.myinfo;

import android.app.Application;
import android.content.Context;

import fyj.lib.android.CrashHandler;

/**
 * Created by fyj on 2017/12/10.
 */

public class ApplicationEx extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        // 异常处理，不需要处理时注释掉下面即可！
        //CrashHandler crashHandler = CrashHandler.getInstance();
        // 注册crashHandler
        //crashHandler.init(getApplicationContext());
        // 发送以前没发送的报告(可选)
        //crashHandler.sendPreviousReportsToServer();
    }


    public static Context getContext(){
        return context;
    }
}
