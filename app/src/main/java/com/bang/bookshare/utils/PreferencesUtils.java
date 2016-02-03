package com.bang.bookshare.utils;

import android.content.Context;
import android.content.SharedPreferences.Editor;

import com.bang.bookshare.R;


/**
 * 数据持久化Utils工具类
 *
 * @author Bang
 * @file com.bang.bookshare.utils
 * @date 2016/1/31
 * @Version 1.0
 */
public class PreferencesUtils {

    /**
     * 写入登录信息
     */
    public static void setLoginInfo(Context context, String loginPhone, String passWord) {
        // 1.实例化SharedPreferences对象&SharedPreferences.Editor对象
        Editor editor = context.getSharedPreferences(ConstantUtil.SHARED_NAME_LOGIN, Context.MODE_PRIVATE).edit();
        // 2.editor.put()存入数据
        editor.putString(ConstantUtil.SHARED_KEY_PHONE, loginPhone);
        editor.putString(ConstantUtil.SHARED_KEY_PASSWORD, passWord);
        // 3.commit()提交修改
        editor.apply();
    }

    /**
     * 获取登录手机号
     */
    public static String getLoginPhone(Context context) {
        return context.getSharedPreferences(ConstantUtil.SHARED_NAME_LOGIN, Context.MODE_PRIVATE)
                .getString(ConstantUtil.SHARED_KEY_PHONE, "");
    }

    /**
     * 获取密码
     */
    public static String getPassWord(Context context) {
        return context.getSharedPreferences(ConstantUtil.SHARED_NAME_LOGIN, Context.MODE_PRIVATE)
                .getString(ConstantUtil.SHARED_KEY_PASSWORD, "");
    }

    /**
     * 写入是否已经引导记录
     */
    public static void setIsFirst(Context context) {
        Editor editor = context.getSharedPreferences(ConstantUtil.SHARED_NAME_FIRST, Context.MODE_PRIVATE).edit();
        editor.putBoolean(ConstantUtil.SHARED_KEY_FIRST, true);
        editor.apply();
    }

    /**
     * 获取是否已经引导记录
     */
    public static Boolean getIsFirst(Context context) {
        return context.getSharedPreferences(ConstantUtil.SHARED_NAME_FIRST, Context.MODE_PRIVATE)
                .getBoolean(ConstantUtil.SHARED_KEY_FIRST, false);
    }

    /**
     * 写入用户信息
     */
    public static void setUserInfo(Context context, boolean isShared, String name,
                                   String phone, boolean gender, String area, String commu, String detail_addr) {
        Editor editor = context.getSharedPreferences(ConstantUtil.SHARED_NAME_USERINFO, Context.MODE_PRIVATE).edit();
        editor.putString(ConstantUtil.SHARED_KEY_NAME, name);
        editor.putBoolean(ConstantUtil.SHARED_KEY_FLAG, isShared);
        editor.putString(ConstantUtil.SHARED_KEY_USERPHONE, phone);
        editor.putBoolean(ConstantUtil.SHARED_KEY_GENDER, gender);
        editor.putString(ConstantUtil.SHARED_KEY_AREA, area);
        editor.putString(ConstantUtil.SHARED_KEY_COMMU, commu);
        editor.putString(ConstantUtil.SHARED_KEY_DETAIL_ADDR, detail_addr);
        editor.apply();
    }

    /**
     * 获取用户名字
     */
    public static String getUserName(Context context) {
        return context.getSharedPreferences(ConstantUtil.SHARED_NAME_USERINFO, Context.MODE_PRIVATE)
                .getString(ConstantUtil.SHARED_KEY_NAME, "");
    }

    /**
     * 获取用户手机号
     */
    public static String getUserPhone(Context context) {
        return context.getSharedPreferences(ConstantUtil.SHARED_NAME_USERINFO, Context.MODE_PRIVATE)
                .getString(ConstantUtil.SHARED_KEY_USERPHONE, "");
    }

    /**
     * 获取用户性别
     */
    public static Boolean getUserGender(Context context) {
        return context.getSharedPreferences(ConstantUtil.SHARED_NAME_USERINFO, Context.MODE_PRIVATE)
                .getBoolean(ConstantUtil.SHARED_KEY_GENDER, true);
    }

    /**
     * 获取用户区域
     */
    public static String getUserArea(Context context) {
        return context.getSharedPreferences(ConstantUtil.SHARED_NAME_USERINFO, Context.MODE_PRIVATE)
                .getString(ConstantUtil.SHARED_KEY_AREA, "");
    }

    /**
     * 获取用户小区
     */
    public static String getUserCommu(Context context) {
        return context.getSharedPreferences(ConstantUtil.SHARED_NAME_USERINFO, Context.MODE_PRIVATE)
                .getString(ConstantUtil.SHARED_KEY_COMMU, "");
    }

    /**
     * 获取用户详细地址
     */
    public static String getUserAddr(Context context) {
        return context.getSharedPreferences(ConstantUtil.SHARED_NAME_USERINFO, Context.MODE_PRIVATE)
                .getString(ConstantUtil.SHARED_KEY_DETAIL_ADDR, "");
    }


}
