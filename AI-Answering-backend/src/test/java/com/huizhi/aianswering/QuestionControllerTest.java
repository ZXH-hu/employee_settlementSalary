package com.huizhi.aianswering;

import com.huizhi.aianswering.controller.QuestionController;
import com.huizhi.aianswering.model.dto.question.AiGenerateQuestionRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class QuestionControllerTest {

    @Resource
    private QuestionController questionController;

    @Test
    void aiGenerateQuestionSSETest() throws InterruptedException {
        // 模拟调用
        AiGenerateQuestionRequest aiGenerateQuestionRequest = new AiGenerateQuestionRequest();
        aiGenerateQuestionRequest.setAppId(3L);
        aiGenerateQuestionRequest.setQuestionNumber(10);
        aiGenerateQuestionRequest.setOptionNumber(2);

        // 模拟普通用户
        questionController.aiGenerateQuestionSSETest(aiGenerateQuestionRequest, false);
        // 模拟普通用户
        questionController.aiGenerateQuestionSSETest(aiGenerateQuestionRequest, false);
        // 模拟会员用户
        questionController.aiGenerateQuestionSSETest(aiGenerateQuestionRequest, true);

        // 模拟主线程一直启动100秒
        Thread.sleep(20000L);
    }
}
