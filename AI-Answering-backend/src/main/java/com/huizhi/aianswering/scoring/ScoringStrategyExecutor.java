package com.huizhi.aianswering.scoring;

import com.huizhi.aianswering.common.ErrorCode;
import com.huizhi.aianswering.exception.BusinessException;
import com.huizhi.aianswering.model.entity.App;
import com.huizhi.aianswering.model.entity.UserAnswer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 评分策略执行器
 */
@Service
public class ScoringStrategyExecutor {

    // 策略列表：三个评分策略对象
    @Resource
    private List<ScoringStrategy> scoringStrategyList;


    /**
     * 评分：根据设置的评分类型，如果注解配置中@ScoringStrategyConfig(appType = 1, scoringStrategy = 1)，如果scoringStrategy值为0，则调用自定义评分策略，如果
     * 值为1，则调用 AI 评分策略。其中自定义评分策略有得分类型和测评类型；AI 评分策略只有测评类型。
     * @param choiceList
     * @param app
     * @return
     * @throws Exception
     */
    public UserAnswer doScore(List<String> choiceList, App app) throws Exception {
        Integer appType = app.getAppType();
        Integer appScoringStrategy = app.getScoringStrategy();
        if (appType == null || appScoringStrategy == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "应用配置有误，未找到匹配的策略");
        }
        // 根据注解获取对应的策略对象
        for (ScoringStrategy strategy : scoringStrategyList) {
            // 通过反射获取策略注解信息（app类别和评分类别）
            if (strategy.getClass().isAnnotationPresent(ScoringStrategyConfig.class)) {
                ScoringStrategyConfig scoringStrategyConfig = strategy.getClass().getAnnotation(ScoringStrategyConfig.class);
                // 判断当前的注解参数和当前用户调用的app和评分策略是否一致，如果一致则通过strategy策略对象执行评分方法返回结果
                if (scoringStrategyConfig.appType() == appType && scoringStrategyConfig.scoringStrategy() == appScoringStrategy) {
                    return strategy.doScoring(choiceList, app);
                }
            }
        }
        throw new BusinessException(ErrorCode.SYSTEM_ERROR, "应用配置有误，未找到匹配的策略");
    }
}
