<!--用户注册页面-->
<template>
  <div id="userRegisterPage">
    <h2 style="margin-bottom: 16px">用户注册</h2>
    <a-form
      :model="form"
      :style="{ width: '480px', margin: '0 auto' }"
      label-align="left"
      auto-label-width
      @submit="handleSubmit"
      :rules="rules"
      ref="formRef"
    >
      <a-form-item
        field="userAccount"
        label="账号"
        :rules="rules.userAccountRules"
      >
        <a-input v-model="form.userAccount" placeholder="请输入账号" />
      </a-form-item>
      <a-form-item
        field="userNickName"
        label="昵称"
        tooltip="定义一个昵称吧！"
        :rules="rules.userNickNameRules"
      >
        <a-input v-model="form.userNickName" placeholder="请输入昵称" />
      </a-form-item>
      <a-form-item
        field="userPassword"
        tooltip="密码不小于 8 位"
        label="密码"
        :rules="rules.passwordRules"
      >
        <a-input-password
          v-model="form.userPassword"
          placeholder="请输入密码"
        />
      </a-form-item>
      <a-form-item
        field="checkPassword"
        tooltip="确认密码不小于 8 位"
        label="确认密码"
        :rules="rules.checkPasswordRules"
      >
        <a-input-password
          v-model="form.checkPassword"
          placeholder="请再次确认密码"
        />
      </a-form-item>
      <a-form-item>
        <div
          style="
            display: flex;
            flex-direction: column;
            width: 100%;
            align-items: center;
            justify-content: center;
          "
        >
          <a-button
            type="primary"
            html-type="submit"
            style="width: 100%; margin-bottom: 10px; border-radius: 8px"
            shape="border-radius: 4px;"
          >
            注&nbsp;册
          </a-button>
          <div
            style="
              display: flex;
              justify-content: flex-end;
              width: 100%;
              margin-top: 10px;
            "
          >
            <a class="register" @click="goToRegister"
              ><b>已有账号？去登录</b></a
            >
          </div>
        </div>
      </a-form-item>
    </a-form>
  </div>
</template>

<script setup lang="ts">
import { reactive } from "vue";
import API from "@/api";
import { userRegisterUsingPost } from "@/api/userController";
import message from "@arco-design/web-vue/es/message";
import { useRouter } from "vue-router";

const router = useRouter();

const form = reactive({
  userAccount: "",
  userPassword: "",
  checkPassword: "",
  userNickName: "",
} as API.UserRegisterRequest);

/**
 * 验证规则
 */
const rules = reactive({
  userAccountRules: [{ required: true, message: "账号是必填项！" }],
  passwordRules: [{ required: true, message: "请输入密码!" }],
  checkPasswordRules: [
    { required: true, message: "请确认密码", trigger: "blur" },
    {
      validator: (rule, value) => {
        if (form.checkPassword !== form.userPassword) {
          form.checkPassword = "";
          return window.alert("两次输入的密码不一致");
        } else {
          return Promise.resolve();
        }
      },
      trigger: "blur",
    },
  ],
});

/**
 * 提交
 */
const handleSubmit = async () => {
  const res = await userRegisterUsingPost(form);
  if (res.data.code === 0) {
    message.success("注册成功");
    router.push({
      path: "/user/login",
      replace: true,
    });
  } else {
    message.error("注册失败，" + res.data.message);
  }
};

const goToRegister = () => {
  router.push("/user/login");
};
</script>

<style scoped>
.register {
  color: #13a2ce;
}
</style>
