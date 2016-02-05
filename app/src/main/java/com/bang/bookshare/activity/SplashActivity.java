package com.bang.bookshare.activity;

import android.os.Bundle;
import android.os.Handler;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.bang.bookshare.R;
import com.bang.bookshare.application.App;
import com.bang.bookshare.entity.User;
import com.bang.bookshare.utils.ActivityStack;
import com.bang.bookshare.utils.ConstantUtil;
import com.bang.bookshare.utils.HttpPathUtil;
import com.bang.bookshare.utils.LogUtil;
import com.bang.bookshare.utils.PreferencesUtils;
import com.bang.bookshare.volley.VolleyHandler;
import com.bang.bookshare.volley.VolleyHttpRequest;

import java.util.HashMap;

/**
 * 打开APP滑动页面
 *
 * @author Bang
 * @file com.bang.bookshare.activity
 * @date 2016/1/31
 * @Version 1.0
 */
public class SplashActivity extends FrameActivity {

    /**
     * 记录是否第一次启动
     */
    private boolean mIsFirstIn;
    private String mLoginPhone;
    private String mPassWord;
    public VolleyHandler<String> volleyRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ActivityStack.getInstance().addActivity(this);

        /**
         * 初始化数据
         */
        initVariable();

        /**
         * Handler的postDelayed方法实现"画面停留2-3秒",即：定时器
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switchActivity();
            }
        }, ConstantUtil.SPLASH_DELAY_MS);
    }

    /**
     * 初始化数据
     */
    private void initVariable() {
        mPassWord = PreferencesUtils.getPassWord(this);
        mLoginPhone = PreferencesUtils.getLoginPhone(this);
        mIsFirstIn = PreferencesUtils.getIsFirst(this);
    }

    /**
     * 判断选择跳转Activity
     */
    private void switchActivity() {
        // 判断程序与第几次运行
        if (mIsFirstIn) {
            volleyRequest = new VolleyHandler<String>() {

                @Override
                public void reqSuccess(String response) {
                    if (response == null) {
                        showMsg(getString(R.string.msg_loading_error));
                    } else {
                        // 解析数据
                        User user = User.praseJson(response);
                        // 登录业务判断
                        if (user.isSuccess()) {
                            // 当前用户聊天登录LearnCloud服务器
                            openActivityFn(MainActivity.class);
                            showMsg(user.getMessage());
                        } else {
                            openActivityFn(LoginActivity.class);
                            showMsg(user.getMessage());
                        }
                    }
                }

                @Override
                public void reqError(String error) {
                    openActivityFn(LoginActivity.class);
                }
            };

            // Post请求键值对
            HashMap<String, String> params = new HashMap<>();
            params.put("userId", mLoginPhone);
            params.put("passWord", mPassWord);
            // 请求网络
            VolleyHttpRequest.String_request(HttpPathUtil.getLoginIfo(), params, volleyRequest);
        } else {
            openActivityFn(GuideActivity.class);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        App.getQueue().cancelAll(ConstantUtil.TAG_STRING_REQUEST);
    }

    @Override
    public void onDestroy() {
        ActivityStack.getInstance().removeActivity(this);
        super.onDestroy();
    }
}
