package com.wangyuelin.app.crawler;

import com.wangyuelin.app.crawler.baidu.BDMoviePage;
import com.wangyuelin.app.crawler.douban.DoubanPage;
import com.wangyuelin.app.crawler.dylol.bean.Movie;
import com.wangyuelin.app.crawler.hdbee.BeePage;
import com.wangyuelin.app.crawler.page.*;
import com.wangyuelin.app.crawler.dylol.service.RankMovieService;
import com.wangyuelin.app.crawler.piaohua.PiaohuaDetailPage;
import com.wangyuelin.app.crawler.piaohua.RecommendPage;
import com.wangyuelin.app.crawler.zuida.ZuidaPage;
import com.wangyuelin.app.util.LogUtils;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

@Component
public class MovieProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

    private final Logger logger = LoggerFactory.getLogger(MovieProcessor.class);

    @Autowired
    private MovieListPage mMovieListPage;

    @Autowired
    private MovieDetailPage mMovieDetailPage;

    @Autowired
    private HomePage mHomePage;

    @Autowired
    private RankMovieService mRankMovieService;
    @Autowired
    private DoubanPage doubanPage;

    @Autowired
    private RecommendPage recommendPage;

    @Autowired
    private PiaohuaDetailPage piaohuaDetailPage;

    @Autowired
    private BeePage beePage;
    @Autowired
    private ZuidaPage zuidaPage;




    @Override
    public void process(Page page) {
        String url = page.getUrl().get();

        LogUtils.writeDownloadPageLog(url);

        if (doubanPage.isMine(url)) {
            doubanPage.parse(page);
        }
        if (recommendPage.isMine(url)) {
            recommendPage.parse(page);
        } else if (piaohuaDetailPage.isMine(url)) {
            piaohuaDetailPage.parse(page);
        } else if (beePage.isMine(url)) {
            beePage.parse(page);
        } else if (zuidaPage.isMine(url)) {
            zuidaPage.parse(page);
        }

        /*
        String url = page.getUrl().get();
        logger.info("开始解析 url:" + url);
        if (TextUtils.isEmpty(url)) {
            return;
        }

        PageUtil.PageType pageType = PageUtil.pageType(url);
        if (pageType.equals(PageUtil.PageType.DEAIL_PAGE)) {//解析详情页
            logger.info("开始解析详情页");
            MovieBean movie = mMovieDetailPage.parse(page);
            mRankMovieService.addOne(movie);

        } else if (pageType.equals(PageUtil.PageType.HOME_PAGE)) {
            logger.info("开始解析首页");
            List<MovieBean> movies = mHomePage.parse(page);
            logger.info("首页解析：" + movies.toString());
            mRankMovieService.addAll(movies);

        } else if (pageType.equals(PageUtil.PageType.SORT_PAGE)) {
            logger.info("开始解析分类页");
            List<MovieBean> movies = mMovieListPage.parse(page);
            //保存到数据库
            mRankMovieService.addAll(movies);


        }

        */

    }

    @Override
    public Site getSite() {
        return site;
    }
}
