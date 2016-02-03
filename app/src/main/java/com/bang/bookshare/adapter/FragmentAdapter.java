package com.bang.bookshare.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bang.bookshare.utils.ConstantUtil;

import java.util.ArrayList;

/**
 * Fragment适配器
 *
 * @author Bang
 * @file com.bang.bookshare.activity
 * @date 2016/2/1
 * @Version 1.0
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    public ArrayList<Fragment> fragList;

    public FragmentAdapter(FragmentManager fm, ArrayList<Fragment> fragList) {
        super(fm);
        this.fragList = fragList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragList.get(position);
    }

    @Override
    public int getCount() {
        return ConstantUtil.TAB_COUNT;
    }

}
