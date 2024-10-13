package com.huizhi.employee.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 审核请求
 *
 * @author   小赵学Java
 *    
 */
@Data
public class StatusRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 审核状态：0-已启用，1-已停用
     */
    private Integer runStatus;


    private static final long serialVersionUID = 1L;
}