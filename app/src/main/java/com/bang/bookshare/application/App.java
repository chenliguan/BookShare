package com.bang.bookshare.application;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

/**
 * 全局application，始终只存在一个示例,用于临时保存各种传值
 *
 * @author Bang
 * @file com.bang.bookshare.application
 * @date 2016/1/31
 * @Version 1.0
 */
public class App extends Application {

    private static RequestQueue queue;

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * 建立Volley请求队列(双重校验锁方法)
         */
        if (queue == null) {
            synchronized (App.class) {
                if (queue == null) {
                    queue = Volley.newRequestQueue(getApplicationContext());
                }
            }
        }
    }

    //入口
    public static RequestQueue getQueue() {
        return queue;
    }

}
