import { RouteRecordRaw } from "vue-router";
import HomeView from "@/views/HomeViewPage.vue";
import UserLayout from "@/layouts/UserLayout.vue";
import UserLoginPage from "@/views/user/UserLoginPage.vue";
import UserRegisterPage from "@/views/user/UserRegisterPage.vue";
import AdminUserPage from "@/views/admin/AdminWorkTimePage.vue";
import ACCESS_ENUM from "@/access/accessEnum";
import UserHomInfo from "@/views/user/UserHomInfo.vue";
import EditMyInfoPage from "@/views/user/EditMyInfoPage.vue";
import UserEditPassPage from "@/views/user/UserEditPassPage.vue";
import AdminEmployeePage from "@/views/admin/AdminTimeTypePage.vue";
import AdminEmployeeInfoPage from "@/views/admin/AdminEmployeeInfoPage.vue";
import AdminTimeTypePage from "@/views/admin/AdminTimeTypePage.vue";
import AdminWorkTimePage from "@/views/admin/AdminWorkTimePage.vue";
import AdminSettlementMoneyPage from "@/views/admin/AdminSettlementMoneyPage.vue";

export const routes: Array<RouteRecordRaw> = [
  {
    path: "/employee_info",
    name: "员工信息",
    component: AdminEmployeeInfoPage,
    meta: {
      access: ACCESS_ENUM.ADMIN,
    },
  },
  {
    path: "/admin/work_type",
    name: "计件类型",
    component: AdminTimeTypePage,
    meta: {
      access: ACCESS_ENUM.ADMIN,
    },
  },
  {
    path: "/admin/employee/work_time_manage",
    name: "员工工时管理",
    component: AdminWorkTimePage,
    meta: {
      access: ACCESS_ENUM.ADMIN,
    },
  },
  {
    path: "/admin/employee/work_money",
    name: "员工工资管理",
    component: AdminSettlementMoneyPage,
    meta: {
      access: ACCESS_ENUM.ADMIN,
    },
  },
  {
    path: "/hide",
    name: "隐藏页面",
    component: HomeView,
    meta: {
      hideInMenu: true,
    },
  },
  {
    path: "/user",
    name: "用户",
    component: UserLayout,
    children: [
      {
        path: "/user/login",
        name: "用户登录",
        component: UserLoginPage,
      },
      {
        path: "/user/register",
        name: "用户注册",
        component: UserRegisterPage,
      },
      {
        path: "/user/edit_password",
        name: "修改密码",
        component: UserEditPassPage,
      },
      {
        path: "/user/myinfo/:id",
        name: "个人中心",
        props: true,
        component: UserHomInfo,
      },
      {
        path: "/user/editinfo/:id",
        name: "修改信息",
        props: true,
        component: EditMyInfoPage,
      },
    ],
    meta: {
      hideInMenu: true,
    },
  },
];
