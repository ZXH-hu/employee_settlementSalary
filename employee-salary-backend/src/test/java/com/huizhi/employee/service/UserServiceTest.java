package com.huizhi.employee.service;

import javax.annotation.Resource;

import org.springframework.boot.test.context.SpringBootTest;

/**
 * 用户服务测试
 *
 * @author   小赵学Java
 *    
 */
@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;

    /*@Test
    void userRegister() {
        String userAccount = "huizhi";
        String userPassword = "";
        String checkPassword = "123456";
        try {
            long result = userService.userRegister(userAccount, userPassword, checkPassword);
            Assertions.assertEquals(-1, result);
            userAccount = "yu";
            result = userService.userRegister(userAccount, userPassword, checkPassword);
            Assertions.assertEquals(-1, result);
        } catch (Exception e) {

        }
    }*/
}
