package com.wangyuelin.app.crawler.dylol.service.itf;

import com.wangyuelin.app.crawler.dylol.bean.LinkBean;

import java.util.List;

public interface ILink {
    void save(List<LinkBean> links, String tableName, boolean isNeedDelete);
    List<LinkBean> getLinks(int pageNum, int curPage, int type);
    int getTotalNum(int type);
}
