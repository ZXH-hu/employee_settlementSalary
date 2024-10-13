package com.huizhi.employee.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huizhi.employee.model.dto.salarytable.SalaryTableQueryRequest;
import com.huizhi.employee.model.entity.SalaryTable;
import com.huizhi.employee.model.vo.SalaryTableVO;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * 工资结算表服务
 *
 * @author   小赵学Java
 *
 */
public interface SalaryTableService extends IService<SalaryTable> {

    /**
     * 校验数据
     *
     * @param salaryTable
     * @param add 对创建的数据进行校验
     */
    void validSalaryTable(SalaryTable salaryTable, boolean add);

    /**
     * 获取查询条件
     *
     * @param salaryTableQueryRequest
     * @return
     */
    QueryWrapper<SalaryTable> getQueryWrapper(SalaryTableQueryRequest salaryTableQueryRequest);
    
    /**
     * 获取工资结算表封装
     *
     * @param salaryTable
     * @param request
     * @return
     */
    SalaryTableVO getSalaryTableVO(SalaryTable salaryTable, HttpServletRequest request);

    /**
     * 分页获取工资结算表封装
     *
     * @param salaryTablePage
     * @param request
     * @return
     */
    Page<SalaryTableVO> getSalaryTableVOPage(Page<SalaryTable> salaryTablePage, HttpServletRequest request);

    BigDecimal settlementSalary(SalaryTable salaryTable);
}
