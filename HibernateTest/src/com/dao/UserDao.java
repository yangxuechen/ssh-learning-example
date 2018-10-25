package com.dao;

import com.model.User;
import com.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDao {
    public User getUserByID(int id)
    {
        Session session = HibernateUtil.getSession();
        User user = (User)session.get(User.class, id);
        session.close();
        return user;
    }

    public void addUser(User user) {
        Session session = HibernateUtil.getSession();
        Transaction ts = session.beginTransaction();
        session.save(user);
        ts.commit();
        session.close();
    }

    public void updateUser(User user) {
        Session session = HibernateUtil.getSession();
        Transaction ts = session.beginTransaction();
        session.saveOrUpdate(user);
        ts.commit();
        session.close();
    }

    public void deleteUser(User user) {
        Session session = HibernateUtil.getSession();
        Transaction ts = session.beginTransaction();
        session.delete(user);
        ts.commit();
        session.close();
    }
}
