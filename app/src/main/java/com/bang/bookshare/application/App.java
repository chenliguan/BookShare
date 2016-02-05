package com.bang.bookshare.application;

import android.app.Application;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.bang.bookshare.utils.LogUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

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

    /**
     * 消息接收Handler
     */
    public static class CustomMessageHandler extends AVIMMessageHandler {
        //接收到消息后的处理逻辑
        @Override
        public void onMessage(AVIMMessage message,AVIMConversation conversation,AVIMClient client){
            if(message instanceof AVIMTextMessage){
                // 显示出信息
                LogUtil.showLog(((AVIMTextMessage) message).getText() + "  " + conversation.getConversationId());

                // 建立全局键值对，以会话id为键，message的list为值。
                // 登陆后，获取当前用户的实例imClient，获取会话判断是否有会话消息，有就继续该会话，没就新建会话
                // 或者笨拙些，只有两个客户端登录后才能对话

                //查询对话成员有 Bob 和 Jerry的Conversation
//                query.withMembers(Arrays.as("Bob", "Jerry"));

                //根据会话Id查询会话
//                AVIMConversation conv = client.getConversation("551260efe4b01608686c3e0f");

                // 在MSG添加会话Id的属性，用于判断是哪一个会话，添加对应的list

                // 需要对方登陆后才有信息返回

                // 当前的在MainActivity登录

                // UI可以借鉴UIWork这个项目
            }
        }

        public void onMessageReceipt(AVIMMessage message,AVIMConversation conversation,AVIMClient client){

        }
    }

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
        /**
         * 聊天通讯配置
         */
        AVOSCloud.initialize(this, "gzAajXnLVPntKBvOGUuayKHX-gzGzoHsz", "Oiau9hY0dTisTKrYMevIxzo9");
        /**
         * 注册默认的消息处理逻辑
         */
        AVIMMessageManager.registerDefaultMessageHandler(new CustomMessageHandler());

//        jerryReceiveMsgFromTom();
    }

    //入口
    public static RequestQueue getQueue() {
        return queue;
    }

//    public static void jerryReceiveMsgFromTom(){
//        //Jerry登录
//        AVIMClient jerry = AVIMClient.getInstance("11111111111");
//        jerry.open(new AVIMClientCallback(){
//
//            @Override
//            public void done(AVIMClient client,AVIMException e){
//                if(e==null){
//                    //登录成功后的逻辑
//                    LogUtil.showLog("11111111111登录成功");
//                }
//            }
//        });
//    }

}
