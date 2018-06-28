package com.wangyuelin.app.crawler.hdbee;

import com.wangyuelin.app.crawler.base.IPage;
import com.wangyuelin.app.crawler.movie.PageUtil;
import org.apache.http.util.TextUtils;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;

/**
 * 描述:蜜蜂电影在线播放的页面的解析
 *
 * @outhor wangyuelin
 * @create 2018-05-31 下午3:05
 */
@Component
public class BeePlayVideoPage implements IPage {
    private final Logger logger = LoggerFactory.getLogger(BeePlayVideoPage.class);

    @Override
    public void parse(Page page) {
        if (page == null) {
            return;
        }
        String url = page.getUrl().get();

        Element iframeEl = PageUtil.getOne(page.getHtml().getDocument(), "div.kz_videodiv iframe");
        if (iframeEl != null) {
            String videoUrl = iframeEl.attr("src");
            logger.info("视频的连接：" + videoUrl);

        }

    }

    @Override
    public boolean isMine(String url) {
        if (!TextUtils.isEmpty(url) && url.startsWith(BeePage.HOST) && url.contains("video")) {
            return true;
        }
        return false;
    }



}