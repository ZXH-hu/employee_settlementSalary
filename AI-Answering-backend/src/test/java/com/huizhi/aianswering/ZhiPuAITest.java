package com.huizhi.aianswering;

import com.huizhi.aianswering.constant.KeyConstant;
import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.service.v4.model.ChatCompletionRequest;
import com.zhipu.oapi.service.v4.model.ChatMessage;
import com.zhipu.oapi.service.v4.model.ChatMessageRole;
import com.zhipu.oapi.service.v4.model.ModelApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ZhiPuAITest {

    @Test
    public void test() {
        // 初始化客户端
        ClientV4 client = new ClientV4.Builder(KeyConstant.SecretKey).build();
        // 构造请求
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "作为一名营销专家，请为智谱开放平台创作一个吸引人的slogan");
        messages.add(chatMessage);
        // 构造请求id，也可以不使用，可以自动生成id存储消息
//        String requestId = String.format(requestIdTemplate, System.currentTimeMillis());

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(Constants.ModelChatGLM4)  // 模型名称
                .stream(Boolean.FALSE)  // 是否流式请求，默认是false，会等模型回答所有内容了才返回；true会每生成一个字都会返回。
                .invokeMethod(Constants.invokeMethod)  // 调用方式，默认是同步调用，如果需要异步调用，请使用async-invoke 调用方法.
                .messages(messages)  // 消息列表
//                .requestId(requestId)  // 请求id，可以不传，默认会自动生成id
                .build();
        ModelApiResponse invokeModelApiResp = client.invokeModelApi(chatCompletionRequest);
        System.out.println("model output:" + invokeModelApiResp.getData().getChoices().get(0));
    }
}
