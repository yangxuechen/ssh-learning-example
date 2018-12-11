package edu.njxz.found.dao.impl;

import edu.njxz.found.dao.UserDao;
import edu.njxz.found.entity.User;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

public class UserDaoImpl extends HibernateDaoSupport implements UserDao {
    public void saveUser(User user) {
        this.getHibernateTemplate().save(user);
    }
}
