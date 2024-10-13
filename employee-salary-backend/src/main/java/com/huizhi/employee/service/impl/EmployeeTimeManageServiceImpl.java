package com.huizhi.employee.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huizhi.employee.common.ErrorCode;
import com.huizhi.employee.constant.CommonConstant;
import com.huizhi.employee.exception.ThrowUtils;
import com.huizhi.employee.mapper.EmployeeMapper;
import com.huizhi.employee.mapper.EmployeeTimeManageMapper;
import com.huizhi.employee.mapper.TimeManageMapper;
import com.huizhi.employee.model.dto.employee.EmployeeTimeManageQueryRequest;
import com.huizhi.employee.model.entity.Employee;
import com.huizhi.employee.model.entity.EmployeeTimeManage;
import com.huizhi.employee.model.entity.TimeManage;
import com.huizhi.employee.model.vo.EmployeeTimeManageVO;
import com.huizhi.employee.service.EmployeeTimeManageService;
import com.huizhi.employee.service.UserService;
import com.huizhi.employee.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 员工工时表服务实现
 *
 * @author   小赵学Java
 *
 */
@Service
@Slf4j
public class EmployeeTimeManageServiceImpl extends ServiceImpl<EmployeeTimeManageMapper, EmployeeTimeManage> implements EmployeeTimeManageService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private TimeManageMapper timeManageMapper;

    /**
     * 校验数据
     *
     * @param employeeTimeManage
     * @param add      对创建的数据进行校验
     */
    @Override
    public void validEmployeeTimeManage(EmployeeTimeManage employeeTimeManage, boolean add) {
        ThrowUtils.throwIf(employeeTimeManage == null, ErrorCode.PARAMS_ERROR);
        // 从对象中取值
        String employeeName = employeeTimeManage.getEmployeeName();
        String employeeNo = employeeTimeManage.getEmployeeNo();
        String timeType = employeeTimeManage.getTimeType();
        BigDecimal salaryMath = employeeTimeManage.getSalaryMath();
        // 创建数据时，参数不能为空
        if (add) {
            // 补充校验规则
            ThrowUtils.throwIf(StringUtils.isBlank(timeType), ErrorCode.PARAMS_ERROR, "计时类型名称不能为空");
            ThrowUtils.throwIf(StringUtils.isBlank(employeeName), ErrorCode.PARAMS_ERROR, "员工姓名不能为空");
            ThrowUtils.throwIf(StringUtils.isBlank(employeeNo), ErrorCode.PARAMS_ERROR, "员工编号不能为空");
            ThrowUtils.throwIf(ObjectUtils.isEmpty(salaryMath), ErrorCode.PARAMS_ERROR, "计时工资基数不能为空");
        }

        // 1. 校验员工是否存在
        Employee employee = employeeMapper.selectOne(new LambdaQueryWrapper<Employee>()
                .eq(Employee::getEmployeeNo, employeeNo));
        ThrowUtils.throwIf(employee == null, ErrorCode.NOT_FOUND_ERROR, "员工编号不存在");

        // 2. 校验工资基数是否一致，且状态为可用
        TimeManage timeManage = timeManageMapper.selectOne(new LambdaQueryWrapper<TimeManage>()
                .eq(TimeManage::getTimeType, timeType)
                .eq(TimeManage::getSalaryMath, salaryMath)
                .eq(TimeManage::getRunStatus, 0));  // 0表示已启用
        ThrowUtils.throwIf(timeManage == null, ErrorCode.NOT_FOUND_ERROR, "计件类型名称与计件基数不匹配或状态已禁用");

    }

    /**
     * 获取查询条件
     *
     * @param employeeTimeManageQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<EmployeeTimeManage> getQueryWrapper(EmployeeTimeManageQueryRequest employeeTimeManageQueryRequest) {
        QueryWrapper<EmployeeTimeManage> queryWrapper = new QueryWrapper<>();
        if (employeeTimeManageQueryRequest == null) {
            return queryWrapper;
        }
        // 从对象中取值
        Long id = employeeTimeManageQueryRequest.getId();
        String employeeNo = employeeTimeManageQueryRequest.getEmployeeNo();
        String employeeName = employeeTimeManageQueryRequest.getEmployeeName();
        String timeType = employeeTimeManageQueryRequest.getTimeType();
        BigDecimal workTime = employeeTimeManageQueryRequest.getWorkTime();
        String sortField = employeeTimeManageQueryRequest.getSortField();
        String sortOrder = employeeTimeManageQueryRequest.getSortOrder();
        // 补充需要的查询条件
        // 模糊查询
        queryWrapper.like(StringUtils.isNotBlank(employeeName), "employeeName", employeeName);
        queryWrapper.like(StringUtils.isNotBlank(timeType), "timeType", timeType);
        // 精确查询
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjectUtils.isNotEmpty(workTime), "workTime", workTime);
        queryWrapper.eq(ObjectUtils.isNotEmpty(employeeNo), "employeeNo", employeeNo);
        // 排序规则
        queryWrapper.orderBy(SqlUtils.validSortField(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    /**
     * 获取员工工时表封装
     *
     * @param employeeTimeManage
     * @param request
     * @return
     */
    @Override
    public EmployeeTimeManageVO getEmployeeTimeManageVO(EmployeeTimeManage employeeTimeManage, HttpServletRequest request) {
        // 对象转封装类
        EmployeeTimeManageVO employeeTimeManageVO = EmployeeTimeManageVO.objToVo(employeeTimeManage);
        return employeeTimeManageVO;
    }

    /**
     * 分页获取员工工时表封装
     *
     * @param employeeTimeManagePage
     * @param request
     * @return
     */
    @Override
    public Page<EmployeeTimeManageVO> getEmployeeTimeManageVOPage(Page<EmployeeTimeManage> employeeTimeManagePage, HttpServletRequest request) {
        List<EmployeeTimeManage> employeeTimeManageList = employeeTimeManagePage.getRecords();
        Page<EmployeeTimeManageVO> employeeTimeManageVOPage = new Page<>(employeeTimeManagePage.getCurrent(), employeeTimeManagePage.getSize(), employeeTimeManagePage.getTotal());
        if (CollUtil.isEmpty(employeeTimeManageList)) {
            return employeeTimeManageVOPage;
        }
        // 对象列表 => 封装对象列表
        List<EmployeeTimeManageVO> employeeTimeManageVOList = employeeTimeManageList.stream().map(employeeTimeManage -> {
            return EmployeeTimeManageVO.objToVo(employeeTimeManage);
        }).collect(Collectors.toList());
        employeeTimeManageVOPage.setRecords(employeeTimeManageVOList);
        return employeeTimeManageVOPage;
    }

}
