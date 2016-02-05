package com.bang.bookshare.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.bang.bookshare.R;
import com.bang.bookshare.adapter.BookHListAdapter;
import com.bang.bookshare.entity.Book;
import com.bang.bookshare.utils.ActivityStack;
import com.bang.bookshare.utils.HttpPathUtil;
import com.bang.bookshare.volley.VolleyHandler;
import com.bang.bookshare.volley.VolleyHttpRequest;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 搜索页面
 *
 * @author Bang
 * @file com.bang.bookshare.activity
 * @date 2016/2/5
 * @Version 1.0
 */
public class SearchActivity extends FrameActivity {

    @InjectView(R.id.input_text)
    EditText inputText;
    @InjectView(R.id.pull_refresh_list)
    PullToRefreshListView pullRefreshList;

    private Book book;
    public VolleyHandler<String> volleyRequest;
    // Post请求键值对
    private HashMap<String, String> params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
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
        book = new Book();
        pullRefreshList.setMode(PullToRefreshBase.Mode.BOTH);
        params = new HashMap<>();
        params.put("bookName", inputText.getText().toString());
    }

    /**
     * 监听实现
     */
    @OnClick({R.id.iv_back, R.id.iv_search})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.iv_search:
                params.put("bookName", inputText.getText().toString());
                /**
                 * 绑定数据
                 */
                bindData();
                break;

            default:
                break;
        }
    }

    /**
     * 绑定数据
     */
    private void bindData() {
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
                    pullRefreshList.setAdapter(new BookHListAdapter(SearchActivity.this, book.ecList));
                }
            }

            @Override
            public void reqError(String error) {
                showMsg(getString(R.string.msg_con_server_error));
            }
        };
        // 请求网络
        VolleyHttpRequest.String_request(HttpPathUtil.searchBookIfo(), params,volleyRequest);

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
                Intent intent = new Intent(SearchActivity.this, BookInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("ecListEntity", book.ecList.get(position - 1));
                intent.putExtras(bundle);
                startActivity(intent);
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
            VolleyHttpRequest.String_request(HttpPathUtil.searchBookIfo(),params, volleyRequest);
            // 任务完成停止刷新
            pullRefreshList.onRefreshComplete();
            super.onPostExecute(result);
        }
    }

}
