package com.wangyuelin.app.crawler.dy2018.dao;

import com.wangyuelin.app.config.mybatis.baseMapper.BaseMapper;
import com.wangyuelin.app.crawler.dy2018.DyttMovie;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 描述:
 *
 * @outhor wangyuelin
 * @create 2018-05-21 下午6:15
 */
@Mapper
public interface DyttMovieDao extends BaseMapper<DyttMovie> {

    @Select("select * from dytt_movie where movie_name = #{movieName}")
    DyttMovie getByName(@Param("movieName") String movieName);

}