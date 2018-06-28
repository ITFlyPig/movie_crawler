package com.wangyuelin.app.crawler.dylol.dao;

import com.wangyuelin.app.crawler.dylol.bean.RankMovieBean;
import com.wangyuelin.app.config.mybatis.baseMapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MovieRankDao extends BaseMapper<RankMovieBean> {


    @Insert("insert into rank(name, rank_type, type, rank_type_str, type_str) values(#{name},  #{rankTypa}, #{type}, #{rankTypastr}, #{typeStr})")
    void insertOne(@Param("name") String name, @Param("type") int type,
                   @Param("rankTypa") int rankTypa, @Param("rankTypastr") String rankTypeStr, @Param("typeStr") String typeStr);

    @Update("update rank set rank_type=#{rankTypa}, type=#{type}, rank_type_str=#{rankTypastr}, type_str=#{typeStr} where name=#{name}")
    void updateOne(@Param("type") int type,
                   @Param("rankTypa") int rankTypa, @Param("rankTypastr") String rankTypeStr, @Param("typeStr") String typeStr, @Param("name") String name);

    @Select("select * from #{tableName} where #{key} = #{arg}")
    List<RankMovieBean> selectByArgs(@Param("tableName") String tableName, @Param("key") String key,@Param("arg") String arg);

    @Select("select * from rank where name = #{name}")
    List<RankMovieBean> selectByName(@Param("name") String name);


}
