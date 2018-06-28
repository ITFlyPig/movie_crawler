package com.wangyuelin.app.crawler.dylol.dao;

import com.wangyuelin.app.crawler.dylol.bean.RankMovieBean;
import com.wangyuelin.app.config.mybatis.baseMapper.BaseMapper;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MonthRankDao extends BaseMapper<RankMovieBean> {

    @Insert("insert into month_rank(name, location, location_str, type, type_str) " +
            "values(#{name}, #{location}, #{locationStr}, #{type}, #{typeStr})")
    void insertOne(@Param("name") String name, @Param("location") String location, @Param("locationStr") String locationStr,
                   @Param("type") String type, @Param("typeStr") String typeStr);

    @Select("select * from month_rank where name=#{name}")
    RankMovieBean getByName(@Param("name") String name);

    @Update("update month_rank set location=#{location}, location_str=#{locationStr} where name=#{name}")
    void updateByName(@Param("name") String name, @Param("location") String location, @Param("locationStr") String locationStr);
}
