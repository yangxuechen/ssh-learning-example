### SSH框架整合实例（spring+struts+hibernate)  
1.新建Maven项目，在pom.xml文件添加ssh框架所需依赖jar包的代码  
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">

  <modelVersion>4.0.0</modelVersion>
  <packaging>war</packaging>

  <name>foundSSH</name>
  <groupId>foundSSH</groupId>
  <artifactId>foundSSH</artifactId>
  <version>1.0-SNAPSHOT</version>
  <properties>
    <!-- 统一源码的编码方式 -->
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <!-- 统一各个框架版本 -->
    <struts.version>2.5.10</struts.version>
    <spring.version>4.3.8.RELEASE</spring.version>
    <hibernate.version>5.1.7.Final</hibernate.version>
  </properties>


  <build>
    <plugins>
      <plugin>
        <groupId>org.mortbay.jetty</groupId>
        <artifactId>maven-jetty-plugin</artifactId>
        <version>6.1.7</version>
        <configuration>
          <connectors>
            <connector implementation="org.mortbay.jetty.nio.SelectChannelConnector">
              <port>8888</port>
              <maxIdleTime>30000</maxIdleTime>
            </connector>
          </connectors>
          <webAppSourceDirectory>${project.build.directory}/${pom.artifactId}-${pom.version}</webAppSourceDirectory>
          <contextPath>/</contextPath>
        </configuration>
      </plugin>
    </plugins>
    <resources>
      <resource>
        <directory>${basedir}/src/main/java</directory>
        <includes>
          <include>**/*.xml</include>
        </includes>
      </resource>
    </resources>
  </build>

  <dependencies>
    <!--dependency>
      <groupId>foundSSH</groupId>
      <artifactId>[the artifact id of the block to be mounted]</artifactId>
      <version>1.0-SNAPSHOT</version>
    </dependency-->
    <!-- Junit依赖 -->
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.12</version>
      <scope>test</scope>
    </dependency>
    <!-- Spring 核心依赖 -->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-core</artifactId>
      <version>${spring.version}</version>
    </dependency>
    <!-- Spring web依赖 -->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-web</artifactId>
      <version>${spring.version}</version>
    </dependency>
    <!-- Spring整合ORM框架依赖 -->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-orm</artifactId>
      <version>${spring.version}</version>
    </dependency>
    <!-- Struts2 核心依赖 -->
    <dependency>
      <groupId>org.apache.struts</groupId>
      <artifactId>struts2-core</artifactId>
      <version>${struts.version}</version>
    </dependency>
    <!-- Struts2和Spring整合依赖 -->
    <dependency>
      <groupId>org.apache.struts</groupId>
      <artifactId>struts2-spring-plugin</artifactId>
      <version>${struts.version}</version>
    </dependency>
    <!-- Hibernate 核心依赖 -->
    <dependency>
      <groupId>org.hibernate</groupId>
      <artifactId>hibernate-core</artifactId>
      <version>${hibernate.version}</version>
    </dependency>
    <!-- MySQL 依赖 -->
    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>5.1.42</version>
    </dependency>
    <!-- C3P0 依赖 -->
    <dependency>
      <groupId>com.mchange</groupId>
      <artifactId>c3p0</artifactId>
      <version>0.9.5</version>
    </dependency>
    <!-- AspectJ依赖 -->
    <dependency>
      <groupId>org.aspectj</groupId>
      <artifactId>aspectjweaver</artifactId>
      <version>1.8.10</version>
    </dependency>
    <!-- SLF4J依赖 -->
    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-log4j12</artifactId>
      <version>1.7.25</version>
    </dependency>
    <!-- 导入java ee jar 包 -->
    <dependency>
      <groupId>javax</groupId>
      <artifactId>javaee-api</artifactId>
      <version>7.0</version>
    </dependency>
    <!-- 日志-->
    <dependency>
      <groupId>org.apache.logging.log4j</groupId>
      <artifactId>log4j-core</artifactId>
      <version>2.9.1</version>
    </dependency>
    <dependency>
      <groupId>org.apache.logging.log4j</groupId>
      <artifactId>log4j-api</artifactId>
      <version>2.9.1</version>
    </dependency>

  </dependencies>

</project>

```  
2.将项目的包建好，如下图  
![ssh_1](https://github.com/yangxuechen/ssh-learning-example/blob/master/resource/img_ssh_1.jpg)    
3.在resources文件目录下创建spring配置文件applicationContext.xml  
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans-4.3.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/tx
        http://www.springframework.org/schema/tx/spring-tx.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd">

    <!-- 开启包扫描，并注册注解 -->
    <context:component-scan base-package="edu.njxz.found.*"/>
    <!-- 引入属性文件 -->
    <context:property-placeholder location="classpath:jdbc.properties"/>

    <!-- 配置C3P0连接池 -->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <!-- 数据库连接相关信息 -->
        <property name="jdbcUrl" value="${jdbc.url}"/>
        <property name="driverClass" value="${jdbc.driverClass}"/>
        <property name="user" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
    </bean>

    <!-- 配置Hibernate的SessionFactory -->
    <bean id="sessionFactory" class="org.springframework.orm.hibernate5.LocalSessionFactoryBean">
        <!-- 注入连接池 -->
        <property name="dataSource" ref="dataSource"/>
        <!-- 配置Hibernate属性 -->
        <property name="hibernateProperties">
            <props>
                <prop key="hibernate.show_sql">true</prop><!-- 是否展示SQL -->
                <prop key="hibernate.hbm2ddl.auto">update</prop><!-- 是否自动创建表结构 -->
                <prop key="hibernate.dialect">org.hibernate.dialect.MySQL5InnoDBDialect</prop>
            </props>
        </property>

        <!--配置hibernate映射文件的位置以及名称，可以使用通配符-->
        <property name="mappingLocations"
                  value="classpath:hbm/*.hbm.xml">
        </property>
    </bean>

    <bean id="userDao" class="edu.njxz.found.dao.impl.UserDaoImpl">
        <property name="sessionFactory" ref="sessionFactory"></property>
    </bean>

    <!-- 配置事务管理器 -->
    <bean id="transactionManager" class="org.springframework.orm.hibernate5.HibernateTransactionManager">
        <!-- 注入SessionFactory -->
        <property name="sessionFactory" ref="sessionFactory"/>
    </bean>

    <!-- 配置事务增强 -->
    <tx:advice id="txAdvice" transaction-manager="transactionManager">
        <tx:attributes>
            <!-- 配置需要进行事务管理的方法，和事务传播行为 -->
            <tx:method name="save*" propagation="REQUIRED"/>
            <tx:method name="update*" propagation="REQUIRED"/>
            <tx:method name="delete*" propagation="REQUIRED"/>
        </tx:attributes>
    </tx:advice>

    <!-- 配置切面 -->
    <aop:config>
        <!-- 配置切入点 com.yjq.ssh.*.service.*+.*(..)
            * org.ssh.service.*+.*(..)
                *：表示方法的作用域，*表示所有
                org.ssh.service.*：表示org.ssh.service下的任何包
                org.ssh.service.*+：表示org.ssh.service下的任何包及其子包
                *(..)：*表示任何方法，(..)表示方法的任何参数
         -->
        <aop:pointcut id="pointcut" expression="execution(* edu.njxz.found.service.*+.*(..))"/>
        <!-- 适配切入点和事务增强 -->
        <aop:advisor advice-ref="txAdvice" pointcut-ref="pointcut"/>
    </aop:config>
</beans>
```  
4.在resources目录下创建struts的配置文件struts.xml  
```xml
<?xml version="1.0" encoding="UTF-8"?>

<!DOCTYPE struts PUBLIC
        "-//Apache Software Foundation//DTD Struts Configuration 2.5//EN"
        "http://struts.apache.org/dtds/struts-2.5.dtd">

<struts>

    <!-- 默认访问页面 -->
    <package name="default" extends="struts-default" namespace="/">
        <default-action-ref name="default" />
        <action name="default">
            <result>index.jsp</result>
        </action>
    </package>
    <!-- Struts2在2.5版本后添加strict-method-invocation(严格方法访问)，默认为true，不能使用动态方法调用功能，故需设为false -->
    <package name="product" extends="struts-default" namespace="/"
             strict-method-invocation="false">
        <!-- userAction 交由spring管理 -->
        <action name="usertest" class="userAction" >
            <result name="success">success.jsp</result>
        </action>
    </package>
</struts>
```  
5.在resources目录下创建jdbc.properties  
```xml
jdbc.url=jdbc:mysql://localhost:3306/lostandfound?useSSL=true&characterEncoding=UTF-8
jdbc.driverClass=com.mysql.jdbc.Driver
jdbc.username=root
jdbc.password=Root
```  
6.在resources目录下创建日志配置文件log4j2.xml  
```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration status="error">
    <appenders>
        <Console name="Console" target="SYSTEM_OUT">
            <ThresholdFilter level="trace" onMatch="ACCEPT" onMismatch="DENY"/>
            <PatternLayout pattern="%d{HH:mm:ss.SSS} %-5level %class{36} %L %M - %msg%xEx%n"/>
        </Console>
        <File name="log" fileName="target/test.log" append="false">
            <PatternLayout pattern="%d{HH:mm:ss.SSS} %-5level %class{36} %L %M - %msg%xEx%n"/>
        </File>
        <RollingFile name="RollingFile" fileName="logs/app.log"
                     filePattern="logs/$${date:yyyy-MM}/app-%d{MM-dd-yyyy}-%i.log.gz">
            <PatternLayout pattern="%d{yyyy.MM.dd 'at' HH:mm:ss z} %-5level %class{36} %L %M - %msg%xEx%n"/>
            <SizeBasedTriggeringPolicy size="500 MB" />
        </RollingFile>
    </appenders>
    <loggers>
        <root level="trace">
            <appender-ref ref="RollingFile"/>
            <appender-ref ref="Console"/>
        </root>
    </loggers>
</configuration>
``` 
7.配置web.xml文件  
```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app version="2.4"
         xmlns="http://java.sun.com/xml/ns/j2ee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd">

  <!-- Servlet Filters ================================================ -->

  <!-- 配置Struts2过滤器 -->
  <filter>
    <filter-name>struts2</filter-name>
    <filter-class>org.apache.struts2.dispatcher.filter.StrutsPrepareAndExecuteFilter</filter-class>
  </filter>
  <filter-mapping>
    <filter-name>struts2</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>
  <!-- 配置Spring的监听器 -->
  <listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>
  <!-- 指定Spring配置文件所在路径 -->
  <context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>classpath:applicationContext.xml</param-value>

  </context-param>
</web-app>
        
```  
8.在entity包下创建一个实体类User  
```java
package edu.njxz.found.entity;

public class User {

    private int userId;  //用户id
    private String userName;//用户名
    private String userPassword;//密码
    private String userEmail;  //邮箱
    private String userPhoto;   //头像地址
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserPassword() {
        return userPassword;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public String getUserPhoto() {
        return userPhoto;
    }
    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

}

```
9.创建Hibernate的映射文件User.hbm.xml(这里要注意，因为本项目使用Maven构建，所有User.hbm.xml文件如果放在跟User.java文件同一个包entity下，最后运行会报找不到User.hbm.xml文件的错误，我们将User.hbm.xml放在资源文件resources目录下)  
```xml
<?xml version="1.0"?>
<!DOCTYPE hibernate-mapping PUBLIC
        "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
        "http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd">

<hibernate-mapping>
    <class name="edu.njxz.found.entity.User" table="lf_user" lazy="false">
        <id name="userId" column="user_id">
            <generator class="increment"/>
        </id>

        <property name="userName" type="java.lang.String">
            <column name="user_name" length="30"/>
        </property>

        <property name="userPassword" type="java.lang.String">
            <column name="user_password"/>
        </property>
        <property name="userEmail" type="java.lang.String">
            <column name="user_email"/>
        </property>
        <property name="userPhoto" type="java.lang.String">
            <column name="user_photo"/>
        </property>
    </class>

</hibernate-mapping>
```  
10.在dao包下创建UserDao  
```java
package edu.njxz.found.dao;

import edu.njxz.found.entity.User;

public interface UserDao {
    void saveUser(User user);
}
```  
11.在dao包的子包impl包下创建UserDaoImpl  
```java
package edu.njxz.found.dao.impl;

import edu.njxz.found.dao.UserDao;
import edu.njxz.found.entity.User;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

public class UserDaoImpl extends HibernateDaoSupport implements UserDao {
    public void saveUser(User user) {
        this.getHibernateTemplate().save(user);
    }
}
```  
12.在service包下创建UserService接口  
```java
package edu.njxz.found.service;

import edu.njxz.found.entity.User;

public interface UserService {
    void saveUser(User user);
}

```  
13.在service的子包impl下创建UserServiceImpl  
```java
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

```  
14.在action包下创建UserAction  
```java
package edu.njxz.found.action;

import com.opensymphony.xwork2.ActionSupport;
import edu.njxz.found.entity.User;
import edu.njxz.found.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;



@Controller
@Scope("prototype")
public class UserAction extends ActionSupport {

    private Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private UserService userService;
    private String userName;
    private String password;

    //重写execute方法
    @Override
    public String execute() throws Exception {

        User user=new User();
        user.setUserName("哈哈哈");
        user.setUserPassword("123456");
        userService.saveUser(user);
        logger.info("保存成功");
        return SUCCESS;
    }
    public String getUserName() {
        return userName;
    }
    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
```
15.在webapp目录下创建index.jsp 和success.jsp  
```jsp
<%--
  Created by IntelliJ IDEA.
  User: yangxuechen
  Date: 2018/12/11
  Time: 10:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>首页</title>
</head>
<body>
   <s:form action="usertest" method="post">
     <input type="text" name="userName">
       <input type="password" name="password">
       <input type="submit" value="提交">
   </s:form>
</body>
</html>
```  
16.success.jsp
```jsp
<%--
  Created by IntelliJ IDEA.
  User: yangxuechen
  Date: 2018/12/11
  Time: 10:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>success</title>
</head>
<body>
success
</body>
</html>

```
17.测试运行，往数据库中存储了一条记录  
18.获取项目源码[https://github.com/yangxuechen/ssh-learning-example/tree/master/foundSSH](https://github.com/yangxuechen/ssh-learning-example/tree/master/foundSSH)  
