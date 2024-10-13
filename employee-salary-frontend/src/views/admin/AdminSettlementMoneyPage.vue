<!--管理员管理用户页面-->
<template>
  <!-- 用户搜索 -->
  <a-form
    :model="formSearchParams"
    :style="{ marginBottom: '20px' }"
    layout="inline"
    @submit="doSearch"
  >
    <a-form-item field="employeeNo" label="员工编号">
      <a-input
        v-model="formSearchParams.employeeNo"
        placeholder="请输入员工编号"
        allow-clear
      />
    </a-form-item>
    <a-form-item field="employeeName" label="员工姓名">
      <a-input
        v-model="formSearchParams.employeeName"
        placeholder="请输入员工姓名"
        allow-clear
      />
    </a-form-item>
    <a-form-item>
      <a-button type="primary" html-type="submit" style="width: 100px">
        搜&nbsp;索
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
    <template #workMoney="{ record }">
      ￥{{ record.workMoney }}
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

  <!-- 更新员工信息的抽屉 -->
  <a-drawer
    v-model:visible="updateDrawerVisible"
    title="更新员工工资信息"
    width="600px"
    closable
    mask
    :footer="false"
    @close="closeUpdateDrawer"
  >
    <a-form :model="form" :rules="rules" ref="formRef">
      <a-form-item label="员工编号" field="employeeNo">
        <a-input v-model="form.employeeNo" placeholder="请输入员工编号" />
      </a-form-item>
      <a-form-item label="员工姓名" field="employeeName">
        <a-input-number
          v-model="form.employeeName"
          placeholder="请输入员工姓名"
        />
      </a-form-item>
      <a-form-item label="结算月份" field="settlementMonth">
        <a-input-number
          v-model="form.settlementMonth"
          placeholder="请输入结算月份"
        />
      </a-form-item>
      <a-form-item label="计件类型基数" field="salaryMath">
        <a-input-number
          v-model="form.salaryMath"
          placeholder="请输入计件类型基数"
        />
      </a-form-item>
      <a-form-item label="核算总工时" field="workTime">
        <a-input-number v-model="form.workTime" placeholder="请输入工时" />
      </a-form-item>
      <a-form-item label="核算工资金额" field="workMoney">
        <a-input-number v-model="form.workMoney" placeholder="请输入工资金额" />
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
import {
  deleteSalaryTableUsingPost,
  listSalaryTableByPageUsingPost,
  updateSalaryTableUsingPost,
} from "@/api/salaryTableController";

const formSearchParams = ref<API.SalaryTableQueryRequest>({});
// 控制抽屉的显示和隐藏
const updateDrawerVisible = ref(false);
const currentEmployeeId = ref(null);

// 填充选中员工信息
const openUpdateDrawer = (record) => {
  currentEmployeeId.value = record.id; // 存储当前员工ID
  form.salaryMath = record.salaryMath;
  form.employeeNo = record.employeeNo;
  form.employeeName = record.employeeName;
  form.workMoney = record.workMoney;
  form.workTime = record.workTime;
  form.settlementMonth = record.settlementMonth;
  updateDrawerVisible.value = true;
};
const closeUpdateDrawer = () => {
  updateDrawerVisible.value = false;
};

// 表单数据
const form = reactive({
  employeeName: "",
  employeeNo: "",
  salaryMath: null,
  settlementMonth: "",
  workMoney: null,
  workTime: null,
} as API.SalaryTableUpdateRequest);

const rules = ref({
  salaryMath: [
    { required: true, message: "请输入计件类型基数", type: "number" },
  ],
  employeeNo: [{ required: true, message: "请输入员工编号", trigger: "blur" }],
  settlementMonth: [
    { required: true, message: "请输入核算月份", trigger: "blur" },
  ],
  workMoney: [
    { required: true, message: "请输入核算工资金额", type: "number" },
  ],
});

// 提交更新表单
const handleUpdate = async () => {
  if (!currentEmployeeId.value) {
    return;
  }
  const res = await updateSalaryTableUsingPost({
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
const searchParams = ref<API.SalaryTableQueryRequest>({
  ...initSearchParams,
});

// 定义两个变量来存储搜索出来的数据
const dataList = ref<API.SalaryTable[]>([]);
const total = ref<number>(0);

/**
 * 加载数据
 */
const loadData = async () => {
  const res = await listSalaryTableByPageUsingPost(searchParams.value);
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
const doDelete = async (record: API.SalaryTable) => {
  if (!record.id) {
    return;
  }
  const res = await deleteSalaryTableUsingPost({
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
    title: "员工姓名",
    dataIndex: "employeeName",
  },
  {
    title: "结算月份",
    dataIndex: "settlementMonth",
  },
  {
    title: "计件工资基数",
    dataIndex: "salaryMath",
  },
  {
    title: "核算总工时",
    dataIndex: "workTime",
  },
  {
    title: "核算工资金额",
    dataIndex: "workMoney",
    slotName: "workMoney",
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
