package com.bang.bookshare.utils;

/**
 * 网络请求常量工具类
 *
 * @author Bang
 * @file com.bang.bookshare.utils
 * @date 2016/1/31
 * @Version 1.0
 */
public class HttpPathUtil {

    // 请求网路IP
    public final static String IP = "http://www.heartguard.cn:8080";

    // 请求网路IPS
    public final static String IPS = "http://192.168.43.75:8080";

    // 请求浣客官网IP
    public final static String IP_USER_AGREE = "http://123.56.138.192:8002";

    // 请求浣客官网域名
    public final static String IP_WEB = "http://www.huankxy.com";

    // register and login
    public static String getRegisterAndLoginIfo() {
        return IPS + "/bookShare/registerAction.action";
    }

    // login
    public static String getLoginIfo() {
        return IPS + "/bookShare/loginAction.action";
    }

    // getBookIfo
    public static String getBookIfo() {
        return IPS + "/bookShare/findBookAction.action";
    }

    // deleteBook
    public static String deleteBook() {
        return IPS + "/bookShare/removeBookAction.action";
    }

    // getServiceNote
    public static String getSerNotefo() {
        return IP + "/demo/myClive?means=12345&url=www.heartguard.cn";
    }

    // getNum
    public static String getNumfo(int id) {
        return IP + "/demo/means" + id + ".png";
    }

    // 用户协议IP
    public static String getUserAgreeIfo() {
        return IP_USER_AGREE + "/protocol/";
    }

    // 常见问题IP
    public static String getProblemIfo() {
        return IP_USER_AGREE + "/faq/";
    }

    // 关于浣洗
    public static String getAboutAppfo() {
        return IP_WEB + "/";
    }
}
