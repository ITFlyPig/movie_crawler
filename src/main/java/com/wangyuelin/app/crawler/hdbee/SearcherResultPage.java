package com.wangyuelin.app.crawler.hdbee;

import com.wangyuelin.app.crawler.MovieProcessor;
import com.wangyuelin.app.crawler.base.IPage;
import com.wangyuelin.app.crawler.SpiderCraw;
import org.apache.http.util.TextUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;

/**
 * 描述:搜索结果页面的解析
 * 1、获取影片的详情页连接
 *
 *
 * @outhor wangyuelin
 * @create 2018-05-31 下午2:22
 */
@Component
public class SearcherResultPage implements IPage {
    private final Logger logger = LoggerFactory.getLogger(SearcherResultPage.class);
    public static final String PAGE_URL = "http://www.hdbee.net/?s=";

    @Autowired
    private MovieProcessor movieProcessor;

    @Override
    public void parse(Page page) {
        if (page == null) {
            return;
        }
        getLinks(page);

    }

    @Override
    public boolean isMine(String url) {
        if (!TextUtils.isEmpty(url) && url.startsWith(PAGE_URL)) {
            return true;
        }
        return false;
    }

    private void getLinks( Page page) {
        if (page == null) {
            return;
        }
        Elements as = page.getHtml().getDocument().select("div.update_area_content ul.update_area_lists.cl li.i_list.list_n2 > a");
        if (as == null) {
            return;
        }
        for (Element a : as) {
            String link = a.attr("href");
            logger.info("电影的连接：" + link);
            SpiderCraw.addRequestUrl(link, movieProcessor);

        }

    }


}