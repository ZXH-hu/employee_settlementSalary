<!--管理员管理用户页面-->
<template>
  <!-- 用户搜索 -->
  <a-form
    :model="formSearchParams"
    :style="{ marginBottom: '20px' }"
    layout="inline"
    @submit="doSearch"
  >
    <a-form-item field="employeeNo" label="编号">
      <a-input
        v-model="formSearchParams.employeeNo"
        placeholder="请输入员工编号"
        allow-clear
      />
    </a-form-item>
    <a-form-item field="employeeName" label="员工名称">
      <a-input
        v-model="formSearchParams.employeeName"
        placeholder="请输入员工名字"
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
        新增员工
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
    <template #updateTime="{ record }">
      {{ dayjs(record.updateTime).format("YYYY-MM-DD") }}
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
    title="新增员工"
    ok-text="提交"
    cancel-text="取消"
    @ok="handleSubmit"
    @cancel="closeModal"
  >
    <a-form :model="form" :rules="rules" ref="formRef">
      <a-form-item label="员工姓名" field="employeeName">
        <a-input v-model="form.employeeName" placeholder="请输入员工姓名" />
      </a-form-item>
      <a-form-item label="计件类型" field="timeType">
        <a-select
          v-model="form.timeType"
          :style="{ width: '220px' }"
          placeholder="请选择计件类型名称"
          allow-clear
        >
          <a-option v-for="item in data" :key="item.value" :value="item.label">
            {{ item.label }}
          </a-option>
        </a-select>
      </a-form-item>
      <a-form-item label="工资基数" field="salaryMath">
        <a-select
          v-model="form.salaryMath"
          :style="{ width: '220px' }"
          placeholder="请选择计件类型基数"
          allow-clear
        >
          <a-option v-for="item in data" :key="item.value" :value="item.value">
            {{ item.value }}
          </a-option>
        </a-select>
      </a-form-item>
    </a-form>
  </a-modal>

  <!-- 更新员工信息的抽屉 -->
  <a-drawer
    v-model:visible="updateDrawerVisible"
    title="更新员工信息"
    width="600px"
    closable
    mask
    :footer="false"
    @close="closeUpdateDrawer"
  >
    <a-form :model="form" :rules="rules" ref="formRef">
      <a-form-item label="员工姓名" field="employeeName">
        <a-input v-model="form.employeeName" placeholder="请输入员工姓名" />
      </a-form-item>
      <a-form-item label="计时类型" field="timeType">
        <a-input v-model="form.timeType" placeholder="请输入计时类型" />
      </a-form-item>
      <a-form-item label="计时工资基数" field="salaryMath">
        <a-input-number
          v-model="form.salaryMath"
          placeholder="请输入工资基数"
        />
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
import { reactive, ref, watchEffect, onMounted } from "vue";
import API from "@/api";
import message from "@arco-design/web-vue/es/message";
import dayjs from "dayjs";
import {
  addEmployeeUsingPost,
  deleteEmployeeUsingPost,
  listEmployeeByPageUsingPost,
  updateEmployeeUsingPost,
} from "@/api/employeeController";
import { listTimeManageByPageUsingPost } from "@/api/timeManageController";

const formSearchParams = ref<API.EmployeeQueryRequest>({});
const visible = ref(false);
// 控制抽屉的显示和隐藏
const updateDrawerVisible = ref(false);
const currentEmployeeId = ref(null);

// 填充选中员工信息
const openUpdateDrawer = (record) => {
  currentEmployeeId.value = record.id; // 存储当前员工ID
  form.employeeName = record.employeeName;
  form.timeType = record.timeType;
  form.salaryMath = record.salaryMath;
  updateDrawerVisible.value = true;
};
const closeUpdateDrawer = () => {
  updateDrawerVisible.value = false;
};

// 表单数据
const form = reactive({
  employeeName: "",
  salaryMath: null,
  timeType: "",
} as API.EmployeeAddRequest);

const rules = ref({
  employeeName: [
    { required: true, message: "请输入员工姓名", trigger: "blur" },
  ],
  timeType: [{ required: true, message: "请输入计时类型", trigger: "blur" }],
  salaryMath: [{ required: true, message: "请输入工资基数", type: "number" }],
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
  const res = await addEmployeeUsingPost({
    ...form,
  });
  if (res.data.code === 0) {
    message.success("新增成功");
    form.employeeName = "";
    form.timeType = "";
    form.salaryMath = null;
    closeModal();
    loadData();
  } else {
    message.error("新增失败，" + res.data.message);
  }
};

// 定义搜索词（查询计件类型名称和基数）
const initSearchParam = {
  current: 1,
  pageSize: 10,
};
const searchParam = ref<API.TimeManageQueryRequest>({
  ...initSearchParam,
});

// 计件类型和基数数据存储
const data = ref([]);

// 获取计件类型和工资基数信息
const fetchTypeData = async () => {
  const res = await listTimeManageByPageUsingPost(searchParam.value);
  if (res.data.code == 0) {
    const typeData = res.data.data?.records;
    data.value = typeData.map((item) => ({
      label: item.timeType,
      value: item.salaryMath,
    }));
  } else {
    message.error("获取计件类型数据失败" + res.data.message);
  }
};
// 组件加载时调用请求
onMounted(() => {
  fetchTypeData();
});

// 提交更新表单
const handleUpdate = async () => {
  if (!currentEmployeeId.value) {
    return;
  }
  const res = await updateEmployeeUsingPost({
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
const searchParams = ref<API.EmployeeQueryRequest>({
  ...initSearchParams,
});

// 定义两个变量来存储搜索出来的数据
const dataList = ref<API.Employee[]>([]);
const total = ref<number>(0);

/**
 * 加载数据
 */
const loadData = async () => {
  const res = await listEmployeeByPageUsingPost(searchParams.value);
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
const doDelete = async (record: API.Employee) => {
  if (!record.id) {
    return;
  }
  const res = await deleteEmployeeUsingPost({
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
    title: "员工编号",
    dataIndex: "employeeNo",
  },
  {
    title: "姓名",
    dataIndex: "employeeName",
  },
  {
    title: "计件类型",
    dataIndex: "timeType",
  },
  {
    title: "计件基数",
    dataIndex: "salaryMath",
  },
  {
    title: "创建时间",
    dataIndex: "createTime",
    slotName: "createTime",
  },
  {
    title: "更新时间",
    dataIndex: "updateTime",
    slotName: "updateTime",
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
