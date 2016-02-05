package com.bang.bookshare.utils;

import android.content.Context;
import android.content.SharedPreferences.Editor;

import com.bang.bookshare.R;
import com.bang.bookshare.activity.MyInfoActivity;


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
    public static void setUserInfo(Context context, boolean isShared, String userName,
                                   String userId, String userProfile, String userSchool, String classes, String dorm) {
        Editor editor = context.getSharedPreferences(ConstantUtil.SHARED_NAME_USERINFO, Context.MODE_PRIVATE).edit();
        editor.putString(ConstantUtil.SHARED_KEY_NAME, userName);
        editor.putBoolean(ConstantUtil.SHARED_KEY_FLAG, isShared);
        editor.putString(ConstantUtil.SHARED_KEY_USERID, userId);
        editor.putString(ConstantUtil.SHARED_KEY_PROFILE, userProfile);
        editor.putString(ConstantUtil.SHARED_KEY_SCHOOL, userSchool);
        editor.putString(ConstantUtil.SHARED_KEY_CLASSES, classes);
        editor.putString(ConstantUtil.SHARED_KEY_DORM, dorm);
        editor.apply();
    }

    public static final String SHARED_KEY_USERID = "user_id";
    // 保存用户信息-简介键名

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
    public static String getUserId(Context context) {
        return context.getSharedPreferences(ConstantUtil.SHARED_NAME_USERINFO, Context.MODE_PRIVATE)
                .getString(ConstantUtil.SHARED_KEY_USERID, "");
    }

    /**
     * 获取用户简介
     */
    public static String getUserProfile(Context context) {
        return context.getSharedPreferences(ConstantUtil.SHARED_NAME_USERINFO, Context.MODE_PRIVATE)
                .getString(ConstantUtil.SHARED_KEY_PROFILE, "");
    }

    /**
     * 获取用户学校
     */
    public static String getUserSchool(Context context) {
        return context.getSharedPreferences(ConstantUtil.SHARED_NAME_USERINFO, Context.MODE_PRIVATE)
                .getString(ConstantUtil.SHARED_KEY_SCHOOL, "");
    }

    /**
     * 获取用户班级
     */
    public static String getUserClass(Context context) {
        return context.getSharedPreferences(ConstantUtil.SHARED_NAME_USERINFO, Context.MODE_PRIVATE)
                .getString(ConstantUtil.SHARED_KEY_CLASSES, "");
    }

    /**
     * 获取用户宿舍号(详细地址)
     */
    public static String getUserDorm(Context context) {
        return context.getSharedPreferences(ConstantUtil.SHARED_NAME_USERINFO, Context.MODE_PRIVATE)
                .getString(ConstantUtil.SHARED_KEY_DORM, "");
    }

}
