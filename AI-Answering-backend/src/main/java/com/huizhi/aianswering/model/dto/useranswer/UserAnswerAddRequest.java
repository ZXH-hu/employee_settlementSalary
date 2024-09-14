package com.huizhi.aianswering.model.dto.useranswer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 创建用户答案请求：存储用户答案
 *
 * @author   小赵学Java
 *  
 */
@Data
public class UserAnswerAddRequest implements Serializable {


    /**
     * 应用 id
     */
    private Long appId;


    /**
     * 用户答案（JSON 数组）
     */
    private List<String> choices;

    /**
     * id：幂等性问题，通过雪花算法生成唯一id
     */
    private Long id;


    private static final long serialVersionUID = 1L;
}