package com.wangyuelin.app.proxypool;

/**
 * 描述:
 *
 * @outhor wangyuelin
 * @create 2018-06-13 下午2:51
 */
public interface IDownloader<T> {

    T download(String url);


    T downloadWithIP(String url, String ip, int port);
}