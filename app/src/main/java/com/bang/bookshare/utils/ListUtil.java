package com.bang.bookshare.utils;

import java.util.Iterator;
import java.util.List;

/**
 * 集合操作工具类
 *
 * @author Bang
 * @file com.bang.bookshare.utils
 * @date 2016/1/31
 * @Version 1.0
 */
public class ListUtil {

    /**
     * 删除集合中所有数据
     */
    public static void removeAll(List list) {
        Iterator iter = list.iterator();
        while (iter.hasNext()) {
            // 没有，会报异常
            iter.next();
            iter.remove();
        }
    }

    /**
     * 将集合转化为字符串
     *
     * @param list
     * @return
     */
    public static String listToString(List list)  {
        GsonUtil gsonUtil = new GsonUtil();
        String listString = gsonUtil.GsonString(list);
        return listString;
    }

    /**
     * 将字符串转化为集合
     *
     * @param listString
     * @return
     */
//    @SuppressWarnings("unchecked")
//    public static List stringToList(String listString) {
//        GsonUtil gsonUtil = new GsonUtil();
//        List<WashOrder> list = gsonUtil.gsonToList(listString, WashOrder.class);
//        return list;
//    }

}
