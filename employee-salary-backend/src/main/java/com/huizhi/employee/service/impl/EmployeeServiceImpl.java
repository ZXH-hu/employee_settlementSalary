package com.huizhi.employee.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huizhi.employee.common.ErrorCode;
import com.huizhi.employee.constant.CommonConstant;
import com.huizhi.employee.exception.ThrowUtils;
import com.huizhi.employee.mapper.EmployeeMapper;
import com.huizhi.employee.model.dto.employee.EmployeeQueryRequest;
import com.huizhi.employee.model.entity.Employee;
import com.huizhi.employee.model.vo.EmployeeVO;
import com.huizhi.employee.service.EmployeeService;
import com.huizhi.employee.service.UserService;
import com.huizhi.employee.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 员工表服务实现
 *
 * @author   小赵学Java
 *
 */
@Service
@Slf4j
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Resource
    private UserService userService;

    /**
     * 校验数据
     *
     * @param employee
     * @param add      对创建的数据进行校验
     */
    @Override
    public void validEmployee(Employee employee, boolean add) {
        ThrowUtils.throwIf(employee == null, ErrorCode.PARAMS_ERROR);
        // 从对象中取值
        String employeeName = employee.getEmployeeName();
        String timeType = employee.getTimeType();
        BigDecimal salaryMath = employee.getSalaryMath();

        // 创建数据时，参数不能为空
        if (add) {
            // 补充校验规则
            ThrowUtils.throwIf(StringUtils.isBlank(employeeName), ErrorCode.PARAMS_ERROR, "员工姓名不能为空");
            ThrowUtils.throwIf(StringUtils.isBlank(timeType), ErrorCode.PARAMS_ERROR, "员工计时类型不能为空");
            ThrowUtils.throwIf(ObjectUtils.isEmpty(salaryMath), ErrorCode.PARAMS_ERROR, "计时工资基数不能为空");
        }
    }

    /**
     * 获取查询条件
     *
     * @param employeeQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<Employee> getQueryWrapper(EmployeeQueryRequest employeeQueryRequest) {
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        if (employeeQueryRequest == null) {
            return queryWrapper;
        }
        // 从对象中取值
        Long id = employeeQueryRequest.getId();
        String employeeNo = employeeQueryRequest.getEmployeeNo();
        String employeeName = employeeQueryRequest.getEmployeeName();
        String timeType = employeeQueryRequest.getTimeType();
        BigDecimal salaryMath = employeeQueryRequest.getSalaryMath();
        String sortField = employeeQueryRequest.getSortField();
        String sortOrder = employeeQueryRequest.getSortOrder();
        // 模糊查询
        queryWrapper.like(StringUtils.isNotBlank(employeeName), "employeeName", employeeName);
        queryWrapper.like(StringUtils.isNotBlank(timeType), "timeType", timeType);
        // 精确查询
        queryWrapper.eq(ObjectUtils.isNotEmpty(employeeNo), "employeeNo", employeeNo);
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjectUtils.isNotEmpty(salaryMath), "salaryMath", salaryMath);
        // 排序规则
        queryWrapper.orderBy(SqlUtils.validSortField(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    /**
     * 获取员工表封装
     *
     * @param employee
     * @param request
     * @return
     */
    @Override
    public EmployeeVO getEmployeeVO(Employee employee, HttpServletRequest request) {
        // 对象转封装类
        EmployeeVO employeeVO = EmployeeVO.objToVo(employee);
        return employeeVO;
    }

    /**
     * 分页获取员工表封装
     *
     * @param employeePage
     * @param request
     * @return
     */
    @Override
    public Page<EmployeeVO> getEmployeeVOPage(Page<Employee> employeePage, HttpServletRequest request) {
        List<Employee> employeeList = employeePage.getRecords();
        Page<EmployeeVO> employeeVOPage = new Page<>(employeePage.getCurrent(), employeePage.getSize(), employeePage.getTotal());
        if (CollUtil.isEmpty(employeeList)) {
            return employeeVOPage;
        }
        // 对象列表 => 封装对象列表
        List<EmployeeVO> employeeVOList = employeeList.stream().map(employee -> {
            return EmployeeVO.objToVo(employee);
        }).collect(Collectors.toList());

        employeeVOPage.setRecords(employeeVOList);
        return employeeVOPage;
    }

}
