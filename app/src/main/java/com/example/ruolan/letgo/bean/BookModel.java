package com.example.ruolan.letgo.bean;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;

/**
 * Created by liuwen on 2017/6/27.
 */
@Entity
public class BookModel implements Serializable {
    @Id(autoincrement = true)
    private long id;
    private String booKName;//书名
    @Transient
    private String bookUrl; //url
    private String bookAuthor;//作者
    private String bookDetailUrl;//详情页面
    @Transient
    private String bookAuthorUrl;//作者书籍url
    @Transient
    private String bookDesc; //描述
    private String bookUpdateTime;//更新时间
    private String bookUpdateContent;//更新内容
    private String bookPic;//作品封面
    @Transient
    private String bookAuthorWriteTime;//作者出道时间
    @Transient
    private String bookType;//书籍类型
    @Transient
    private String bookWriteRead;//多少读者阅读过
    private String bookReadTime;//添加书架的时间
    private String bookFreeRead;//免费试读
    @Transient
    public BookTypeLayout bookTypeLayout;//书籍类型


    public enum BookTypeLayout {
        editHead, editData, newUpdateHead, newUpdateData, newBookHead, newBookData, freeTimeHead, freeTimeData;
    }

    public BookModel() {
    }

    public BookModel(String booKName, String bookUrl) {
        this.booKName = booKName;
        this.bookUrl = bookUrl;
    }

    @Generated(hash = 2037088771)
    public BookModel(long id, String booKName, String bookAuthor, String bookDetailUrl, String bookUpdateTime,
            String bookUpdateContent, String bookPic, String bookReadTime, String bookFreeRead) {
        this.id = id;
        this.booKName = booKName;
        this.bookAuthor = bookAuthor;
        this.bookDetailUrl = bookDetailUrl;
        this.bookUpdateTime = bookUpdateTime;
        this.bookUpdateContent = bookUpdateContent;
        this.bookPic = bookPic;
        this.bookReadTime = bookReadTime;
        this.bookFreeRead = bookFreeRead;
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

    public String getBookPic() {
        return bookPic;
    }

    public void setBookPic(String bookPic) {
        this.bookPic = bookPic;
    }

    public String getBookAuthorWriteTime() {
        return bookAuthorWriteTime;
    }

    public void setBookAuthorWriteTime(String bookAuthorWriteTime) {
        this.bookAuthorWriteTime = bookAuthorWriteTime;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public String getBookWriteRead() {
        return bookWriteRead;
    }

    public void setBookWriteRead(String bookWriteRead) {
        this.bookWriteRead = bookWriteRead;
    }

    public String getBookReadTime() {
        return bookReadTime;
    }

    public void setBookReadTime(String bookReadTime) {
        this.bookReadTime = bookReadTime;
    }

    public String getBookFreeRead() {
        return this.bookFreeRead;
    }

    public void setBookFreeRead(String bookFreeRead) {
        this.bookFreeRead = bookFreeRead;
    }
}
