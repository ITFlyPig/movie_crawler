package com.wangyuelin.app.crawler.dy2018;

import com.wangyuelin.app.util.MyTextUtil;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 描述:电影的属性，中英对照
 *
 * @outhor wangyuelin
 * @create 2018-05-21 上午11:13
 */
public enum  MoviePropertys {
    POSTER(1, "海报", "poster"),
    TRANSLATED_NAME(2, "译名", "translated_name"),
    MOVIE_NAME(3, "片名", "movie_name"),
    YEAR(4, "年代", "year"),
    LOCATION(5, "产地", "location"),
    TYPE(6, "类别", "type"),
    LANGUAGE(7, "语言", "language"),
    SUBTITLE(8, "字幕", "subtitle"),
    SHOW_TIME(9, "上映日期", "show_time"),
    DOUBAN_RANK(10, "豆瓣评分", "douban_rank"),
    IMDB_RANK(11, "IMDb评分", "imdb_rank"),
    FORMAT(12, "文件格式", "format"),
    SIZE(13, "文件尺寸,视频尺寸", "size"),
    DAXIAO(14, "文件大小", "daxiao"),
    TOTAL_TIME(15, "片长", "total_time"),
    DIRECTOR(16, "导演", "director"),
    ACTORS(17, "主演", "actors"),
    INTRO(18, "简介", "intro"),
    SCREENSHOT(19, "截图", "screenshot"),
    DOWNLOADS(20, "下载地址", "downloads"),
    NAME(21, "名称", "name"),
    SHOW_TITLE(22, "显示标题", "show_title"),
    URL(23, "电影详情链接", "detail_url");



    private int index;
    private String cnName;
    private String enName;


    MoviePropertys(int index, String cnName, String enName) {
        this.index = index;
        this.cnName = cnName;
        this.enName = enName;
    }


    /**
     * 据字符串返回对应的电影属性
     * @param text
     * @return
     */
    public static MoviePropertys getProperty(String text){
        if (TextUtils.isEmpty(text)){
            return null;
        }
        text = text.replaceAll("◎", "");
        text = MyTextUtil.removeAllBlank(text);

        MoviePropertys[] propertys = MoviePropertys.values();
        if (propertys == null){
            return null;
        }

        for (MoviePropertys property : propertys) {
            String key = property.cnName;
            if (text.contains(key) || key.contains(text) || text.equals(key)){
                System.out.println(text + " 有对应的类型：" + property.enName);
                return property;
            }

        }
        return null;

    }
}