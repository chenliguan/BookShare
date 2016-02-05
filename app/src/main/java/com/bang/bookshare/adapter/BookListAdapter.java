package com.bang.bookshare.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bang.bookshare.R;
import com.bang.bookshare.entity.Book;
import com.bang.bookshare.utils.HttpPathUtil;
import com.bang.bookshare.view.CircleImageView;
import com.bang.bookshare.volley.VolleyHandler;
import com.bang.bookshare.volley.VolleyHttpRequest;

import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 图书列表适配器
 *
 * @author Bang
 * @file com.bang.bookshare.adapter
 * @date 2016/2/2
 * @Version 1.0
 */
public class BookListAdapter extends SimpleBaseAdapter<Book.EcListEntity> {

    private static Context sContext;

    private List<Book.EcListEntity> mList;

    public BookListAdapter(Context context, List<Book.EcListEntity> list) {
        super(context, list);
        this.sContext = context;
        this.mList = list;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView != null) {
            // 3、获取ViewHolder
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(sContext).inflate(R.layout.item_book, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        final Book.EcListEntity entity = mList.get(position);
        // 绑定数据到holder
        holder.civHeadAfd.setImageUrl(entity.getBookName(), R.mipmap.ic_head, R.mipmap.ic_head);
        holder.tvBookName.setText(entity.getBookName());
        holder.tvProfile.setText(entity.getBookProfile());
        // 是否被借阅
        if (entity.getIsBorrowed().equals("NO"))
            holder.ivIsBorrowed.setVisibility(View.INVISIBLE);
        // 删除监听
        holder.llytDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                                            showMsg(sContext.getString(R.string.msg_loading_error));
                                        } else {
                                            // 解析数据
                                            Book book = Book.praseJson(response);
                                            // 请求业务判断
                                            if (book.isSuccess()) {
                                                // 更新本地
                                                mList.remove(position);
                                                notifyDataSetChanged();
                                                showMsg(sContext.getString(R.string.delete_success));
                                            } else {
                                                showMsg(sContext.getString(R.string.delete_failue));
                                            }
                                        }
                                    }

                                    @Override
                                    public void reqError(String error) {
                                        dialog.dismiss();
                                        showMsg(sContext.getString(R.string.msg_con_server_error));
                                    }
                                };
                                // Post请求键值对
                                HashMap<String, String> params = new HashMap<>();
                                params.put("id", String.valueOf(entity.getId()));
                                VolleyHttpRequest.String_request(HttpPathUtil.deleteBook(), params, volleyRequest);
                                break;

                            case Dialog.BUTTON_NEGATIVE:
                                // 隐退对话框
                                dialog.dismiss();
                                break;
                        }
                    }
                };
                //dialog参数设置
                AlertDialog.Builder builder = new AlertDialog.Builder(sContext);
                //设置内容
                builder.setMessage(sContext.getString(R.string.is_delete));
                builder.setPositiveButton(sContext.getString(R.string.sure), dialogOnclicListener);
                builder.setNegativeButton(sContext.getString(R.string.no), dialogOnclicListener);
                builder.create().show();
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.civ_head_afd)
        CircleImageView civHeadAfd;
        @InjectView(R.id.tv_book_name)
        TextView tvBookName;
        @InjectView(R.id.tv_profile)
        TextView tvProfile;
        @InjectView(R.id.iv_is_borrowed)
        ImageView ivIsBorrowed;
        @InjectView(R.id.llyt_delete)
        RelativeLayout llytDelete;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
