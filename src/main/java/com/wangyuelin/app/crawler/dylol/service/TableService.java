package com.wangyuelin.app.crawler.dylol.service;

import com.wangyuelin.app.crawler.dylol.dao.TableDao;
import com.wangyuelin.app.crawler.dylol.service.itf.ITable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TableService implements ITable {
    @Autowired
    private TableDao tableDao;

    @Override
    public void createTables(String... names) {
        if (names == null){
            return;
        }
        for (String name: names) {

        }

    }


}
