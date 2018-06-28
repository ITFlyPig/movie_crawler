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


}