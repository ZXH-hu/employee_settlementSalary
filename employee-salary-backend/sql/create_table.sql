# 数据库初始化
# @author   小赵学Java
#

-- 创建库
create database if not exists employee_salary;

-- 切换库
use employee_salary;

-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    userName     varchar(256)                           null comment '用户昵称',
    userAvatar   varchar(1024)                          null comment '用户头像',
    userProfile  varchar(512)                           null comment '用户简介',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/admin/ban',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除'
) comment '用户' collate = utf8mb4_unicode_ci;

-- 员工信息表
create table if not exists employee
(
    id           bigint auto_increment comment 'id' primary key,
    employeeNo   varchar(256)                           not null comment '员工编号',
    employeeName   varchar(256)                           null comment '员工姓名',
    timeType     varchar(256)                           not null comment '计时类型名称',
    salaryMath  DECIMAL(3, 2)                           not null comment '计时工资基数',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除'
) comment '员工表' collate = utf8mb4_unicode_ci;

create table if not exists time_manage
(
    id           bigint auto_increment comment 'id' primary key,
    timeType     varchar(256)                           not null comment '计时类型名称',
    salaryMath  tinyint                           not null comment '计时工资基数',
    runStatus   int                           not null comment '状态：0-已启用, 1-已停用',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除'
) comment '计时管理表' collate = utf8mb4_unicode_ci;

create table if not exists employee_time_manage
(
    id           bigint auto_increment comment 'id' primary key,
    employeeNo   varchar(256)                           not null comment '员工编号',
    employeeName   varchar(256)                           null comment '员工姓名',
    timeType     varchar(256)                           not null comment '计时类型名称',
    salaryMath  DECIMAL(3, 2)                           not null comment '计时工资基数',
    workTime   DECIMAL(5, 2)      null comment '员工工时',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除'
) comment '员工工时表' collate = utf8mb4_unicode_ci;

create table if not exists salary_table
(
    id           bigint auto_increment comment 'id' primary key,
    employeeNo   varchar(256)                           not null comment '员工编号',
    employeeName   varchar(256)                           null comment '员工姓名',
    salaryMath  DECIMAL(3, 2)                           not null comment '计时工资基数',
    workTime   DECIMAL(5, 2)      null comment '员工总工时',
    workMoney DECIMAL(10, 2) null comment '员工工时工资',
    SettlementMonth datetime      not null comment '结算月份',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除'
) comment '工资结算表' collate = utf8mb4_unicode_ci;

