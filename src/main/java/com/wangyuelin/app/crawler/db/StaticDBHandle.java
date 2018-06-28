package com.wangyuelin.app.crawler.db;

import com.wangyuelin.app.crawler.douban.service.DoubanMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 描述:全局的数据库操作放在这里
 *
 * @outhor wangyuelin
 * @create 2018-06-26 下午9:07
 */
@Component
public class StaticDBHandle {
    public static DoubanMovieService doubanMovieService;
    @Autowired
    public  void setDoubanMovieService(DoubanMovieService doubanMovieService) {
        StaticDBHandle.doubanMovieService = doubanMovieService;
    }


}