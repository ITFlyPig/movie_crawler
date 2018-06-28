package com.wangyuelin.app.crawler.douban;

import com.wangyuelin.app.crawler.MovieProcessor;
import com.wangyuelin.app.crawler.base.IPage;
import com.wangyuelin.app.crawler.base.MovieBean;
import com.wangyuelin.app.crawler.douban.service.DoubanMovieService;
import com.wangyuelin.app.util.Constant;
import com.wangyuelin.app.crawler.SpiderCraw;
import org.apache.http.util.TextUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;

/**
 * 描述：解析豆瓣的正在上映和即将上映的电影
 * 页面：https://movie.douban.com/cinema/nowplaying/beijing/
 *
 * @outhor wangyuelin
 * @create 2018-06-11 下午2:07
 */
@Component
public class MovieHousePage implements IPage {
    private final Logger logger = LoggerFactory.getLogger(MovieHousePage.class);
    public static final String URL = "https://movie.douban.com/cinema/nowplaying/beijing/";
    @Autowired
    private MovieProcessor pageProcessor;
    @Autowired
    private DoubanMovieService doubanMovieService;

    @Override
    public void parse(Page page) {
        if (page == null ) {
            return;
        }
        Document doc = page.getHtml().getDocument();
        parseNowDisplayingMovie(doc);
        parseUpComingMovie(doc);


    }

    @Override
    public boolean isMine(String url) {
        if (!TextUtils.isEmpty(url) && url.equalsIgnoreCase(URL)) {
            return true;
        }
        return false;
    }

    /**
     * 解析得到正在上映的电影链接
     * @param doc
     */
    private void parseNowDisplayingMovie(Document doc) {
        if (doc == null) {
            return;
        }
        Elements as = doc.select("div#nowplaying ul.lists li.stitle a");
        if (as == null) {
            return;
        }
        for (Element a : as) {
            String link = a.attr("href");
            String movieName = a.attr("title");
            SpiderCraw.addRequestUrl(link, pageProcessor);
            logger.info(movieName + "正在上映的电影链接：" + link);
            MovieBean movieBean = new MovieBean();
            movieBean.setName(movieName);
            movieBean.setDetaiWeblUrl(link);
            movieBean.setTag(Constant.MovieTag.SHOWING);
            doubanMovieService.updateByDetailUrl(movieBean);

        }



    }


    /**
     * 解析得到即将上映的电影链接
     * @param doc
     */
    private void parseUpComingMovie(Document doc) {
        if (doc == null) {
            return;
        }
        Elements as = doc.select("div#upcoming ul.lists li.stitle a ");
        if (as == null) {
            return;
        }
        for (Element a : as) {
            String link = a.attr("href");
            String movieName = a.text();
            SpiderCraw.addRequestUrl(link, pageProcessor);
            logger.info("即将上映的电影链接：" + link);
            MovieBean movieBean = new MovieBean();
            movieBean.setName(movieName);
            movieBean.setDetaiWeblUrl(link);
            movieBean.setTag(Constant.MovieTag.WILL_SHOW);
            doubanMovieService.updateByDetailUrl(movieBean);

        }
    }

    /**
     * 开始爬影院电影
     */
    public void startCrawHouseMovie() {
        SpiderCraw.addRequestUrl(URL, pageProcessor);
    }
}