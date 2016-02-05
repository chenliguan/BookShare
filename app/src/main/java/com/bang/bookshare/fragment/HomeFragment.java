package com.bang.bookshare.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.bang.bookshare.R;
import com.bang.bookshare.activity.BookInfoActivity;
import com.bang.bookshare.activity.ChatActivity;
import com.bang.bookshare.activity.MyInfoActivity;
import com.bang.bookshare.activity.SearchActivity;
import com.bang.bookshare.adapter.BookHListAdapter;
import com.bang.bookshare.entity.Book;
import com.bang.bookshare.utils.HttpPathUtil;
import com.bang.bookshare.utils.LogUtil;
import com.bang.bookshare.volley.VolleyHandler;
import com.bang.bookshare.volley.VolleyHttpRequest;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 主页Fragment
 *
 * @author Guan
 * @file com.bang.bookshare.fragment
 * @date 2016/2/1
 * @Version 1.0
 */
public class HomeFragment extends BaseFragment {

    @InjectView(R.id.pull_refresh_list)
    PullToRefreshListView pullRefreshList;

    private Book book;
    public VolleyHandler<String> volleyRequest;

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
        View _view = inflater.inflate(R.layout.fragment_home, container, false);
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
                    pullRefreshList.setAdapter(new BookHListAdapter(getActivity(), book.ecList));
                }
            }

            @Override
            public void reqError(String error) {
                showMsg(getString(R.string.msg_con_server_error));
            }
        };
    }

    /**
     * 绑定/设置数据操作
     */
    @Override
    public void bindData() {
        // 请求网络
        VolleyHttpRequest.String_request(HttpPathUtil.getBookIfo(), volleyRequest);

        // 上下拉任务执行监听
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
                Intent intent = new Intent(getActivity(), BookInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("ecListEntity", book.ecList.get(position - 1));
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });
    }

    /**
     * 监听实现
     */
    @OnClick({R.id.tv_search})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tv_search:
                openActivity(SearchActivity.class);
                break;

            default:
                break;
        }
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
            VolleyHttpRequest.String_request(HttpPathUtil.getBookIfo(), volleyRequest);
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
