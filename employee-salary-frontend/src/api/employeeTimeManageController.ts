// @ts-ignore
/* eslint-disable */
import request from '@/request';

/** addEmployeeTimeManage POST /api/employeeTimeManage/add */
export async function addEmployeeTimeManageUsingPost(
  body: API.EmployeeTimeManageAddRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseLong_>('/api/employeeTimeManage/add', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** deleteEmployeeTimeManage POST /api/employeeTimeManage/delete */
export async function deleteEmployeeTimeManageUsingPost(
  body: API.DeleteRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseBoolean_>('/api/employeeTimeManage/delete', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** getEmployeeTimeManageVOById GET /api/employeeTimeManage/get/vo */
export async function getEmployeeTimeManageVoByIdUsingGet(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getEmployeeTimeManageVOByIdUsingGETParams,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseEmployeeTimeManageVO_>('/api/employeeTimeManage/get/vo', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** listEmployeeTimeManageByPage POST /api/employeeTimeManage/list/page */
export async function listEmployeeTimeManageByPageUsingPost(
  body: API.EmployeeTimeManageQueryRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponsePageEmployeeTimeManage_>('/api/employeeTimeManage/list/page', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** listEmployeeTimeManageVOByPage POST /api/employeeTimeManage/list/page/vo */
export async function listEmployeeTimeManageVoByPageUsingPost(
  body: API.EmployeeTimeManageQueryRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponsePageEmployeeTimeManageVO_>(
    '/api/employeeTimeManage/list/page/vo',
    {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      data: body,
      ...(options || {}),
    },
  );
}

/** updateEmployeeTimeManage POST /api/employeeTimeManage/update */
export async function updateEmployeeTimeManageUsingPost(
  body: API.EmployeeTimeManageUpdateRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseBoolean_>('/api/employeeTimeManage/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
