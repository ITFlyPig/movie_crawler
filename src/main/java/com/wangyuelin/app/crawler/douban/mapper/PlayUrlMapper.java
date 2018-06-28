package com.wangyuelin.app.crawler.douban.mapper;

import com.wangyuelin.app.crawler.base.PlayUrl;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 描述:
 *
 * @outhor wangyuelin
 * @create 2018-06-12 下午1:33
 */
@Mapper
public interface PlayUrlMapper {
    List<PlayUrl> getByUrlAndMovieId(String playUrl, int movieId);
    void insert(PlayUrl playUrl);
    void updateById(PlayUrl playUrl);

}