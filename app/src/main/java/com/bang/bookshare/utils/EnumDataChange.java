package com.bang.bookshare.utils;

/**
 * 枚举类型切换
 *
 * @author Bang
 * @file com.bang.bookshare.utils
 * @date 2016/2/3
 * @Version 1.0
 */
public class EnumDataChange {

    /**
     * 英文转中文
     * @param string
     * @return
     */
    public static String EN_CH(String string) {
        switch (string) {
            case "DONGGUANINSTITUTEOFTECHNOLOGY":
                return "东莞理工学院";

            case "CITYCOLLEGEOFDONGGUANUNIVERSITYOFTECHNOLOGY":
                return "东莞理工学院城市学院";

            case "DONGGUANVOCATIONALANDTECHNICALCOLLEGE":
                return "东莞职业技术学院";

            case "GUANGDONGMEDICALCOLLEGE":
                return "广东医学院";

            default:
                return null;
        }
    }

    /**
     * 中文转英文
     * @param string
     * @return
     */
    public static String CH_EN(String string) {
        switch (string) {
            case "东莞理工学院":
                return "DONGGUANINSTITUTEOFTECHNOLOGY";

            case "东莞理工学院城市学院":
                return "CITYCOLLEGEOFDONGGUANUNIVERSITYOFTECHNOLOGY";

            case "东莞职业技术学院":
                return "DONGGUANVOCATIONALANDTECHNICALCOLLEGE";

            case "广东医学院":
                return "GUANGDONGMEDICALCOLLEGE";

            default:
                return null;
        }
    }
}
