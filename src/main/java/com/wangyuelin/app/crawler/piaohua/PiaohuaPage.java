package com.wangyuelin.app.crawler.piaohua;

import com.wangyuelin.app.crawler.base.IPage;
import us.codecraft.webmagic.Page;

/**
 * 描述: 解析飘花电影
 *
 * @outhor wangyuelin
 * @create 2018-05-30 下午6:17
 */
public class PiaohuaPage implements IPage {
    public static final String HOST = "https://www.piaohua.com";

    @Override
    public void parse(Page page) {

    }

    @Override
    public boolean isMine(String url) {
        return false;
    }
}