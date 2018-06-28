package com.wangyuelin.app.crawler.base;

import us.codecraft.webmagic.Page;

/**
 * 描述:
 *
 * @outhor wangyuelin
 * @create 2018-05-30 下午4:52
 */
public interface IPage {
    void parse(Page page);

    boolean isMine(String url);

}