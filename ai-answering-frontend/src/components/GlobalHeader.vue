<!--头部导航栏-->
<template>
  <a-row id="globalHeader" align="center" :wrap="false">
    <a-col flex="auto">
      <a-menu
        mode="horizontal"
        :selected-keys="selectedKeys"
        @menu-item-click="doMenuClick"
      >
        <a-menu-item
          key="0"
          :style="{ padding: 0, marginRight: '38px' }"
          disabled
        >
          <div class="titleBar">
            <img class="logo" src="../assets/logo.png" />
            <div class="title"><b>慧知-AI答题</b></div>
          </div>
        </a-menu-item>
        <a-menu-item v-for="item in visibleRoutes" :key="item.path">
          {{ item.name }}
        </a-menu-item>
      </a-menu>
    </a-col>
    <a-col flex="100px">
      <div v-if="loginUserStore.loginUser.id">
        <a-dropdown trigger="hover" style="width: 100px; text-align: center">
          <a-button style="background: #fd9400; color: white"
            >{{ data.userName ?? "无名" }}
          </a-button>
          <template #content>
            <a-button @click="goToMyInfo">个人中心</a-button>
            <a-button @click="goToLoginOut">退出登录</a-button>
          </template>
        </a-dropdown>
      </div>
      <div v-else>
        <a-button type="primary" href="/user/login">登录</a-button>
      </div>
    </a-col>
  </a-row>
</template>

<script setup lang="ts">
import { routes } from "@/router/routes";
import { useRouter } from "vue-router";
import { computed, ref, watchEffect } from "vue";
import { useLoginUserStore } from "@/store/userStore";
import checkAccess from "@/access/checkAccess";
import { getLoginUserUsingGet, userLogoutUsingPost } from "@/api/userController";
import message from "@arco-design/web-vue/es/message";
import API from "@/api";

const loginUserStore = useLoginUserStore();

const router = useRouter();
// 当前选中的菜单项
const selectedKeys = ref(["/"]);

const data = ref<API.UserVO>({});

// 当路由跳转时，自动更新选中的菜单项
router.afterEach((to, from, failure) => {
  selectedKeys.value = [to.path];
});

const loadData = async () => {
  const res = await getLoginUserUsingGet();
  if (res.data.code === 0) {
    data.value = res.data.data as any;
  } else {
    message.error("获取数据失败，" + res.data.message);
  }
};
watchEffect(() => {
  loadData();
});

// 退出登录
let loginUserId = loginUserStore.loginUser?.id;
const goToLoginOut = async () => {
  const res = await userLogoutUsingPost(loginUserId);
  if (res.data.code == 0) {
    message.success("退出成功！");
    router.push({
      path: "/user/login",
      replace: true,
    });
  } else {
    message.error("退出失败！" + res.data.message);
  }
};

const goToMyInfo = () => {
  router.push(`/user/myinfo/${loginUserId}`);
};

// 可以展示得到菜单栏的路由数组
const visibleRoutes = computed(() => {
  return routes.filter((item) => {
    if (item.meta?.hideInMenu) {
      return false;
    }
    // 根据权限过滤用户
    if (!checkAccess(loginUserStore.loginUser, item.meta?.access as string)) {
      return false;
    }
    return true;
  });
});

// 点击菜单跳转到对应页面
const doMenuClick = (key: string) => {
  router.push({
    path: key,
  });
};
</script>

<style scoped>
#globalHeader {
}

.titleBar {
  display: flex;
  align-items: center;
}

.title {
  margin-left: 16px;
  color: black;
}

.logo {
  height: 48px;
}
</style>
