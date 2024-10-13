<!--用户登录页面-->
<template>
  <div id="userLoginPage">
    <h2 style="margin-bottom: 16px">管理员登录</h2>
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
        <a-input v-model="form.userAccount" placeholder="体验账号：admin" />
      </a-form-item>
      <a-form-item
        field="userPassword"
        label="密码"
        tooltip="密码不小于 8 位"
        :rules="rules.passwordRules"
      >
        <a-input-password
          v-model="form.userPassword"
          placeholder="体验密码：123456789"
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
            登&nbsp;录
          </a-button>
          <div
            style="
              display: flex;
              justify-content: space-between;
              width: 100%;
              margin-top: 5px;
            "
          >
            <a class="forgetPassword"><b>忘记密码?</b></a>
            <a class="register"><b>注册账号</b></a>
          </div>
        </div>
      </a-form-item>
    </a-form>
  </div>
</template>

<script setup lang="ts">
import { reactive } from "vue";
import API from "@/api";
import { userLoginUsingPost } from "@/api/userController";
import { useLoginUserStore } from "@/store/userStore";
import message from "@arco-design/web-vue/es/message";
import { useRouter } from "vue-router";

const loginUserStore = useLoginUserStore();
const router = useRouter();

const form = reactive({
  userAccount: "",
  userPassword: "",
} as API.UserLoginRequest);

/**
 * 验证规则
 */
const rules = reactive({
  userAccountRules: [
    { required: true, message: "账号是必填项！", trigger: "blur" },
  ],
  passwordRules: [
    { required: true, message: "请输入密码!" },
    { min: 8, message: "密码长度至少为8个字符" },
  ],
});

/**
 * 提交
 */
const handleSubmit = async () => {
  const res = await userLoginUsingPost({
    ...form,
  });
  if (res.data.code === 0) {
    await loginUserStore.fetchLoginUser();
    message.success("登录成功");
    router.push({
      path: "/employee_info",
      replace: true,
    });
  } else {
    message.error("登录失败，" + res.data.message);
  }
};

// 定义注册跳转函数
const goToRegister = () => {
  router.push("/user/register");
};

// 定义修改密码跳转函数
const goToForgotPassword = () => {
  router.push("/user/edit_password");
};
</script>

<style scoped>
.register,
.forgetPassword {
  color: #13a2ce;
}
</style>
