package com.wangyuelin.app.crawler.dylol.bean;

import java.util.List;

public class PageHomeBean {
    private List<Movie> reMovies;//当日推荐的电影
    private List<Movie> newMovies;//最新的电影
    private List<Movie> rankMovies;//电影周排行

    private List<Movie> newTeleplays;//最新的电视剧
    private List<Movie> rankTeleplays;//电视剧周排行

    private List<Movie> newCartoons;//最新的动漫
    private List<Movie> rankCartoons;//动漫的周排行

    private List<Movie> newVarietys;//最新的综艺
    private List<Movie> rankVarietys;//综艺周排行

    private List<Movie> hotRanks;//热门排行

    public List<Movie> getReMovies() {
        return reMovies;
    }

    public void setReMovies(List<Movie> reMovies) {
        this.reMovies = reMovies;
    }

    public List<Movie> getNewMovies() {
        return newMovies;
    }

    public void setNewMovies(List<Movie> newMovies) {
        this.newMovies = newMovies;
    }

    public List<Movie> getRankMovies() {
        return rankMovies;
    }

    public void setRankMovies(List<Movie> rankMovies) {
        this.rankMovies = rankMovies;
    }

    public List<Movie> getNewTeleplays() {
        return newTeleplays;
    }

    public void setNewTeleplays(List<Movie> newTeleplays) {
        this.newTeleplays = newTeleplays;
    }

    public List<Movie> getRankTeleplays() {
        return rankTeleplays;
    }

    public void setRankTeleplays(List<Movie> rankTeleplays) {
        this.rankTeleplays = rankTeleplays;
    }

    public List<Movie> getNewCartoons() {
        return newCartoons;
    }

    public void setNewCartoons(List<Movie> newCartoons) {
        this.newCartoons = newCartoons;
    }

    public List<Movie> getRankCartoons() {
        return rankCartoons;
    }

    public void setRankCartoons(List<Movie> rankCartoons) {
        this.rankCartoons = rankCartoons;
    }

    public List<Movie> getNewVarietys() {
        return newVarietys;
    }

    public void setNewVarietys(List<Movie> newVarietys) {
        this.newVarietys = newVarietys;
    }

    public List<Movie> getRankVarietys() {
        return rankVarietys;
    }

    public void setRankVarietys(List<Movie> rankVarietys) {
        this.rankVarietys = rankVarietys;
    }

    public List<Movie> getHotRanks() {
        return hotRanks;
    }

    public void setHotRanks(List<Movie> hotRanks) {
        this.hotRanks = hotRanks;
    }

    @Override
    public String toString() {
       StringBuffer buffer = new StringBuffer();
       buffer.append("[ reMovies=").append(reMovies).append(";newMovies=").append(newMovies).append(";rankMovies=").append(rankMovies)
            .append(";newTeleplays=").append(newTeleplays).append(";rankTeleplays=").append(rankTeleplays)
               .append(";newCartoons=").append(newCartoons).append(";rankCartoons=").append(rankCartoons)
               .append(";newVarietys=").append(newVarietys).append(";rankVarietys=").append(rankVarietys)
               .append(";hotRanks=").append(hotRanks).append("]");
       return buffer.toString();
    }
}
