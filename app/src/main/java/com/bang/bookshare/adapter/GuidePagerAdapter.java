package com.bang.bookshare.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;

/**
 * 引导页面切换适配器
 *
 * @author Bang
 * @file com.bang.bookshare.adapter
 * @date 2016/1/31
 * @Version 1.0
 */
public class GuidePagerAdapter extends PagerAdapter {

    private ArrayList<View> mList;
    private Activity mActivity;

    public GuidePagerAdapter(ArrayList<View> list, Activity activity) {
        this.mList = list;
        this.mActivity = activity;
    }

    @Override
    public int getCount() {
        // 返回页面数目实现有限滑动效果
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public Object instantiateItem(View container, int position) {
        ((ViewPager) container).addView(mList.get(position), 0);
        return mList.get(position);
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
          // 注销父类销毁item的方法，因为此方法并不是使用此方法
//        super.destroyItem(container, position, object);
        ((ViewPager) container).removeView(mList.get(position));
    }
}
