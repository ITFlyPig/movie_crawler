package com.wangyuelin.app.crawler.douban.mapper;

import com.wangyuelin.app.crawler.base.MovieBean;
import com.wangyuelin.app.crawler.dylol.bean.Movie;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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


    /**
     * 删除对应表此tag下的所有电影，因为不同网站的热度或者最新是分表存储的
     * @param tag
     */
    void deleteByTag(@Param("tag") String tag, @Param("table") String table);

    /**
     * 获取特定tag下对应的电影是否存在（存在就不插入，避免多个重复的数据）
     * @param tag
     * @param name
     * @param table
     */
    List<MovieBean> getMovieByTagAndName(@Param("tag") String tag, @Param("name") String name,@Param("table") String table);

    /**
     * 将电影放入到对应的tag表中
     * @param movieBean
     * @param table
     */
    void insertTagMovie(@Param("movie") MovieBean movieBean, @Param("table") String table,@Param("index") int index);

    /**
     * 获取该tag下电影的数量
     * @param tag
     * @return
     */
    int getNumByTag(@Param("tag") String tag, @Param("table") String table);

    /**
     * 更新
     * @param tagMovie
     */
    void updateTagMovie(@Param("movie") MovieBean tagMovie, @Param("table") String table);

}