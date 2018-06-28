package com.wangyuelin.app.crawler.dylol.dao;

import com.wangyuelin.app.crawler.dylol.bean.RankMovieBean;
import com.wangyuelin.app.config.mybatis.baseMapper.BaseMapper;
import org.apache.ibatis.annotations.*;

@Mapper
public interface RecommandDao extends BaseMapper<RankMovieBean> {

    @Insert("insert into recomand(name, location, location_str) values(#{name}, #{location}, #{locationStr})")
    void insertOne(@Param("name") String name, @Param("location") String location,@Param("locationStr") String locationStr);

    @Select("select * from recomand where name=#{name}")
    RankMovieBean getByName(@Param("name") String name);

    @Update("update recomand set location=#{location}, location_str=#{locationStr} where name=#{name}")
    void updateByName(@Param("name") String name,@Param("location")String location, @Param("locationStr") String locationStr);
}
