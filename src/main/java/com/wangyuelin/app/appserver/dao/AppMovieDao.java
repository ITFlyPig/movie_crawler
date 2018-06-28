package com.wangyuelin.app.appserver.dao;

import com.wangyuelin.app.appserver.bean.RepMoviebean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 描述:
 *
 * @outhor wangyuelin
 * @create 2018-05-28 下午5:19
 */
@Mapper
public interface AppMovieDao {

    @Select("select * from dytt_movie where movie_name = #{name} ")
    RepMoviebean getByName(String name);
}