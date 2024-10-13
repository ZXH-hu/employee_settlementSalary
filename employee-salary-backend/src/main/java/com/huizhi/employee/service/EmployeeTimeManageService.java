package com.huizhi.employee.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huizhi.employee.model.dto.employee.EmployeeTimeManageQueryRequest;
import com.huizhi.employee.model.entity.EmployeeTimeManage;
import com.huizhi.employee.model.vo.EmployeeTimeManageVO;

import javax.servlet.http.HttpServletRequest;

/**
 * 员工工时表服务
 *
 * @author   小赵学Java
 *
 */
public interface EmployeeTimeManageService extends IService<EmployeeTimeManage> {

    /**
     * 校验数据
     *
     * @param employeeTimeManage
     * @param add 对创建的数据进行校验
     */
    void validEmployeeTimeManage(EmployeeTimeManage employeeTimeManage, boolean add);

    /**
     * 获取查询条件
     *
     * @param employeeTimeManageQueryRequest
     * @return
     */
    QueryWrapper<EmployeeTimeManage> getQueryWrapper(EmployeeTimeManageQueryRequest employeeTimeManageQueryRequest);
    
    /**
     * 获取员工工时表封装
     *
     * @param employeeTimeManage
     * @param request
     * @return
     */
    EmployeeTimeManageVO getEmployeeTimeManageVO(EmployeeTimeManage employeeTimeManage, HttpServletRequest request);

    /**
     * 分页获取员工工时表封装
     *
     * @param employeeTimeManagePage
     * @param request
     * @return
     */
    Page<EmployeeTimeManageVO> getEmployeeTimeManageVOPage(Page<EmployeeTimeManage> employeeTimeManagePage, HttpServletRequest request);
}
