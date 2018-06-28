package com.wangyuelin.app.crawler.page;

import com.wangyuelin.app.crawler.dylol.bean.Movie;
import com.wangyuelin.app.crawler.dylol.bean.PageHomeBean;
import com.wangyuelin.app.crawler.movie.MovieRankType;
import com.wangyuelin.app.crawler.movie.MovieType;
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

/**
 * 解析首页
 */
@Component
public class HomePage implements IPage<List<Movie>> {
    private final Logger logger = LoggerFactory.getLogger(HomePage.class);

    private String[] queryCss = new String[]{"div.zuoce div.xinfenlei",  "div.zuocez div.xxfl"};
    private String[] rankQueryCss = new String[]{"div.youce div.yueyph", "div.youcey div.yyph"};

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public List<Movie> parse(Page page) {
        parseTypes(page);

        ArrayList<Movie> movies = new ArrayList<Movie>();
        parseUpdateMovies(page, movies);
        parseRankMovies(page, movies);
        parseRecmond(page, movies);
        return movies;
    }

    /**
     * 解析得到本页面不同类型的最新的电影
     *
     * @param page
     * @return
     */
    private void parseUpdateMovies(Page page, ArrayList<Movie> movies) {
        Document doc = page.getHtml().getDocument();

        for (String cssq : queryCss) {
            Elements divs = doc.select(cssq);
            if (divs == null) {
                continue;
            }
            for (Element div : divs) {
                //解析标题和新电影列表
                String title = null;
                Element titleEl = PageUtil.getOne(div, "h2");
                if (titleEl != null) {
                    //标题
                    title = titleEl.text();
                }

                //列表
                Elements lis = div.select("ul>li");
                if (lis != null) {
                    for (Element li : lis) {
                        Movie movie = new Movie();
                        //更新的时间
                        Element timeEl = PageUtil.getOne(li, "p");
                        if (timeEl != null) {
                            String time = timeEl.text();
                            movie.setTime(time);
                        }
                        //类别
                        String typeStr = li.ownText();
                        //去除多余的字符
                        if (!TextUtils.isEmpty(typeStr)){
                            typeStr.replaceAll("】","");
                            typeStr.replaceAll("【", "");
                        }
                        movie.setTypeStr(typeStr);
                        //名称和链接
                        Element a = PageUtil.getOne(li, "a");
                        if (a != null) {
                            String name = a.text();
                            String detailUrl = a.attr("href");
                            movie.setName(name);
                            movie.setDetailUrl(detailUrl);

                            page.addTargetRequest(PageUtil.getUrl(detailUrl));

                        if (!TextUtils.isEmpty(detailUrl)){
                            page.addTargetRequest(PageUtil.getUrl(detailUrl));
                        }
                        }

                        //设置在页面的位置
                        movie.setLocation(String.valueOf(MovieType.HOME.getIndex()));
                        movie.setLocationStr(MovieType.HOME.getTypeStr());

                        //设置排行的类型
                        MovieRankType movieRankType = getUpdateType(title);
                        if (movieRankType != null){
                            movie.setRankType(String.valueOf(movieRankType.getRankType()));
                            movie.setRankTypeStr(movieRankType.getRankTypeStr());
                        }



                        movies.add(movie);

                    }
                }

            }






        }


    }

    /**
     * 将解析得到的电影放入对应的list
     *
     * @param title
     * @param movies
     */
    private void fillType(String title, List<Movie> movies, PageHomeBean pageHomeBean) {
        if (TextUtils.isEmpty(title) || pageHomeBean == null) {
            return;
        }
        if (title.contains("电影")) {
            pageHomeBean.setNewMovies(movies);
        } else if (title.contains("电视剧")) {
            pageHomeBean.setNewTeleplays(movies);
        } else if (title.contains("动漫")) {
            pageHomeBean.setNewCartoons(movies);
        } else if (title.contains("综艺")) {
            pageHomeBean.setNewVarietys(movies);
        }
    }



    /**
     * 获取最新更新的排行榜
     * @param title
     * @return
     */
    private MovieRankType getUpdateType(String title) {
        if (TextUtils.isEmpty(title) ) {
            return null;
        }
        if (title.contains("电影")) {
            return MovieRankType.UPDATE;
        } else if (title.contains("电视剧")) {
            return MovieRankType.NEW_TV;
        } else if (title.contains("动漫")) {
            return MovieRankType.NEW_CARTOON;
        } else if (title.contains("综艺")) {
            return MovieRankType.NEW_VAR;
        }
        return null;
    }

    /**
     * 解析得到排行的电影
     *
     * @param page
     * @param movies
     * @return
     */
    private void parseRankMovies(Page page, ArrayList<Movie> movies) {
        Document doc = page.getHtml().getDocument();

        for (String ranCssq : rankQueryCss) {
            Elements divs = doc.select(ranCssq);
            for (Element div : divs) {
                //解析标题
                String title = null;
                Element titleEl = PageUtil.getOne(div, "div > h3");
                if (titleEl != null){
                    title = titleEl.ownText();
                }

                //解析排行列表
                Elements lis = div.select("ul > li");
                for (Element li : lis) {
                    Movie movie = parseLi(li, page);
                    if (movie == null){
                        continue;
                    }
                    //设置在页面中的位置
                    movie.setLocation(String.valueOf(MovieType.HOME.getIndex()));
                    movie.setLocationStr(MovieType.HOME.getTypeStr());

                    //设置排行榜的类型
                    MovieRankType movieRankType = PageUtil.getRankType(title);
                    if (movieRankType != null){
                        movie.setRankType(String.valueOf(movieRankType.getRankType()));
                        movie.setRankTypeStr(movieRankType.getRankTypeStr());
                        logger.info("title:" + title + "电影：" + movie.getName() + " 排行榜：" + movieRankType.getRankTypeStr());

                    }

                    movies.add(movie);

                }

            }


        }

    }

    /**
     * 解析电影名称和详情
     * @param li
     * @return
     */
    public Movie parseLi(Element li, Page page){
        if (li == null){
            return null;
        }
        Movie movie = new Movie();
        Element a = PageUtil.getOne(li, "a");
        if (a == null){
            return null;
        }
        String name = a.attr("title");
        if (TextUtils.isEmpty(name)){
            name = a.ownText();
        }
        String detailUrl = a.attr("href");
        movie.setName(name);
        movie.setDetailUrl(detailUrl);

        if (!TextUtils.isEmpty(detailUrl)){
            page.addTargetRequest(PageUtil.getUrl(detailUrl));
        }

        return movie;
    }

    /**
     * 解析推荐的电影
     * @param page
     * @return
     */
    private void parseRecmond(Page page, ArrayList<Movie> movies){
        Document doc = page.getHtml().getDocument();
        Elements lis = doc.select("div.commend > ul > li");
        for (Element li : lis) {
            Movie movie = parseLi(li, page);
            if (movie != null){
                //设置在页面中的位置
                movie.setLocation(String.valueOf(MovieType.HOME.getIndex()));
                movie.setLocationStr(MovieType.HOME.getTypeStr());

                //设置
                movie.setRankType(String.valueOf(MovieRankType.RECOMAND.getRankType()));
                movie.setRankTypeStr(MovieRankType.RECOMAND.getRankTypeStr());

                movies.add(movie);
            }
        }


    }

    /**
     * 解析出所有的分类
     * @param page
     */
    private void parseTypes(Page page){
        if (page == null){
            return;
        }
        Document doc = page.getHtml().getDocument();
        Elements as = doc.select("div.new_nav ul li a");
        if (as == null){
            return;
        }
        for (Element a : as) {
            String href = a.attr("href");
            page.addTargetRequest(PageUtil.getUrl(href));

        }

    }

}
