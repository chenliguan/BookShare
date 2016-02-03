package com.bang.bookshare.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.bang.bookshare.R;
import com.bang.bookshare.activity.MyBookInfoActivity;
import com.bang.bookshare.activity.MyInfoActivity;
import com.bang.bookshare.adapter.BookListAdapter;
import com.bang.bookshare.entity.Book;
import com.bang.bookshare.utils.HttpPathUtil;
import com.bang.bookshare.utils.PreferencesUtils;
import com.bang.bookshare.view.CustomHanzTV;
import com.bang.bookshare.view.CustomMsyhTV;
import com.bang.bookshare.volley.VolleyHandler;
import com.bang.bookshare.volley.VolleyHttpRequest;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 我的页面Fragment
 *
 * @author Guan
 * @file com.bang.bookshare.fragment
 * @date 2016/2/1
 * @Version 1.0
 */
public class MyFragment extends BaseFragment {

    @InjectView(R.id.pull_refresh_list)
    PullToRefreshListView pullRefreshList;
    @InjectView(R.id.tv_title)
    CustomHanzTV tvTitle;
    @InjectView(R.id.tv_name)
    CustomMsyhTV tvName;
    @InjectView(R.id.tv_phone)
    CustomMsyhTV tvPhone;
    @InjectView(R.id.tv_address)
    CustomMsyhTV tvAddress;
    @InjectView(R.id.rlyt_my_info)
    RelativeLayout rlytMyInfo;

    private Book book;
    private OnClickListener mCallback;
    public VolleyHandler<String> volleyRequest;
    public HashMap<String, String> params;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ButterKnife.inject(this, super.onCreateView(inflater, container, savedInstanceState));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 实现父类方法
     *
     * @param inflater
     * @param container
     * @return
     */
    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View _view = inflater.inflate(R.layout.fragment_my, container, false);
        ButterKnife.inject(this, _view);
        return _view;
    }

    /**
     * 初始化变量
     */
    @Override
    public void initVariable() {
        book = new Book();
        pullRefreshList.setMode(PullToRefreshBase.Mode.BOTH);
        tvTitle.setText(getString(R.string.main_navigation_my));
        tvPhone.setText(PreferencesUtils.getLoginPhone(getActivity()));

        // Post请求键值对
        params = new HashMap<>();
        params.put("userId", PreferencesUtils.getLoginPhone(getActivity()));
    }

    /**
     * 绑定/设置数据操作
     */
    @Override
    public void bindData() {
        // 请求网络网络获取图书
        getBookInfo();
        // 上下拉任务执行监听
        onItemClickListener();
    }

    /**
     * 监听实现
     */
    @OnClick({R.id.iv_back,R.id.rlyt_my_info})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_back:
                // 返回
                mCallback.onIntentSelected();
                break;

            case R.id.rlyt_my_info:
                // 我的信息
                openActivity(MyInfoActivity.class);
                break;

            default:
                break;
        }
    }

    /**
     * 请求网络网络获取图书
     */
    private void getBookInfo() {
        // 请求获取图书信息
        volleyRequest = new VolleyHandler<String>() {
            @Override
            public void reqSuccess(String response) {
                if (response == null) {
                    showMsg(getString(R.string.msg_loading_error));
                } else {
                    // 解析数据
                    book = Book.praseJson(response);
                    // 设置适配器
                    pullRefreshList.setAdapter(new BookListAdapter(getActivity(), book.ecList));
                }
            }

            @Override
            public void reqError(String error) {
                showMsg(getString(R.string.msg_con_server_error));
            }
        };
        // 请求网络
        VolleyHttpRequest.String_request(HttpPathUtil.getBookIfo(), params, volleyRequest);
    }

    /**
     * 上下拉任务执行监听
     */
    private void onItemClickListener() {

        pullRefreshList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                // 这里写下拉刷新的任务
                new GetDataTask().execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 这里写上拉加载更多的任务
                new GetDataTask().execute();
            }
        });

        // 选项监听
        pullRefreshList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), MyBookInfoActivity.class);
                intent.putExtra("userId", book.ecList.get(position - 1).getUserId());
                getActivity().startActivity(intent);
            }
        });
    }

    /**
     * 开启后台任务执行处理
     */
    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            // 请求网络
            VolleyHttpRequest.String_request(HttpPathUtil.getBookIfo(), params, volleyRequest);
            // 任务完成停止刷新
            pullRefreshList.onRefreshComplete();
            super.onPostExecute(result);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
