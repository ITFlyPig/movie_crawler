package com.wangyuelin.app.crawler.movie;

import org.apache.http.util.TextUtils;

/**
 * 描述:电影的标签
 *
 * @outhor wangyuelin
 * @create 2018-06-26 下午3:27
 */
public enum  MovieTag {
    ZUIRE(1,"最热"),
    ZUIXIN(2,"最新"),
    HAOAPING(3,"好评"),
    TUIJIAN(4,"推荐"),
    DOUBANGAOFEN(5, "豆瓣高分");
    private int tag;
    private String tagStr;

    MovieTag(int tag, String tagStr) {
        this.tag = tag;
        this.tagStr = tagStr;
    }

    public int getTag() {
        return tag;
    }


    public String getTagStr() {
        return tagStr;
    }

    /**
     * 据字符串获得对应的电影标签
     * @param tagStr
     * @return
     */
    public static MovieTag getMovieType(String tagStr){
        if (TextUtils.isEmpty(tagStr)) {
            return null;
        }
        MovieTag[] array = MovieTag.values();
        if (array == null) {
            return null;
        }
        for (MovieTag movieTag : array) {
            String tag = movieTag.getTagStr();
            if (tag.equals(tagStr) || tagStr.contains(tag)) {
                return movieTag;
            }
        }
        return null;
    }
}