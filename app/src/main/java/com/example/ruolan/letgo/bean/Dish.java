package com.example.ruolan.letgo.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by liuwen on 2017/6/22.
 */
@Entity
public class Dish implements Serializable {

    @Id(autoincrement = true)
    private long id;
    private String title;
    private String url;

    public Dish() {
    }

    public Dish(String title) {
        this.title = title;
    }

    public Dish(String title, String url) {
        this.title = title;
        this.url = url;
    }

    @Generated(hash = 589957198)
    public Dish(long id, String title, String url) {
        this.id = id;
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

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
