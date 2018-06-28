package com.wangyuelin.app.crawler.dylol.dao;

import com.wangyuelin.app.crawler.dylol.bean.RankMovieBean;
import com.wangyuelin.app.config.mybatis.baseMapper.BaseMapper;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UptodateDao extends BaseMapper<RankMovieBean> {

    @Insert("insert into up_to_date(name, location, location_str) values(#{name}, #{location}, #{locationStr})")
    void insertOne(@Param("name") String name, @Param("location") String location, @Param("locationStr") String locationStr);

    @Select("select * from up_to_date where name=#{name}")
    RankMovieBean getByName(@Param("name") String name);

    @Update("update up_to_date set location=#{location}, location_str=#{locationStr} where name=#{name}")
    void updateByName(@Param("name") String name, @Param("location") String location, @Param("locationStr") String locationStr);
}
