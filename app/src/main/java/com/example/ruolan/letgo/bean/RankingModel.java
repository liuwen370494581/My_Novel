package com.example.ruolan.letgo.bean;

import java.io.Serializable;

/**
 * Created by liuwen on 2017/6/26.
 */
public class RankingModel implements Serializable {
    private int url;
    private String name;
    private String WebUrl;

    public RankingModel() {
    }


    public RankingModel(int url, String name) {
        this.url = url;
        this.name = name;
    }

    public RankingModel(int url, String name, String webUrl) {
        this.url = url;
        this.name = name;
        WebUrl = webUrl;
    }

    public String getWebUrl() {
        return WebUrl;
    }

    public void setWebUrl(String webUrl) {
        WebUrl = webUrl;
    }

    public int getUrl() {
        return url;
    }

    public void setUrl(int url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
