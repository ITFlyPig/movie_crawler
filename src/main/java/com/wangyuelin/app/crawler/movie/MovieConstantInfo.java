package com.wangyuelin.app.crawler.movie;

import com.wangyuelin.app.crawler.dylol.bean.Movie;

/**
 * 描述:电影相关的常量
 *
 * @outhor wangyuelin
 * @create 2018-06-26 下午3:33
 */
public class MovieConstantInfo {
//    public static String[] LOCATION = new String[]{"", "美国", "大陆", "香港", "台湾", "韩国", "日本", "泰国", "英国", "法国", "其他"};//""表示全部
//    public static String[] BD_YEAR = new String[]{"", "2018", "2017", "2016", "2010-2015", "00年代", "90年代", "更早"};//百度电影的时间分类方式   ""表示全部
//    public static int[] BD_SORT = new int[]{16, 17, 18};//分别是最热电影、最新电影、用户好评
    public static String BD_URL = "https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?resource_id=28286&from_mid=1&&format=json&ie=utf-8&oe=utf-8&query=%E7%94%B5%E5%BD%B1&sort_key=%d&sort_type=1&stat0=%s&stat1=%s&stat2=%s&stat3=%s";

    public static String[] LOCATION = new String[]{"", "美国" };//""表示全部
    public static String[] BD_YEAR = new String[]{"", "2018"};//百度电影的时间分类方式   ""表示全部
    public static int[] BD_SORT = new int[]{16, 17, 18};//分别是最热电影、最新电影、用户好评

    /**
     * 获得百度的电影的tag
     * @param tag
     * @return
     */
    public static MovieTag getBaiduMovieTag(int tag) {
        if (tag == 16) {
            return MovieTag.ZUIRE;
        } else if (tag == 17) {
            return MovieTag.ZUIXIN;
        } else if (tag == 18) {
            return MovieTag.HAOAPING;
        }
        return null;
    }
    /**
     * 据不同的条件获取百度的查询url
     *
     * @param sortKey
     * @param type
     * @param location
     * @param year
     * @param stat3
     * @return
     */
    public static String getBDUrl(int sortKey, String type, String location, String year, String stat3) {
        if (type == null) {
            type = "";
        }
        if (location == null) {
            location = "";
        }
        if (year == null) {
            year = "";
        }
        if (stat3 == null) {
            stat3 = "";
        }
        String url = "https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?resource_id=28286&from_mid=1&&format=json&ie=utf-8&oe=utf-8&query=电影&sort_key=" + sortKey + "&sort_type=1&stat0=" + type + "&stat1=" + location + "&stat2=" + year + "&stat3=" + stat3;

        return url + "&pn=%s&rn=8";
    }


}