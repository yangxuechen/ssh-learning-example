package com.test;

import com.dao.UserDao;
import com.model.User;

public class Test {
    public static void main(String[] args) {

        User user=new User();
        user.setUserName("LeBronJames");
        user.setUserPwd("123456");
        user.setUserEmail("1324688478@qq.com");

        UserDao userDao=new UserDao();
        userDao.addUser(user);
    }
}
