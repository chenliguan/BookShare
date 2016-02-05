package com.bang.bookshare.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.bang.bookshare.R;
import com.bang.bookshare.adapter.BookListAdapter;
import com.bang.bookshare.entity.Book;
import com.bang.bookshare.entity.User;
import com.bang.bookshare.utils.ActivityStack;
import com.bang.bookshare.utils.ConstantUtil;
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
import butterknife.OnItemSelected;

/**
 * 我的信息页面
 *
 * @author Bang
 * @file com.bang.bookshare.activity
 * @date 2016/1/31
 * @Version 1.0
 */
public class MyInfoActivity extends FrameActivity {

    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.et_name)
    EditText etName;
    @InjectView(R.id.et_phone)
    EditText etPhone;
    @InjectView(R.id.et_profile)
    EditText etProfile;
    @InjectView(R.id.spr_school)
    Spinner sprSchool;
    @InjectView(R.id.et_class)
    EditText etClass;
    @InjectView(R.id.et_dorm)
    EditText etDorm;

    private String userSchool;
    private String[] mSchool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        ButterKnife.inject(this);
        ActivityStack.getInstance().addActivity(this);

        /**
         * 初始化变量
         */
        initVariable();
        /**
         * 自定义Spinner样式
         */
        setSpinner();
    }

    /**
     * 初始化变量
     */
    private void initVariable() {
        tvTitle.setText(R.string.my_info);
        mSchool = getResources().getStringArray(R.array.school);
        etName.setText(PreferencesUtils.getUserName(this));
        etPhone.setText(PreferencesUtils.getLoginPhone(this));
        etProfile.setText(PreferencesUtils.getUserProfile(this));
        etClass.setText(PreferencesUtils.getUserClass(this));
        etDorm.setText(PreferencesUtils.getUserDorm(this));
    }

    /**
     * 自定义Spinner样式
     */
    private void setSpinner() {
        // 1.定义适配器,自定义布局，以及传入数据area。
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_spinner, mSchool);
        // 2.为适配器设置下拉列表下拉时的菜单样式。
        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
        // 3.将适配器添加到下拉列表上
        sprSchool.setAdapter(adapter);
        // 4.Spinner点击监听
        // 5.根据值, 设置spinner默认选中:
        if (userSchool == null) {
            userSchool = mSchool[0];
        }
        setSpinnerItemByValue(sprSchool, userSchool);
    }

    /**
     * Spinner点击监听
     *
     * @param view
     */
    @OnItemSelected(R.id.spr_school)
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        userSchool = mSchool[position];
    }

    /**
     * 监听实现
     *
     * @param view
     */
    @OnClick({R.id.iv_back, R.id.btn_save})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.iv_back:
                finish();
                break;

            case R.id.btn_save:
                String userName = String.valueOf(etName.getText());
                String userId = String.valueOf(etPhone.getText());
                String userProfile = String.valueOf(etProfile.getText());
                String classes = String.valueOf(etClass.getText());
                String dorm = String.valueOf(etDorm.getText());

                // 权限验证
                if (userId.isEmpty()) {
                    showTipsWindow(view, getString(R.string.pop_tip_title), getString(R.string.phone_null));
                } else if (!RegularUtil.isMobileNO(userId)) {
                    showTipsWindow(view, getString(R.string.pop_tip_title), getString(R.string.pop_tip_content));
                } else if (userName.isEmpty()) {
                    showTipsWindow(view, getString(R.string.pop_tip_title), getString(R.string.name_null));
                } else if (userProfile.isEmpty()) {
                    showTipsWindow(view, getString(R.string.pop_tip_title), getString(R.string.profile_null));
                } else if (userSchool.isEmpty()) {
                    showTipsWindow(view, getString(R.string.pop_tip_title), getString(R.string.school_null));
                } else if (classes.isEmpty()) {
                    showTipsWindow(view, getString(R.string.pop_tip_title), getString(R.string.classes_null));
                } else if (dorm.isEmpty()) {
                    showTipsWindow(view, getString(R.string.pop_tip_title), getString(R.string.detail_dorm_null));
                } else {

                    // 请求网络更新服务器与本地用户信息
                    updateUser(userId, userName, userSchool, classes, dorm, userProfile);
                }
                break;

            default:
                break;
        }
    }

    /**
     * 请求网络更新服务器与本地用户信息
     */
    private void updateUser(final String userId, final String userName, final String userSchool,
                            final String classes, final String dorm, final String userProfile) {

        //先new出一个监听器，设置好监听
        DialogInterface.OnClickListener dialogOnclicListener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, int which) {
                switch (which) {

                    case Dialog.BUTTON_POSITIVE:
                        // 请求获取用户信息
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
                                        // 更新本地用户信息
                                        PreferencesUtils.setUserInfo(MyInfoActivity.this, true, userName, userId, userProfile, userSchool, classes, dorm);
                                        // 向BasketFragment发送广播,更新界面
                                        sendBroadcast();
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
                        params.put("oldPass", PreferencesUtils.getPassWord(MyInfoActivity.this));
                        params.put("userName", userName);
                        params.put("userSchool", EnumDataChange.CH_EN(userSchool));
                        params.put("classes", classes);
                        params.put("adress", dorm);
                        params.put("userProfile", userProfile);
                        params.put("lastUpdateTime", new SimpleDateFormat("yyyy-MM-dd HH-mm-ss", Locale.getDefault()).format(new Date()));
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
        builder.setMessage(getString(R.string.is_update));
        builder.setPositiveButton(getString(R.string.sure), dialogOnclicListener);
        builder.setNegativeButton(getString(R.string.no), dialogOnclicListener);
        builder.create().show();
    }

    /**
     * 向BasketFragment发送广播,更新界面
     */
    private void sendBroadcast() {
        Intent intent = new Intent(ConstantUtil.ACTION_B_U_P_UPDATEUI);
        intent.putExtra(ConstantUtil.INTENT_KEY, ConstantUtil.VALUE_MyInfo_ACTIVITY);
        sendBroadcast(intent);
        finish();
    }

    /**
     * 根据值, 设置spinner默认选中
     *
     * @param spinner
     * @param value
     */
    public static void setSpinnerItemByValue(Spinner spinner, String value) {
        //得到SpinnerAdapter对象
        SpinnerAdapter apsAdapter = spinner.getAdapter();
        int k = apsAdapter.getCount();
        for (int i = 0; i < k; i++) {
            if (value.equals(apsAdapter.getItem(i).toString())) {
                // 默认选中项
                spinner.setSelection(i, true);
                break;
            }
        }
    }

}
