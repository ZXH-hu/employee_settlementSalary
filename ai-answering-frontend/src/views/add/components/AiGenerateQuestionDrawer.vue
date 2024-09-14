<!--AI 生成题目页面，提供两种生成策略：
1、一键生成：需等待 AI 生成全部题目后一起返回题目信息
2、实时生成：无需等待 AI 全部生成后再返回，而是生成一道返回一道-->
<template>
  <a-button type="outline" @click="handleClick">AI 生成题目</a-button>
  <a-drawer
    :width="340"
    :visible="visible"
    :placement="position"
    @ok="handleOk"
    @cancel="handleCancel"
    unmountOnClose
  >
    <template #title>AI 生成题目</template>
    <div>
      <a-form
        :model="form"
        :style="{ width: '480px' }"
        label-align="left"
        auto-label-width
        @submit="handleSubmit"
      >
        <a-form-item label="应用 id">
          {{ appId }}
        </a-form-item>
        <a-form-item field="questionNumber" label="生成题目数量">
          <a-input-number
            min="0"
            max="20"
            v-model="form.questionNumber"
            placeholder="请输入题目数量"
          />
        </a-form-item>
        <a-form-item field="optionNumber" label="题目选项数量">
          <a-input-number
            min="0"
            max="6"
            v-model="form.optionNumber"
            placeholder="请输入选项数量"
          />
        </a-form-item>
        <a-form-item>
          <a-space>
            <a-button
              :loading="submitting"
              type="primary"
              html-type="submit"
              style="width: 120px"
            >
              {{ submitting ? "生成中" : "一键生成" }}
            </a-button>
            <a-button
              :loading="sseSubmitting"
              type="secondary"
              @click="handleSseSubmit"
              style="width: 120px"
            >
              {{ sseSubmitting ? "生成中" : "实时生成" }}
            </a-button>
          </a-space>
        </a-form-item>
      </a-form>
    </div>
  </a-drawer>
</template>

<script setup lang="ts">
import { defineProps, reactive, ref, withDefaults } from "vue";
import API from "@/api";
import { aiGenerateQuestionUsingPost } from "@/api/questionController";
import message from "@arco-design/web-vue/es/message";

// 请求参数appId
interface Props {
  appId: string;
  // 提交成功后，将结果传给父组件QuestionContentDTO对象插入题目信息
  onSuccess?: (result: API.QuestionContentDTO[]) => void;
  // 实时生成推送数据到创建题目页面，传递建立连接、关闭连接信息
  onSSESuccess?: (result: API.QuestionContentDTO) => void;
  onSSEStart?: (event: any) => void;
  onSSEClose?: (event: any) => void;
}

const props = withDefaults(defineProps<Props>(), {
  appId: () => {
    return "";
  },
});
const visible = ref(false);
// 定义抽屉位置：top、bottom、left、right
const position = ref("right");
// 添加一个等待变量
const submitting = ref(false);
const sseSubmitting = ref(false);

const form = reactive({
  // 初始化表单数量
  optionNumber: 2,
  questionNumber: 10,
} as API.AiGenerateQuestionRequest);

/**
 * 提交（一键提交）
 */
const handleSubmit = async () => {
  // 如果外层没有传递appId，直接返回
  if (!props.appId) {
    return;
  }
  submitting.value = true;
  // 调用后端接口：传递appId和表单变量
  const res = await aiGenerateQuestionUsingPost({
    appId: props.appId as any,
    ...form,
  });
  // 判断返回体
  if (res.data.code === 0 && res.data.data.length > 0) {
    if (props.onSuccess) {
      props.onSuccess(res.data.data);
    } else {
      message.success("生成题目成功");
    }
    // 成功后关闭抽屉
    handleCancel();
  } else {
    message.error("操作失败，" + res.data.message);
  }
  submitting.value = false;
};

/**
 * 提交（实时SSE）
 */
const handleSseSubmit = async () => {
  // 如果外层没有传递appId，直接返回
  if (!props.appId) {
    return;
  }
  sseSubmitting.value = true;
  // 创建 SSE 请求，这里需要手动创建后端url接口地址（因为没有使用请求体传递，自定义的请求方法）
  const eventSource = new EventSource(
    "http://localhost:8101/api/question/ai_generate/sse" +
      `?appId=${props.appId}&optionNumber=${form.optionNumber}&questionNumber=${form.questionNumber}`
  );
  let first = true;
  // 接收消息
  eventSource.onmessage = function (event) {
    if (first) {
      props.onSSEStart?.(event);
      handleCancel();
      first = !first;
    }
    // 有数据了就传递JSON解析的数据过去，？表示有可能为空
    props.onSSESuccess?.(JSON.parse(event.data));
  };
  // 报错或连接关闭时触发
  eventSource.onerror = function (event) {
    if (event.eventPhase === EventSource.CLOSED) {
      console.log("关闭连接");
      props.onSSEClose?.(event);
      eventSource.close();
    } else {
      eventSource.close();
    }
  };
  sseSubmitting.value = false;
};

const handleClick = () => {
  visible.value = true;
};
const handleOk = () => {
  visible.value = false;
};
const handleCancel = () => {
  visible.value = false;
};
</script>
