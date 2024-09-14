package com.huizhi.aianswering.model.dto.question;

import com.huizhi.aianswering.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询题目请求
 *
 * @author   小赵学Java
 *  
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionQueryRequest extends PageRequest implements Serializable {


    /**
     * id
     */
    private Long id;

    /**
     * 题目内容（json格式）
     */
    private String questionContent;

    /**
     * 应用 id
     */
    private Long appId;

    /**
     * 创建用户 id
     */
    private Long userId;


    /**
     * id
     */
    private Long notId;



    private static final long serialVersionUID = 1L;
}