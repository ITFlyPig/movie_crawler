package com.wangyuelin.app.appserver.service.itf;

import com.wangyuelin.app.appserver.bean.RepMoviebean;

import java.util.List;

/**
 * 描述:
 *
 * @outhor wangyuelin
 * @create 2018-05-28 下午5:48
 */
public interface IMovie {
    List<RepMoviebean> getMovies( int pageSize, int pageIndex,  int type);

    boolean hasMore(int pageSize, int pageIndex, int type);

}