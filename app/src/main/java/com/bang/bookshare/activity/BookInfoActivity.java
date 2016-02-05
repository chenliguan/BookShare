package com.bang.bookshare.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bang.bookshare.R;
import com.bang.bookshare.entity.Book;
import com.bang.bookshare.entity.User;
import com.bang.bookshare.utils.ActivityStack;
import com.bang.bookshare.utils.EnumDataChange;
import com.bang.bookshare.utils.HttpPathUtil;
import com.bang.bookshare.utils.LogUtil;
import com.bang.bookshare.view.CircleImageView;
import com.bang.bookshare.volley.VolleyHandler;
import com.bang.bookshare.volley.VolleyHttpRequest;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 图书详情页面
 *
 * @author Bang
 * @file com.bang.bookshare.activity
 * @date 2016/1/31
 * @Version 1.0
 */
public class BookInfoActivity extends FrameActivity {

    @InjectView(R.id.civ_head_afd)
    CircleImageView civHeadAfd;
    @InjectView(R.id.tv_book_name)
    TextView tvBookName;
    @InjectView(R.id.tv_user_name)
    TextView tvUserName;
    @InjectView(R.id.tv_profile)
    TextView tvProfile;
    @InjectView(R.id.tv_author)
    TextView tvAuthor;
    @InjectView(R.id.tv_is_borrowed)
    TextView tvIsBorrowed;
    @InjectView(R.id.tv_book_collections)
    TextView tvBookCollections;
    @InjectView(R.id.tv_phone)
    TextView tvPhone;
    @InjectView(R.id.tv_address)
    TextView tvAddress;
    @InjectView(R.id.tv_title)
    TextView tvTitle;

    private Book.EcListEntity ecListEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        ButterKnife.inject(this);
        ActivityStack.getInstance().addActivity(this);

        /**
         * 初始化变量
         */
        initVariable();
        /**
         * 绑定/设置数据操作
         */
        bindData();
    }

    /**
     * 初始化变量
     */
    private void initVariable() {
        tvTitle.setText(getString(R.string.book_detail));
        // 暂时以此图片代替
        civHeadAfd.setImageUrl(getString(R.string.book), R.mipmap.ic_head, R.mipmap.ic_head);
        // 获取传来的book
        Intent intent = this.getIntent();
        ecListEntity = (Book.EcListEntity) intent.getSerializableExtra("ecListEntity");

        tvUserName.setText(ecListEntity.getUserName());
        tvIsBorrowed.setText(ecListEntity.getIsBorrowed());
        tvBookName.setText(ecListEntity.getBookName());
        tvAuthor.setText(ecListEntity.getBookAuthor());
        tvBookCollections.setText(ecListEntity.getBookCollections());
        tvProfile.setText(ecListEntity.getBookProfile());
        tvPhone.setText(String.valueOf(ecListEntity.getUserId()));
    }

    /**
     * 绑定/设置数据操作
     */
    public void bindData() {
        // 请求获取用户信息
        getUserIfo();
    }

    /**
     * 监听实现
     */
    @OnClick({R.id.iv_back, R.id.rlyt_phone, R.id.btn_chat})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.rlyt_phone:
                // 用户拨打书主电话
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tvPhone.getText()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;

            case R.id.btn_chat:
                Intent intent2 = new Intent(this, ChatActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("relatedUserId",ecListEntity.getUserId());
                intent2.putExtras(bundle);
                startActivity(intent2);

                LogUtil.showLog("ecListEntity.getUserId():" + ecListEntity.getUserId());
                break;

            default:
                break;
        }
    }

    /**
     * 请求获取用户信息
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
                        String userSchool = null;
                        if (entity.getUserSchool() != null) {
                            userSchool = EnumDataChange.EN_CH(entity.getUserSchool());
                        }
                        // 设置页面当前的值
                        String address = userSchool + entity.getClasses() + entity.getAdress();
                        tvAddress.setText(address);
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
        params.put("userId", String.valueOf(ecListEntity.getUserId()));
        VolleyHttpRequest.String_request(HttpPathUtil.getUserIfo(), params, volleyRequest);
    }
}
