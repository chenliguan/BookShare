package com.bang.bookshare.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bang.bookshare.R;
import com.bang.bookshare.utils.ActivityStack;
import com.bang.bookshare.view.CustomHanzTV;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 图书详情
 *
 * @author Bang
 * @file com.bang.bookshare.activity
 * @date 2016/2/2
 * @Version 1.0
 */
public class MyBookInfoActivity extends FrameActivity {

    @InjectView(R.id.iv_back)
    ImageView ivBack;
    @InjectView(R.id.tv_title)
    CustomHanzTV tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_book_info);
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
        tvTitle.setText(R.string.my_book_info);
    }

    /**
     * 初始化变量
     */
    private void bindData() {

    }

}
