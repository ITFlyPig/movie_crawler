package com.wangyuelin.app.crawler.downloader;

import org.apache.http.util.TextUtils;

/**
 * 描述:
 *
 * @outhor wangyuelin
 * @create 2018-06-14 下午6:21
 */
public class UrlUtils {
    private static String[] PROXYPOOL_HOSTS = new String[]{"douban"};

    /**
     * 检查是否需要走ip池
     * @param url
     * @return
     */
    public static boolean isNeedProxyPool(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        int size = PROXYPOOL_HOSTS.length;
        for (int i = 0; i < size; i++) {
            if (url.contains(PROXYPOOL_HOSTS[i])) {
                return true;
            }
        }

        return false;

    }

}