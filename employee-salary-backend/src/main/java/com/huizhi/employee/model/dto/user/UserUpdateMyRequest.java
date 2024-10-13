package com.huizhi.employee.model.dto.user;

import java.io.Serializable;
import lombok.Data;

/**
 * 用户更新个人信息请求
 *
 * @author   小赵学Java
 *    
 */
@Data
public class UserUpdateMyRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private String userAccount;

    private String userPassword;

    private String captureCode;

    private String userKey;

    private String checkPassword;



    private static final long serialVersionUID = 1L;
}