package com.bang.bookshare.activity;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bang.bookshare.R;
import com.bang.bookshare.activity.FrameActivity;
import com.bang.bookshare.application.App;
import com.bang.bookshare.entity.User;
import com.bang.bookshare.utils.ActivityStack;
import com.bang.bookshare.utils.ConstantUtil;
import com.bang.bookshare.utils.HttpPathUtil;
import com.bang.bookshare.utils.LogUtil;
import com.bang.bookshare.utils.PreferencesUtils;
import com.bang.bookshare.utils.RegularUtil;
import com.bang.bookshare.view.CustomHanzTV;
import com.bang.bookshare.volley.VolleyHandler;
import com.bang.bookshare.volley.VolleyHttpRequest;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 注册页面
 *
 * @author Bang
 * @file com.bang.bookshare.activity
 * @date 2016/1/31
 * @Version 1.0
 */
public class RegisterActivity extends FrameActivity {

    @InjectView(R.id.et_phone)
    EditText etPhone;
    @InjectView(R.id.et_password)
    EditText etPassWord;
    @InjectView(R.id.et_password_next)
    EditText etPassWordNext;
    @InjectView(R.id.tv_title)
    CustomHanzTV tvTitle;

    private String mLoginPhone;
    private String mPassWord;
    private String mPassWordNext;
    private View mLocalView;
    public Context context;
    public VolleyHandler<String> volleyRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);
        ActivityStack.getInstance().addActivity(this);
        /**
         * 初始化变量
         */
        initVariable();
    }

    /**
     * 初始化变量
     */
    private void initVariable() {
        mPassWord = null;
        mLoginPhone = null;
        context = RegisterActivity.this;
        tvTitle.setText(R.string.title_register);
        // 获取到根View
        mLocalView = getWindow().getDecorView();
    }

    /**
     * 监听实现
     */
    @OnClick({R.id.iv_back, R.id.btn_register, R.id.tv_book2})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_back:
                // 注册
                finish();
                break;

            case R.id.btn_register:
                // 用户注册登录请求
                registerAndLogin(view);
                break;

            case R.id.tv_book2:
//                openActivity(UserAgreeActivity.class);
                break;

            default:
                break;
        }
    }

    /**
     * 用户注册并登陆请求
     */
    private void registerAndLogin(final View view) {
        mLoginPhone = etPhone.getText().toString();
        mPassWord = etPassWord.getText().toString();
        mPassWordNext = etPassWordNext.getText().toString();

        volleyRequest = new VolleyHandler<String>() {
            @Override
            public void reqSuccess(String response) {
                if (response == null) {
                    showMsg(getString(R.string.msg_loading_error));
                } else {
                    // 解析数据
                    User user = User.praseJson(response);
                    // 用户注册并登陆业务判断
                    if (user.isSuccess()) {
                        LogUtil.showLog("用户注册并登陆");
                        // 写入登录信息
                        PreferencesUtils.setLoginInfo(context, mLoginPhone, mPassWord);
                        openActivityFn(MainActivity.class);
                    } else {
                        LogUtil.showLog("未能注册并登陆");
                        showMsg(user.getMessage());
                    }
                    // 隐藏对话框
                    loadingWindow.dismiss();
                }
            }

            @Override
            public void reqError(String error) {
                showMsg(getString(R.string.msg_con_server_error));
            }
        };

        // 对手机号码与密码验证
        if (mLoginPhone.isEmpty()) {
            showTipsWindow(mLocalView, getString(R.string.pop_tip_title), getString(R.string.phone_null));
        } else if (!RegularUtil.isMobileNO(mLoginPhone)) {
            showTipsWindow(mLocalView, getString(R.string.pop_tip_title), getString(R.string.pop_tip_content));
        } else if (mPassWord.isEmpty()) {
            showTipsWindow(mLocalView, getString(R.string.pop_tip_title), getString(R.string.pass_null));
        } else if (mPassWordNext.isEmpty()) {
            showTipsWindow(mLocalView, getString(R.string.pop_tip_title), getString(R.string.pass_null));
        } else if (RegularUtil.isChineseNo(mPassWord)) {
            showTipsWindow(mLocalView, getString(R.string.pop_tip_title), getString(R.string.pass_error));
        } else if (!mPassWord.equals(mPassWordNext)) {
            showTipsWindow(mLocalView, getString(R.string.pop_tip_title), getString(R.string.pass_not_equal));
        } else {
            // Post请求键值对
            HashMap<String, String> params = new HashMap<>();
            params.put("userId", mLoginPhone);
            params.put("passWord", mPassWord);
            // 对话框提示注册登录中
            showLoadingWindow(view);
            // 请求网络
            VolleyHttpRequest.String_request(HttpPathUtil.getRegisterAndLoginIfo(), params, volleyRequest);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        ActivityStack.getInstance().removeActivity(this);
        super.onDestroy();
    }
}
