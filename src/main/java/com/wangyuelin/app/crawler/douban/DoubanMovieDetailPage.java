package com.wangyuelin.app.crawler.douban;

import com.wangyuelin.app.crawler.base.ActorBean;
import com.wangyuelin.app.crawler.base.IPage;
import com.wangyuelin.app.crawler.base.MovieBean;
import com.wangyuelin.app.crawler.douban.mapper.MovieMapper;
import com.wangyuelin.app.crawler.douban.service.DoubanMovieService;
import com.wangyuelin.app.crawler.movie.PageUtil;
import com.wangyuelin.app.crawler.zuida.ZuidaPage;
import com.wangyuelin.app.crawler.zuida.ZuidaSearchPage;
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

/**
 * 描述: 处理豆瓣的视频页面
 *
 * @outhor wangyuelin
 * @create 2018-05-30 下午4:56
 */
@Component
public class DoubanMovieDetailPage implements IPage {
    private static final Logger logger = LoggerFactory.getLogger(DoubanMovieDetailPage.class);
    public static final String HOST = "https://movie.douban.com/subject/";
    @Autowired
    private MovieMapper movieMapper;
    @Autowired
    private ZuidaPage zuidaPage;
    @Autowired
    private ZuidaSearchPage zuidaSearchPage;

    @Autowired
    private DoubanMovieService doubanMovieService;

    @Override
    public void parse(Page page) {
        if (page == null) {
            return;
        }
        Document doc = page.getHtml().getDocument();
        MovieBean movieBean = new MovieBean();
        parseInfo(doc, movieBean);
        parseName(doc, movieBean);
        parseIntro(doc, movieBean);
        parseAboutPerson(doc, movieBean);
        parsePoster(doc, movieBean);
        parseDoubanRank(doc, movieBean);
        movieBean.setDetaiWeblUrl(page.getUrl().get());

        //TODO 将数据放入数据库
        doubanMovieService.updateByDetailUrl(movieBean);

        //TODO 查询电影的下载和在线播放的url
        zuidaSearchPage.startSearch(movieBean.getName());


    }

    @Override
    public boolean isMine(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        if (url.startsWith(HOST)) {
            return true;
        }
        return false;
    }


    /**
     * 解析得到基本的信息
     *
     * @param doc
     */
    private void parseInfo(Document doc, MovieBean movieBean) {
        if (doc == null) {
            return;
        }
        Element infoEl = PageUtil.getOne(doc, "div#info");
        if (infoEl == null) {
            return;
        }

        String introStr = infoEl.text();
        if (TextUtils.isEmpty(introStr)) {
            return;
        }

        String[] items = introStr.split(" ");
        if (items == null) {
            return;
        }

        String key = "";
        StringBuffer value = new StringBuffer();
        for (String item : items) {
            if (isKey(item)) {
                if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                    movieBean.set(key, value.toString());
                    //复位
                    key = "";
                    value = new StringBuffer();

                }
                key = item;
            } else {
                value.append(item);

            }

        }
        if (!TextUtils.isEmpty(key) && isKey(key) && !TextUtils.isEmpty(value)) {//最后的一个key-value可能循环不到
            movieBean.set(key, value.toString());
        }

        logger.info("解析到的信息：" + movieBean.toString());

    }


    /**
     * 是否是key，即导演、演员这些的
     *
     * @return
     */
    private String[] keys = new String[]{"导演", "编剧", "主演", "类型", "国家", "地区", "语言", "上映日期", "片长", "又名", "IMDb链接"};

    private boolean isKey(String key) {
        if (TextUtils.isEmpty(key)) {
            return false;
        }
        for (String s : keys) {
            if (key.contains(s) && key.contains(":")) {
                return true;
            }
        }

        return false;
    }

    /**
     * 解析获得名称
     *
     * @param doc
     */
    private void parseName(Document doc, MovieBean movieBean) {
        if (doc == null) {
            return;
        }
        Elements spans = doc.select("h1 span");
        if (spans == null) {
            return;
        }
        int size = spans.size();
        for (int i = 0; i < size; i++) {
            Element span = spans.get(i);
            if (i == 0) {//名称
                String name = span.text();
                String cnName = getCnName(name);
                String enName = getEnName(name);
                movieBean.setEnName(enName);
                movieBean.setName(cnName);
            } else if (i == 1) {//上映的年份
                String year = span.ownText().replaceAll("\\u0028", "").replaceAll("\\u0029", "");
                movieBean.setShowYear(year);
            }
        }
    }

    /**
     * 中文名
     *
     * @param name
     * @return
     */
    private String getCnName(String name) {
        if (TextUtils.isEmpty(name)) {
            return null;
        }

        int start = name.indexOf(" ");
        if (start < 0) {
            return name;
        } else {
            return name.substring(0, start);
        }


    }

    /**
     * 英文名
     *
     * @param name
     * @return
     */
    private String getEnName(String name) {
        if (TextUtils.isEmpty(name)) {
            return null;
        }

        int start = name.indexOf(" ");
        if (start < 0) {
            return name;
        } else {
            return name.substring(start);
        }

    }


    /**
     * 解析得到简介
     *
     * @param doc
     * @param movieBean
     */
    private void parseIntro(Document doc, MovieBean movieBean) {
        if (doc == null || movieBean == null) {
            return;
        }

        Element span = PageUtil.getOne(doc, "div.related-info div.indent span");
        if (span == null) {
            return;
        }

        String intro = span.html();
        movieBean.setIntro(intro);
    }

    /**
     * 相关的影人
     *
     * @param doc
     * @param movieBean
     */
    private void parseAboutPerson(Document doc, MovieBean movieBean) {
        if (doc == null || movieBean == null) {
            return;
        }

        Elements lis = doc.select("ul.celebrities-list.from-subject.__oneline li");
        if (lis == null) {
            return;
        }
        for (Element li : lis) {
            ActorBean actorBean = new ActorBean();
            if (movieBean.getAboutPerson() == null) {
                movieBean.setAboutPerson(new ArrayList<ActorBean>());
            }
            Element avatarEl = PageUtil.getOne(li, "div.avatar");
            if (avatarEl != null) {
                String urlInfo = avatarEl.attr("style");
                if (!TextUtils.isEmpty(urlInfo)) {
                    int start = urlInfo.indexOf("(");
                    int end = urlInfo.indexOf(")");
                    if (start < end && start >= 0) {
                        String url = urlInfo.substring(start + 1, end);//真正的头像的地址
                        actorBean.setAvatar(url);
                    }
                }
            }

            Element nameEl = PageUtil.getOne(li, "span.name a");
            if (nameEl != null) {
                String name = nameEl.ownText();//名称
                actorBean.setName(name);
            }

            Element spanEl = PageUtil.getOne(li, "span.role");
            if (spanEl != null) {
                String role = spanEl.ownText();//饰演的角色
                actorBean.setRole(role);

            }
        }
    }

    /**
     * 解析豆瓣评分
     * @param doc
     * @param movieBean
     */
    private void parseDoubanRank(Document doc, MovieBean movieBean) {
        if (doc == null || movieBean ==null) {
            return;
        }

        Element strongEl = PageUtil.getOne(doc, "strong.ll.rating_num");
        if (strongEl != null) {
            String rank = strongEl.ownText();//豆瓣评分
            if (!TextUtils.isEmpty(rank)) {
                try {
                    float doubanRank = Float.valueOf(rank);
                    movieBean.setDoubanRank(doubanRank);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * 解析海报的url
     * @param doc
     * @param movieBean
     */
    private void parsePoster(Document doc, MovieBean movieBean) {
        if (doc == null || movieBean == null) {
            return;
        }

        Element imgEl = PageUtil.getOne(doc, "div#mainpic img");
        if (imgEl == null) {
            return;
        }
        String poster = imgEl.attr("src");
        movieBean.setPosters(poster);


    }
}