package com.bang.bookshare.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.bang.bookshare.activity.BaseActivity;

import java.util.List;

/**
 * Adapter基础类封装方法
 *
 * @author Bang
 * @file com.bang.bookshare.adapter
 * @date 2016/1/31
 * @Version 1.0
 */
public abstract class SimpleBaseAdapter<T> extends BaseAdapter {

    private List<T> mList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public SimpleBaseAdapter(Context context, List<T> list) {
        this.mContext = context;
        this.mList = list;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public LayoutInflater getLayoutInflater() {
        return mLayoutInflater;
    }

    public Context getContext() {
        return mContext;
    }

    public List getList() {
        return mList;
    }

    @Override
    public int getCount() {
        if (mList != null && mList.size() > 0) {
            return mList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if (mList != null && mList.size() > 0) {
            return mList.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Toast公共方法
     * @param pMsg
     */
    public void showMsg(String pMsg) {
        Toast.makeText(mContext, pMsg, Toast.LENGTH_SHORT).show();
    }

}
