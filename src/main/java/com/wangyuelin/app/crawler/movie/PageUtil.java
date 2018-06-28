package com.wangyuelin.app.crawler.movie;

import com.wangyuelin.app.crawler.dylol.bean.Movie;
import com.wangyuelin.app.util.FileUtil;
import org.apache.http.util.TextUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.selector.PlainText;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageUtil {
    public static final String HOST = "www.loldyttw.net";

    private static String[] types = new String[]{"Dongzuopian","Kehuanpian", "Kongbupian",
            "Xijupian", "Aiqingpian", "Juqingpian", "Zhanzhengpian", "Dongman", "Zongyi"};

    /**
     * 据网页的url获取电影的类型和位置
     * @param url
     * @return
     */
    public static MovieType getMovieType(String url){
        if (TextUtils.isEmpty(url)){
            return null;
        }
        if (!url.contains(HOST)){
            return null;
        }


        MovieType movieType = null;
        if (url.contains("Dongzuopian")){
            movieType = MovieType.ACTION;

        }else if (url.contains("Kehuanpian")){
            movieType = MovieType.SCIENCE_FICTION;
        }else if (url.contains("Kongbupian")){
            movieType = MovieType.DRACULA;
        }else if (url.contains("Xijupian")){
            movieType = MovieType.COMEDY;
        }else if (url.contains("Aiqingpian")){
            movieType = MovieType.AFFECTIONAL;
        }else if (url.contains("Juqingpian")){
            movieType = MovieType.STORY;
        }else if (url.contains("Zhanzhengpian")){
            movieType = MovieType.WAR;
        }else if (url.contains("Dongman")){
            movieType = MovieType.CARTOON;
        }else if (url.contains("Zongyi")){
            movieType = MovieType.VARIETY;
        }else {
            movieType = MovieType.VARIETY;
        }

        return movieType;

    }


    /**
     * 获取排行榜的类型
     * @param title
     * @return
     */
    public static MovieRankType getRankType(String title) {
        if (TextUtils.isEmpty(title) ) {
            return null;
        }
        if (title.contains("电影")) {
            return MovieRankType.MONTH_RANK;
        } else if (title.contains("电视剧")) {
            return MovieRankType.RANK_TV;
        } else if (title.contains("动漫")) {
            return MovieRankType.RANK_CARTOON;
        } else if (title.contains("综艺")) {
            return MovieRankType.RANK_VAR;
        }else if (title.contains("最新更新")){
            return MovieRankType.UP_TO_DATE;
        }else if (title.contains("本月排行")){
            return MovieRankType.BEN_YUE_RANK;
        }
        return null;
    }


    /**
     * 获取一个元素
     *
     * @param doc
     * @param select
     * @return
     */
    public static Element getOne(Document doc, String select) {
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
    public static Element getOne(Element element, String select) {
        if (element == null || TextUtils.isEmpty(select)) {
            return null;
        }
        Elements elements = element.select(select);
        if (elements == null || elements.size() == 0) {
            return null;
        }
        return elements.first();
    }

    /**
     * 判断是否是详情页
     * @param url
     * @return
     */
    public static boolean isDetail(String url){
        if (TextUtils.isEmpty(url)){
            return false;
        }

        for (String type : types){
            int index = url.indexOf(type);
            if (index <= 0){
                continue;
            }

            if (index + type.length() + 1 < url.length()){
                return true;
            }
        }

        return false;

    }

    /**
     * 解析是首页0、分类页1、详情页2
     * @param url
     * @return
     */
    public static PageType pageType(String url){
        if (TextUtils.isEmpty(url)){
            return PageType.UNKNOWN;
        }
        if (!url.contains(HOST)){
            return null;
        }
        int index = url.indexOf(HOST);
        if (index + HOST.length() == url.length() || index + HOST.length() + 1 == url.length()){
            return PageType.HOME_PAGE;
        }

        String regexSort = "(http://|https://)www.loldyttw.net/(Dongzuopian|Kehuanpian|Kongbupian|Xijupian|Aiqingpian|Juqingpian|Zhanzhengpian|Dongman|Zongyi|Meiju)(/)*";

        String regexDetail = "(http://|https://)www.loldyttw.net/(Dongzuopian|Kehuanpian|Kongbupian|Xijupian|Aiqingpian|Juqingpian|Zhanzhengpian|Dongman|Zongyi|Meiju)/([0-9]\\d*).html";
        boolean isMatch = Pattern.matches(regexSort, url);
        if (isMatch){
            return PageType.SORT_PAGE;
        }else if (Pattern.matches(regexDetail, url)){
            return PageType.DEAIL_PAGE;
        }
        return PageType.UNKNOWN;
    }

    public  enum PageType{
        HOME_PAGE,//首页
        SORT_PAGE,//分类页
        DEAIL_PAGE,//详情页
        UNKNOWN;

    }

    /**
     * 将没有域名的url添加上域名
     * @param url
     * @return
     */
    public static String getUrl(String url){
        if (TextUtils.isEmpty(url)){
            return null;
        }
        if (!url.contains(HOST)){
            url = "http://" + HOST + url;
        }
        return url;

    }

    /**
     * 设置电影的位置：首页、恐怖也等
     * @param url
     * @param movie
     */
    public static void setMovieLocation(String url, Movie movie){
        if (TextUtils.isEmpty(url) || movie == null){
            return;
        }
        MovieType movieType = PageUtil.getMovieType(url);
        if (movieType == null){
            return;
        }
        int index = movieType.getIndex();
        String location = movieType.getTypeStr();

        movie.setLocation(String.valueOf(index));
        movie.setLocationStr(location);

    }

    /**
     * 设置电影类型： 恐怖、喜剧等
     * @param url
     * @param movie
     */
    public static void setMovieType(String url, Movie movie){
        if (TextUtils.isEmpty(url) || movie == null){
            return;
        }
        MovieType movieType = PageUtil.getMovieType(url);
        if (movieType == null){
            return;
        }
        int index = movieType.getIndex();
        String typeStr = movieType.getTypeStr();

        movie.setType(String.valueOf(index));
        movie.setTypeStr(typeStr);
    }


    /**
     * 设置电影的排行榜的类型
     * @param title
     * @param movie
     */
    public static void setMovieRankType(String title, Movie movie){
        if (TextUtils.isEmpty(title) || movie == null){
            return;
        }

        MovieRankType movieRankType = PageUtil.getRankType(title);
        if (movieRankType != null){
            movie.setRankType(String.valueOf(movieRankType.getRankType()));
            movie.setRankTypeStr(movieRankType.getRankTypeStr());

        }
    }

    /**
     * 提起日期，格式类似：日期：2018-04-05
     * @param timeStr
     * @return
     */
    public static String parseTime(String timeStr){
        if(TextUtils.isEmpty(timeStr)){
            return null;
        }

        String patt = "[0-9]\\d{3}-[0-9]\\d{1}-[0-9]\\d{1}";
        Pattern r = Pattern.compile(patt);
        Matcher m = r.matcher(timeStr);
        if (m.find()){
            return m.group();
        }
       return null;

    }

    /**
     * 解析得到电影名称：格式类似：xxxxxx《xxxxxx》xxxxx
     * @param title
     * @return
     */
    public static String getMovieName(String title){
        if (TextUtils.isEmpty(title)){
            return null;
        }
        int start = title.indexOf("《");
        if (start < 0){
            return null;
        }
        int end = title.indexOf("》");
        if (end < 0 || start > end || end >= title.length()){
            return null;
        }

        return title.substring(start, end);
    }

    /**
     * 从本地读取html
     * @param path
     * @return
     */
    public static Page getPageFromLocal(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        Request request = new Request();
        request.setUrl("");
        Page page = new Page();
        String content = null;
        content = FileUtil.readToString(path, null);
        page.setRawText(content);
        try {
            page.setBytes(content.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        page.setRequest(request);
        page.setStatusCode(200);
        page.setDownloadSuccess(true);
        page.setUrl(new PlainText(request.getUrl()));
        return page;

    }

}
