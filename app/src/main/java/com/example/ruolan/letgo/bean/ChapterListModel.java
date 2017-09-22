package com.example.ruolan.letgo.bean;

import java.io.Serializable;

/**
 * Created by liuwen on 2017/9/18.
 */
public class ChapterListModel implements Serializable {


    private String url;//对应的每本书的url
    private int duxChapterIndex;//当前章数
    private String durChapterUrl;//当前章数对应的文章url;
    private String durChapterName;//当前章节名称
    private String tag;
    private Boolean hasCache = false;
    private String updateTime;

    public ChapterListModel() {
    }

    public ChapterListModel(String url, int duxChapterIndex, String durChapterUrl, String durChapterName, String tag, Boolean hasCache) {
        this.url = url;
        this.duxChapterIndex = duxChapterIndex;
        this.durChapterUrl = durChapterUrl;
        this.durChapterName = durChapterName;
        this.tag = tag;
        this.hasCache = hasCache;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDuxChapterIndex() {
        return duxChapterIndex;
    }

    public void setDuxChapterIndex(int duxChapterIndex) {
        this.duxChapterIndex = duxChapterIndex;
    }

    public String getDurChapterUrl() {
        return durChapterUrl;
    }

    public void setDurChapterUrl(String durChapterUrl) {
        this.durChapterUrl = durChapterUrl;
    }

    public String getDurChapterName() {
        return durChapterName;
    }

    public void setDurChapterName(String durChapterName) {
        this.durChapterName = durChapterName;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Boolean getHasCache() {
        return hasCache;
    }

    public void setHasCache(Boolean hasCache) {
        this.hasCache = hasCache;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
