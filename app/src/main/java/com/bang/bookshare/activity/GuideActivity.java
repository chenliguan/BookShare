package com.bang.bookshare.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bang.bookshare.R;
import com.bang.bookshare.activity.FrameActivity;
import com.bang.bookshare.adapter.GuidePagerAdapter;
import com.bang.bookshare.utils.ActivityStack;
import com.bang.bookshare.utils.PreferencesUtils;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 引导页面
 *
 * @author Bang
 * @file com.bang.bookshare.activity
 * @date 2016/1/31
 * @Version 1.0
 */
public class GuideActivity extends FrameActivity {

    @InjectView(R.id.viewPager)
    ViewPager viewPager;
    @InjectView(R.id.llyt_dots)
    LinearLayout layoutDots;
    @InjectView(R.id.btn_im_exp)
    Button btnImExp;

    private ArrayList<View> mList;
    private ImageView[] mImageViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.inject(this);
        ActivityStack.getInstance().addActivity(this);

        /**
         * 初始化ViewPager
         */
        initViewPager();
    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        LayoutInflater inflater = getLayoutInflater();
        mList = new ArrayList<View>();
        mList.add(inflater.inflate(R.layout.view_pager_first, null));
        mList.add(inflater.inflate(R.layout.view_pager_second, null));
        mList.add(inflater.inflate(R.layout.view_pager_third, null));

        mImageViews = new ImageView[mList.size()];
        for (int i = 0; i < mList.size(); i++) {
            mImageViews[i] = new ImageView(GuideActivity.this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            // 设置边界
            params.setMargins(7, 10, 7, 10);
            mImageViews[i].setLayoutParams(params);
            if (0 == i) {
                mImageViews[i].setBackgroundResource(R.mipmap.ic_indicator_c);
            } else {
                mImageViews[i].setBackgroundResource(R.mipmap.ic_indicator);
            }
            layoutDots.addView(mImageViews[i]);
        }

        viewPager.setAdapter(new GuidePagerAdapter(mList, GuideActivity.this));
        // 绑定回调
        viewPager.addOnPageChangeListener(new onPageChangeListener());
//        viewPager.setCurrentItem(0);
    }

    /**
     * 立刻体验按钮监听
     */
    @OnClick(R.id.btn_im_exp)
    public void onButtonClick() {
        // 写入是否引导记录
        PreferencesUtils.setIsFirst(GuideActivity.this);
        // 跳转到LoginActivity
        openActivityFn(LoginActivity.class);
    }

    /**
     * 监听ViewPager滑动效果
     */
    private class onPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            // 更新小圆点图标
            for (int i = 0; i < mList.size(); i++) {
                if (position == i) {
                    mImageViews[i].setBackgroundResource(R.mipmap.ic_indicator_c);
                } else {
                    mImageViews[i].setBackgroundResource(R.mipmap.ic_indicator);
                }
            }

            // 滑动到最后pager时显示“立刻体验”按钮并监听
            if (position == mList.size() - 1) {
                btnImExp.setVisibility(View.VISIBLE);
            } else {
                btnImExp.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    @Override
    public void onDestroy() {
        ActivityStack.getInstance().removeActivity(this);
        super.onDestroy();
    }
}