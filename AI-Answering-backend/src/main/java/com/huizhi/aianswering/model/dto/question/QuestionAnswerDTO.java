package com.huizhi.aianswering.model.dto.question;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户答题结果，用于 AI 评分
 */
@Data
public class QuestionAnswerDTO implements Serializable {

    /**
     * 题目
     */
    private String title;


    /**
     * 用户答题结果
     */
    private String userAnswer;

    private static final long serialVersionUID = 1L;

}
