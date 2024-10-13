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
        新增员工工时
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
        <a-button status="warning" @click="openUpdateDrawer2(record)">工资结算</a-button>
      </a-space>
      <a-space>
        <a-button status="danger" @click="doDelete(record)">删除</a-button>
      </a-space>
    </template>
  </a-table>

  <!-- 结算工资弹出框 -->
  <a-modal
    v-model:visible="visible2"
    title="计件工资核算"
    ok-text="提交"
    cancel-text="取消"
    @ok="handleSubmit2"
    @cancel="closeModal2"
  >
    <a-form :model="form1" :rules="rules" ref="formRef">
      <a-form-item label="计件月份" field="settlementMonth">
        <!-- 使用 a-month-picker 组件来选择月份 -->
        <a-month-picker
          v-model="form1.settlementMonth"
          style="width: 200px;"
          placeholder="请选择月份"
          allow-clear
        />
      </a-form-item>
    </a-form>
  </a-modal>

  <!-- 弹出框 -->
  <a-modal
    v-model:visible="visible"
    title="新增员工工时"
    ok-text="提交"
    cancel-text="取消"
    @ok="handleSubmit"
    @cancel="closeModal"
  >
    <a-form :model="form" :rules="rules" ref="formRef">
      <a-form-item label="员工编号" field="employeeNo">
        <a-select
          v-model="form.employeeNo"
          :style="{ width: '220px' }"
          placeholder="请选择员工编号"
          allow-clear
        >
          <a-option
            v-for="item in emData"
            :key="item.value"
            :value="item.label"
          >
            {{ item.label }}
          </a-option>
        </a-select>
      </a-form-item>
      <a-form-item label="员工姓名" field="employeeName">
        <a-select
          v-model="form.employeeName"
          :style="{ width: '220px' }"
          placeholder="请选择员工姓名"
          allow-clear
        >
          <a-option
            v-for="item in emData"
            :key="item.value"
            :value="item.value"
          >
            {{ item.value }}
          </a-option>
        </a-select>
      </a-form-item>
      <a-form-item label="计件类型" field="timeType">
        <a-select
          v-model="form.timeType"
          :style="{ width: '220px' }"
          placeholder="请选择计件类型"
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
          placeholder="请选择计件基数"
          allow-clear
        >
          <a-option v-for="item in data" :key="item.value" :value="item.value">
            {{ item.value }}
          </a-option>
        </a-select>
      </a-form-item>
      <a-form-item label="工时" field="workTime">
        <a-input-number
          v-model="form.workTime"
          :style="{ width: '220px' }"
          placeholder="请输入员工工时"
        />
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
      <a-form-item label="员工编号" field="employeeNo">
        <a-input v-model="form.employeeNo" placeholder="请输入员工编号" />
      </a-form-item>
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
      <a-form-item label="工时" field="workTime">
        <a-input-number v-model="form.workTime" placeholder="请输入员工工时" />
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
import { onMounted, reactive, ref, watchEffect } from "vue";
import API from "@/api";
import message from "@arco-design/web-vue/es/message";
import dayjs from "dayjs";
import { listEmployeeByPageUsingPost } from "@/api/employeeController";
import {
  addEmployeeTimeManageUsingPost,
  deleteEmployeeTimeManageUsingPost,
  listEmployeeTimeManageByPageUsingPost,
  updateEmployeeTimeManageUsingPost,
} from "@/api/employeeTimeManageController";
import { listTimeManageByPageUsingPost } from "@/api/timeManageController";
import { addSalaryTableUsingPost } from "@/api/salaryTableController";

const formSearchParams = ref<API.EmployeeTimeManage>({});
const visible = ref(false);
const visible2 = ref(false);
// 控制抽屉的显示和隐藏
const updateDrawerVisible = ref(false);
const currentEmployeeId = ref(null);

// 填充更新选中员工信息
const openUpdateDrawer = (record) => {
  currentEmployeeId.value = record.id; // 存储当前员工ID
  form.employeeName = record.employeeName;
  form.employeeNo = record.employeeNo;
  form.timeType = record.timeType;
  form.salaryMath = record.salaryMath;
  form.workTime = record.workTime;
  updateDrawerVisible.value = true;
};

// 填充当前结算工资的员工信息
const openUpdateDrawer2 = (record) => {
  form1.employeeName = record.employeeName;
  form1.employeeNo = record.employeeNo;
  form1.salaryMath = record.salaryMath;
  visible2.value = true;
};
const closeUpdateDrawer = () => {
  updateDrawerVisible.value = false;
};

// 表单数据
const form = reactive({
  employeeName: "",
  employeeNo: "",
  salaryMath: null,
  timeType: "",
  workTime: null,
} as API.EmployeeTimeManageAddRequest);

// 结算工资表单数据
const form1 = reactive({
  employeeName: "",
  employeeNo: "",
  salaryMath: null,
  settlementMonth: "",
} as API.SalaryTableAddRequest);

const rules = ref({
  employeeName: [
    { required: true, message: "请输入员工姓名", trigger: "blur" },
  ],
  timeType: [{ required: true, message: "请输入计时类型", trigger: "blur" }],
  salaryMath: [{ required: true, message: "请输入工资基数", type: "number" }],
  employeeNo: [{ required: true, message: "请选择员工编号", type: "number" }],
  settlementMonth: [{ required: true, message: "请选择核算月份", trigger: "blur" }],
});

// 打开弹出框
const openModal = () => {
  visible.value = true;
};

// 关闭弹出框
const closeModal = () => {
  visible.value = false;
};

// 关闭工资结算弹出框
const closeModal2 = () => {
  visible2.value = false;
};

// 定义搜索词（查询计件类型名称和基数）
const initSearchParam = {
  current: 1,
  pageSize: 10,
};
const searchParam = ref<API.TimeManageQueryRequest>({
  ...initSearchParam,
});
const searchParam2 = ref<API.EmployeeQueryRequest>({
  ...initSearchParam,
});

// 计件类型和基数数据存储
const data = ref([]);

// 员工编号和姓名数据存储
const emData = ref([]);

// 获取计件类型和工资基数信息
const fetchTypeData = async () => {
  const res = await listTimeManageByPageUsingPost(searchParam2.value);
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

// 获取员工编号和姓名信息
const fetchTypeData2 = async () => {
  const res = await listEmployeeByPageUsingPost(searchParam.value);
  if (res.data.code == 0) {
    const typeData = res.data.data?.records;
    emData.value = typeData.map((item) => ({
      label: item.employeeNo,
      value: item.employeeName,
    }));
  } else {
    message.error("获取员工编号和姓名失败" + res.data.message);
  }
};

// 组件加载时调用请求
onMounted(() => {
  fetchTypeData();
  fetchTypeData2();
});

// 提交新增表单
const handleSubmit = async () => {
  const res = await addEmployeeTimeManageUsingPost({
    ...form,
  });
  if (res.data.code === 0) {
    message.success("新增成功");
    form.employeeName = "";
    form.employeeNo = "";
    form.workTime = null;
    form.timeType = "";
    form.salaryMath = null;
    closeModal();
    loadData();
  } else {
    message.error("新增失败，" + res.data.message);
  }
};

// 提交核算工资表单
const handleSubmit2 = async () => {
  const res = await addSalaryTableUsingPost({
    ...form1,
  });
  if (res.data.code === 0) {
    message.success("已结算，请到工资管理模块查看！");
    form1.settlementMonth = "";
    closeModal();
  } else {
    message.error("结算失败，" + res.data.message);
  }
};

// 提交更新表单
const handleUpdate = async () => {
  if (!currentEmployeeId.value) {
    return;
  }
  const res = await updateEmployeeTimeManageUsingPost({
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
const searchParams = ref<API.EmployeeTimeManageQueryRequest>({
  ...initSearchParams,
});

// 定义两个变量来存储搜索出来的数据
const dataList = ref<API.EmployeeTimeManage[]>([]);
const total = ref<number>(0);

/**
 * 加载数据
 */
const loadData = async () => {
  const res = await listEmployeeTimeManageByPageUsingPost(searchParams.value);
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
const doDelete = async (record: API.EmployeeTimeManage) => {
  if (!record.id) {
    return;
  }
  const res = await deleteEmployeeTimeManageUsingPost({
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
    title: "员工计件基数",
    dataIndex: "salaryMath",
  },
  {
    title: "员工工时",
    dataIndex: "workTime",
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
