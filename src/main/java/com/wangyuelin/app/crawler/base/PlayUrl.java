package com.wangyuelin.app.crawler.base;

/**
 * 描述: 播放的url
 *
 * @outhor wangyuelin
 * @create 2018-06-11 下午6:15
 */
public class PlayUrl {
    private int id;
    private String name;
    private String url;
    private String type;//播放的类型
    private int movieId;

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}