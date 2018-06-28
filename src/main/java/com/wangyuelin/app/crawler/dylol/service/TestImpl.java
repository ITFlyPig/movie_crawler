package com.wangyuelin.app.crawler.dylol.service;

import com.wangyuelin.app.crawler.dylol.bean.User;
import com.wangyuelin.app.crawler.dylol.dao.UserDao;
import com.wangyuelin.app.crawler.dylol.service.itf.ITest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestImpl implements ITest{

    @Autowired
    private UserDao userDao;

    @Override
    public User getUser() {
        User user = userDao.getUserById(2);
        return user;
    }

    @Override
    public List<User> getAll() {
        return userDao.selectAll();
    }
}
