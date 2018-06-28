package com.wangyuelin.app.crawler.zuida;

import com.wangyuelin.app.crawler.base.DownloadLink;
import com.wangyuelin.app.crawler.base.IPage;
import com.wangyuelin.app.crawler.base.PlayUrl;
import com.wangyuelin.app.crawler.douban.service.DoubanMovieService;
import com.wangyuelin.app.crawler.movie.PageUtil;
import com.wangyuelin.app.util.Constant;
import org.apache.http.util.TextUtils;
import org.jsoup.nodes.Document;
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
 * 描述:
 *
 * @outhor wangyuelin
 * @create 2018-06-11 下午6:17
 */
@Component
public class ZuidaDetailPage implements IPage {
    private static final String HOST = "http://zuidazy.com/?m=vod-detail-id";
    private static final Logger logger = LoggerFactory.getLogger(ZuidaDetailPage.class);

    @Autowired
    private DoubanMovieService doubanMovieService;



    @Override
    public void parse(Page page) {
        if (page == null) {
            return;
        }
        Document doc = page.getHtml().getDocument();
        String movieName = getMovieName(doc);
        logger.info("最大资源详情页：" + movieName);
        List<PlayUrl> playUrls = new ArrayList<PlayUrl>();
        List<DownloadLink> downs = new ArrayList<DownloadLink>();

        parsePlayUrl(doc, playUrls, downs);
        if (playUrls.size() > 0) {
            for (PlayUrl playUrl : playUrls) {
                doubanMovieService.savePlayUrl(playUrl, movieName);

            }
        }
    }

    @Override
    public boolean isMine(String url) {
        if ( !TextUtils.isEmpty(url) && url.startsWith(HOST)) {
            return true;
        }
        return false;
    }

    /**
     * 解析得到播放和下载的连接
     * @param doc
     */
    private void parsePlayUrl(Document doc, List<PlayUrl> playUrls, List<DownloadLink> downs) {
        Elements divs = doc.select("div.vodplayinfo > div > div");
        if (divs == null) {
            return;
        }
        if (playUrls == null) {
            playUrls = new ArrayList<PlayUrl>();
        }
        if (downs == null) {
            downs = new ArrayList<DownloadLink>();
        }

        for (Element div : divs) {
            Element h3 = PageUtil.getOne(div, "h3");
            String h3Str = h3.text();
            String playType = "";
            if (!TextUtils.isEmpty(h3Str)) {
                if (h3Str.contains(Constant.PlayLinkType.M3U8)) {//m3u8类型的播放链接
                    playType = Constant.PlayLinkType.M3U8;
                } else if (h3Str.contains(Constant.PlayLinkType.ZUIDALL)) {//直接播放的类型
                    playType = Constant.PlayLinkType.DIRECT_PLAY;
                } else if (h3Str.contains(Constant.PlayLinkType.XUNLEI_DOWNLOAD)) {//迅雷下载
                    playType = Constant.PlayLinkType.DOWNLOAD;
                }

            }
            //获取具体的播放链接
            Elements lis = div.select("ul li");
            if (lis == null || lis.size() == 0) {
                continue;
            }

            for (Element li : lis) {

                Element inputEl = PageUtil.getOne(li, "input");
                if (inputEl == null) {
                    continue;
                }
                String playUrl = inputEl.attr("value");
                if (TextUtils.isEmpty(playUrl)) {
                    continue;
                }
                String content = li.ownText();
                String name = "";
                if (!TextUtils.isEmpty(content)) {
                    name = content.replaceAll(playUrl, "");
                    //去掉$字符
                    int start = name.indexOf("$");
                    if (start > 0) {
                        name = name.substring(0, start);
                    }
                }

                logger.info("最大资源详情页：" + name + " 对应的连接：" + playUrl);
                PlayUrl pUrl = new PlayUrl();
                pUrl.setUrl(playUrl);//播放的url
                pUrl.setType(playType);//播放的类型
                pUrl.setName(name);
                playUrls.add(pUrl);

            }

        }

    }

    /**
     * 获取电影的名称
     * @param doc
     * @return
     */
    private String getMovieName(Document doc) {
        Element h2El = PageUtil.getOne(doc, "div.vodInfo h2");
        if (h2El == null) {
            return "";
        }
        return h2El.ownText();


    }
}