package com.wangyuelin.app.crawler.page;

import com.wangyuelin.app.crawler.dylol.bean.Movie;
import com.wangyuelin.app.crawler.dylol.bean.MovieDownload;
import com.wangyuelin.app.crawler.movie.PageUtil;
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
 * 解析电影的详情页
 */
@Component
public class MovieDetailPage implements IPage<Movie> {

    private final Logger logger = LoggerFactory.getLogger(MovieDetailPage.class);
    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public Movie parse(Page page) {
        Movie movie = parseDetail(page);
        logger.info("详情页解析的结果：" + movie);
        return movie;
    }


    private Movie parseDetail(Page page){
        Document doc = page.getHtml().getDocument();

        Movie movie = new Movie();
        //解析得到海报
        Element img = PageUtil.getOne(doc, "div.haibao img");
        if (img != null){
            //获取海报的链接
            String posterUrl = img.attr("src");
            movie.setCoverUrl(posterUrl);
        }

        //获取名称

        Element aTitle = PageUtil.getOne(doc, "div.biaoti a");
        if (aTitle != null){
            String name = aTitle.ownText();
            movie.setName(name);
        }

        //详情连接
        movie.setDetailUrl(page.getUrl().get());

        //获取迅雷下载的链接
        Elements as = doc.select("ul.downurl#ul1  a");
        if (as != null){
            List<MovieDownload> tunders = new ArrayList<MovieDownload>();
            for (Element a : as){
                String downloadName = a.ownText();
                String tunderUrl = a.attr("href");
                MovieDownload tunder = new MovieDownload();
                tunder.setName(downloadName);
                tunder.setUrl(tunderUrl);
                tunders.add(tunder);
            }
            movie.setThunders(tunders);
        }


        //获取磁力链接
        Elements mas = doc.select("div#ljishu ul.downurl div.loldytt a");
        if (mas != null){
            List<MovieDownload> magnets = new ArrayList<MovieDownload>();
            for (Element a : mas){
                String name = a.attr("title");
                String magnetUrl = a.attr("href");

                MovieDownload magnet = new MovieDownload();
                magnet.setUrl(magnetUrl);
                magnet.setName(name);
                magnets.add(magnet);
            }

            movie.setMagnets(magnets);
        }

        //获取种子下载链接
        Elements btsA = doc.select("div#bt ul li a");
        if (btsA != null){
            List<MovieDownload> bts = new ArrayList<MovieDownload>();
            for (Element a : btsA) {
                String name = a.ownText();
                String link = a.attr("href");
                MovieDownload btDownload = new MovieDownload();
                btDownload.setName(name);
                btDownload.setUrl(link);
                bts.add(btDownload);
            }

            movie.setBts(bts);
        }

        //获取基本信息介绍：剧情、演员、导演等

        Element pIntro = PageUtil.getOne(doc, "div.neirong p");
        if (pIntro != null){
            String intro =  pIntro.html();//以<br>分隔不同的信息
            movie.setIntro(intro);
        }
        return movie;

    }
}
