###Hibernate5简单应用实例  

1.下载Hibernate所需jar包  
2.新建java项目，并导入jar包  
3.新建User.java文件，User类的属性于数据库表的字段一一对应  
```sql
-- ----------------------------
-- Table structure for `p_user`
-- ----------------------------
DROP TABLE IF EXISTS `p_user`;
CREATE TABLE `p_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) DEFAULT NULL,
  `user_pwd` varchar(20) DEFAULT NULL,
  `user_email` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of p_user
-- ----------------------------
INSERT INTO `p_user` VALUES ('1', 'yxc', '123456', 'for@163.com');
INSERT INTO `p_user` VALUES ('2', '张飞', '123456', '1324688478@qq.com');
INSERT INTO `p_user` VALUES ('5', '李四', '123456', null);
INSERT INTO `p_user` VALUES ('6', 'LeBronJames', '123456', '1324688478@qq.com');
```  
```java
package com.model;

public class User {
    private int userId;  //用户编号
    private String userName; //用户名
    private String userPwd;  //密码
    private String userEmail;//邮箱

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}

```  
4.新建User.hbm.xml文件,建立User类和p_user表的映射关系    
```xml
<?xml version="1.0"?>
<!--
  ~ Hibernate, Relational Persistence for Idiomatic Java
  ~
  ~ License: GNU Lesser General Public License (LGPL), version 2.1 or later.
  ~ See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
  -->
<!DOCTYPE hibernate-mapping PUBLIC
        "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
        "http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd">

<hibernate-mapping>
    <class name="com.model.User" table="p_user" lazy="false">
        <id name="userId" column="user_id">
            <generator class="increment"/>
        </id>

        <property name="userName" type="java.lang.String">
            <column name="user_name" length="30"/>
        </property>
        
        <property name="userPwd" type="java.lang.String">
            <column name="user_pwd"/>
        </property>
        <property name="userEmail" type="java.lang.String">
            <column name="user_email"/>
        </property>
    </class>

</hibernate-mapping>

```  
5.在src目录下新建hibernate.cfg.xml文件  
```xml

  <!DOCTYPE hibernate-configuration PUBLIC
          "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
          "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">

<hibernate-configuration>
    <session-factory>
    <property name="hibernate.dialect">org.hibernate.dialect.MySQLDialect</property>
    <property name="hibernate.connection.driver_class">com.mysql.jdbc.Driver</property>
    <property name="hibernate.connection.username">root</property>
    <property name="hibernate.connection.password">Root</property>
    <property name="hibernate.connection.url">jdbc:mysql://localhost/pblog</property>
    <property name="hibernate.show_sql">true</property>
    <mapping resource="com/model/User.hbm.xml"/>
    <mapping class="com.model.User"/>
    </session-factory>
</hibernate-configuration>

```  
6.创建HibernateUtil工具类，用来获取hibernate的session对象  
```java
package com.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtil {
    private static SessionFactory factory = null;

    static {
        Configuration configuration = new Configuration().configure();
        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .buildServiceRegistry();
        factory = configuration.buildSessionFactory(serviceRegistry);
    }

    public static Session getSession() {
        return factory.openSession();
    }
}

```  
7.创建UserDao.java文件，对User进行增删改查操作  
```java
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

```  
8.测试运行  
```java
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

```  
查看控制台打印信息  
```sql
Hibernate: select max(user_id) from p_user
Hibernate: insert into p_user (user_name, user_pwd, user_email, user_id) values (?, ?, ?, ?)
```  
实现了往数据库中插入一条记录的操作  
获取源码地址[https://github.com/yangxuechen/ssh-learning-example/tree/master/HibernateTest](https://github.com/yangxuechen/ssh-learning-example/tree/master/HibernateTest)
