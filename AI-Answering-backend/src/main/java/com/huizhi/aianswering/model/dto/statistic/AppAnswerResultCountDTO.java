package com.huizhi.aianswering.model.dto.statistic;

import lombok.Data;

/**
 * 统计热门应用请求字段
 */
@Data
public class AppAnswerResultCountDTO {

    // 结果名称
    private String resultName;

    //回答次数
    private String answerResultCount;
}
