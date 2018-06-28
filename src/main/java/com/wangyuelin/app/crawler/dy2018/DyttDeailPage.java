package com.wangyuelin.app.crawler.dy2018;

import com.wangyuelin.app.crawler.dy2018.service.DyttMovieService;
import com.wangyuelin.app.crawler.movie.PageUtil;
import com.wangyuelin.app.util.MyTextUtil;
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
 * 描述:电影天堂详情页的解析处理
 *
 * @outhor wangyuelin
 * @create 2018-05-21 上午10:48
 */
@Component
public class DyttDeailPage {
    private final Logger logger = LoggerFactory.getLogger(DyttDeailPage.class);

    @Autowired
    private DyttMovieService dyttMovieService;
    private Page page;

    public void handle(Page page) {
        if (page == null) {
            return;
        }
        this.page = page;

        Document doc = page.getHtml().getDocument();
        DyttMovie movie = new DyttMovie();
        movie.setDetailUrl(page.getUrl().get());
        parse(doc, movie);

        if (!TextUtils.isEmpty(movie.getMovieName())) {
            dyttMovieService.saveOne(movie);
        }

    }


    /**
     * 解析详情页
     *
     * @param doc
     * @param movie
     */
    private void parse(Document doc, DyttMovie movie) {
        if (doc == null) {
            return;
        }

        Element titleH1 = PageUtil.getOne(doc, "div.title_all h1");
        if (titleH1 == null) {
            return;
        }
        //获取显示标题
        String showTitle = titleH1.ownText();
        logger.info("显示的标题：" + showTitle);
        movie.setShowTitle(showTitle);

        //获取电影名称
        String movieName = PageUtil.getMovieName(showTitle);
        logger.info("电影的名称：" + movieName);
        movie.setMovieName(movieName);

        //评分
        Element strongEl = PageUtil.getOne(doc, "div.position strong.rank");
        if (strongEl != null) {
            String rank = strongEl.ownText();
            logger.info("评分：" + rank);
            movie.setDoubanRank(rank);
        }

        //类型
        Elements typeEls = doc.select("div.position span a");
        if (typeEls != null) {
            StringBuffer typeBuffer = new StringBuffer();
            for (Element typeEl : typeEls) {
                String typeStr = typeEl.ownText();
                if (!TextUtils.isEmpty(typeStr)) {
                    if (!TextUtils.isEmpty(typeBuffer)) {
                        typeBuffer.append(";");
                    }
                    typeBuffer.append(typeStr);
                }
            }
            movie.setType(typeBuffer.toString());
        }

        //发布时间
        Element timeEl = PageUtil.getOne(doc, "div.position span.updatetime");
        if (timeEl != null) {
            String time = PageUtil.parseTime(timeEl.ownText());
            logger.info("时间：" + time);
            movie.setShowTime(time);
        }

        parseMovieProperty(doc, movie);

        parseDownloads(doc, movie);

    }


    /**
     * 解析详情页，获得对应的属性
     *
     * @param doc
     * @param movie
     * @return
     */
    private DyttMovie parseMovieProperty(Document doc, DyttMovie movie) {
        if (doc == null || movie == null) {
            return null;
        }

        Elements ps = doc.select("div#Zoom > p");
        if (ps == null) {
            return null;
        }

//        int splitIndex = getSplitIndex(ps);
//        if (splitIndex < 0) {
//            return null;
//        }
        int size = ps.size();
        for (int i = 0; i < size; i++) {
            Element pEl = ps.get(i);
            if (i == 0) {//海报
                Element img = PageUtil.getOne(pEl, "img");
                if (img != null) {
                    String src = img.attr("src");
                    logger.info("海报url:" + src);

                    movie.setProperty(MoviePropertys.POSTER, src);
                }
            } else {
                String text = pEl.ownText();
                int splitIndex = getSplitIndex(text);
                if (TextUtils.isEmpty(text) || splitIndex > text.length()) {
                    continue;
                }
                String curkey = text.substring(0, splitIndex);
                if (TextUtils.isEmpty(curkey) || !curkey.startsWith("◎")) {
                    continue;
                }
                String curvalue = text.substring(splitIndex);

                StringBuffer buffer = new StringBuffer();
                if (!TextUtils.isEmpty(MyTextUtil.removeAllBlank(curvalue))) {
                    buffer.append(MyTextUtil.removeAllBlank(curvalue));
                }
                if (!TextUtils.isEmpty(curkey) && curkey.startsWith("◎")) {
                    int nextIndex = getValue(ps, i, buffer);
                    if (nextIndex > 0) {
                        i = --nextIndex;
                    }
                }

                logger.info(curkey + "：" + buffer.toString());
                MoviePropertys property = MoviePropertys.getProperty(MyTextUtil.removeAllBlank(curkey));
                if (property == null) {
                    continue;
                }
                movie.setProperty(property, buffer.toString());

            }
        }

        return movie;

    }

    /**
     * 获取分隔的位置，例如：◎译　　名　西游记·女儿国/西游记之女儿国，以简介作为标准
     *
     * @param ps
     * @return
     */
    private int getSplitIndex(Elements ps) {
        if (ps == null) {
            return -1;
        }
        for (Element p : ps) {
            String curText = p.ownText();
            if (!TextUtils.isEmpty(curText)) {
                String temp = MyTextUtil.removeAllBlank(curText);
                if (temp.contains("简介")) {
                    return curText.length();
                }
            }

        }
        return -1;


    }

    /**
     * 获取分隔的索引，以全角的最后一个空格为标识
     *
     * @param text
     * @return
     */
    private int getSplitIndex(String text) {
        if (TextUtils.isEmpty(text)) {
            return -1;
        }

        MoviePropertys property = MoviePropertys.getProperty(text);
        if (property == MoviePropertys.INTRO || property == MoviePropertys.SCREENSHOT) {
            return text.length();
        }

        int curIndex = -1;
        int size = text.length();
        for (int i = 0; i < size; i++) {
            char c = text.charAt(i);
            if ((int) c == 12288) {
                curIndex = i;
            }
        }

        if (curIndex > -1) {
            return curIndex;
        }
        return text.length();


    }


    /**
     * 获取属性下对应的值
     *
     * @param ps
     * @param curIndex
     * @param buffer
     * @return
     */
    private int getValue(Elements ps, int curIndex, StringBuffer buffer) {
        if (ps == null) {
            return -1;
        }
        int size = ps.size();
        if (curIndex >= size - 1) {
            return -1;
        }
        if (buffer == null) {
            buffer = new StringBuffer();
        }
        int i = curIndex;

        while (true) {
            i++;
            if (i >= size) {
                return -1;
            }
            Element pEl = ps.get(i);
            if (pEl == null) {
                return -1;
            }
            String ownText = pEl.ownText();
            if (!TextUtils.isEmpty(ownText) && ownText.startsWith("◎")) {
                return i;
            } else {
                if (!TextUtils.isEmpty(MyTextUtil.removeAllBlank(ownText))) {//文字
                    if (!TextUtils.isEmpty(MyTextUtil.removeAllBlank(buffer.toString()))) {
                        buffer.append(";");
                    }
                    buffer.append(ownText);
                } else {//连接
                    Element img = PageUtil.getOne(pEl, "img");
                    if (img == null) {//从div获取
                        img = PageUtil.getOne(page.getHtml().getDocument(), "div#Zoom > div > img");
                    }
                    if (img != null) {
                        String src = img.attr("src");
                        if (!TextUtils.isEmpty(src)) {
                            if (!TextUtils.isEmpty(buffer)){
                                buffer.append(";");
                            }
                            buffer.append(src);
                        }
                        return i;//只要获取到截图连接就返回，因为截图连接只会有一个
                    }


                }

            }
        }
    }


    /**
     * 解析下载链接
     *
     * @param doc
     * @param movie
     * @return
     */
    private DyttMovie parseDownloads(Document doc, DyttMovie movie) {
        if (doc == null || movie == null) {
            return null;
        }

        Elements as = doc.select("table[style=\"BORDER-BOTTOM: #cccccc 1px dotted; BORDER-LEFT: #cccccc 1px dotted; TABLE-LAYOUT: fixed; BORDER-TOP: #cccccc 1px dotted; BORDER-RIGHT: #cccccc 1px dotted\"] tbody td a");
        if (as == null) {
            return null;
        }
        StringBuffer buffer = new StringBuffer();
        for (Element a : as) {
            String link = a.text();
            if (!TextUtils.isEmpty(link)) {
                if (!TextUtils.isEmpty(buffer)){
                    buffer.append(";");
                }
                buffer.append(link);
            }

        }
        logger.info("下载链接：" + buffer.toString());
        movie.setDownloads(buffer.toString());
        return movie;

    }
}