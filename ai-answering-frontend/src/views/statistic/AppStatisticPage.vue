<!--系统主页-->
<template>
  <div id="appStatistic">
    <h2>热门应用统计</h2>
    <v-chart :option="appAnswerCountOptions" style="height: 300px" />
    <h2>答题结果统计</h2>
    <div class="search-bar">
      <a-input-search
        :style="{ width: '320px' }"
        placeholder="输入 appId"
        button-text="搜索"
        size="large"
        search-button
        @search="(value) => loadAppAnswerResultCountData(value)"
      />
    </div>
    <div style="margin-bottom: 16px" />
    <v-chart :option="appAnswerResultCountOptions" style="height: 300px" />
  </div>
</template>

<script setup lang="ts">
import { computed, ref, watchEffect } from "vue";
import API from "@/api";
import message from "@arco-design/web-vue/es/message";
import {
  getAppAnswerCountUsingGet,
  getAppAnswerResultCountUsingGet,
} from "@/api/appStatisticController";
import VChart from "vue-echarts";
import "echarts";

const appAnswerCountList = ref<API.AppAnswerCountDTO[]>([]);
const appAnswerResultCountList = ref<API.AppAnswerResultCountDTO[]>([]);

/**
 * 加载热门应用数据
 */
const loadAppAnswerCountData = async () => {
  const res = await getAppAnswerCountUsingGet();
  if (res.data.code === 0) {
    appAnswerCountList.value = res.data.data || [];
  } else {
    message.error("获取热门应用数据失败，" + res.data.message);
  }
};

/**
 * 获取热门应用 options 数据格式
 */
const appAnswerCountOptions = computed(() => {
  return {
    tooltip: {
      trigger: "axis",
      axisPointer: {
        type: "shadow",
      },
    },
    toolbox: {
      // 添加toolbox配置项
      show: true, // 显示工具栏
      feature: {
        saveAsImage: {
          // 保存为图片功能
          show: true,
        },
        restore: {
          // 恢复默认视图功能
          show: true,
        },
        dataView: {
          // 数据视图功能
          show: true,
          readOnly: false,
        },
        magicType: {
          // 动态类型切换功能
          show: true,
          type: ["pie", "funnel"],
        },
      },
    },
    xAxis: {
      type: "category",
      data: appAnswerCountList.value.map((item) => item.appId),
      name: "应用 id",
    },
    yAxis: {
      type: "value",
      name: "用户答案数",
    },
    series: [
      {
        data: appAnswerCountList.value.map((item) => item.answerCount),
        type: "bar",
      },
    ],
  };
});

/**
 * 获取答题结果占比 options 数据格式
 */
const appAnswerResultCountOptions = computed(() => {
  return {
    tooltip: {
      trigger: "item",
    },
    legend: {
      orient: "vertical",
      left: "left",
    },
    toolbox: {
      // 添加toolbox配置项
      show: true, // 显示工具栏
      feature: {
        saveAsImage: {
          // 保存为图片功能
          show: true,
        },
        restore: {
          // 恢复默认视图功能
          show: true,
        },
        dataView: {
          // 数据视图功能
          show: true,
          readOnly: false,
        },
        magicType: {
          // 动态类型切换功能
          show: true,
          type: ["pie", "funnel"],
        },
      },
    },
    series: [
      {
        name: "应用答案结果分布",
        type: "pie",
        radius: "50%",
        data: appAnswerResultCountList.value.map((item) => {
          return { value: item.answerResultCount, name: item.resultName };
        }),
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: "rgba(0, 0, 0, 0.5)",
          },
        },
      },
    ],
  };
});

/**
 * 加载应用结果占比数据
 */
const loadAppAnswerResultCountData = async (appId: string) => {
  if (!appId) {
    return;
  }
  const res = await getAppAnswerResultCountUsingGet({
    appId: appId as any,
  });
  if (res.data.code === 0) {
    appAnswerResultCountList.value = res.data.data || [];
  } else {
    message.error("获取应用结果占比数据失败，" + res.data.message);
  }
};

/**
 * 监听 searchParams 变量，改变时触发数据的重新加载
 */
watchEffect(() => {
  loadAppAnswerCountData();
});
watchEffect(() => {
  loadAppAnswerResultCountData("1");
});
</script>

<style scoped></style>
