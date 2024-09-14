package com.huizhi.aianswering.model.dto.statistic;

import lombok.Data;

/**
 * 统计热门应用请求字段
 */
@Data
public class AppAnswerCountDTO {

    //应用id
    private Long appId;

    //回答次数
    private Long answerCount;
}
