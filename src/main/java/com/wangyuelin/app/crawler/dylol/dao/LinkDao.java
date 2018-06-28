package com.wangyuelin.app.crawler.dylol.dao;

import com.wangyuelin.app.crawler.dylol.bean.LinkBean;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LinkDao {

    @Delete("delete from ${tableName}")
    void deleteAll(@Param("tableName") String tableName);

    @Insert("insert into ${tablell}(title, time, link, movie_name) values(#{linkBean.title}, #{linkBean.time}, #{linkBean.link}, #{linkBean.movieName})")
    void insertOne(@Param("linkBean") LinkBean linkBean,@Param("tablell") String table);

    @Select("select * from ${tableName} limit #{offset},#{num}")
    List<LinkBean> getLinks(@Param("tableName") String tableName, @Param("offset") int offset,@Param("num") int num);

    @Select("select count(id) from ${tableName}")
    int getTotalNum(@Param("tableName") String tableName);



}
