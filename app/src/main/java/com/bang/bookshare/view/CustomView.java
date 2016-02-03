package com.bang.bookshare.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bang.bookshare.R;


/**
 * 自定义View
 *
 * @author Bang
 * @file com.bang.bookshare.view
 * @date 2016/2/2
 * @Version 1.0
 */
public class CustomView extends FrameLayout {

    private ImageView mMoreImage;
    private TextView mCustomer;
    private ImageView mRightImage;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 通过布局view_custom.xml自定义每一行的格式
        // 再获取到view,并获取到具体的控件
        View view = View.inflate(context, R.layout.item_more, this);
        mMoreImage = (ImageView) view.findViewById(R.id.iv_more);
        mCustomer = (TextView) view.findViewById(R.id.tv_customer);
        mRightImage = (ImageView) view.findViewById(R.id.iv_right);

        // 通过attr.xml取得declare-styleable集合，从集合里取出相对应的属性值
        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.zView);
        Drawable imageMore = typeArray.getDrawable(R.styleable.zView_iconMore);
        String customerString = typeArray.getString(R.styleable.zView_textCustomer);
        Drawable imageRight = typeArray.getDrawable(R.styleable.zView_iconRight);

        // 将attr.xml取得的属性值添加到自定义的控件中绑定
        mMoreImage.setImageDrawable(imageMore);
        mCustomer.setText(customerString);
        mRightImage.setImageDrawable(imageRight);

        // 关闭资源
        typeArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
