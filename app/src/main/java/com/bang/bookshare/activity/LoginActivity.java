package com.bang.bookshare.activity;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
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
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 登录页面
 *
 * @author Bang
 * @file com.bang.bookshare.activity
 * @date 2016/1/31
 * @Version 1.0
 */
public class LoginActivity extends FrameActivity {

    @InjectView(R.id.et_phone)
    EditText etPhone;
    @InjectView(R.id.et_password)
    EditText etPassWord;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.tv_title_right)
    TextView tvRegister;

    private String mLoginPhone;
    private String mPassWord;
    private View mLocalView;
    private long mExitTime;
    public Context context;
    public VolleyHandler<String> volleyRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
        mExitTime = 0;
        mPassWord = null;
        mLoginPhone = null;
        context = LoginActivity.this;
        tvTitle.setText(R.string.title_login);
        tvRegister.setText(R.string.text_register);
        // 获取到根View
        mLocalView = getWindow().getDecorView();
    }

    /**
     * 监听实现
     */
    @OnClick({R.id.tv_title_right, R.id.btn_login, R.id.tv_book2})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tv_title_right:
                // 注册
                openActivity(RegisterActivity.class);
                break;

            case R.id.btn_login:
                // 用户登录请求
                userLogin(view);
                break;

            case R.id.tv_book2:
//                openActivity(UserAgreeActivity.class);
                break;

            default:
                break;
        }
    }

    /**
     * 用户登录请求
     */
    private void userLogin(final View view) {
        mLoginPhone = etPhone.getText().toString();
        mPassWord = etPassWord.getText().toString();

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
                        // 写入登录信息
                        PreferencesUtils.setLoginInfo(context, mLoginPhone, mPassWord);
                        openActivityFn(MainActivity.class);
                        showMsg(user.getMessage());
                    } else {
                        showMsg(user.getMessage());
                    }
                }
                // 隐藏对话框
                loadingWindow.dismiss();
            }

            @Override
            public void reqError(String error) {
                showMsg(getString(R.string.msg_con_server_error));
                // 隐藏对话框
                loadingWindow.dismiss();
            }
        };

        // 对手机号码与验证码验证
        if (mLoginPhone.isEmpty()) {
            showTipsWindow(mLocalView, getString(R.string.pop_tip_title), getString(R.string.phone_null));
        } else if (!RegularUtil.isMobileNO(mLoginPhone)) {
            showTipsWindow(mLocalView, getString(R.string.pop_tip_title), getString(R.string.pop_tip_content));
        } else if (mPassWord.isEmpty()) {
            showTipsWindow(mLocalView, getString(R.string.pop_tip_title), getString(R.string.pass_null));
        } else if (RegularUtil.isChineseNo(mPassWord)) {
            showTipsWindow(mLocalView, getString(R.string.pop_tip_title), getString(R.string.pass_error));
        } else {
            // Post请求键值对
            HashMap<String, String> params = new HashMap<>();
            params.put("userId", mLoginPhone);
            params.put("passWord", mPassWord);
            // 对话框提示登录中
            showLoadingWindow(view);
            // 请求网络
            VolleyHttpRequest.String_request(HttpPathUtil.getLoginIfo(),params, volleyRequest);
        }
    }

    /**
     * 再按一次退出程序
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                mExitTime = System.currentTimeMillis();
                showMsg(getResources().getString(R.string.msg_repress));
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
