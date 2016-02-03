package com.bang.bookshare.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.bang.bookshare.R;
import com.bang.bookshare.adapter.FragmentAdapter;
import com.bang.bookshare.fragment.HomeFragment;
import com.bang.bookshare.fragment.MoreFragment;
import com.bang.bookshare.fragment.MyFragment;
import com.bang.bookshare.utils.ActivityStack;
import com.bang.bookshare.utils.ConstantUtil;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 主页面,包含4个fragment
 *
 * @author Bang
 * @file com.bang.bookshare.activity
 * @date 2016/1/31
 * @Version 1.0
 */
public class MainActivity extends FrameActivity implements
        MyFragment.OnClickListener, MoreFragment.OnClickListener {

    @InjectView(R.id.frag_vpager)
    ViewPager fragVPager;
    @InjectView(R.id.main_tab_group)
    RadioGroup mainTabGroup;
    @InjectView(R.id.iv_pop_add)
    ImageView ivPopAdd;

    private long mExitTime;
    private ArrayList<Fragment> mFragList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
     * 初始化变量
     */
    private void initVariable() {
        mExitTime = 0;
        mFragList = new ArrayList<>();
        mFragList.add(new HomeFragment());
        mFragList.add(new MyFragment());
        mFragList.add(new MoreFragment());
    }

    /**
     * 绑定数据
     */
    private void bindData() {
        // 缓存页面的个数
        fragVPager.setOffscreenPageLimit(2);
        fragVPager.setAdapter(new FragmentAdapter(
                getSupportFragmentManager(), mFragList));
        // 页面选择监听
        fragVPager.addOnPageChangeListener(new onPageChangeListener());
        // 设置底栏选择按钮选择监听
        mainTabGroup.setOnCheckedChangeListener(new onCheckedChangeListener());
    }

    /**
     * 底栏选择按钮选择监听
     */
    private class onCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.main_tab_home:
                    fragVPager.setCurrentItem(ConstantUtil.TAB_HOME);
                    ivPopAdd.setVisibility(View.INVISIBLE);
                    break;

                case R.id.main_tab_my:
                    fragVPager.setCurrentItem(ConstantUtil.TAB_MY);
                    ivPopAdd.setVisibility(View.VISIBLE);
                    break;

                case R.id.main_tab_more:
                    fragVPager.setCurrentItem(ConstantUtil.TAB_MORE);
                    ivPopAdd.setVisibility(View.INVISIBLE);
                    break;

                default:
                    break;
            }
        }
    }

    /**
     * 页面滑动选择监听
     */
    private class onPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {

            switch (position) {
                case ConstantUtil.TAB_HOME:
                    // 设置选择按钮监听
                    mainTabGroup.check(R.id.main_tab_home);
                    ivPopAdd.setVisibility(View.INVISIBLE);
                    break;

                case ConstantUtil.TAB_MY:
                    mainTabGroup.check(R.id.main_tab_my);
                    ivPopAdd.setVisibility(View.VISIBLE);
                    break;

                case ConstantUtil.TAB_MORE:
                    mainTabGroup.check(R.id.main_tab_more);
                    ivPopAdd.setVisibility(View.INVISIBLE);
                    break;

                default:
                    break;
            }
        }

        @Override
        public void onPageScrolled(int position, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int position) {
        }
    }

    /**
     * 监听实现
     */
    @OnClick(R.id.iv_pop_add)
    public void onClick(View view) {
        // 跳转添加图书页面
        openActivity(MyBookInfoActivity.class);
    }

    /**
     * 实现-----Fragment的回调方法
     */
    @Override
    public void onIntentSelected() {
        fragVPager.setCurrentItem(ConstantUtil.TAB_HOME);
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
                // 结束所有Activity
                ActivityStack.getInstance().finishAllActivity();
                // 跳转到主题
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
