package com.wangyuelin.app.crawler.hdbee;

import com.wangyuelin.app.crawler.MovieProcessor;
import com.wangyuelin.app.crawler.base.IPage;
import com.wangyuelin.app.crawler.SpiderCraw;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;

/**
 * 描述:电影蜜蜂【http://www.hdbee.net/】的解析页
 *
 * @outhor wangyuelin
 * @create 2018-05-31 下午2:20
 */
@Component
public class BeePage implements IPage {
    public static final String HOST = "http://www.hdbee.net";

    @Autowired
    private SearcherResultPage searcherResultPage;
    @Autowired
    private BeeDetailPage beeDetailPage;
    @Autowired
    private BeePlayVideoPage beePlayVideoPage;

    @Override
    public void parse(Page page) {
        String url = page.getUrl().get();
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (searcherResultPage.isMine(url)) {
            searcherResultPage.parse(page);
        } else if (beeDetailPage.isMine(url)) {
            beeDetailPage.parse(page);
        } else if (beePlayVideoPage.isMine(url)) {
            beePlayVideoPage.parse(page);
        }


    }

    @Override
    public boolean isMine(String url) {
        if (!TextUtils.isEmpty(url) && url.contains(HOST)) {
            return true;
        }
        return false;
    }


    /**
     * 蜜蜂电影开始查询
     * @param name
     */
    public static void startSearch(String name, MovieProcessor movieProcessor) {
        if (TextUtils.isEmpty(name)) {
            return;
        }

        String url = SearcherResultPage.PAGE_URL + name;
        SpiderCraw.addRequestUrl(url, movieProcessor);

    }
}