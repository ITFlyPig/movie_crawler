package com.wangyuelin.app.crawler.base;

/**
 * 描述: 下载链接
 *
 * @outhor wangyuelin
 * @create 2018-06-11 下午2:51
 */
public class DownloadLink {
    private int id;
    private String linkName;
    private String linkUrl;
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}