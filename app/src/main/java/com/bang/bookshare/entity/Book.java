package com.bang.bookshare.entity;

import com.bang.bookshare.utils.GsonUtil;

import java.util.List;

/**
 * Book实体
 *
 * @author Bang
 * @file com.bang.bookshare.entity
 * @date 2016/2/2
 * @Version 1.0
 */
public class Book {


    /**
     * ecList : [{"bookAuthor":"神","bookCode":"0002","bookCollections":null,"bookName":"Java","bookProfile":"差书","creater":"小邦","createrTime":"2016-01-30T23:13:20","id":2,"isBorrowed":"NO","lastUpdateTime":"2016-01-30T23:13:20","lastUpdater":null,"photo":null,"publisherDate":null,"publisherName":null,"remake":null,"userId":11111111111,"userName":"小明"},{"bookAuthor":"陶","bookCode":"0001","bookCollections":null,"bookName":"C++","bookProfile":"好书","creater":"小邦","createrTime":"2016-01-30T23:13:19","id":1,"isBorrowed":"YES","lastUpdateTime":"2016-01-30T23:13:19","lastUpdater":null,"photo":null,"publisherDate":null,"publisherName":null,"remake":null,"userId":13751338740,"userName":"小邦"}]
     * failure : false
     * message :
     * success : true
     * totalCount : 2
     */

    private boolean failure;
    private String message;
    private boolean success;
    private int totalCount;
    /**
     * bookAuthor : 神
     * bookCode : 0002
     * bookCollections : null
     * bookName : Java
     * bookProfile : 差书
     * creater : 小邦
     * createrTime : 2016-01-30T23:13:20
     * id : 2
     * isBorrowed : NO
     * lastUpdateTime : 2016-01-30T23:13:20
     * lastUpdater : null
     * photo : null
     * publisherDate : null
     * publisherName : null
     * remake : null
     * userId : 11111111111
     * userName : 小明
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
        private String bookAuthor;
        private String bookCode;
        private Object bookCollections;
        private String bookName;
        private String bookProfile;
        private String creater;
        private String createrTime;
        private int id;
        private String isBorrowed;
        private String lastUpdateTime;
        private Object lastUpdater;
        private Object photo;
        private Object publisherDate;
        private Object publisherName;
        private Object remake;
        private long userId;
        private String userName;

        public void setBookAuthor(String bookAuthor) {
            this.bookAuthor = bookAuthor;
        }

        public void setBookCode(String bookCode) {
            this.bookCode = bookCode;
        }

        public void setBookCollections(Object bookCollections) {
            this.bookCollections = bookCollections;
        }

        public void setBookName(String bookName) {
            this.bookName = bookName;
        }

        public void setBookProfile(String bookProfile) {
            this.bookProfile = bookProfile;
        }

        public void setCreater(String creater) {
            this.creater = creater;
        }

        public void setCreaterTime(String createrTime) {
            this.createrTime = createrTime;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setIsBorrowed(String isBorrowed) {
            this.isBorrowed = isBorrowed;
        }

        public void setLastUpdateTime(String lastUpdateTime) {
            this.lastUpdateTime = lastUpdateTime;
        }

        public void setLastUpdater(Object lastUpdater) {
            this.lastUpdater = lastUpdater;
        }

        public void setPhoto(Object photo) {
            this.photo = photo;
        }

        public void setPublisherDate(Object publisherDate) {
            this.publisherDate = publisherDate;
        }

        public void setPublisherName(Object publisherName) {
            this.publisherName = publisherName;
        }

        public void setRemake(Object remake) {
            this.remake = remake;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getBookAuthor() {
            return bookAuthor;
        }

        public String getBookCode() {
            return bookCode;
        }

        public Object getBookCollections() {
            return bookCollections;
        }

        public String getBookName() {
            return bookName;
        }

        public String getBookProfile() {
            return bookProfile;
        }

        public String getCreater() {
            return creater;
        }

        public String getCreaterTime() {
            return createrTime;
        }

        public int getId() {
            return id;
        }

        public String getIsBorrowed() {
            return isBorrowed;
        }

        public String getLastUpdateTime() {
            return lastUpdateTime;
        }

        public Object getLastUpdater() {
            return lastUpdater;
        }

        public Object getPhoto() {
            return photo;
        }

        public Object getPublisherDate() {
            return publisherDate;
        }

        public Object getPublisherName() {
            return publisherName;
        }

        public Object getRemake() {
            return remake;
        }

        public long getUserId() {
            return userId;
        }

        public String getUserName() {
            return userName;
        }
    }

    /**
     * Gson解析Json数据
     */
    public static Book praseJson(String reponse) {
        GsonUtil gsonUtil = new GsonUtil();
        Book book = gsonUtil.GsonToBean(reponse, Book.class);
        return book;
    }
}
