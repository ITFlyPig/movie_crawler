package com.wangyuelin.app.crawler.dy2018;

import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

@Component
public class DyttProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(10 * 1000).setTimeOut(10000)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

    private final Logger logger = LoggerFactory.getLogger(DyttProcessor.class);

    @Autowired
    private DyttHomePage homePage;
    @Autowired
    private DyttListPage dyttListPage;
    @Autowired
    private DyttDeailPage dyttDeailPage;


    @Override
    public void process(Page page) {
        if (page == null) {
            return;
        }
        String url = page.getUrl().get();
        if (TextUtils.isEmpty(url)) {
            return;
        }

        handlePage(url, page);


    }

    @Override
    public Site getSite() {
        return site;
    }


    private void handlePage(String url, Page page) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        DYConstant.PageDYType pageDYType = DYConstant.getPageType(url);
        if (pageDYType == null) {
            return;
        }
        if (pageDYType.equals(DYConstant.PageDYType.HOME)) {
            logger.info("开始处理首页 \n" + url);
            homePage.handle(page);


        } else if (pageDYType.equals(DYConstant.PageDYType.SORT_LIST)) {
            logger.info("开始处理分类列表 \n" + url);
            dyttListPage.handle(page);

        } else if (pageDYType.equals(DYConstant.PageDYType.DEATIL)) {
            logger.info("开始处理详情页");
            dyttDeailPage.handle(page);

        }


    }
}
