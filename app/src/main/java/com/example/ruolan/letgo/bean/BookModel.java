package com.example.ruolan.letgo.bean;

import java.io.Serializable;

/**
 * Created by liuwen on 2017/6/27.
 */
public class BookModel implements Serializable {

    private long id;
    private String booKName;
    private String bookUrl; //url
    private String bookAuthor;//作者
    private String bookDetailUrl;//详情页面
    private String bookAuthorUrl;//作者书籍集合
    private String bookDesc; //描述
    private String bookUpdateTime;//更新时间
    private String bookUpdateContent;//更新内容

    public BookModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBooKName() {
        return booKName;
    }

    public void setBooKName(String booKName) {
        this.booKName = booKName;
    }

    public String getBookUrl() {
        return bookUrl;
    }

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookDetailUrl() {
        return bookDetailUrl;
    }

    public void setBookDetailUrl(String bookDetailUrl) {
        this.bookDetailUrl = bookDetailUrl;
    }

    public String getBookAuthorUrl() {
        return bookAuthorUrl;
    }

    public void setBookAuthorUrl(String bookAuthorUrl) {
        this.bookAuthorUrl = bookAuthorUrl;
    }

    public String getBookDesc() {
        return bookDesc;
    }

    public void setBookDesc(String bookDesc) {
        this.bookDesc = bookDesc;
    }

    public String getBookUpdateTime() {
        return bookUpdateTime;
    }

    public void setBookUpdateTime(String bookUpdateTime) {
        this.bookUpdateTime = bookUpdateTime;
    }

    public String getBookUpdateContent() {
        return bookUpdateContent;
    }

    public void setBookUpdateContent(String bookUpdateContent) {
        this.bookUpdateContent = bookUpdateContent;
    }
}
