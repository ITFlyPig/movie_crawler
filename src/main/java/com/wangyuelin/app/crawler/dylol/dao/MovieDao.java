package com.wangyuelin.app.crawler.dylol.dao;

import com.wangyuelin.app.crawler.dylol.bean.Movie;
import com.wangyuelin.app.config.mybatis.baseMapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MovieDao extends BaseMapper<Movie> {

    @Select("select * from movie where name=#{name}")
    Movie getByName(@Param("name") String name);

    @Update("update movie set detail_url=#{movie.detailUrl}, cover_url=#{movie.coverUrl}, time=#{movie.time}, magnets=#{movie.magnets}, thunders=#{movie.thunders}" +
            ",bts=#{movie.btsStr}, intro=#{movie.intro}, type=#{movie.type}, type_str=#{movie.typeStr}, rank_type=#{movie.rankType}, rank_type_str=#{movie.rankTypeStr}" +
            ",location=#{movie.location}, location_str=#{movie.locationStr} where name=#{movie.name}")
    void updateByName(@Param("movie") Movie movie);
}
