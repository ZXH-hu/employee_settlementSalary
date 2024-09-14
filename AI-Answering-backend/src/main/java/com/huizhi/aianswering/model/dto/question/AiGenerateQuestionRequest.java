package com.huizhi.aianswering.model.dto.question;

import lombok.Data;

import java.io.Serializable;

/**
 * AI 生成问题请求generate question request
 */
@Data
public class AiGenerateQuestionRequest implements Serializable {

    /**
     * 应用ID
     */
    private Long appId;

    /**
     * 题目数
     */
    int questionNumber = 10;

    /**
     * 选项数
     */
    int optionNumber = 2;

    private static final long serialVersionUID = 1L;
}
