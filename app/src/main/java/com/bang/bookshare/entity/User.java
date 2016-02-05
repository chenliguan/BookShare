package com.bang.bookshare.entity;

import com.bang.bookshare.utils.GsonUtil;

import java.util.List;

/**
 * User实体
 *
 * @author Bang
 * @file com.bang.bookshare.entity
 * @date 2016/1/31
 * @Version 1.0
 */
public class User {

    /**
     * ecList : [{"adress":"宿舍14513","bitmap":null,"classes":"软件1班","creater":"小邦","createrTime":"2016-01-30T23:13:00","lastUpdateTime":"2016-01-30T23:13:00","lastUpdater":null,"passWord":"123456","remake":null,"userId":13751338740,"userName":"小邦","userProfile":null,"userSchool":"DONGGUANINSTITUTEOFTECHNOLOGY"}]
     * failure : false
     * message :
     * success : true
     * totalCount : 0
     */

    private boolean failure;
    private String message;
    private boolean success;
    private int totalCount;
    /**
     * adress : 宿舍14513
     * bitmap : null
     * classes : 软件1班
     * creater : 小邦
     * createrTime : 2016-01-30T23:13:00
     * lastUpdateTime : 2016-01-30T23:13:00
     * lastUpdater : null
     * passWord : 123456
     * remake : null
     * userId : 13751338740
     * userName : 小邦
     * userProfile : null
     * userSchool : DONGGUANINSTITUTEOFTECHNOLOGY
     */

    public List<EcListEntity> ecList;

    public void setFailure(boolean failure) {
        this.failure = failure;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void setEcList(List<EcListEntity> ecList) {
        this.ecList = ecList;
    }

    public boolean isFailure() {
        return failure;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public List<EcListEntity> getEcList() {
        return ecList;
    }

    public static class EcListEntity {
        private String adress;
        private String bitmap;
        private String classes;
        private String creater;
        private String createrTime;
        private String lastUpdateTime;
        private String lastUpdater;
        private String passWord;
        private String remake;
        private long userId;
        private String userName;
        private String userProfile;
        private String userSchool;

        public void setAdress(String adress) {
            this.adress = adress;
        }

        public void setBitmap(String bitmap) {
            this.bitmap = bitmap;
        }

        public void setClasses(String classes) {
            this.classes = classes;
        }

        public void setCreater(String creater) {
            this.creater = creater;
        }

        public void setCreaterTime(String createrTime) {
            this.createrTime = createrTime;
        }

        public void setLastUpdateTime(String lastUpdateTime) {
            this.lastUpdateTime = lastUpdateTime;
        }

        public void setLastUpdater(String lastUpdater) {
            this.lastUpdater = lastUpdater;
        }

        public void setPassWord(String passWord) {
            this.passWord = passWord;
        }

        public void setRemake(String remake) {
            this.remake = remake;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public void setUserProfile(String userProfile) {
            this.userProfile = userProfile;
        }

        public void setUserSchool(String userSchool) {
            this.userSchool = userSchool;
        }

        public String getAdress() {
            return adress;
        }

        public String getBitmap() {
            return bitmap;
        }

        public String getClasses() {
            return classes;
        }

        public String getCreater() {
            return creater;
        }

        public String getCreaterTime() {
            return createrTime;
        }

        public String getLastUpdateTime() {
            return lastUpdateTime;
        }

        public String getLastUpdater() {
            return lastUpdater;
        }

        public String getPassWord() {
            return passWord;
        }

        public String getRemake() {
            return remake;
        }

        public long getUserId() {
            return userId;
        }

        public String getUserName() {
            return userName;
        }

        public String getUserProfile() {
            return userProfile;
        }

        public String getUserSchool() {
            return userSchool;
        }
    }

    /**
     * Gson解析Json数据
     */
    public static User praseJson(String reponse) {
        GsonUtil gsonUtil = new GsonUtil();
        User user = gsonUtil.GsonToBean(reponse, User.class);
        return user;
    }
}
