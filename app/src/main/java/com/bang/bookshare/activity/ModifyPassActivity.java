package com.bang.bookshare.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bang.bookshare.R;
import com.bang.bookshare.entity.User;
import com.bang.bookshare.utils.ActivityStack;
import com.bang.bookshare.utils.EnumDataChange;
import com.bang.bookshare.utils.HttpPathUtil;
import com.bang.bookshare.utils.PreferencesUtils;
import com.bang.bookshare.utils.RegularUtil;
import com.bang.bookshare.volley.VolleyHandler;
import com.bang.bookshare.volley.VolleyHttpRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 我的信息页面
 *
 * @author Bang
 * @file com.bang.bookshare.activity
 * @date 2016/1/31
 * @Version 1.0
 */
public class ModifyPassActivity extends FrameActivity {

    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.et_old_password)
    EditText etOldPassword;
    @InjectView(R.id.et_password)
    EditText etPassword;
    @InjectView(R.id.et_password_next)
    EditText etPasswordNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pass);
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
        tvTitle.setText(R.string.modify_pass);
    }

    /**
     * 监听实现
     */
    @OnClick({R.id.iv_back, R.id.btn_save})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.btn_save:
                String userId = String.valueOf(PreferencesUtils.getLoginPhone(this));
                String oldPass = String.valueOf(etOldPassword.getText());
                String newPass = String.valueOf(etPassword.getText());
                String newPassNext = String.valueOf(etPasswordNext.getText());

                // 权限验证
                if (oldPass.isEmpty()) {
                    showTipsWindow(view, getString(R.string.pop_tip_title), getString(R.string.pass_null));
                } else if (newPass.isEmpty()) {
                    showTipsWindow(view, getString(R.string.pop_tip_title), getString(R.string.pass_null));
                } else if (newPassNext.isEmpty()) {
                    showTipsWindow(view, getString(R.string.pop_tip_title), getString(R.string.pass_null));
                } else if (RegularUtil.isChineseNo(oldPass) & RegularUtil.isChineseNo(newPass) & RegularUtil.isChineseNo(newPassNext)) {
                    showTipsWindow(view, getString(R.string.pop_tip_title), getString(R.string.pass_error));
                } else if (!newPass.equals(newPassNext)) {
                    showTipsWindow(view, getString(R.string.pop_tip_title), getString(R.string.pass_not_equal));
                } else {

                    // 请求网络更新服务器与本地用户信息
                    updateUser(userId, oldPass, newPass);
                }
                break;

            default:
                break;
        }
    }

    /**
     * 请求网络更新服务器与本地用户信息
     */
    private void updateUser(final String userId, final String oldPass, final String newPass) {

        //先new出一个监听器，设置好监听
        DialogInterface.OnClickListener dialogOnclicListener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, int which) {
                switch (which) {

                    case Dialog.BUTTON_POSITIVE:
                        // 请求获取图书信息
                        VolleyHandler<String> volleyRequest = new VolleyHandler<String>() {
                            @Override
                            public void reqSuccess(String response) {
                                dialog.dismiss();
                                if (response == null) {
                                    showMsg(getString(R.string.msg_loading_error));
                                } else {
                                    // 解析数据
                                    User user = User.praseJson(response);
                                    // 请求业务判断
                                    if (user.isSuccess()) {
                                        // 更新本地登录信息
                                        PreferencesUtils.setLoginInfo(ModifyPassActivity.this, userId, newPass);
                                        finish();
                                        showMsg(user.getMessage());
                                    } else {
                                        showMsg(user.getMessage());
                                    }
                                }
                            }

                            @Override
                            public void reqError(String error) {
                                dialog.dismiss();
                                showMsg(getString(R.string.msg_con_server_error));
                            }
                        };
                        // Post请求键值对
                        HashMap<String, String> params = new HashMap<>();
                        params.put("userId", userId);
                        params.put("oldPass", oldPass);
                        params.put("passWord", newPass);
                        VolleyHttpRequest.String_request(HttpPathUtil.updateUser(), params, volleyRequest);
                        break;

                    case Dialog.BUTTON_NEGATIVE:
                        // 隐退对话框
                        dialog.dismiss();
                        break;
                }
            }
        };
        //dialog参数设置
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //设置内容
        builder.setMessage(getString(R.string.is_pass_update));
        builder.setPositiveButton(getString(R.string.sure), dialogOnclicListener);
        builder.setNegativeButton(getString(R.string.no), dialogOnclicListener);
        builder.create().show();
    }

}
