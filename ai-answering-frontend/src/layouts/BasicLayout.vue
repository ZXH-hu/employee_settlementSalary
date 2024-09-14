<!--全局布局：展示导航栏-->
<template>
  <div id="basicLayout">
    <a-layout style="height: 100vh">
      <a-layout-header class="header">
        <GlobalHeader></GlobalHeader>
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

<script setup lang="ts">
import GlobalHeader from "@/components/GlobalHeader.vue";
</script>

<style scoped>
#basicLayout {
}

#basicLayout .content {
  box-sizing: border-box;
  width: 100%;
  max-width: 1200px;
  margin: 0 auto 28px;
  padding: 20px;
  background: linear-gradient(to right, #fefefe, #fff);
}

#basicLayout .header {
  margin-bottom: 16px;
  box-shadow: #eee 1px 1px 5px;
}

.footer {
  padding: 16px;
  text-align: center;
  text-decoration: none;
  background: #efefef;
}
</style>
