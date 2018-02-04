package cn.ok.mybatisdemo.controller;

import cn.ok.mybatisdemo.entity.User;
import cn.ok.mybatisdemo.mapper.mydb.MydbUserMapper;
import cn.ok.mybatisdemo.mapper.smp.SmpUserMapper;
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
    private MydbUserMapper mydbUserMapper;

    @Autowired
    private SmpUserMapper smpUserMapper;

    @RequestMapping("/smpuser")
    public String getSmpUser() {
        User user = new User();
        user.setPassword("AA");
        user.setUserName("AA");

        List<User> users = smpUserMapper.doSelect();
        System.out.println(users.toString());

        return users.toString();
    }

    @RequestMapping("/mydbuser")
    public String getMydbUser() {
        List<User> users = mydbUserMapper.doSelect();
        System.out.println(users.toString());

        return users.toString();
    }
}
