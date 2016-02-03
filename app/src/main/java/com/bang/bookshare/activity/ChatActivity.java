package com.bang.bookshare.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bang.bookshare.R;
import com.bang.bookshare.utils.ActivityStack;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 聊天页面
 *
 * @author Bang
 * @file com.bang.bookshare.activity
 * @date 2016/2/2
 * @Version 1.0
 */
public class ChatActivity extends FrameActivity {

    @InjectView(R.id.iv_back)
    ImageView ivBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.inject(this);
        ActivityStack.getInstance().addActivity(this);

        /**
         * 初始化变量
         */
        initVariable();

        /**
         * 绑定数据
         */
        bindData();
    }

    /**
     * 绑定数据
     */
    private void initVariable() {
        tvTitle.setText(R.string.chat);
    }

    /**
     * 初始化变量
     */
    private void bindData() {

    }
}
