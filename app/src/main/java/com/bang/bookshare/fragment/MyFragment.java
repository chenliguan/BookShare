package com.bang.bookshare.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bang.bookshare.R;
import com.bang.bookshare.activity.MyBookInfoActivity;
import com.bang.bookshare.activity.MyInfoActivity;
import com.bang.bookshare.adapter.BookListAdapter;
import com.bang.bookshare.entity.Book;
import com.bang.bookshare.entity.User;
import com.bang.bookshare.utils.ConstantUtil;
import com.bang.bookshare.utils.EnumDataChange;
import com.bang.bookshare.utils.HttpPathUtil;
import com.bang.bookshare.utils.PreferencesUtils;
import com.bang.bookshare.view.CustomHanzTV;
import com.bang.bookshare.view.CustomMsyhTV;
import com.bang.bookshare.volley.VolleyHandler;
import com.bang.bookshare.volley.VolleyHttpRequest;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

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
    TextView tvTitle;
    @InjectView(R.id.tv_name)
    CustomMsyhTV tvName;
    @InjectView(R.id.tv_phone)
    CustomMsyhTV tvPhone;
    @InjectView(R.id.tv_address)
    CustomMsyhTV tvAddress;

    private Book book;
    private OnClickListener mCallback;
    private BookListAdapter mBookListAdapter;
    private UpdateUIReceiver mUpdateUIReceiver;
    private UpdateLVReceiver mUpdateLVReceiver;
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
        // 动态注册广播
        mUpdateUIReceiver = new UpdateUIReceiver();
        IntentFilter filter = new IntentFilter(ConstantUtil.ACTION_B_U_P_UPDATEUI);
        getActivity().registerReceiver(mUpdateUIReceiver, filter);

        mUpdateLVReceiver = new UpdateLVReceiver();
        IntentFilter filter2 = new IntentFilter(ConstantUtil.ACTION_HC_UPDATEUI);
        getActivity().registerReceiver(mUpdateLVReceiver, filter2);
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
        tvTitle.setText(getString(R.string.main_navigation_my));
        // 设置值
        setValue();
        pullRefreshList.setMode(PullToRefreshBase.Mode.BOTH);
        // Post请求键值对
        params = new HashMap<>();
        params.put("userId", PreferencesUtils.getLoginPhone(getActivity()));
    }

    /**
     * 设置值
     */
    private void setValue() {
        String name = PreferencesUtils.getUserName(getActivity());
        String phone = PreferencesUtils.getLoginPhone(getActivity());
        String address = PreferencesUtils.getUserSchool(getActivity()) +
                PreferencesUtils.getUserClass(getActivity()) + PreferencesUtils.getUserDorm(getActivity());
        tvName.setText(name);
        tvPhone.setText(phone);
        tvAddress.setText(address);
    }

    /**
     * 绑定/设置数据操作
     */
    @Override
    public void bindData() {
        // 请求获取用户信息保存本地
        getUserIfo();
        // 请求网络网络获取图书
        getBookIfo();
        // 上下拉任务执行监听
        onItemClickListener();
    }

    /**
     * 监听实现
     */
    @OnClick({R.id.iv_back, R.id.rlyt_my_info})
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
     * 请求获取用户信息保存本地
     */
    private void getUserIfo() {
        VolleyHandler<String> volleyRequest = new VolleyHandler<String>() {
            @Override
            public void reqSuccess(String response) {
                if (response == null) {
                    showMsg(getString(R.string.msg_loading_error));
                } else {
                    // 解析数据
                    User user = User.praseJson(response);
                    // 请求业务判断
                    if (user.isSuccess()) {
                        User.EcListEntity entity = user.ecList.get(0);
                        // 英文转换陈中文
                        String usetSchool = null;
                        if (entity.getUserSchool() != null) {
                            usetSchool = EnumDataChange.EN_CH(entity.getUserSchool());
                        }
                        // 更新本地用户信息
                        PreferencesUtils.setUserInfo(getActivity(), true, entity.getUserName(), String.valueOf(entity.getUserId()),
                                entity.getUserProfile(), usetSchool, entity.getClasses(), entity.getAdress());
                        // 更新页面当前的值
                        setValue();
                    } else {
                        showMsg(user.getMessage());
                    }
                }
            }

            @Override
            public void reqError(String error) {
                showMsg(getString(R.string.msg_con_server_error));
            }
        };
        // Post请求键值对
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", PreferencesUtils.getLoginPhone(getActivity()));
        VolleyHttpRequest.String_request(HttpPathUtil.getUserIfo(), params, volleyRequest);
    }

    /**
     * 请求网络网络获取图书
     */
    private void getBookIfo() {
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
                    mBookListAdapter = new BookListAdapter(getActivity(), book.ecList);
                    pullRefreshList.setAdapter(mBookListAdapter);
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
                Bundle bundle = new Bundle();
                bundle.putSerializable("ecListEntity", book.ecList.get(position - 1));
                intent.putExtras(bundle);
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

    /**
     * 定义广播接收器（内部类）
     */
    private class UpdateUIReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // 更新数据,设置值
            setValue();
        }
    }

    /**
     * 定义广播接收器（内部类）
     */
    private class UpdateLVReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // 请求网络,刷新数据
            VolleyHttpRequest.String_request(HttpPathUtil.getBookIfo(), params, volleyRequest);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        // 注销广播
        getActivity().unregisterReceiver(mUpdateUIReceiver);
    }
}
