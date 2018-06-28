package com.wangyuelin.app.crawler.task;

/**
 * 描述: 下一个url
 *
 * @outhor wangyuelin
 * @create 2018-06-15 下午8:19
 */
public interface TaskCallBack {
    String next(String[] tags, int pageIndex, String type, String location);
    boolean isNeedProxy();
    boolean result(String url, String[] tags, String response);//返回值表示是否继续

}