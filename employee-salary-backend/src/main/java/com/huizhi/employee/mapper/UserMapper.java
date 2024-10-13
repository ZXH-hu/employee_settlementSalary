package com.huizhi.employee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huizhi.employee.model.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * 用户数据库操作
 *
 * @author   小赵学Java
 *    
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 更改密码
     * @param userAccount
     * @param encryptPassword
     * @return
     */
    int updatePasswordByAccount(@Param("userAccount") String userAccount,
                                @Param("encryptPassword") String encryptPassword);

}




