package com.wangyuelin.app.appserver.bean;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 描述: 电影列表的需要字段
 *
 * @outhor wangyuelin
 * @create 2018-05-28 下午5:38
 */
@Table(name = "dytt_movie")
public class RepMoviebean {
    @Column(name = "id")
    private int id;
    @Column(name = "movie_name")
    private String name;
    @Column(name = "douban_rank")
    private String rank;
    @Column(name = "poster")
    private String poster;
    @Column(name = "type")
    private String type;

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

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}