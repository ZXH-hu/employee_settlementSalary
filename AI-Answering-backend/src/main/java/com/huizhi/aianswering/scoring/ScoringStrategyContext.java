package com.huizhi.aianswering.scoring;

import com.huizhi.aianswering.common.ErrorCode;
import com.huizhi.aianswering.exception.BusinessException;
import com.huizhi.aianswering.model.entity.App;
import com.huizhi.aianswering.model.entity.UserAnswer;
import com.huizhi.aianswering.model.enums.AppScoringStrategyEnum;
import com.huizhi.aianswering.model.enums.AppTypeEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Deprecated // 这个注解表示下面这种写法不是很推荐使用，如果选择类别多了就不好扩展。
public class ScoringStrategyContext {

    @Resource
    private CustomScoreScoringStrategy customScoreScoringStrategy;

    @Resource
    private CustomTestScoringStrategy customTestScoringStrategy;

    /**
     * 评分
     *
     * @param choiceList
     * @param app
     * @return
     * @throws Exception
     */
    public UserAnswer doScore(List<String> choiceList, App app) throws Exception {
        // 应用枚举类和评分策略枚举类
        AppTypeEnum appTypeEnum = AppTypeEnum.getEnumByValue(app.getAppType());
        AppScoringStrategyEnum appScoringStrategyEnum = AppScoringStrategyEnum.getEnumByValue(app.getScoringStrategy());
        if (appTypeEnum == null || appScoringStrategyEnum == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "应用配置有误，未找到匹配的策略");
        }
        // 根据不同的应用类别和评分策略，选择对应的策略执行
        switch (appTypeEnum) {
            // 如果是得分类型应用并且是自定义评分策略
            case SCORE:
                switch (appScoringStrategyEnum) {
                    case CUSTOM:
                        // 调用自定义得分类型策略中的方法返回结果对象
                        return customScoreScoringStrategy.doScoring(choiceList, app);
                    case AI:
                        break;
                }
                break;
            case TEST:
                switch (appScoringStrategyEnum) {
                    case CUSTOM:
                        // 调用自定义测评类型策略中的方法返回结果对象
                        return customTestScoringStrategy.doScoring(choiceList, app);
                    case AI:
                        break;
                }
                break;
        }
        throw new BusinessException(ErrorCode.SYSTEM_ERROR, "应用配置有误，未找到匹配的策略");
    }
}
