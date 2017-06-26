package com.example.ruolan.letgo.bean;

import java.io.Serializable;

/**
 * Created by liuwen on 2017/6/22.
 */
public class Dish implements Serializable {

    private String title;
    private String url;

    public Dish() {
    }

    public Dish(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
