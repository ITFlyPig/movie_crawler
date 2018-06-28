package com.wangyuelin.app.crawler.piaohua;

import com.wangyuelin.app.crawler.MovieProcessor;
import com.wangyuelin.app.crawler.base.IPage;
import com.wangyuelin.app.crawler.douban.bean.DoubanMovieInfo;
import com.wangyuelin.app.crawler.movie.PageUtil;
import org.apache.http.util.TextUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述: 处理今日热门推荐
 *
 * @outhor wangyuelin
 * @create 2018-05-30 下午6:19
 */
@Component
public class RecommendPage implements IPage {
    private final Logger logger = LoggerFactory.getLogger(RecommendPage.class);

    public static final String PAGE_URL = "https://www.piaohua.com/html/dianying.html";

    @Autowired
    private MovieProcessor movieProcessor;

    @Override
    public void parse(Page page) {
        List<DoubanMovieInfo> movieInfos = getMovies(page);//获得今日推荐的列表

    }

    @Override
    public boolean isMine(String url) {
        if (!TextUtils.isEmpty(url) && url.equalsIgnoreCase(PAGE_URL)) {
            return true;
        }
        return false;
    }

    /**
     * 获取电影的列表
     * @param page
     * @return
     */
    public List<DoubanMovieInfo> getMovies(Page page) {
        Elements lis = page.getHtml().getDocument().select("div#iml1 ul li");
        if (lis == null) {
            return null;
        }
        List<DoubanMovieInfo> movieInfos = new ArrayList<DoubanMovieInfo>();
        for (Element li : lis) {
            DoubanMovieInfo movieInfo = new DoubanMovieInfo();
            Element a = PageUtil.getOne(li, "a");
            if (a != null) {
                String detailUrl = a.attr("href");//电影详情的连接
                if (!TextUtils.isEmpty(detailUrl) && detailUrl.contains("lianxuju")) {//连续剧就不处理
                    continue;
                }
                if (!detailUrl.contains(PiaohuaPage.HOST)) {
                    detailUrl = PiaohuaPage.HOST + detailUrl;
                }
//                SpiderUtil.addRequestUrl(detailUrl, movieProcessor);//添加
                movieInfo.setAlt(detailUrl);
                logger.info("详情：" + detailUrl);
            }


            Element nameEl = PageUtil.getOne(li, " a strong");
            if (nameEl != null) {
                String name = nameEl.text();//电影的名称
                movieInfo.setTitle(name);
                logger.info("名称：" + name);

            }
            Element timeEl = PageUtil.getOne(li, "span");
            if (timeEl != null) {
                String time = timeEl.text();//电影的时间
                DoubanMovieInfo.ShowTime showTime = new DoubanMovieInfo.ShowTime();
                showTime.setTime(time);
                if (movieInfo.getShowTimes() == null) {
                    movieInfo.setShowTimes(new ArrayList<DoubanMovieInfo.ShowTime>());
                }
                movieInfo.getShowTimes().add(showTime);
                logger.info("时间：" + time);
            }



        }
        return movieInfos;


    }
}