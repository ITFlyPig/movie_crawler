package com.wangyuelin.app.crawler.douban.mapper;

import com.wangyuelin.app.crawler.base.MovieBean;
import com.wangyuelin.app.crawler.dylol.bean.Movie;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 描述:
 *
 * @outhor wangyuelin
 * @create 2018-06-11 下午8:59
 */
@Mapper
public interface MovieMapper {
    void insert(MovieBean movieBean);
    MovieBean getByDetailUrl(String detailUrl);
    MovieBean getByDownloadWeblUrl(String dowloadWebUrl);
    List<MovieBean> getByName(String movieName);
    void update(MovieBean movieBean);

    List<MovieBean> getEmptyMovies();

}