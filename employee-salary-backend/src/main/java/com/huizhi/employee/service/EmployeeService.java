package com.huizhi.employee.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huizhi.employee.model.dto.employee.EmployeeQueryRequest;
import com.huizhi.employee.model.entity.Employee;
import com.huizhi.employee.model.vo.EmployeeVO;

import javax.servlet.http.HttpServletRequest;

/**
 * 员工表服务
 *
 * @author   小赵学Java
 *
 */
public interface EmployeeService extends IService<Employee> {

    /**
     * 校验数据
     *
     * @param employee
     * @param add 对创建的数据进行校验
     */
    void validEmployee(Employee employee, boolean add);

    /**
     * 获取查询条件
     *
     * @param employeeQueryRequest
     * @return
     */
    QueryWrapper<Employee> getQueryWrapper(EmployeeQueryRequest employeeQueryRequest);
    
    /**
     * 获取员工表封装
     *
     * @param employee
     * @param request
     * @return
     */
    EmployeeVO getEmployeeVO(Employee employee, HttpServletRequest request);

    /**
     * 分页获取员工表封装
     *
     * @param employeePage
     * @param request
     * @return
     */
    Page<EmployeeVO> getEmployeeVOPage(Page<Employee> employeePage, HttpServletRequest request);
}
