package com.bang.bookshare.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.bang.bookshare.R;
import com.bang.bookshare.activity.LoginActivity;
import com.bang.bookshare.activity.ModifyPassActivity;
import com.bang.bookshare.activity.MyInfoActivity;
import com.bang.bookshare.entity.User;
import com.bang.bookshare.utils.ActivityStack;
import com.bang.bookshare.utils.ConstantUtil;
import com.bang.bookshare.utils.EnumDataChange;
import com.bang.bookshare.utils.HttpPathUtil;
import com.bang.bookshare.utils.LogUtil;
import com.bang.bookshare.utils.PreferencesUtils;
import com.bang.bookshare.view.CustomHanzTV;
import com.bang.bookshare.view.CustomView;
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
 * 更多Fragment
 *
 * @author Bang
 * @file com.bang.bookshare.fragment
 * @date 2016/2/1
 * @Version 1.0
 */
public class MoreFragment extends BaseFragment {

    @InjectView(R.id.tv_title)
    TextView tvTitle;

    private OnClickListener mCallback;

    // 存放fragment的Activtiy必须实现的接口
    public interface OnClickListener {
        public void onIntentSelected();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // 为保证Activity容器实现以回调的接口,如果没会抛出一个异常。
        try {
            mCallback = (OnClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 实现父类方法
     *
     * @param inflater
     * @return
     */
    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View _view = inflater.inflate(R.layout.fragment_more, container, false);
        ButterKnife.inject(this, _view);
        return _view;
    }

    /**
     * 初始化变量
     */
    public void initVariable() {
        tvTitle.setText(R.string.main_navigation_more);
    }

    /**
     * 绑定/设置数据操作
     */
    @Override
    public void bindData() {
    }

    /**
     * 监听实现，回调接口
     */
    @OnClick({R.id.iv_back, R.id.cv_customer, R.id.out_login, R.id.cv_modify_pass,
            R.id.cv_about_us, R.id.user_agree, R.id.cv_feed_back})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_back:
                // 返回
                mCallback.onIntentSelected();
                break;

            case R.id.cv_customer:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + 723612));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;

            case R.id.out_login:
                // 退出登录
                outLogin();
                break;

            case R.id.cv_modify_pass:
                openActivity(ModifyPassActivity.class);
                break;

            case R.id.cv_about_us:
//                openActivity(AboutUsActivity.class);
                break;

            case R.id.user_agree:
//                openActivity(UserAgreeActivity.class);
                break;

            case R.id.cv_feed_back:
//                openActivity(FeekBackActivity.class);
                break;

            default:
                break;
        }

    }

    /**
     * 退出登录
     */
    private void outLogin() {

        //先new出一个监听器，设置好监听
        DialogInterface.OnClickListener dialogOnclicListener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, int which) {
                switch (which) {

                    case Dialog.BUTTON_POSITIVE:
                        // 退出当前activity
                        openActivityFn(LoginActivity.class);
                        // 退出登录LearnCloud
                        AVIMClient imClient = AVIMClient.getInstance(PreferencesUtils.getLoginPhone(getActivity()));
                        imClient.close(new AVIMClientCallback() {
                            @Override
                            public void done(AVIMClient avimClient, AVIMException e) {
                                LogUtil.showLog("退出成功");
                            }
                        });
                        break;

                    case Dialog.BUTTON_NEGATIVE:
                        // 隐退对话框
                        dialog.dismiss();
                        break;
                }
            }
        };
        //dialog参数设置
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //设置内容
        builder.setMessage(getString(R.string.is_out));
        builder.setPositiveButton(getString(R.string.sure), dialogOnclicListener);
        builder.setNegativeButton(getString(R.string.no), dialogOnclicListener);
        builder.create().show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}










