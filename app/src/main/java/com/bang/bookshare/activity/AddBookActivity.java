package com.bang.bookshare.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.bang.bookshare.R;
import com.bang.bookshare.entity.Book;
import com.bang.bookshare.utils.ActivityStack;
import com.bang.bookshare.utils.ConstantUtil;
import com.bang.bookshare.utils.HttpPathUtil;
import com.bang.bookshare.utils.PreferencesUtils;
import com.bang.bookshare.view.CircleImageView;
import com.bang.bookshare.volley.VolleyHandler;
import com.bang.bookshare.volley.VolleyHttpRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemSelected;

/**
 * 添加图书信息
 *
 * @author Bang
 * @file com.bang.bookshare.activity
 * @date 2016/2/4
 * @Version 1.0
 */
public class AddBookActivity extends FrameActivity {

    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.civ_head_afd)
    CircleImageView civHeadAfd;
    @InjectView(R.id.et_book_name)
    EditText etBookName;
    @InjectView(R.id.et_author)
    EditText etAuthor;
    @InjectView(R.id.et_book_collections)
    EditText etBookCollections;
    @InjectView(R.id.et_book_profile)
    EditText etBookProfile;
    @InjectView(R.id.spr_is_borrowed)
    Spinner sprIsBorrowed;

    private String borrowed;
    private String[] mBorrowed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_book_info);
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
     * 绑定数据
     */
    private void initVariable() {
        tvTitle.setText(R.string.add_book_info);
        mBorrowed = getResources().getStringArray(R.array.borrowed);
        // 暂时以此图片代替
        civHeadAfd.setImageUrl(getString(R.string.book), R.mipmap.ic_head, R.mipmap.ic_head);
    }

    /**
     * 初始化变量
     */
    private void bindData() {
        // 自定义Spinner样式
        setSpinner();
    }

    /**
     * Spinner点击监听
     *
     * @param view
     */
    @OnItemSelected(R.id.spr_is_borrowed)
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        borrowed = mBorrowed[position];
    }

    /**
     * 监听实现
     *
     * @param view
     */
    @OnClick({R.id.iv_back, R.id.btn_save})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.iv_back:
                finish();
                break;

            case R.id.btn_save:
                String bookName = String.valueOf(etBookName.getText());
                String bookAuthor = String.valueOf(etAuthor.getText());
                String bookCollections = String.valueOf(etBookCollections.getText());
                String bookProfile = String.valueOf(etBookProfile.getText());
                String createrTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").format(new Date(System.currentTimeMillis()));
                String lastUpdateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").format(new Date(System.currentTimeMillis()));

                // 权限验证
                if (bookName.isEmpty()) {
                    showTipsWindow(view, getString(R.string.pop_tip_title), getString(R.string.phone_null));
                } else if (bookAuthor.isEmpty()) {
                    showTipsWindow(view, getString(R.string.pop_tip_title), getString(R.string.name_null));
                } else if (bookCollections.isEmpty()) {
                    showTipsWindow(view, getString(R.string.pop_tip_title), getString(R.string.profile_null));
                } else if (bookProfile.isEmpty()) {
                    showTipsWindow(view, getString(R.string.pop_tip_title), getString(R.string.classes_null));
                } else {

                    // 请求网络更新服务器与本地图书信息
                    updateBook(bookName, bookAuthor, borrowed, bookCollections, bookProfile, createrTime,lastUpdateTime);
                }
                break;

            default:
                break;
        }
    }

    /**
     * 自定义Spinner样式
     */
    private void setSpinner() {
        // 1.定义适配器,自定义布局，以及传入数据area。
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_spinner, mBorrowed);
        // 2.为适配器设置下拉列表下拉时的菜单样式。
        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
        // 3.将适配器添加到下拉列表上
        sprIsBorrowed.setAdapter(adapter);
        // 4.Spinner点击监听
        // 5.根据值, 设置spinner默认选中:
        if (borrowed == null) {
            borrowed = mBorrowed[0];
        }
        setSpinnerItemByValue(sprIsBorrowed, borrowed);
    }


    /**
     * 请求网络更新服务器与本地图书信息
     *
     * @param bookName
     * @param bookAuthor
     * @param borrowed
     * @param bookCollections
     * @param bookProfile
     */
    private void updateBook(final String bookName, final String bookAuthor, final String borrowed, final String bookCollections, final String bookProfile, final String createrTime,final String lastUpdateTime) {
        //先new出一个监听器，设置好监听
        DialogInterface.OnClickListener dialogOnclicListener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, int which) {
                switch (which) {

                    case Dialog.BUTTON_POSITIVE:
                        // 请求获取图书信息
                        VolleyHandler<String> volleyRequest = new VolleyHandler<String>() {
                            @Override
                            public void reqSuccess(String response) {
                                dialog.dismiss();
                                if (response == null) {
                                    showMsg(getString(R.string.msg_loading_error));
                                } else {
                                    // 解析数据
                                    Book book = Book.praseJson(response);
                                    // 请求业务判断
                                    if (book.isSuccess()) {
                                        // 向BasketFragment发送广播,更新界面
                                        sendBroadcast();
                                        showMsg(book.getMessage());
                                    } else {
                                        showMsg(book.getMessage());
                                    }
                                }
                            }

                            @Override
                            public void reqError(String error) {
                                dialog.dismiss();
                                showMsg(getString(R.string.msg_con_server_error));
                            }
                        };

                        String userName = PreferencesUtils.getUserName(AddBookActivity.this);
                        // Post请求键值对
                        HashMap<String, String> params = new HashMap<>();
                        params.put("bookName", bookName);
                        params.put("bookAuthor", bookAuthor);
                        params.put("isBorrowed", borrowed);
                        params.put("bookCollections", bookCollections);
//                        params.put("publisherName", publisherName);
                        params.put("bookProfile", bookProfile);

                        params.put("userId", PreferencesUtils.getUserId(AddBookActivity.this));
                        params.put("userName", userName);
                        params.put("bookCode", getString(R.string.book));
                        params.put("createrTime", createrTime);
                        params.put("creater", userName);
                        params.put("lastUpdateTime", lastUpdateTime);
                        params.put("lastUpdater", userName);
                        VolleyHttpRequest.String_request(HttpPathUtil.createBook(), params, volleyRequest);
                        break;

                    case Dialog.BUTTON_NEGATIVE:
                        // 隐退对话框
                        dialog.dismiss();
                        break;
                }
            }
        };
        //dialog参数设置
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //设置内容
        builder.setMessage(getString(R.string.is_add));
        builder.setPositiveButton(getString(R.string.sure), dialogOnclicListener);
        builder.setNegativeButton(getString(R.string.no), dialogOnclicListener);
        builder.create().show();
    }

    /**
     * 向BasketFragment发送广播,更新界面
     */
    private void sendBroadcast() {
        Intent intent = new Intent(ConstantUtil.ACTION_HC_UPDATEUI);
        intent.putExtra(ConstantUtil.INTENT_KEY, ConstantUtil.VALUE_MyBookInfo_ACTIVITY);
        sendBroadcast(intent);
        finish();
    }

    /**
     * 根据值, 设置spinner默认选中
     *
     * @param spinner
     * @param value
     */
    public static void setSpinnerItemByValue(Spinner spinner, String value) {
        //得到SpinnerAdapter对象
        SpinnerAdapter apsAdapter = spinner.getAdapter();
        int k = apsAdapter.getCount();
        for (int i = 0; i < k; i++) {
            if (value.equals(apsAdapter.getItem(i).toString())) {
                // 默认选中项
                spinner.setSelection(i, true);
                break;
            }
        }
    }
}
