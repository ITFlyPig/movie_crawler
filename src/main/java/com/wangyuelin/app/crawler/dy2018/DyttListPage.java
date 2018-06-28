package com.wangyuelin.app.crawler.dy2018;

import com.wangyuelin.app.crawler.dylol.bean.LinkBean;
import com.wangyuelin.app.crawler.movie.PageUtil;
import com.wangyuelin.app.crawler.dylol.service.LinkService;
import com.wangyuelin.app.util.ArrayUtils;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 描述:用来解析分类的列表
 *
 * @outhor wangyuelin
 * @create 2018-05-16 下午4:48
 */
@Component
public class DyttListPage {

    private final Logger logger = LoggerFactory.getLogger(DyttListPage.class);
    @Autowired
    private LinkService linkService;

     private Page page;;

    public void handle(Page page) {
        this.page = page;
        String url = page.getUrl().get();
        String sortTitle = getTitleStr(page);
        if (TextUtils.isEmpty(sortTitle)) {
            return;
        }
        TableUtil.Table table = TableUtil.getMovieTable(sortTitle);
        if (table == null) {
            return;
        }

        if (!url.endsWith(".html")) {//分类中除了第一页的其他页面
            //将分类 下的所有页面的连接添加
            addAllPage(page);
        }
        List<LinkBean> links = getAllMovies(page);
        if (!ArrayUtils.isEmpty(links)) {
            linkService.save(links, table.getEnName(), false);
        }

    }


    /**
     * 将一个分类下的所有页面的链接添加
     *
     * @param page
     */
    private void addAllPage(Page page) {
        if (page == null) {
            return;
        }
        String url = page.getUrl().get();
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Document doc = page.getHtml().getDocument();

        Elements as = doc.select("div.x p a");
        if (as == null) {
            return;
        }
        for (Element a : as) {
            String text = a.ownText();
            if (!TextUtils.isEmpty(text) && text.equals("尾页")) {
                String href = a.attr("href");
                if (TextUtils.isEmpty(href)) {
                    continue;
                }
                //获得最大的页数
                String sufix = href.substring(href.lastIndexOf("/"), href.length());
                if (TextUtils.isEmpty(sufix)) {
                    continue;
                }
                String pattern = "[1-9]\\d*";
                Pattern p = Pattern.compile(pattern);
                Matcher matcher = p.matcher(sufix);
                if (matcher != null && matcher.find()) {
                    String maxPageNumStr = matcher.group();
                    logger.info("最大页数：" + maxPageNumStr);
                    try {
                        int maxPageNum = Integer.valueOf(maxPageNumStr);
                        String prefix = url.substring(0, url.lastIndexOf("/"));
                        if (!TextUtils.isEmpty(prefix)) {
                            for (int i = 1; i <= maxPageNum; i++) {
                                String pageUrl = prefix + "/index_" + i + ".html";
                                logger.info("解析得到的本类的页面url：" + pageUrl);
                                addRequestUrl(pageUrl);

                            }

                        }

                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }


                }


            }
        }

    }

    /**
     * 将一个页面的所有电影解析
     *
     * @param page
     */
    private List<LinkBean> getAllMovies(Page page) {
        Document doc = page.getHtml().getDocument();
        Elements tables = doc.select("div.co_content8 ul table");
        if (tables == null) {
            return null;
        }
        List<LinkBean> linkBeans = new ArrayList<LinkBean>();
        for (Element tableEl : tables) {
            LinkBean linkBean = handleOneTableElement(tableEl);
            if (linkBean != null && !TextUtils.isEmpty(linkBean.getMovieName())) {
                linkBeans.add(linkBean);
            }

        }
        return linkBeans;

    }

    /**
     * 处理一个table标签，一个table标签里面就是一个item的电影信息
     *
     * @param tableEl
     */
    private LinkBean handleOneTableElement(Element tableEl) {
        if (tableEl == null) {
            return null;
        }
        Elements trs = tableEl.select("tr");
        if (trs == null) {
            return null;
        }
        int size = trs.size();
        LinkBean linkBean = new LinkBean();
        for (int i = 0; i < size; i++) {
            Element trEl = trs.get(i);
            //第一个tr：标题
            //地二个tr：日期和评分
            //第三个tr：片名、类型和 主演
            if (i == 1) {
                Elements as = trEl.select("a");
                if (as == null) {
                    continue;
                }
                int index = 0;
                for (Element a : as) {
                    if (index == 0) {
                        String type = a.ownText();//类型
                        logger.info(type);
                    } else if (index == 1) {
                        String title = a.attr("title");//标题
                        linkBean.setTitle(title);

                        String href = a.attr("href");
                        if (!href.contains(DYConstant.HOST)) {
                            href = DYConstant.HOST + href;
                            linkBean.setLink(href);
                            addRequestUrl(href);

                        }

                        //解析得到真正的电影名称
                        if (!TextUtils.isEmpty(title)) {
                            String movieName = title.substring(title.indexOf("《") + 1, title.indexOf("》"));
                            linkBean.setMovieName(movieName);

                        }
                        logger.info(title);
                    }
                    index++;

                }

            } else if (i == 2) {
                Elements fonts = trEl.select("font");
                if (fonts == null) {
                    continue;
                }
                int sizeFont = fonts.size();
                for (int j = 0; j < sizeFont; j++) {
                    Element font = fonts.get(j);
                    if (j == 0) {
                        String time = font.ownText();//日期
                        if (!TextUtils.isEmpty(time)) {
                            time = PageUtil.parseTime(time);
                            linkBean.setTime(time);

                        }
                        linkBean.setTime(time);
                        logger.info(time);
                    } else if (j == 1) {
                        String grade = font.ownText();//评分
                        logger.info(grade);
                    }
                }

            }
            if (i == 3) {
                Elements ps = trEl.select("p");
                if (ps == null) {
                    continue;
                }
                int sizeP = ps.size();
                for (int k = 0; k < sizeP; k++) {//片名、类型和 主演
                    Element p = ps.get(k);
                    String str = p.ownText();
                    logger.info(str);


                }

            }
        }
        return linkBean;


    }

    /**
     * 获取分类的标题
     *
     * @param page
     * @return
     */
    private String getTitleStr(Page page) {
        if (page == null) {
            return null;
        }
        Document doc = page.getHtml().getDocument();
        Element font = PageUtil.getOne(doc, "div.title_all font");
        if (font != null) {
            return font.text();
        }
        return null;

    }


    /**
     * 添加请求的url
     * @param url
     */
    private void addRequestUrl(String url){
        if (TextUtils.isEmpty(url) || page == null){
            return;
        }

        logger.info("将列表页中解析得到的详情页的连接添加：" + url);
        page.addTargetRequest(url);

    }


}