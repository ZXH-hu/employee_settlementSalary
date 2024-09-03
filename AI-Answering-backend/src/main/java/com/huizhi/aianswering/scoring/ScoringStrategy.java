package com.huizhi.aianswering.scoring;

import com.huizhi.aianswering.model.entity.App;
import com.huizhi.aianswering.model.entity.UserAnswer;

import java.util.List;

/**
 * 评分策略接口
 */
public interface ScoringStrategy {
    UserAnswer doScoring(List<String> choice, App app) throws Exception;
}
