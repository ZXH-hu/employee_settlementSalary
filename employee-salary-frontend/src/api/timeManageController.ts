// @ts-ignore
/* eslint-disable */
import request from '@/request';

/** addTimeManage POST /api/timeManage/add */
export async function addTimeManageUsingPost(
  body: API.TimeManageAddRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseLong_>('/api/timeManage/add', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** deleteTimeManage POST /api/timeManage/delete */
export async function deleteTimeManageUsingPost(
  body: API.DeleteRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseBoolean_>('/api/timeManage/delete', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** getTimeManageVOById GET /api/timeManage/get/vo */
export async function getTimeManageVoByIdUsingGet(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getTimeManageVOByIdUsingGETParams,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseTimeManageVO_>('/api/timeManage/get/vo', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** listTimeManageByPage POST /api/timeManage/list/page */
export async function listTimeManageByPageUsingPost(
  body: API.TimeManageQueryRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponsePageTimeManage_>('/api/timeManage/list/page', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** listTimeManageVOByPage POST /api/timeManage/list/page/vo */
export async function listTimeManageVoByPageUsingPost(
  body: API.TimeManageQueryRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponsePageTimeManageVO_>('/api/timeManage/list/page/vo', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** updateTimeManage POST /api/timeManage/update */
export async function updateTimeManageUsingPost(
  body: API.TimeManageUpdateRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseBoolean_>('/api/timeManage/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
