package com.wangyuelin.app.crawler.piaohua;

import com.wangyuelin.app.crawler.base.IPage;
import com.wangyuelin.app.crawler.douban.bean.DoubanMovieInfo;
import com.wangyuelin.app.crawler.movie.PageUtil;
import org.apache.http.util.TextUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 描述: 处理飘花的详情页
 *
 * @outhor wangyuelin
 * @create 2018-05-30 下午6:21
 */
@Component
public class PiaohuaDetailPage implements IPage {
    private final Logger logger = LoggerFactory.getLogger(PiaohuaDetailPage.class);

    private static final String PAGE_URL = "^https://www.piaohua.com/html/[a-zA-Z]+/[0-9]+/[0-9]+/[0-9]+.html$";

    @Override
    public void parse(Page page) {
        if (page == null) {
            return;
        }
        DoubanMovieInfo info = new DoubanMovieInfo();
        getMovieInfo(page, info);


    }

    @Override
    public boolean isMine(String url) {
        if (!TextUtils.isEmpty(url) && Pattern.matches(PAGE_URL, url)) {
            return true;
        }
        return false;
    }

    /**
     * 获取基本信息
     * @param page
     */
    private void getMovieInfo(Page page, DoubanMovieInfo info) {
        if (page == null || info == null) {
            return;
        }
        Document doc = page.getHtml().getDocument();

        //获取海报
        Element posterEl = PageUtil.getOne(doc, "div#showinfo img");
        if (posterEl != null) {
            String posterUrl = posterEl.attr("src");
            logger.info("海报的地址：" + posterUrl);
        }

        //获取下载地址
        Elements as = doc.select("tbody td a");
        List<DoubanMovieInfo.Download> downloads = new ArrayList<DoubanMovieInfo.Download>();
        if (as != null) {
            for (Element a : as) {
                String downloadUrl = a.ownText();
                logger.info("下载地址：" + downloadUrl);
                DoubanMovieInfo.Download download = new DoubanMovieInfo.Download();
                download.setLink(downloadUrl);
                downloads.add(download);
            }
        }



    }
}