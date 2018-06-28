package com.wangyuelin.app.crawler.zuida;

import com.wangyuelin.app.crawler.MovieProcessor;
import com.wangyuelin.app.crawler.base.IPage;
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
 * 描述:最大资源的搜索结果list页处理
 *
 * @outhor wangyuelin
 * @create 2018-06-11 下午5:59
 */
@Component
public class ZuidaSearchPage implements IPage {
    private static final String HOST = "http://zuidazy.com/index.php?m=vod-search&wd=";
    private final Logger logger = LoggerFactory.getLogger(ZuidaSearchPage.class);

    @Autowired
    private MovieProcessor mMovieProcessor;

    @Override
    public void parse(Page page) {
        if (page == null) {
            return;
        }
        parseDetailUrl(page.getHtml().getDocument());

    }

    @Override
    public boolean isMine(String url) {
        if (!TextUtils.isEmpty(url) && url.startsWith(HOST)) {
            return true;
        }
        return false;
    }


    /**
     * 解析得到详情的地址
     * @param doc
     */
    private void parseDetailUrl(Document doc) {
        if (doc == null ) {
            return;
        }
        Elements as = doc.select("div.xing_vb a");
        if (as == null) {
            return;
        }
        for (Element a : as) {
            String detailUrl = a.attr("href");
            String url = ZuidaPage.HOST + detailUrl;//详情的地址
            SpiderCraw.addRequestUrl(url, mMovieProcessor);
            logger.info("最大资源搜索结果：" + detailUrl);


        }
    }


    /**
     * 开始查询
     * @param wd
     */
    public void startSearch(String wd) {
        if (TextUtils.isEmpty(wd)) {
            return;
        }
//        SpiderUtil.addRequestUrl(HOST + wd, mMovieProcessor);

    }
}