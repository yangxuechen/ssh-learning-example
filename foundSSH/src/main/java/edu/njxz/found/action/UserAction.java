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
