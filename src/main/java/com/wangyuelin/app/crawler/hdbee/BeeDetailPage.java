package com.wangyuelin.app.crawler.hdbee;

import com.wangyuelin.app.crawler.MovieProcessor;
import com.wangyuelin.app.crawler.base.IPage;
import com.wangyuelin.app.crawler.douban.bean.DoubanMovieInfo;
import com.wangyuelin.app.crawler.movie.PageUtil;
import com.wangyuelin.app.crawler.SpiderCraw;
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
import java.util.regex.Pattern;

/**
 * 描述:
 *
 * @outhor wangyuelin
 * @create 2018-05-31 下午2:48
 */
@Component
public class BeeDetailPage implements IPage {
    private final Logger logger = LoggerFactory.getLogger(BeeDetailPage.class);
    public static final String PAGE_URL = "^http://www.hdbee.net/[0-9]+.html$";
    @Autowired
    private MovieProcessor movieProcessor;

    @Override
    public void parse(Page page) {
        if (page == null) {
            return;
        }
        String url = page.getUrl().get();
        DoubanMovieInfo info = new DoubanMovieInfo();
        getInfo(info, page);


    }

    @Override
    public boolean isMine(String url) {
        if (!TextUtils.isEmpty(url) && Pattern.matches(PAGE_URL, url)) {
            return true;
        }
        return false;
    }

    /**
     * 获取相关的信息
     * @param info
     * @param page
     */
    private void getInfo(DoubanMovieInfo info, Page page) {
        if (page == null || info == null) {
            return;
        }
        //获取在线播放的地址
        Elements as = page.getHtml().getDocument().select("div.kzvideo_page ul li > a");
        if (as != null) {
            for (Element a : as) {
                String playVideoLink = a.attr("href");
                logger.info("在线播放的地址：" + playVideoLink);
                //在线播放的还得解析获得具体的播放链接
                SpiderCraw.start(playVideoLink, movieProcessor);

            }
        }
        List<DoubanMovieInfo.Download> downloads = new ArrayList<DoubanMovieInfo.Download>();
        //获取迅雷下载地址
        Elements divEls = page.getHtml().getDocument().select("tbody tr td div");
        if (divEls != null) {
            for (Element div : divEls) {

                DoubanMovieInfo.Download download = new DoubanMovieInfo.Download();
                Element a = PageUtil.getOne(div, "a");
                if (a != null) {
                    String link = a.attr("href");
                    String name = a.ownText();
                    download.setLink(link);
                    download.setName(name);
                }

                Element strongEl = PageUtil.getOne(div, "strong");
                if (strongEl != null) {
                    String pwd = strongEl.ownText();
                    if (!TextUtils.isEmpty(pwd)) {
                        String name = download.getName();
                        String text =  name== null ? "" : name  +  " " + pwd;
                        download.setName(text);
                    }
                }



                downloads.add(download);
                logger.info("下载地址  ：" + download.getName() + " : " + download.getLink());
            }
        }
        if (info.getDownloads() == null) {
            info.setDownloads(new ArrayList<DoubanMovieInfo.Download>());
        }
        info.getDownloads().addAll(downloads);

    }
}