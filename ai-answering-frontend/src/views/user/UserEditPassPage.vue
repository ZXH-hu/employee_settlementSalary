<!--用户登录页面-->
<template>
  <div id="userLoginPage">
    <h2 style="margin-bottom: 16px">修改个人密码</h2>
    <a-form
      :model="form"
      :style="{ width: '480px', margin: '0 auto' }"
      label-align="left"
      auto-label-width
      @submit="handleSubmit"
      :rules="rules"
    >
      <a-form-item
        field="userAccount"
        label="账号"
        :rules="rules.userAccountRules"
      >
        <a-input v-model="form.userAccount" placeholder="请输入原始账号" />
      </a-form-item>
      <a-form-item
        field="userPassword"
        label="新密码"
        tooltip="密码不小于 8 位"
        :rules="rules.passwordRules"
      >
        <a-input-password
          v-model="form.userPassword"
          placeholder="请输入新密码"
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
      <a-form-item
        field="captureCode"
        label="验证码"
        :rules="rules.captureCode"
      >
        <a-input
          v-model="form.captureCode"
          placeholder="请输入验证码"
          style="width: 46%; margin-right: 3px"
        />
        <img
          :src="captchaUrl"
          width="135px"
          height="33px"
          @click="reloadCaptcha"
          alt="验证码"
          style="
            margin-left: 30px;
            border: 2px solid #ccc;
            padding: 2px;
            border-radius: 4px;
          "
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
            提&nbsp;交
          </a-button>
        </div>
      </a-form-item>
    </a-form>
  </div>
</template>

<script setup lang="ts">
import { defineProps, onMounted, reactive, ref, withDefaults } from "vue";
import API from "@/api";
import {
  updateMyUserUsingPost,
  userLoginUsingPost,
} from "@/api/userController";
import { useLoginUserStore } from "@/store/userStore";
import message from "@arco-design/web-vue/es/message";
import { useRouter } from "vue-router";

const router = useRouter();

interface Props {
  userKey: string;
}

const props = withDefaults(defineProps<Props>(), {
  userKey: () => {
    return Math.random().toString(36).substr(2, 8);
  },
});

const captchaUrl = ref(""); // 初始化 captchaUrl
const userKey = ref(props.userKey);

// 获取验证码 URL
const getCaptchaUrl = () => {
  captchaUrl.value =
    "http://localhost:8101/api/capt/math_captcha?userKey=" + userKey.value;
};

// 页面加载时获取验证码
onMounted(() => {
  getCaptchaUrl();
});

// 点击刷新验证码的函数
const reloadCaptcha = () => {
  userKey.value = Math.random().toString(36).substr(2, 8); // 生成新的 userKey
  getCaptchaUrl(); // 获取新的验证码 URL
  form.captureCode = "";
};

const form = reactive({
  userAccount: "",
  userPassword: "",
  checkPassword: "",
  captureCode: "",
} as API.UserUpdateMyRequest);

/**
 * 验证规则
 */
const rules = reactive({
  userAccountRules: [
    { required: true, message: "账号是必填项！" },
    { min: 4, message: "密码长度至少为4个字符" },
  ],
  passwordRules: [
    { required: true, message: "请输入密码!" },
    { min: 8, message: "密码长度至少为8个字符" },
  ],
  captureCode: [{ required: true, message: "请输入验证码", trigger: "blur" }],
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
  const res = await updateMyUserUsingPost({
    userKey: userKey.value,
    ...form,
  });
  if (res.data.code === 0) {
    message.success("修改成功，即将返回登录");
    setTimeout(() => {
      router.push({
        path: "/user/login",
        replace: true,
      });
    }, 2000);
  } else {
    message.error("修改失败，" + res.data.message);
    // 重新刷新验证码和key
    reloadCaptcha();
  }
};
</script>

<style scoped>
.register,
.forgetPassword {
  color: #13a2ce;
}
</style>
