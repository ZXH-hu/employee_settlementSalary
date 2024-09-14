package com.huizhi.aianswering.controller;


import com.huizhi.aianswering.common.BaseResponse;
import com.huizhi.aianswering.common.ErrorCode;
import com.huizhi.aianswering.common.ResultUtils;
import com.huizhi.aianswering.exception.ThrowUtils;
import com.huizhi.aianswering.mapper.UserAnswerMapper;
import com.huizhi.aianswering.model.dto.statistic.AppAnswerCountDTO;
import com.huizhi.aianswering.model.dto.statistic.AppAnswerResultCountDTO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * App 统计分析接口
 *
 */
@RestController
@RequestMapping("/app/statistic")
@Slf4j
public class AppStatisticController {

    @Resource
    private UserAnswerMapper userAnswerMapper;

    /**
     * 热门应用及回答数统计（top 10）
     *
     * @return
     */
    @ApiOperation("热门应用使用量统计")
    @GetMapping("/answer_count")
    public BaseResponse<List<AppAnswerCountDTO>> getAppAnswerCount() {
        return ResultUtils.success(userAnswerMapper.selectAppAnswerCount());
    }

    /**
     * 某应用回答结果分布统计
     *
     * @param appId
     * @return
     */
    @ApiOperation("某应用回答结果分布统计")
    @GetMapping("/answer_result_count")
    public BaseResponse<List<AppAnswerResultCountDTO>> getAppAnswerResultCount(Long appId) {
        ThrowUtils.throwIf(appId == null || appId <= 0, ErrorCode.PARAMS_ERROR);
        return ResultUtils.success(userAnswerMapper.selectAppAnswerResultCount(appId));
    }
}
