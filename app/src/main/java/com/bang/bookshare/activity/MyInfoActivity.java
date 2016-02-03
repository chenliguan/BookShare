package com.bang.bookshare.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bang.bookshare.R;
import com.bang.bookshare.utils.ActivityStack;
import com.bang.bookshare.view.CustomHanzTV;

import butterknife.ButterKnife;
import butterknife.InjectView;

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
    CustomHanzTV tvTitle;

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
    }

    /**
     * 初始化变量
     */
    private void initVariable() {
        tvTitle.setText(R.string.my_info);
    }
}
