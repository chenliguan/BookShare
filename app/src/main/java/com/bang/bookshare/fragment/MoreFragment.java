package com.bang.bookshare.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bang.bookshare.R;
import com.bang.bookshare.utils.ConstantUtil;
import com.bang.bookshare.view.CustomHanzTV;
import com.bang.bookshare.view.CustomView;

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
    CustomHanzTV tvTitle;

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
    @OnClick({R.id.iv_back, R.id.cv_customer, R.id.cv_problem, R.id.cv_service_scope, R.id.cv_about_us, R.id.user_agree, R.id.cv_feed_back})
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

            case R.id.cv_problem:
//                openActivity(ProblemActivity.class);
                break;

            case R.id.cv_service_scope:
//                openActivity(ServiceAreaActivity.class);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}










