<!--修改个人信息-->
<template>
  <div id="addAppPage">
    <h2 style="margin-bottom: 60px">修改个人信息</h2>
    <a-form
      :model="form"
      :style="{ width: '480px', marginLeft: '612px' }"
      label-align="left"
      auto-label-width
      @submit="handleSubmit"
    >
      <a-form-item field="userName" label="用户昵称">
        <a-input v-model="form.userName" placeholder="请输入昵称" />
      </a-form-item>
      <a-form-item field="userAccount" label="用户账号">
        <a-input v-model="form.userAccount" placeholder="请输入账号" />
      </a-form-item>
      <a-form-item field="userAvatar" label="用户头像">
        <a-input v-model="form.userAvatar" placeholder="请输入头像地址" />
      </a-form-item>
      <a-form-item field="userProfile" label="用户简介">
        <a-input v-model="form.userProfile" placeholder="请输入用户简介" />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" html-type="submit" style="width: 120px">
          提&nbsp;交
        </a-button>
        <a-button type="secondary" style="width: 120px; margin-left: 60px" @click="handleCancel">
          取&nbsp;消
        </a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script setup lang="ts">
import { defineProps, onMounted, ref, withDefaults } from "vue";
import API from "@/api";
import message from "@arco-design/web-vue/es/message";
import { useRouter } from "vue-router";
import {
  getLoginUserUsingGet,
  updateUserUsingPost,
} from "@/api/userController";

interface Props {
  id: string;
}

const props = withDefaults(defineProps<Props>(), {
  id: () => {
    return "";
  },
});

// 返回上一级页面
const handleCancel = () => {
  router.go(-1);
};

const router = useRouter();
const data = ref<API.UserVO>({});

const form = ref({
  userName: "",
  userAccount: "",
  userAvatar: "",
  userProfile: "",
} as API.UserUpdateMyRequest);

const loadData = async () => {
  if (!props.id) {
    return;
  }
  const res = await getLoginUserUsingGet({
    id: props.id as any,
  });
  if (res.data.code === 0) {
    data.value = res.data.data as any;
    // 将获取到的用户数据填充到表单中
    form.value = {
      userName: data.value.userName || "",
      userAccount: data.value.userAccount || "",
      userAvatar: data.value.userAvatar || "",
      userProfile: data.value.userProfile || "",
    };
  } else {
    message.error("获取数据失败，" + res.data.message);
  }
};

onMounted(() => {
  loadData();
});

/**
 * 提交
 */
const handleSubmit = async () => {
  let res: any;
  // 如果是修改
  if (props.id) {
    res = await updateUserUsingPost({
      id: props.id as any,
      ...form.value,
    });
  }
  if (res.data.code === 0) {
    message.success("修改成功，即将返回个人中心");
    setTimeout(() => {
      router.push(`/user/myinfo/${props.id}`);
    }, 2000);
  } else {
    message.error("操作失败，" + res.data.message);
  }
};
</script>

<style scoped></style>
