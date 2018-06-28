package com.wangyuelin.app.crawler.dylol.bean;

import javax.persistence.Column;

public class LinkBean {
    @Column(name = "id")
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "link")
    private String link;//电影的显示标题
    @Column(name = "time")
    private String time;
    @Column(name = "movie_name")
    private String movieName;//电影的名称

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[ title=").append(title).append(";link=").append(link).append(";time=").append(time).append(" name=").append(movieName).append("]");
        return buffer.toString();
    }
}
