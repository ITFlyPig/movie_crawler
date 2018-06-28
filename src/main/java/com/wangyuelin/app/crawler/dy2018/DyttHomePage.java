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

/**
 * 电影天堂的首页解析
 */
@Component
public class DyttHomePage {
    private static String[] needs = new String[]{"精品", "必看", "迅雷电影", "经典大片"};
    private final Logger logger = LoggerFactory.getLogger(DyttHomePage.class);

    @Autowired
    private LinkService linkService;

    private Page page;


    public void handle(Page page){
        if (page == null){
            return;
        }
        this.page = page;
        String url = page.getUrl().get();
        if (TextUtils.isEmpty(url)){
            return;
        }
        Elements allSortsEl = getAllSorts(page.getHtml().getDocument());
        if (allSortsEl == null){
            return;
        }
        //解析每个分类
        for (Element sortEl : allSortsEl) {
            String title = getSortTitle(sortEl);
            logger.info("解析得到的title：" + title);
            if (isSortNeed(title)){//需要的分类才解析
                logger.info("开始解析title：" + title);
                Elements sortItems = getSortItems(sortEl);
                List<LinkBean> linkBeans = parseAllSortItem(sortItems);
                if (ArrayUtils.isEmpty(linkBeans)){
                    continue;
                }
                //获取类型
                TableUtil.Table table = TableUtil.getMovieTable(title);
                if (table == null){
                    continue;
                }
                //数据库插入

                linkService.save(linkBeans, table.getEnName(), true);


            }
        }


        parseAllSortLinks(page);



    }

    //获取首页所有的分类
    private Elements getAllSorts(Document doc){
        return doc.select("div.co_area2");
    }

    /**
     * 获取分类的标题
     * @param sortEl
     * @return
     */
    private String getSortTitle(Element sortEl){
        if (sortEl == null){
            return null;
        }
        Element titleEl = PageUtil.getOne(sortEl, "div.title_all span");
        Element aEl = PageUtil.getOne(sortEl, "div.title_all span a");
        if (aEl != null){
            return aEl.ownText();
        }
        if (titleEl == null){
            return null;
        }
        return titleEl.ownText();
    }

    /**
     * 获取分类下的所有item
     * @param sortEl
     * @return
     */
    private Elements getSortItems(Element sortEl){
        if (sortEl == null){
            return null;
        }
        return sortEl.select("ul > li");
    }

    /**
     * 判断分类是否是需要的
     * @param title
     * @return
     */
    private boolean isSortNeed(String title){
        if (TextUtils.isEmpty(title)){
            return false;
        }

        for (String need : needs) {
            if (title.contains(need)){
                return true;
            }
        }
        return false;
    }

    /**
     * 解析一个分类下的所有的item
     * @param allItems
     * @return
     */
    private List<LinkBean> parseAllSortItem(Elements allItems){
        if (allItems == null){
            return null;
        }
        List<LinkBean> linkBeans = new ArrayList<LinkBean>();
        for (Element item : allItems) {
            LinkBean linkBean = new LinkBean();
            Element a = PageUtil.getOne(item, "a");
            if (a == null){
                continue;
            }
            //解析先死名称
            String title = a.ownText();
            //解析电影名称
            if (!TextUtils.isEmpty(title)){
                String name = title.substring(title.indexOf("《") + 1, title.indexOf("》"));
                linkBean.setMovieName(name);
            }

            //解析连接
            String url = a.attr("href");
            linkBean.setTitle(title);
            linkBean.setLink(url);
            addRequestUrl(url);

            Element span = PageUtil.getOne(item, "span");
            if (span != null){
                String time = span.ownText();
                //解析时间
                linkBean.setTime(time);
            }
            linkBeans.add(linkBean);
        }
        return linkBeans;
    }

    /**
     * 解析得到所有分类的连接
     * @param page
     */
    private void parseAllSortLinks(Page page){
        Document doc = page.getHtml().getDocument();
        Elements as = doc.select("div#menu div.contain ul li > a");
        for (Element a : as) {
            String shortLink = a.attr("href");
            if (!TextUtils.isEmpty(shortLink) && !shortLink.contains(DYConstant.HOST)){
                String link = DYConstant.HOST + shortLink;
                //需要的分类链接才添加
                DYConstant.PageDYType pageDYType =  DYConstant.getPageType(link);
                if (pageDYType != null && pageDYType.equals(DYConstant.PageDYType.SORT_LIST)){
                    page.addTargetRequest(link);
                    logger.info("添加的的分类：" + a.ownText() + " 连接：" + link);
                }

            }

        }
    }

    /**
     * 添加请求的url
     * @param url
     */
    private void addRequestUrl(String url){
        if (TextUtils.isEmpty(url) || page == null){
            return;
        }
        page.addTargetRequest(url);

    }
}
