// @ts-ignore
/* eslint-disable */
import request from '@/request';

/** 生成字符组合验证码 GET /api/capt/captcha */
export async function getCaptchaUsingGet(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getCaptchaUsingGETParams,
  options?: { [key: string]: any },
) {
  return request<any>('/api/capt/captcha', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 生成算术验证码 GET /api/capt/math_captcha */
export async function getMathCaptchaUsingGet(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getMathCaptchaUsingGETParams,
  options?: { [key: string]: any },
) {
  return request<any>('/api/capt/math_captcha', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}
