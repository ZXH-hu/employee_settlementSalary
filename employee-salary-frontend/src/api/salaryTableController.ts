// @ts-ignore
/* eslint-disable */
import request from '@/request';

/** addSalaryTable POST /api/salaryTable/add */
export async function addSalaryTableUsingPost(
  body: API.SalaryTableAddRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseLong_>('/api/salaryTable/add', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** deleteSalaryTable POST /api/salaryTable/delete */
export async function deleteSalaryTableUsingPost(
  body: API.DeleteRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseBoolean_>('/api/salaryTable/delete', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** getSalaryTableVOById GET /api/salaryTable/get/vo */
export async function getSalaryTableVoByIdUsingGet(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getSalaryTableVOByIdUsingGETParams,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseSalaryTableVO_>('/api/salaryTable/get/vo', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** listSalaryTableByPage POST /api/salaryTable/list/page */
export async function listSalaryTableByPageUsingPost(
  body: API.SalaryTableQueryRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponsePageSalaryTable_>('/api/salaryTable/list/page', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** listSalaryTableVOByPage POST /api/salaryTable/list/page/vo */
export async function listSalaryTableVoByPageUsingPost(
  body: API.SalaryTableQueryRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponsePageSalaryTableVO_>('/api/salaryTable/list/page/vo', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** updateSalaryTable POST /api/salaryTable/update */
export async function updateSalaryTableUsingPost(
  body: API.SalaryTableUpdateRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseBoolean_>('/api/salaryTable/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
