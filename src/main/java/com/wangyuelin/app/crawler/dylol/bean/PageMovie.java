package com.wangyuelin.app.crawler.dylol.bean;

import java.util.List;

public class PageMovie {
    private int id;
    private int type;//表示是恐怖片还是喜剧等
    private String typeStr;
    private List<Movie> reMovies;//推荐的电影
    private List<Movie> updateMovies;//最新更新的电影
    private List<Movie> monthRankList;//月排行榜
    private List<Movie> praiseRankList;//超赞排行榜
    private List<Movie> allMovies;//此类型下的所有电影


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Movie> getReMovies() {
        return reMovies;
    }

    public void setReMovies(List<Movie> reMovies) {
        this.reMovies = reMovies;
    }

    public List<Movie> getUpdateMovies() {
        return updateMovies;
    }

    public void setUpdateMovies(List<Movie> updateMovies) {
        this.updateMovies = updateMovies;
    }

    public List<Movie> getMonthRankList() {
        return monthRankList;
    }

    public void setMonthRankList(List<Movie> monthRankList) {
        this.monthRankList = monthRankList;
    }

    public List<Movie> getPraiseRankList() {
        return praiseRankList;
    }

    public void setPraiseRankList(List<Movie> praiseRankList) {
        this.praiseRankList = praiseRankList;
    }

    public List<Movie> getAllMovies() {
        return allMovies;
    }

    public void setAllMovies(List<Movie> allMovies) {
        this.allMovies = allMovies;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[reMovies=").append(reMovies).append(";updateMovies=").append(updateMovies)
                .append(";monthRankList=").append(monthRankList).append(";praiseRankList=").append(praiseRankList)
                .append(";allMovies=").append(allMovies).append("]");
        return buffer.toString();

    }
}
