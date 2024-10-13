<!--管理员管理用户页面-->
<template>
  <!-- 用户搜索 -->
  <a-form
    :model="formSearchParams"
    :style="{ marginBottom: '20px' }"
    layout="inline"
    @submit="doSearch"
  >
    <a-form-item field="timeType" label="计时类型">
      <a-input
        v-model="formSearchParams.timeType"
        placeholder="请输入计时类型名称"
        allow-clear
      />
    </a-form-item>
    <a-form-item field="salaryMath" label="工资基数">
      <a-input
        v-model="formSearchParams.salaryMath"
        placeholder="请输入计时工资基数"
        allow-clear
      />
    </a-form-item>
    <a-form-item>
      <a-button type="primary" html-type="submit" style="width: 100px">
        搜&nbsp;索
      </a-button>
    </a-form-item>
    <a-form-item>
      <a-button type="primary" @click="openModal" style="width: 100px">
        新增类型
      </a-button>
    </a-form-item>
  </a-form>

  <!-- 列表 -->
  <a-table
    :columns="columns"
    :data="dataList"
    :pagination="{
      showTotal: true,
      pageSize: searchParams.pageSize,
      current: searchParams.current,
      total,
    }"
    @page-change="onPageChange"
  >
    <!--   以插槽方式展示头像，record为记录变量   -->
    <template #createTime="{ record }">
      {{ dayjs(record.createTime).format("YYYY-MM-DD") }}
    </template>
    <template #runStatus="{ record }">
      {{ RUN_STATUS_MAP[record.runStatus] }}
    </template>
    <!--   定义删除按钮   -->
    <template #optional="{ record }">
      <a-space>
        <a-button @click="openUpdateDrawer(record)">设置</a-button>
      </a-space>
      <a-space>
        <a-button status="danger" @click="doDelete(record)">删除</a-button>
      </a-space>
    </template>
  </a-table>

  <!-- 弹出框 -->
  <a-modal
    v-model:visible="visible"
    title="计件类型新增"
    ok-text="提交"
    cancel-text="取消"
    @ok="handleSubmit"
    @cancel="closeModal"
  >
    <a-form :model="form" :rules="rules" ref="formRef">
      <a-form-item label="计件类型名称" field="timeType">
        <a-input v-model="form.timeType" placeholder="请输入计件类型名称" />
      </a-form-item>
      <a-form-item label="计件类型基数" field="salaryMath">
        <a-input-number
          v-model="form.salaryMath"
          placeholder="请输入计件类型基数"
        />
      </a-form-item>
      <a-form-item label="启用状态" field="runStatus">
        <a-select
          v-model="form.runStatus"
          :style="{ width: '220px' }"
          placeholder="请选择状态"
          allow-clear
        >
          <a-option v-for="item in data" :key="item.value" :value="item.value">
            {{ item.label }}
          </a-option>
        </a-select>
      </a-form-item>
    </a-form>
  </a-modal>

  <!-- 更新员工信息的抽屉 -->
  <a-drawer
    v-model:visible="updateDrawerVisible"
    title="更新计件类型信息"
    width="600px"
    closable
    mask
    :footer="false"
    @close="closeUpdateDrawer"
  >
    <a-form :model="form" :rules="rules" ref="formRef">
      <a-form-item label="计件类型名称" field="timeType">
        <a-input v-model="form.timeType" placeholder="请输入计件类型名称" />
      </a-form-item>
      <a-form-item label="计件类型基数" field="salaryMath">
        <a-input-number
          v-model="form.salaryMath"
          placeholder="请输入计件类型基数"
        />
      </a-form-item>
      <a-form-item label="启用状态" field="runStatus">
        <a-select
          v-model="form.runStatus"
          :style="{ width: '220px' }"
          placeholder="请选择状态"
          allow-clear
        >
          <a-option v-for="item in data" :key="item.value" :value="item.value">
            {{ item.label }}
          </a-option>
        </a-select>
      </a-form-item>
    </a-form>
    <!-- 提交和取消按钮 -->
    <div class="drawer-footer">
      <a-button @click="closeUpdateDrawer">取消</a-button>
      <a-button type="primary" @click="handleUpdate">提交</a-button>
    </div>
  </a-drawer>
</template>

<script setup lang="ts">
import { reactive, ref, watchEffect } from "vue";
import API from "@/api";
import message from "@arco-design/web-vue/es/message";
import dayjs from "dayjs";
import { updateEmployeeUsingPost } from "@/api/employeeController";
import {
  addTimeManageUsingPost,
  deleteTimeManageUsingPost,
  listTimeManageByPageUsingPost, updateTimeManageUsingPost
} from "@/api/timeManageController";
import { RUN_STATUS_MAP } from "@/constant/app";

const formSearchParams = ref<API.TimeManageQueryRequest>({});
const visible = ref(false);
// 控制抽屉的显示和隐藏
const updateDrawerVisible = ref(false);
const currentEmployeeId = ref(null);

// 状态枚举
const data = [
  { value: "0", label: "已启用" },
  { value: "1", label: "已禁用" },
];

// 填充选中员工信息
const openUpdateDrawer = (record) => {
  currentEmployeeId.value = record.id; // 存储当前类型ID
  form.timeType = record.timeType;
  form.salaryMath = record.salaryMath;
  updateDrawerVisible.value = true;
};
const closeUpdateDrawer = () => {
  updateDrawerVisible.value = false;
};

// 表单数据
const form = reactive({
  timeType: "",
  salaryMath: null,
  runStatus: null,
} as API.TimeManageAddRequest);

const rules = ref({
  timeType: [
    { required: true, message: "请输入计件类型名称", trigger: "blur" },
  ],
  runStatus: [{ required: true, message: "请下拉选择状态", trigger: "blur" }],
  salaryMath: [
    { required: true, message: "请输入计件类型基数", type: "number" },
  ],
});

// 打开弹出框
const openModal = () => {
  visible.value = true;
};

// 关闭弹出框
const closeModal = () => {
  visible.value = false;
};

// 提交新增表单
const handleSubmit = async () => {
  const res = await addTimeManageUsingPost({
    ...form,
  });
  if (res.data.code === 0) {
    message.success("新增成功");
    form.timeType = "";
    form.runStatus = null;
    form.salaryMath = null;
    closeModal();
    loadData();
  } else {
    message.error("新增失败，" + res.data.message);
  }
};

// 提交更新表单
const handleUpdate = async () => {
  if (!currentEmployeeId.value) {
    return;
  }
  const res = await updateTimeManageUsingPost({
    id: currentEmployeeId.value,
    ...form,
  });
  if (res.data.code == 0) {
    message.success("更新成功！");
    closeUpdateDrawer();
    loadData();
  } else {
    message.error("更新失败！" + res.data.message);
  }
};

// 初始化搜索条件（不应该被修改）
const initSearchParams = {
  current: 1,
  pageSize: 10,
};

// 定义搜索词
const searchParams = ref<API.TimeManageQueryRequest>({
  ...initSearchParams,
});

// 定义两个变量来存储搜索出来的数据
const dataList = ref<API.TimeManage[]>([]);
const total = ref<number>(0);

/**
 * 加载数据
 */
const loadData = async () => {
  const res = await listTimeManageByPageUsingPost(searchParams.value);
  if (res.data.code == 0) {
    dataList.value = res.data.data?.records || [];
    total.value = res.data.data?.total || 0;
  } else {
    message.error("获取数据列表失败" + res.data.message);
  }
};

// 监听 loadData 数据变量，如果发生变化就重新加载数据列表
watchEffect(() => {
  loadData();
});

/**
 * 当分页改变时，改变搜索条件，触发数据加载
 * @param page
 */
const onPageChange = (page: number) => {
  searchParams.value = {
    // 先把旧值拿出来
    ...searchParams.value,
    // 改变页码，然后就会触发监听加载数据
    current: page,
  };
};

/**
 * 删除用户请求
 * @param record
 */
const doDelete = async (record: API.TimeManage) => {
  if (!record.id) {
    return;
  }
  const res = await deleteTimeManageUsingPost({
    id: record.id,
  });
  if (res.data.code == 0) {
    loadData();
    message.success("删除成功！");
  } else {
    message.error("删除失败！" + res.data.message);
  }
};

/**
 * 执行搜索
 */
const doSearch = () => {
  searchParams.value = {
    ...initSearchParams,
    ...formSearchParams.value,
  };
};

// 配置用户列表需要展示的数据列
const columns = [
  {
    title: "计件类型名称",
    dataIndex: "timeType",
  },
  {
    title: "计件工资基数",
    dataIndex: "salaryMath",
  },
  {
    title: "状态",
    dataIndex: "runStatus",
    slotName: "runStatus",
  },
  {
    title: "创建时间",
    dataIndex: "createTime",
    slotName: "createTime",
  },
  {
    title: "操作",
    slotName: "optional",
  },
];
</script>

<style scoped>
.drawer-footer {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
}
</style>
