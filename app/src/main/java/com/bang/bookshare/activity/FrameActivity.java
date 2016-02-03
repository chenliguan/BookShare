package com.bang.bookshare.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bang.bookshare.R;
import com.bang.bookshare.utils.ActivityStack;
import com.bang.bookshare.utils.ConstantUtil;

import butterknife.ButterKnife;

/**
 * 框架类封装业务相关的方法
 *
 * @author Bang
 * @file com.bang.bookshare.activity
 * @date 2016/1/31
 * @Version 1.0
 */
public class FrameActivity extends BaseActivity {

    private PopupWindow mPopupWindow;
    public PopupWindow loadingWindow;

    /**
     * 把与业务相关的系统框架、界面初始化、设置等操作封装
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        ActivityStack.getInstance().addActivity(this);
    }

    /**
     * 定义提示PopupWindow
     *
     * @param view
     */
    public void showTipsWindow(View view,String tipT,String tipC) {
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.view_pop_tip, null);
        TextView tipTitle = ButterKnife.findById(contentView, R.id.tv_tip_title);
        TextView tipContent = ButterKnife.findById(contentView, R.id.tv_tip_content);
        tipTitle.setText(tipT);
        tipContent.setText(tipC);

        mPopupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT, true);
        mPopupWindow.setOutsideTouchable(true);
        // 必须实现,否则点击外部区域和Back键都无法dismiss
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        backgroundAlpha(0.6f);
        // 设置好参数之后再show
        mPopupWindow.showAtLocation(view, Gravity.CENTER_VERTICAL, 0, 0);
        // 隐退监听
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
        // 停留2秒,隐退
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPopupWindow.dismiss();
                mPopupWindow = null;
            }
        }, ConstantUtil.POPWIN_DELAY_MS);
    }

    /**
     * 登录中PopupWindow
     *
     * @param view
     */
    public void showLoadingWindow(View view) {
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.view_loading_dialog, null);
        // 取消view_loading_dialog背景底图片
        contentView.setBackground(null);
        TextView tips = ButterKnife.findById(contentView, R.id.tv_tips);
        tips.setText(getString(R.string.text_loginning));

        loadingWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT, true);
        backgroundAlpha(0.6f);
        // 设置好参数之后再show
        loadingWindow.showAtLocation(view, Gravity.CENTER_VERTICAL, 0, 0);
        // 隐退监听
        loadingWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // 跳转到PayActivity
//                Intent intent = new Intent(FrameActivity.this, MainActivity.class);
//                startActivity(intent);
                loadingWindow.dismiss();
                loadingWindow = null;
                backgroundAlpha(1f);
//                finish();
            }
        });
    }

    @Override
    public void onDestroy() {
        ActivityStack.getInstance().removeActivity(this);
        super.onDestroy();
    }
}
