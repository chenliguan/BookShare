package com.bang.bookshare.utils;

/**
 * 常量工具类
 *
 * @author Bang
 * @file com.bang.bookshare.utils
 * @date 2016/1/31
 * @Version 1.0
 */
public class ConstantUtil {

    // 文件内存路径
    public static String FILELOCATION = "/storage/sdcard0/files/";

    /*
     * Handler消息处理
     */
    // Handler失败标志
    public final static int FLAG_LOGN_ERROR = 0;
    // Handler成功标志
    public final static int FLAG_LOGN_SUCCESS = 1;

    /**
     * 时间
     */
    // 延迟2.3秒
    public static final long SPLASH_DELAY_MS = 2300;
    // Popupwindow显示时间
    public static final long POPWIN_DELAY_MS = 1500;
    // loadingDialog显示时间
    public static final long LOADING_DELAY_MS = 1500;

    /**
     * sharedpreferences保存表名
     */
    // 保存是否第一次登陆客户端
    public static final String SHARED_NAME_FIRST = "first_pref";
    // 保存用户登录信息
    public static final String SHARED_NAME_LOGIN = "login_data";
    // 保存用户信息
    public static final String SHARED_NAME_USERINFO = "user_info";
    // 保存城市
    public static final String SHARED_NAME_CITY = "city_info";

    /**
     * sharedpreferences保存键名
     */
    // 保存是否已经引导记录
    public static final String SHARED_KEY_FIRST = "first_key";
    // 保存用户名键名
    public static final String SHARED_KEY_PHONE = "login_phone";
    // 保存密码键名
    public static final String SHARED_KEY_PASSWORD = "password";

    // 保存用户信息-标志键名
    public static final String SHARED_KEY_FLAG = "user_flag";
    // 保存用户信息-姓名键名
    public static final String SHARED_KEY_NAME = "user_name";
    // 保存用户信息-手机号(用户Id)键名
    public static final String SHARED_KEY_USERID = "user_id";
    // 保存用户信息-简介键名
    public static final String SHARED_KEY_PROFILE = "user_profile";
    // 保存用户信息-学校键名
    public static final String SHARED_KEY_SCHOOL = "user_school";
    // 保存用户信息-班级键名
    public static final String SHARED_KEY_CLASSES = "user_classes";
    // 保存用户信息-宿舍号键名
    public static final String SHARED_KEY_DORM = "user_dorm";

    // 保存城市-键名
    public static final String SHARED_KEY_CITY = "city_key";

    /**
     * volley请求标签
     */
    public static final String TAG_STRING_REQUEST = "StringRequest";
    public static final String TAG_JSON_REQUEST = "jsonObjectRequest";
    public static final String TAG_IMAGE_REQUEST = "ImageRequest";
    public static final String TAG_IMAGE_LOADER = "ImageLoader";

    /*
     *底部tab的选项
     */
    // TAB数目
    public final static int TAB_COUNT = 3;
    // 主页
    public static final int TAB_HOME = 0;
    // 我
    public static final int TAB_MY = 1;
    // 更多
    public static final int TAB_MORE = 2;

    /**
     * Intent识别哪个跳转ACTIVITY/FRAGMENT
     */
    // loginActivity
    public static final String VALUE_LOGIN_ACTIVITY = "LoginActivity";
    // moreFragment
    public static final String VALUE_MAIN_ACTIVITY = "MainActivity";
    // MyInfoActivity
    public static final String VALUE_MyInfo_ACTIVITY = "MyInfoActivity";
    // MyBookInfoActivity
    public static final String VALUE_MyBookInfo_ACTIVITY = "MyBookInfoActivity";

    // KEY值1
    public static final String INTENT_KEY = "KEY";
    // KEY值2
    public static final String INTENT_PARAM = "PARAM";

    /**
     * 页面跳转请求码
     */
    public static final int CODE_HOME_AWASH = 1001;
    public static final int CODE_HOME_CITY = 1002;

    public static final int CODE_CUSTOMER = 3000;
    public static final int CODE_PROBLEM = 3001;
    public static final int CODE_SERVICESCOPE = 3003;
    public static final int CODE_ABOUTUS = 3004;
    public static final int CODE_USERAGREE = 3005;
    public static final int CODE_FEEDBACK = 3006;

    /**
     * 页面轮询
     */
    // 请求切换显示的View。
    public static final int MSG_UPDATE_IMAGE = 1;

    /**
     * 密码个数
     */
    public static final int PASS_NUM = 6;


    /**
     * 广播更新界面
     */
    // MyFragment-->MyInfoActivity更新UI广播标志
    public static final String ACTION_B_U_P_UPDATEUI = "Action_BU_updateUI";
    // MyFragment-->MyBookInfoActivity更新UI广播标志
    public static final String ACTION_HC_UPDATEUI = "Action_HC_updateUI";

    // SmsObserver
    public static final int MSG_RECEIVED_CODE = 1;

    /**
     * MainActivity状态保存
     */
    public static final String OUTSTATE_BOOLEAN = "isRecycle";
    public static final String OUTSTATE_INT = "index";
}
