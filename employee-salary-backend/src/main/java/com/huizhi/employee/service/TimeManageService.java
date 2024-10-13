package com.huizhi.employee.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huizhi.employee.model.dto.timemanage.TimeManageQueryRequest;
import com.huizhi.employee.model.entity.TimeManage;
import com.huizhi.employee.model.vo.TimeManageVO;

import javax.servlet.http.HttpServletRequest;

/**
 * 计时管理表服务
 *
 * @author   小赵学Java
 *
 */
public interface TimeManageService extends IService<TimeManage> {

    /**
     * 校验数据
     *
     * @param timeManage
     * @param add 对创建的数据进行校验
     */
    void validTimeManage(TimeManage timeManage, boolean add);

    /**
     * 获取查询条件
     *
     * @param timeManageQueryRequest
     * @return
     */
    QueryWrapper<TimeManage> getQueryWrapper(TimeManageQueryRequest timeManageQueryRequest);
    
    /**
     * 获取计时管理表封装
     *
     * @param timeManage
     * @param request
     * @return
     */
    TimeManageVO getTimeManageVO(TimeManage timeManage, HttpServletRequest request);

    /**
     * 分页获取计时管理表封装
     *
     * @param timeManagePage
     * @param request
     * @return
     */
    Page<TimeManageVO> getTimeManageVOPage(Page<TimeManage> timeManagePage, HttpServletRequest request);
}
