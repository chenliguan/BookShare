package com.bang.bookshare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.bang.bookshare.R;
import com.bang.bookshare.entity.Book;
import com.bang.bookshare.utils.ActivityStack;
import com.bang.bookshare.utils.LogUtil;
import com.bang.bookshare.utils.PreferencesUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 聊天页面
 *
 * @author Bang
 * @file com.bang.bookshare.activity
 * @date 2016/2/2
 * @Version 1.0
 */
public class ChatActivity extends FrameActivity {

    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.msg_list_view)
    ListView msgListView;
    @InjectView(R.id.input_text)
    EditText inputText;

    private String relatedUserId;
    private AVIMConversation mConversation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
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
        tvTitle.setText(R.string.chat);
        // 取出BookInfoActivity传过来的userId
        Intent intent = this.getIntent();
        relatedUserId = String.valueOf(intent.getSerializableExtra("relatedUserId"));
    }

    /**
     * 初始化变量
     */
    private void bindData() {
        // 创建会话
        createConversation();
    }

    /**
     * 监听实现
     */
    @OnClick({R.id.iv_back, R.id.btn_send})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.iv_back:
                finish();
                break;

            case R.id.btn_send:
                // 发送消息
                String msg = inputText.getText().toString();
                if (!msg.equals("")) {
                    sendMsg(msg);
                } else {
                    LogUtil.showLog("msg为空");
                }
                break;

            default:
                break;
        }
    }

    /**
     * 创建会话
     */
    public void createConversation() {

        final String loginPhone = PreferencesUtils.getLoginPhone(this);
        List<String> clientIds = new ArrayList<String>();
        clientIds.add(loginPhone);
        clientIds.add(relatedUserId);

        // 我们给对话增加一个自定义属性 type，表示单聊还是群聊
        Map<String, Object> attr = new HashMap<>();
        attr.put("type", 0);

        // 获取当前用户的实例
        AVIMClient imClient = AVIMClient.getInstance(loginPhone);
        // 创建之间的对话
        imClient.createConversation(clientIds, attr, new AVIMConversationCreatedCallback() {

            @Override
            public void done(AVIMConversation conversation, AVIMException e) {
                if (e == null) {
                    mConversation = conversation;
                    LogUtil.showLog("创建会话成功！");
                } else {
                    LogUtil.showLog("创建会话失败！");
                }
            }
        });
    }

    /**
     * 发送消息
     */
    public void sendMsg(String msg) {
        AVIMTextMessage message = new AVIMTextMessage();
        message.setText(msg);

        // 发送消息
        mConversation.sendMessage(message, new AVIMConversationCallback() {
            @Override
            public void done(AVIMException e) {
                if (e == null) {
                    LogUtil.showLog("发送成功！");
                }
            }
        });
    }

}
