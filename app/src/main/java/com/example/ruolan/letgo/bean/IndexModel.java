package com.example.ruolan.letgo.bean;

import java.io.Serializable;

/**
 * Created by liuwen on 2017/6/29.
 */
public class IndexModel implements Serializable {

    private String name;
    private String bookUrl;
    private String webUrl;

    public IndexModel() {
    }

    public IndexModel(String name) {
        this.name = name;
    }

    public IndexModel(String name, String bookUrl, String webUrl) {
        this.name = name;
        this.bookUrl = bookUrl;
        this.webUrl = webUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBookUrl() {
        return bookUrl;
    }

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }
}
