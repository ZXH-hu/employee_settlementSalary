<!--个人中心-->
<template>
  <div id="userHomInfo">
    <a-card>
      <a-row style="margin-bottom: 16px">
        <a-col flex="auto" class="content-wrapper">
          <a-space>
            <div :style="{ display: 'flex', alignItems: 'center' }">
              <a-avatar
                :size="30"
                :image-url="data.userAvatar"
                :style="{ marginRight: '8px' }"
              />
              <h2>
                <a-typography-text
                  >{{ data.userName ?? "无名" }}
                </a-typography-text>
              </h2>
            </div>
            <h2>&nbsp;欢迎来到个人中心</h2>
          </a-space>
          <div class="info-container">
            <p><b>你的账号：</b>{{ data.userAccount }}</p>
            <p><b>当前角色：</b>{{ USER_ROLE_MAP[data.userRole] }}</p>
            <p><b>个人简介：</b>{{ data.userProfile ?? "暂无简介信息哦，快来添加吧！" }}</p>
            <p>
              <b>账户创建时间：</b
              >{{ dayjs(data.createTime).format("YYYY-MM-DD HH:mm:ss") }}
            </p>
          </div>
          <a-space size="medium">
            <a-button type="primary" @click="toEditInfo">修改信息 </a-button>
            <a-button @click="toIndex">返回主页</a-button>
          </a-space>
        </a-col>
      </a-row>
    </a-card>
  </div>
</template>

<script setup lang="ts">
import { computed, defineProps, ref, watchEffect, withDefaults } from "vue";
import API from "@/api";
import { useRouter } from "vue-router";
import { dayjs } from "@arco-design/web-vue/es/_utils/date";
import { useLoginUserStore } from "@/store/userStore";
import { USER_ROLE_MAP } from "../../constant/app";
import message from "@arco-design/web-vue/es/message";
import { getLoginUserUsingGet } from "@/api/userController";

interface Props {
  id: string;
}

const props = withDefaults(defineProps<Props>(), {
  id: () => {
    return "";
  },
});

const router = useRouter();

const data = ref<API.UserVO>({});

// 获取登录用户
const loginUserStore = useLoginUserStore();
let loginUserId = loginUserStore.loginUser?.id;
// 是否为本人创建
const isMy = computed(() => {
  return loginUserId && loginUserId === data.value.userId;
});

const loadData = async () => {
  if (!props.id) {
    return;
  }
  const res = await getLoginUserUsingGet({
    id: props.id as any,
  });
  if (res.data.code === 0) {
    data.value = res.data.data as any;
  } else {
    message.error("获取数据失败，" + res.data.message);
  }
};

const toIndex = () => {
  router.push("/");
};

const toEditInfo = () => {
  router.push(`/user/editinfo/${loginUserId}`);
};

/**
 * 监听 searchParams 变量，改变时触发数据的重新加载
 */
watchEffect(() => {
  loadData();
});
</script>

<style scoped>
#userHomInfo {
}

#userHomInfo .content-wrapper > * {
  margin-bottom: 24px;
}

.info-container {
  display: flex;
  flex-direction: column;
  gap: 1em;
  align-items: flex-start;
  justify-content: center;
  width: fit-content;
  margin: 0 auto;
}

.info-container p {
  margin: 0;
}
</style>
