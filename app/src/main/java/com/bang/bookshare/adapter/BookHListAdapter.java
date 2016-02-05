package com.bang.bookshare.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bang.bookshare.R;
import com.bang.bookshare.entity.Book;
import com.bang.bookshare.view.CircleImageView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 主页图书列表适配器
 *
 * @author Bang
 * @file com.bang.bookshare.adapter
 * @date 2016/2/2
 * @Version 1.0
 */
public class BookHListAdapter extends SimpleBaseAdapter<Book.EcListEntity> {

    private static Context sContext;

    private List<Book.EcListEntity> mList;

    public BookHListAdapter(Context context, List<Book.EcListEntity> list) {
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
            convertView = LayoutInflater.from(sContext).inflate(R.layout.item_book_h, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        final Book.EcListEntity entity = mList.get(position);
        // 绑定数据到holder
        holder.civHeadAfd.setImageUrl(entity.getBookName(), R.mipmap.ic_head, R.mipmap.ic_head);
        holder.tvBookName.setText(entity.getBookName());
        holder.tvUserName.setText(entity.getUserName());
        holder.tvProfile.setText(entity.getBookProfile());
        // 是否被借阅
        if (entity.getIsBorrowed().equals("NO"))
            holder.ivIsBorrowed.setVisibility(View.INVISIBLE);
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.civ_head_afd)
        CircleImageView civHeadAfd;
        @InjectView(R.id.tv_book_name)
        TextView tvBookName;
        @InjectView(R.id.tv_user_name)
        TextView tvUserName;
        @InjectView(R.id.tv_profile)
        TextView tvProfile;
        @InjectView(R.id.iv_is_borrowed)
        ImageView ivIsBorrowed;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
