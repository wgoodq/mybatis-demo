package cn.ok.mybatisdemo.entity;

import lombok.Data;

/**
 * @author kyou on 2018/2/4 12:40
 */
@Data
public class User {
    private int userId = 0;
    private String userName;
    private String password;
}
