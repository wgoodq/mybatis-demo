package cn.ok.mybatisdemo.controller;

import cn.ok.mybatisdemo.entity.User;
import cn.ok.mybatisdemo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author kyou on 2018/2/4 14:25
 */
@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/user")
    public String getUser() {
        User user = new User();
        user.setPassword("AA");
        user.setUserName("AA");

        List<User> users = userMapper.doSelect(user);
        System.out.println(users.toString());

        return users.toString();

    }
}
