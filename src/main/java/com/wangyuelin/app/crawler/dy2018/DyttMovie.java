package com.wangyuelin.app.crawler.dy2018;

import org.springframework.context.annotation.Primary;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 描述:表示电影的详情,每个字段的意思可以参照 enum  MoviePropertys
 *
 * @outhor wangyuelin
 * @create 2018-05-21 上午11:55
 */
@Table(name ="dytt_movie")
public class DyttMovie {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "poster")
    private String poster;
    @Column(name = "translated_name")
    private String translatedName;
    @Column(name = "movie_name")
    private String movieName;
    @Column(name = "year")
    private String year;
    @Column(name = "location")
    private String location;
    @Column(name = "type")
    private String type;
    @Column(name = "language")
    private String language;
    @Column(name = "subtitle")
    private String subtitle;
    @Column(name = "show_time")
    private String showTime;
    @Column(name = "douban_rank")
    private String doubanRank;
    @Column(name = "imdb_rank")
    private String imdbRank;
    @Column(name = "format")
    private String format;
    @Column(name = "size")
    private String size;
    @Column(name = "daxiao")
    private String daxiao;
    @Column(name = "total_time")
    private String totalTime;
    @Column(name = "director")
    private String director;
    @Column(name = "actors")
    private String actors;
    @Column(name = "intro")
    private String intro;
    @Column(name = "screenshot")
    private String screenshot;
    @Column(name = "downloads")
    private String downloads;
    @Column(name = "name")
    private String name;
    @Column(name = "show_title")
    private String showTitle;
    @Column(name = "detail_url")
    private String detailUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTranslatedName() {
        return translatedName;
    }

    public void setTranslatedName(String translatedName) {
        this.translatedName = translatedName;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public String getDoubanRank() {
        return doubanRank;
    }

    public void setDoubanRank(String doubanRank) {
        this.doubanRank = doubanRank;
    }

    public String getImdbRank() {
        return imdbRank;
    }

    public void setImdbRank(String imdbRank) {
        this.imdbRank = imdbRank;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDaxiao() {
        return daxiao;
    }

    public void setDaxiao(String daxiao) {
        this.daxiao = daxiao;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(String screenshot) {
        this.screenshot = screenshot;
    }

    public String getDownloads() {
        return downloads;
    }

    public void setDownloads(String downloads) {
        this.downloads = downloads;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShowTitle() {
        return showTitle;
    }

    public void setShowTitle(String showTitle) {
        this.showTitle = showTitle;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }


    /**
     * 设置属性
     * @param property
     * @param value
     */
    public void setProperty(MoviePropertys property, String value) {
        if (property == null) {
            return;
        }
        switch (property) {
            case POSTER:
                setPoster(value);
                break;
            case URL:
                setDetailUrl(value);
                break;
            case NAME:
                setName(value);
                break;
            case SIZE:
                setSize(value);
                break;
            case TYPE:
                setType(value);
                break;
            case YEAR:
                setYear(value);
                break;
            case INTRO:
                setIntro(value);
                break;
            case ACTORS:
                setActors(value);
                break;
            case DAXIAO:
                setDaxiao(value);
                break;
            case FORMAT:
                setFormat(value);
                break;
            case DIRECTOR:
                setDirector(value);
                break;
            case LANGUAGE:
                setLanguage(value);
                break;
            case LOCATION:
                setLocation(value);
                break;
            case SUBTITLE:
                setSubtitle(value);
                break;
            case DOWNLOADS:
                setDownloads(value);
                break;
            case IMDB_RANK:
                setImdbRank(value);
                break;
            case SHOW_TIME:
                setShowTime(value);
                break;
            case MOVIE_NAME:
                setMovieName(value);
                break;
            case SCREENSHOT:
                setScreenshot(value);
                break;
            case SHOW_TITLE:
                setShowTitle(value);
                break;
            case TOTAL_TIME:
                setTotalTime(value);
                break;
            case DOUBAN_RANK:
                setDoubanRank(value);
                break;
            case TRANSLATED_NAME:
                setTranslatedName(value);
                break;
            default:
                break;

        }

    }
}