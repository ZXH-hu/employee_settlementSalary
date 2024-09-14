<!--用户对象布局，如登录注册、修改用户信息等页面都使用这个布局-->
<template>
  <div id="userLayout">
    <a-layout style="height: 100vh">
      <a-layout-header class="header">
        <a-space>
          <img class="logo" src="../assets/logo.png" />
          <div>慧知 AI 答题应用平台</div>
        </a-space>
      </a-layout-header>
      <a-layout-content class="content">
        <router-view />
      </a-layout-content>
      <a-layout-footer class="footer">
        @2024 小赵在坚持码代码，头发快掉光了~ |
        <a-link
          href="https://n.sinaimg.cn/sinakd20117/485/w643h642/20240905/0d85-3d8453111f89402b8ff6bfc283533a9c.jpg"
          target="_blank"
          >加油啊
        </a-link>
        |
        <a-link href="https://maas.aminer.cn/console/overview" target="_blank"
          >智普AI
        </a-link>
      </a-layout-footer>
    </a-layout>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped>
#userLayout {
  text-align: center;
  background: url("https://gw.alipayobjects.com/zos/rmsportal/FfdJeJRQWjEeGTpqgBKj.png")
    0% 0% / 100% 100%;
}

#userLayout .logo {
  width: 48px;
  height: 48px;
}

#userLayout .header {
  margin-top: 16px;
  font-size: 21px;
}

#userLayout .content {
  margin-bottom: 16px;
  padding: 20px;
}

.footer {
  padding: 16px;
  text-align: center;
  background: #efefef;
}
</style>
