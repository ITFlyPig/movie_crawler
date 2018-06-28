package com.wangyuelin.app.crawler.dy2018;

import org.apache.http.util.TextUtils;

import java.util.regex.Pattern;

public class DYConstant {
    public static final String HOST = "https://www.dy2018.com";

    /**
     * 页面的种类
     */
    public enum PageDYType {
        HOME(1, "首页", "^(https|http)://www.dy2018.com[/]*$"),

        //        SORT_LIST(2, "分类页", "^(https|http)://www.dy2018.com/[1-9]\\d*([/]*|/index_[0-9]\\d*.html)$"),
        SORT_LIST(2, "分类页", "^(https|http)://www.dy2018.com/([0-9]*|[a-zA-Z]*)([/]{0,1}|/index_[0-9]\\d*.html)$"),
        DEATIL(3, "详情页", "^(https|http)://www.dy2018.com/[A-Za-z]+/[1-9]\\d*.html$");

        PageDYType(int index, String name, String regex) {
            this.index = index;
            this.name = name;
            this.regex = regex;
        }

        private int index;
        private String name;
        private String regex;//表达式

        public int getIndex() {
            return index;
        }

        public String getName() {
            return name;
        }

        public String getRegex() {
            return regex;
        }
    }

    /**
     * 获取页面的类型
     *
     * @param url
     * @return
     */
    public static PageDYType getPageType(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        PageDYType[] pageDYTypes = PageDYType.values();
        if (pageDYTypes == null) {
            return null;
        }
        int size = pageDYTypes.length;
        for (int i = 0; i < size; i++) {
            PageDYType pageDYType = pageDYTypes[i];
            if (Pattern.matches(pageDYType.regex, url)) {
                return pageDYType;
            }
        }
        return null;
    }

}
