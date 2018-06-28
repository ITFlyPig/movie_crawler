package com.wangyuelin.app.crawler.douban;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import com.wangyuelin.app.crawler.MovieProcessor;
import com.wangyuelin.app.crawler.base.IPage;
import com.wangyuelin.app.crawler.base.MovieBean;
import com.wangyuelin.app.crawler.douban.service.DoubanMovieService;
import com.wangyuelin.app.crawler.downloader.OkhttpDownloader;
import com.wangyuelin.app.crawler.downloader.OkhttpUtil;
import com.wangyuelin.app.crawler.SpiderCraw;
import okhttp3.Response;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 描述:豆瓣选电影页面：主要是为了解析得到热门、最新、经典、豆瓣高分、冷门佳片等
 *
 * @outhor wangyuelin
 * @create 2018-06-12 下午4:58
 */
@Component
public class DoubanChooseMoviePage implements IPage {
    private static final Logger logger = LoggerFactory.getLogger(DoubanChooseMoviePage.class);
    private static final String URL_DEMO = "https://movie.douban.com/explore#!type=movie&tag=%E7%83%AD%E9%97%A8&sort=recommend&page_limit=20&page_start=80";
    private static final String CHOOSE_URL = "https://movie.douban.com/j/search_subjects?type=movie&tag=%s&sort=recommend&page_limit=%s&page_start=%s";
    //https://movie.douban.com/j/search_subjects?type=movie&tag=%E7%83%AD%E9%97%A8&sort=recommend&page_limit=20&page_start=80
    private BlockingQueue<String> tags = new LinkedBlockingDeque<String>();//用来存放需要处理的tag

    @Autowired
    private DoubanMovieService doubanMovieService;

    @Autowired
    private MovieProcessor movieProcessor;

    private SpecialTagMovieRunnable specialTagMovieRunnable;

    @Override
    public void parse(Page page) {

    }

    @Override
    public boolean isMine(String url) {
        return false;
    }

    /**
     * 开始爬取特定tag的电影
     *
     * @param tag
     */
    public void startCrawSpecialTagMovies(String tag) {
        if (TextUtils.isEmpty(tag)) {
            return;
        }
        if (specialTagMovieRunnable == null) {
            specialTagMovieRunnable = new SpecialTagMovieRunnable();
            new Thread(specialTagMovieRunnable).start();
        }
        try {
            tags.put(tag);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    private class SpecialTagMovieRunnable implements Runnable {
        private boolean stop;
        private int limit = 400;
        private int page = -1;

        @Override
        public void run() {

            while (!stop) {
                try {
                    logger.info("将要开始爬取tag的数据：");
                    String tag = tags.take();
                    page = -1;
                    logger.info("开始爬取tag的数据：" + tag);
                    getData(tag);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }

        /**
         * 下一页数据的url
         *
         * @param tag
         * @return
         */
        private String nextPageUrl(String tag) {
            page++;
            return String.format(CHOOSE_URL, tag, limit, page * limit);
        }


        /**
         * 是否结束了
         *
         * @return
         */
        private void getData(final String tag) {
            if (TextUtils.isEmpty(tag)) {
                return;
            }
            while (true) {
                String url = nextPageUrl(tag);
                logger.info("开始抓取的页面：" + page + " url:" + url);

                Response response = null;
                response = OkhttpUtil.downloadPageWithIp(url, OkhttpDownloader.singleInstance().getmOkHttpClient());
                if (response == null) {
                    SpiderCraw.addRequestUrl(url, null);
                }

                try {
                    String content = response.body().string();
                    if (TextUtils.isEmpty(content)) {
                        return;
                    }

                    ReadContext ctx = JsonPath.parse(content);
                    logger.info("get请求返回的数据：" + content);
                    List<Map<String, String>> list = ctx.read("$.subjects[*]");
                    if (list == null || list.size() == 0) {//没有更多了
                        logger.info(tag + " 返回的电影列表为空，结束这个tag的抓取");
                        return;
                    } else {
                        for (Map<String, String> map : list) {
                            String name = map.get("title");
                            String detailUrl = map.get("url");
                            if (TextUtils.isEmpty(detailUrl)) {
                                continue;
                            }
                            MovieBean movieBean = new MovieBean();
                            movieBean.setName(name);
                            movieBean.setDetaiWeblUrl(detailUrl);
                            movieBean.setTag(tag);
                            doubanMovieService.updateByDetailUrl(movieBean);
                            if (doubanMovieService.isShouldCraw(name)) {//查看数据库中书否已存在
                                logger.info("tag为:" + tag + " 下的" + name + " 已存在，不用在爬取详情");
                                SpiderCraw.addRequestUrl(detailUrl, movieProcessor);
                            }


                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    SpiderCraw.addRequestUrl(url, null);
                }

                sleep();//随机休眠一定时间后在爬取

            }

        }


    }


    /**
     * 获取随机的休眠时间，避免被网站禁止怕取
     *
     * @param start
     * @param end
     * @return
     */
    public static int getRandom(int start, int end) {
        return (int) (Math.random() * (end - start + 1) + start);
    }

    private int max = 40000;//10s
    private int min = 1000;//3s

    /**
     * 随机的休眠
     */
    private void sleep() {
        int sleepTime = getRandom(min, max);
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}