package com.wangyuelin.app.crawler.movie;

import org.apache.http.util.TextUtils;

/**
 * 电影的类型
 */
public enum  MovieType {
    ACTION(1, "动作"),
    SCIENCE_FICTION(2, "科幻"),
    DRACULA(3, "恐怖"),
    COMEDY(4, "喜剧"),
    AFFECTIONAL(5, "爱情"),
    STORY(6, "剧情"),
    WAR(7, "战争"),
    CARTOON(8, "动画"),
    VARIETY(9, "综艺"),
    JIGNSONG(10, "惊悚"),
    FANZUI(11, "犯罪"),
    MAOXIAN(12, "冒险"),
    HOME(13, "首页");


    private String typeStr;
    private int index;

     MovieType(int index, String typeStr) {
        this.typeStr = typeStr;
        this.index = index;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public int getIndex() {
        return index;
    }

    /**
     * 据字符串获得对应的电影类型
     * @param typeStr
     * @return
     */
    public static MovieType getMovieType(String typeStr){
        if (TextUtils.isEmpty(typeStr)) {
            return null;
        }
        MovieType[] array = MovieType.values();
        if (array == null) {
            return null;
        }
        for (MovieType movieType : array) {
            String type = movieType.getTypeStr();
            if (type.equals(typeStr) || typeStr.contains(type)) {
                return movieType;
            }
        }
        return null;
    }


}
