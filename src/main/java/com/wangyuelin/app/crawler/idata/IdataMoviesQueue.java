package com.wangyuelin.app.crawler.idata;

import com.wangyuelin.app.proxypool.ProxyPool;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * 描述:存放用于idata查询的数据
 * http://www.idataapi.cn/   api接口提供网站
 *
 *
 * @outhor wangyuelin
 * @create 2018-06-26 下午7:15
 */
public class IdataMoviesQueue {
    public static LinkedBlockingDeque<String> movieQueue = new LinkedBlockingDeque<String>();//存放等待查询的电影名字的队列

}