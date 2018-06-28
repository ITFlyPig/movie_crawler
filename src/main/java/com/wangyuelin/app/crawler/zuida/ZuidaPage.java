package com.wangyuelin.app.crawler.zuida;

import com.wangyuelin.app.crawler.base.IPage;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;

/**
 * 描述: 最大资源采集的分发处理页
 * http://zuidazy.com/
 *
 * @outhor wangyuelin
 * @create 2018-06-11 下午5:56
 */
@Component
public class ZuidaPage implements IPage {
    public static final String HOST = "http://zuidazy.com";
    @Autowired
    private ZuidaSearchPage zuidaSearchPage;
    @Autowired
    private ZuidaDetailPage zuidaDetailPage;

    @Override
    public void parse(Page page) {
//        if (page == null) {
//            return;
//        }
//        String url = page.getUrl().get();
//        if (TextUtils.isEmpty(url)) {
//            return;
//        }
//        if (zuidaSearchPage.isMine(url)) {
//            zuidaSearchPage.parse(page);
//        } else if (zuidaDetailPage.isMine(url)) {
//            zuidaDetailPage.parse(page);
//        }


    }

    @Override
    public boolean isMine(String url) {
        if (!TextUtils.isEmpty(url) && url.startsWith(HOST)) {
            return true;
        }
        return false;
    }
}