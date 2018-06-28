package com.wangyuelin.app.crawler.task;

import com.wangyuelin.app.crawler.base.MovieBean;
import com.wangyuelin.app.crawler.douban.service.DoubanMovieService;
import com.wangyuelin.app.crawler.SpiderCraw;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 描述: 这个任务是检查数据库中的内容没有获取到的电影，存在的话添加到爬虫重新获取
 *
 * @outhor wangyuelin
 * @create 2018-06-15 下午4:35
 */
@Component
public class CheckContentEmptyMovie implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(CheckContentEmptyMovie.class);

    private static final long SPLIT_TIME = 1000 * 60 * 60 ;//1小时检查一次

    @Autowired
    private DoubanMovieService doubanMovieService;

    @Override
    public void run() {
        while (true) {

            checkContentEmptyMovie();


            try {
                Thread.sleep(SPLIT_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }


    /**
     * 检查内容为空的电影
     */
    private void checkContentEmptyMovie() {
        List<MovieBean> list = doubanMovieService.getEmptyContentMovie();

        logger.info("检查空数据：" + (list == null ? 0 : list.size()));
        if (list != null) {//将没有抓取的到的详情重新抓取
            for (MovieBean movieBean : list) {
                String detailUrl = movieBean.getDetaiWeblUrl();
                if (!TextUtils.isEmpty(detailUrl)) {
//                    logger.info("添加到爬虫：" + detailUrl);
                    SpiderCraw.addRequestUrl(detailUrl, null);
                }

            }


        }
    }
}