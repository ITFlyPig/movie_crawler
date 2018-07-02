package com.wangyuelin.app.util;

import com.wangyuelin.app.crawler.douban.MovieHousePage;

/**
 * 描述:
 *
 * @outhor wangyuelin
 * @create 2018-05-28 下午5:16
 */
public interface Constant {
    interface MovieHouseType {//电影院的电影类型
        int MOVING = 1;//正在上映
        int WILL_MOVING = 2;//即将上映

    }

    /**
     * 播放链接的类型
     */
    interface PlayLinkType {
        String M3U8 = "m3u8";//播放链接
        String DIRECT_PLAY = "direct_play";//在浏览器打开可以直接播放
        String ZUIDALL = "zuidall";
        String DOWNLOAD = "download";//下载链接
        String XUNLEI_DOWNLOAD = "迅雷下载";//迅雷下载

    }

    /**
     * 函数返回值的code
     */
    interface ReturnCode{
        int ERROE = -1;
    }

    /**
     * 电影的tag
     */
    interface MovieTag{
        String HOT = "热门";
        String JINGDIAN = "经典";
        String DOUBANGAOFEN = "豆瓣高分";
        String NEW = "最新";
        String LENGMENJIAPIAN = "冷门佳片";
        String HUAYU = "华语";
        String OUMEI = "欧美";
        String HANGUO = "韩国";
        String RIBEN = "日本";
        String DONGZUO = "动作";
        String XIJU = "喜剧";
        String AIQING = "爱情";
        String KEHUAN = "科幻";
        String XUANYU = "悬疑";
        String KONGBU = "恐怖";
        String ZHIYU = "治愈";
        String SHOWING = "正在上映";
        String WILL_SHOW = "即将上映";
    }


    /**
     * 文件的名称
     */
     interface FileNames{
        String MONITOR_FILE = "ippool";//ip连接或者本地json文件的文件夹
        String IP_JSON_FILE = "local_ip_json.txt";//存ip的json文件
        String IP_URL_FILE = "get_ip_url.txt";//存放获取ip的url
        String GET_IP_LOG = "get_ip_log.txt";//保存从url获取ip的相关log
        String DOWNLOAD_PAGE_LOG = "download_page_log.txt";//下载页面的log
        String BAIDU_CRAW_LOG = "baidu_craw_log";//爬取百度电影的log
    }

    interface Host{
         String ZHIMA = "zhimacangku";
    }

    interface Table {
         String BAIDU_TAG_MOVIE_TABLE = "baidu_movie_tag";

    }

}