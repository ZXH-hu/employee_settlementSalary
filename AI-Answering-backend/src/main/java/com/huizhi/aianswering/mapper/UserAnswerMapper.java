package com.huizhi.aianswering.mapper;

import com.huizhi.aianswering.model.dto.statistic.AppAnswerCountDTO;
import com.huizhi.aianswering.model.dto.statistic.AppAnswerResultCountDTO;
import com.huizhi.aianswering.model.entity.UserAnswer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 小赵
* @description 针对表【user_answer(用户答题记录)】的数据库操作Mapper
* @createDate 2024-09-01 21:25:46
* @Entity com.huizhi.aianswering.model.entity.UserAnswer
*/
public interface UserAnswerMapper extends BaseMapper<UserAnswer> {

    /**
     * 查询热门应用排行
     * @return
     */
    @Select("select appId , count(userId) as answerCount from user_answer\n" +
            "group by appId order by answerCount desc limit 10;")
    List<AppAnswerCountDTO> selectAppAnswerCount();


    /**
     * 查询应用答题结果排行
     * @param appId
     * @return
     */
    @Select("select resultName, count(resultName) as answerResultCount from user_answer\n" +
            "    where appId = #{appId}\n" +
            "    group by resultName order by answerResultCount desc;")
    List<AppAnswerResultCountDTO> selectAppAnswerResultCount(Long appId);
}




