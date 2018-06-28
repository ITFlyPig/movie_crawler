package com.wangyuelin.app.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述:
 *
 * @outhor wangyuelin
 * @create 2018-06-26 下午4:21
 */
public class UrlUtil {
    /**
     * 获取url的参数
     *
     * @param url
     * @return
     */
    public static Map<String, String> getQueryParams(String url) {
        try {
            Map<String, String> params = new HashMap<String, String>();
            String[] urlParts = url.split("\\?");
            if (urlParts.length > 1) {
                String query = urlParts[1];
                for (String param : query.split("&")) {
                    String[] pair = param.split("=");
                    String key = URLDecoder.decode(pair[0], "UTF-8");
                    String value = "";
                    if (pair.length > 1) {
                        value = URLDecoder.decode(pair[1], "UTF-8");
                    }

                    params.put(key, value);
                }
            }

            return params;
        } catch (UnsupportedEncodingException ex) {
            throw new AssertionError(ex);
        }
    }

}