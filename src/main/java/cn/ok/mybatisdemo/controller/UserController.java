package cn.ok.mybatisdemo.controller;

import cn.ok.mybatisdemo.entity.AsyncTask;
import cn.ok.mybatisdemo.entity.User;
import cn.ok.mybatisdemo.mapper.mydb.MydbUserMapper;
import cn.ok.mybatisdemo.mapper.smp.SmpUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.TransactionManager;
import java.util.List;

/**
 * @author kyou on 2018/2/4 14:25
 */
@Slf4j
@RestController
public class UserController {

    private final AsyncTask asyncTask;

    private final MydbUserMapper mydbUserMapper;

    private final SmpUserMapper smpUserMapper;

    private final TransactionManager transactionManager;

    @Autowired
    public UserController(AsyncTask asyncTask, MydbUserMapper mydbUserMapper, SmpUserMapper smpUserMapper, TransactionManager transactionManager) {
        this.asyncTask = asyncTask;
        this.mydbUserMapper = mydbUserMapper;
        this.smpUserMapper = smpUserMapper;
        this.transactionManager = transactionManager;
    }

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

    @RequestMapping("insert")
    public String doInsert() throws Exception {
        log.debug("开始=============");

        try {
            transactionManager.begin();

            smpUserMapper.doInsert();
            log.debug("插入1=============");

            mydbUserMapper.doInsert();
            log.debug("插入2=============");

            // get a NullPointerException
            User user = null;
            //noinspection ConstantConditions
            log.debug(user.getUserName());
            log.debug("完成=============");

            transactionManager.commit();
        } catch (NullPointerException e) {
            log.debug("回滚=============");
            transactionManager.rollback();
        }

        log.debug("End=============");
        return "END.";
    }

    @RequestMapping("doUpdate")
    public String doUpdate() {

        return "update done.";
    }

    @GetMapping("doSth")
    public String doSth() {
        asyncTask.dealNoReturnTask("password");
        asyncTask.dealNoReturnTask("password1");

        log.debug("Main Thread end");
        return "Main Thread end";
    }
}
