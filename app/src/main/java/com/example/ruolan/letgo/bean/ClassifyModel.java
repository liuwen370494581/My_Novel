package com.example.ruolan.letgo.bean;

import java.io.Serializable;

/**
 * Created by liuwen on 2017/6/29.
 */
public class ClassifyModel implements Serializable {

    private long id;
    private String bookName;//书名
    private String bookCount;//共有多少书籍
    private String bookWebUrl;//对应的网站

    public ClassifyModel() {

    }

    public ClassifyModel(String bookWebUrl, String bookName, String bookCount) {
        this.bookWebUrl = bookWebUrl;
        this.bookName = bookName;
        this.bookCount = bookCount;
    }

    public ClassifyModel(String bookName) {
        this.bookName = bookName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookCount() {
        return bookCount;
    }

    public void setBookCount(String bookCount) {
        this.bookCount = bookCount;
    }

    public String getBookWebUrl() {
        return bookWebUrl;
    }

    public void setBookWebUrl(String bookWebUrl) {
        this.bookWebUrl = bookWebUrl;
    }


}
