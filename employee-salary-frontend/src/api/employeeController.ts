// @ts-ignore
/* eslint-disable */
import request from '@/request';

/** addEmployee POST /api/employee/add */
export async function addEmployeeUsingPost(
  body: API.EmployeeAddRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseLong_>('/api/employee/add', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** deleteEmployee POST /api/employee/delete */
export async function deleteEmployeeUsingPost(
  body: API.DeleteRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseBoolean_>('/api/employee/delete', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** getEmployeeVOById GET /api/employee/get/vo */
export async function getEmployeeVoByIdUsingGet(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getEmployeeVOByIdUsingGETParams,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseEmployeeVO_>('/api/employee/get/vo', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** listEmployeeByPage POST /api/employee/list/page */
export async function listEmployeeByPageUsingPost(
  body: API.EmployeeQueryRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponsePageEmployee_>('/api/employee/list/page', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** listEmployeeVOByPage POST /api/employee/list/page/vo */
export async function listEmployeeVoByPageUsingPost(
  body: API.EmployeeQueryRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponsePageEmployeeVO_>('/api/employee/list/page/vo', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** updateEmployee POST /api/employee/update */
export async function updateEmployeeUsingPost(
  body: API.EmployeeUpdateRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseBoolean_>('/api/employee/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
