package com.wangyuelin.app.crawler.page;

import com.wangyuelin.app.crawler.dylol.bean.Movie;
import com.wangyuelin.app.crawler.movie.MovieRankType;
import com.wangyuelin.app.crawler.movie.PageUtil;
import com.wangyuelin.app.util.ArrayUtils;
import org.apache.http.util.TextUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;

import java.util.*;

/**
 * 解析电影的列表页
 */
@Component
public class MovieListPage implements IPage<List<Movie>> {
    private final Logger logger = LoggerFactory.getLogger(MovieListPage.class);
    private static final String PRE_FUIX = "https://www.loldytt.com/";//url的前缀
    private String mPageUrl;


    @Override
    public String getUrl() {
        return mPageUrl;
    }

    @Override
    public List<Movie> parse(Page page) {
        if (page == null) {
            return null;
        }
        mPageUrl = page.getUrl().get();

        ArrayList<Movie> reslut = new ArrayList<Movie>();
        //开始解析推荐的
        List<Movie> re = parseRecommend(page);
        if (!ArrayUtils.isEmpty(re)){
            reslut.addAll(re);
        }

        //解析本月排行和最新更新
        List<Movie> rankList = parseSortList(page);
        if (!ArrayUtils.isEmpty(rankList)){
            reslut.addAll(rankList);
        }


        //此种类型下所有的电影
        List<Movie> all = parseAll(page);
        if (!ArrayUtils.isEmpty(all)){
            reslut.addAll(all);
        }


        return reslut;
    }

    /**
     * 解析推荐的电影
     *
     * @param page
     * @return
     */
    private List<Movie> parseRecommend(Page page) {
        Document doc = page.getHtml().getDocument();
        if (doc == null) {
            return null;
        }

        Elements lis = doc.select("div.commend li");
        if (lis == null) {
            return null;
        }
        List<Movie> movies = new ArrayList<Movie>();
        for (Element li : lis) {

            Element a = getOne(li, "a.db");
            if (a == null) {
                continue;
            }
            Movie movie = new Movie();
            String detailUrl = a.attr("href");
            String title = a.attr("title");
            movie.setDetailUrl(detailUrl);
            movie.setName(title);
            movies.add(movie);

            if (!TextUtils.isEmpty(detailUrl)) {
                page.addTargetRequest(PageUtil.getUrl(detailUrl));
            }

            //设置位置
            PageUtil.setMovieLocation(mPageUrl, movie);
            //设置类型
            PageUtil.setMovieType(mPageUrl, movie);

            //获取排行信息
            MovieRankType rankType = MovieRankType.RECOMAND;
            movie.setRankType(String.valueOf(rankType.getRankType()));
            movie.setRankTypeStr(rankType.getRankTypeStr());

        }
        return movies;

    }


    /**
     * 解析没有图片的列表更新、本月排行、超赞排行都是一个样式
     *
     * @param page
     * @return 排行的名称
     */
    private List<Movie> parseSortList(Page page) {
        if (page == null) {
            return null;
        }

        Document doc = page.getHtml().getDocument();
        Elements divs = doc.select("div.xifen");//得到本月排行
        if (divs == null) {
            return null;
        }

        Elements divUpdates = doc.select("div.gengxin");//获取更新的数据
        if (divUpdates != null) {
            divs.addAll(divUpdates);
        }

        List<Movie> movies = new ArrayList<Movie>();
        for (Element div : divs) {
            //解析电影列表
            Elements uls = div.select("ul");//得到本月排行
            if (uls == null) {
                return null;
            }

            for (Element ul : uls) {
                Elements lis = ul.select("li");
                if (lis == null) {
                    continue;
                }
                //解析标题
                Element b = getOne(div, "div b");
                String title = null;
                if (b != null) {
                    title = b.text();
                }
                for (Element li : lis) {
                    Element a = getOne(li, "a");
                    if (a == null) {
                        continue;
                    }
                    //解析得到电影名称
                    String name = a.text();
                    //解析得到电影详情的url
                    String detailUrl = a.attr("href");
                    Movie movie = new Movie();
                    movie.setName(name);
                    movie.setDetailUrl(detailUrl);
                    movies.add(movie);

                    if (!TextUtils.isEmpty(detailUrl)) {
                        page.addTargetRequest(PageUtil.getUrl(detailUrl));
                    }

                    //设置位置
                    PageUtil.setMovieLocation(mPageUrl, movie);
                    //设置类型
                    PageUtil.setMovieType(mPageUrl, movie);
                    //设置排行类型
                    PageUtil.setMovieRankType(title, movie);


                }
            }


        }
        return movies;

    }


    /**
     * 解析得到全部的电影，map的key是电影的首个汉字的第一个字母，比如按照a、b、c等来查找
     *
     * @param page
     * @return
     */
    private List<Movie> parseAll(Page page) {
        if (page == null) {
            return null;
        }
        Document doc = page.getHtml().getDocument();

        Elements divs = doc.select("div.classpage div.middle2aa1");
        if (divs == null) {
            return null;
        }

        ArrayList<Movie> movies = new ArrayList<Movie>();
        for (Element div : divs) {
            //解析字母
            Element a = getOne(div, "div.middle2aa1_h div.fl > a");
            if (a == null) {
                continue;
            }
            String letter = a.text();
            if (TextUtils.isEmpty(letter)) {
                continue;
            }

            //解析字母下面对应的电影
            Elements lis = div.select("ul li");
            if (lis == null) {
                continue;
            }

            for (Element li : lis) {
                Element aEl = getOne(li, "a");
                if (aEl == null) {
                    continue;
                }

                String name = aEl.text();
                String url = aEl.attr("href");
                Movie movie = new Movie();
                movie.setName(name);
                movie.setDetailUrl(url);
                movies.add(movie);

                if (TextUtils.isEmpty(url)) {
                    page.addTargetRequest(PageUtil.getUrl(url));
                }

            }

        }
        return movies;

    }


    /**
     * 获取一个元素
     *
     * @param doc
     * @param select
     * @return
     */
    private Element getOne(Document doc, String select) {
        if (doc == null || TextUtils.isEmpty(select)) {
            return null;
        }
        Elements elements = doc.select(select);
        if (elements == null || elements.size() == 0) {
            return null;
        }
        return elements.first();

    }


    /**
     * 获取一个元素
     *
     * @param element
     * @param select
     * @return
     */
    private Element getOne(Element element, String select) {
        if (element == null || TextUtils.isEmpty(select)) {
            return null;
        }
        Elements elements = element.select(select);
        if (elements == null || elements.size() == 0) {
            return null;
        }
        return elements.first();
    }
}
