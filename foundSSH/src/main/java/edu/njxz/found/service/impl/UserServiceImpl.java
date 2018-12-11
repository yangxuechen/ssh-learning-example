package edu.njxz.found.service.impl;

import edu.njxz.found.dao.UserDao;
import edu.njxz.found.entity.User;
import edu.njxz.found.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    public void saveUser(User user) {
        userDao.saveUser(user);
    }
}
