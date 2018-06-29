package com.wangyuelin.app.crawler.douban.service.itf;

import com.wangyuelin.app.crawler.base.MovieBean;
import com.wangyuelin.app.crawler.base.PlayUrl;
import com.wangyuelin.app.crawler.dylol.bean.Movie;

import java.util.List;

/**
 * 描述:
 *
 * @outhor wangyuelin
 * @create 2018-06-12 上午11:16
 */
public interface IDoubanMovie {
    int insert(MovieBean movieBean);
    void updateByDetailUrl(MovieBean movieBean);
    List<MovieBean> getMoviesByName(String movieName);
    void savePlayUrl(PlayUrl playUrl, String movieName);
    boolean isShouldCraw(String movieName);
    List<MovieBean> getEmptyContentMovie();

    /**
     * 删除对应表此tag下的所有电影，因为不同网站的热度或者最新是分表存储的
     * @param tag
     */
    void deleteByTag(String tag, String table);

    /**
     * 获取特定tag下对应的电影是否存在（存在就不插入，避免多个重复的数据）
     * @param tag
     * @param name
     * @param table
     */
    List<MovieBean> getMovieByTagAndName(String tag, String name, String table);

    /**
     * 将电影放入到对应的tag表中
     * @param movieBean
     * @param table
     */
    void insertTagMovie(MovieBean movieBean, String table, int index);

    /**
     * 将百度的电影插入（百度获取的电影只有名字和tag）
     * @param movieBean
     * @param index
     */
    void insertBDMovie(MovieBean movieBean, int index);


}