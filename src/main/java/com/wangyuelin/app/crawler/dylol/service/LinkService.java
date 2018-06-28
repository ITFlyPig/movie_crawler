package com.wangyuelin.app.crawler.dylol.service;

import com.wangyuelin.app.crawler.dylol.bean.LinkBean;
import com.wangyuelin.app.crawler.dy2018.TableUtil;
import com.wangyuelin.app.crawler.dylol.dao.LinkDao;
import com.wangyuelin.app.crawler.dylol.service.itf.ILink;
import com.wangyuelin.app.util.ArrayUtils;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkService implements ILink {
    @Autowired
    private LinkDao linkDao;

    @Override
    public void save(List<LinkBean> links, String tableName, boolean isNeedDelete) {
        if (ArrayUtils.isEmpty(links) || TextUtils.isEmpty(tableName)){
            return;
        }
        if (isNeedDelete) {
            linkDao.deleteAll(tableName);
        }

        for (LinkBean link : links) {
            linkDao.insertOne(link, tableName);
        }
    }

    @Override
    public List<LinkBean> getLinks(int pageNum, int curPage, int type) {
        String tableName = TableUtil.getTableName(type);
        if (TextUtils.isEmpty(tableName)) {
            return null;
        }
        int offset = pageNum * curPage - 1;
        return linkDao.getLinks(tableName, offset, pageNum);
    }

    @Override
    public int getTotalNum(int type) {
        String tableName = TableUtil.getTableName(type);
        if (TextUtils.isEmpty(tableName)) {
            return 0;
        }
        return linkDao.getTotalNum(tableName);

    }


}
