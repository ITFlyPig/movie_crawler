package com.wangyuelin.app.crawler.dylol.bean;

public class MovieDownload {
    //下载的地址
    private String url;
    //下载名称
    private String name;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    @Override
    public String toString() {
       StringBuffer buffer = new StringBuffer();
       buffer.append("[ name=").append(name).append(" url=").append(url).append("]");
       return buffer.toString();

    }
}

