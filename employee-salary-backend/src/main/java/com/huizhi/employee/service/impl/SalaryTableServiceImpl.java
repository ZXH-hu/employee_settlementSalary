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
import com.huizhi.employee.mapper.SalaryTableMapper;
import com.huizhi.employee.mapper.TimeManageMapper;
import com.huizhi.employee.model.dto.salarytable.SalaryTableQueryRequest;
import com.huizhi.employee.model.entity.Employee;
import com.huizhi.employee.model.entity.EmployeeTimeManage;
import com.huizhi.employee.model.entity.SalaryTable;
import com.huizhi.employee.model.entity.TimeManage;
import com.huizhi.employee.model.vo.SalaryTableVO;
import com.huizhi.employee.service.SalaryTableService;
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
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 工资结算表服务实现
 *
 * @author   小赵学Java
 *
 */
@Service
@Slf4j
public class SalaryTableServiceImpl extends ServiceImpl<SalaryTableMapper, SalaryTable> implements SalaryTableService {


    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private TimeManageMapper timeManageMapper;

    @Autowired
    private EmployeeTimeManageMapper employeeTimeManageMapper;

    /**
     * 校验数据
     *
     * @param salaryTable
     * @param add      对创建的数据进行校验
     */
    @Override
    public void validSalaryTable(SalaryTable salaryTable, boolean add) {
        ThrowUtils.throwIf(salaryTable == null, ErrorCode.PARAMS_ERROR);
        // 从对象中取值
        String employeeName = salaryTable.getEmployeeName();
        String employeeNo = salaryTable.getEmployeeNo();
        BigDecimal salaryMath = salaryTable.getSalaryMath();
        String settlementMonth = salaryTable.getSettlementMonth();
        // 创建数据时，参数不能为空
        if (add) {
            // 补充校验规则
            ThrowUtils.throwIf(StringUtils.isBlank(employeeName), ErrorCode.PARAMS_ERROR, "员工姓名不能为空");
            ThrowUtils.throwIf(StringUtils.isBlank(employeeNo), ErrorCode.PARAMS_ERROR, "员工编号不能为空");
            ThrowUtils.throwIf(ObjectUtils.isEmpty(salaryMath), ErrorCode.PARAMS_ERROR, "计时工资基数不能为空");
            ThrowUtils.throwIf(StringUtils.isBlank(settlementMonth), ErrorCode.PARAMS_ERROR, "核算月份不能为空");
        }

        // 1. 校验员工是否存在
        Employee employee = employeeMapper.selectOne(new LambdaQueryWrapper<Employee>()
                .eq(Employee::getEmployeeNo, employeeNo));
        ThrowUtils.throwIf(employee == null, ErrorCode.NOT_FOUND_ERROR, "该员工编号不存在");

        // 2. 校验工资基数是否一致，且状态为可用
        TimeManage timeManage = timeManageMapper.selectOne(new LambdaQueryWrapper<TimeManage>()
                .eq(TimeManage::getSalaryMath, salaryMath));
        ThrowUtils.throwIf(timeManage == null, ErrorCode.NOT_FOUND_ERROR, "计件基数不匹配");

        /*// 3.校验核算月份是否存在
        EmployeeTimeManage settlementMonthRes = employeeTimeManageMapper.selectOne(new LambdaQueryWrapper<EmployeeTimeManage>()
                .like(EmployeeTimeManage::getCreateTime, settlementMonth + "-"));
        ThrowUtils.throwIf(settlementMonthRes == null, ErrorCode.NOT_FOUND_ERROR, "该月份可能不存在");*/
    }

    /**
     * 获取查询条件
     *
     * @param salaryTableQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<SalaryTable> getQueryWrapper(SalaryTableQueryRequest salaryTableQueryRequest) {
        QueryWrapper<SalaryTable> queryWrapper = new QueryWrapper<>();
        if (salaryTableQueryRequest == null) {
            return queryWrapper;
        }
        // 从对象中取值
        Long id = salaryTableQueryRequest.getId();
        String employeeNo = salaryTableQueryRequest.getEmployeeNo();
        String employeeName = salaryTableQueryRequest.getEmployeeName();
        BigDecimal workTime = salaryTableQueryRequest.getWorkTime();
        BigDecimal salaryMath = salaryTableQueryRequest.getSalaryMath();
        String settlementMonth = salaryTableQueryRequest.getSettlementMonth();
        String sortField = salaryTableQueryRequest.getSortField();
        String sortOrder = salaryTableQueryRequest.getSortOrder();
        // 补充需要的查询条件
        // 模糊查询
        queryWrapper.like(StringUtils.isNotBlank(employeeName), "employeeName", employeeName);
        queryWrapper.like(ObjectUtils.isNotEmpty(workTime), "workTime", workTime);
        queryWrapper.like(ObjectUtils.isNotEmpty(settlementMonth), "settlementMonth", settlementMonth);
        // 精确查询
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjectUtils.isNotEmpty(employeeNo), "employeeNo", employeeNo);
        queryWrapper.eq(ObjectUtils.isNotEmpty(salaryMath), "salaryMath", salaryMath);
        // 排序规则
        queryWrapper.orderBy(SqlUtils.validSortField(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    /**
     * 获取工资结算表封装
     *
     * @param salaryTable
     * @param request
     * @return
     */
    @Override
    public SalaryTableVO getSalaryTableVO(SalaryTable salaryTable, HttpServletRequest request) {
        // 对象转封装类
        SalaryTableVO salaryTableVO = SalaryTableVO.objToVo(salaryTable);
        return salaryTableVO;
    }

    /**
     * 分页获取工资结算表封装
     *
     * @param salaryTablePage
     * @param request
     * @return
     */
    @Override
    public Page<SalaryTableVO> getSalaryTableVOPage(Page<SalaryTable> salaryTablePage, HttpServletRequest request) {
        List<SalaryTable> salaryTableList = salaryTablePage.getRecords();
        Page<SalaryTableVO> salaryTableVOPage = new Page<>(salaryTablePage.getCurrent(), salaryTablePage.getSize(), salaryTablePage.getTotal());
        if (CollUtil.isEmpty(salaryTableList)) {
            return salaryTableVOPage;
        }
        // 对象列表 => 封装对象列表
        List<SalaryTableVO> salaryTableVOList = salaryTableList.stream().map(salaryTable -> {
            return SalaryTableVO.objToVo(salaryTable);
        }).collect(Collectors.toList());
        salaryTableVOPage.setRecords(salaryTableVOList);
        return salaryTableVOPage;
    }

    // 结算当月工资
    @Override
    public BigDecimal settlementSalary(SalaryTable salaryTable) {
        ThrowUtils.throwIf(salaryTable == null, ErrorCode.PARAMS_ERROR);
        String employeeNo = salaryTable.getEmployeeNo();
        BigDecimal salaryMath = salaryTable.getSalaryMath();
        String settlementMonth = salaryTable.getSettlementMonth();
        // 获取员工当月总工时
        BigDecimal totalWorkTime = employeeTimeManageMapper.getTotalWorkTimeForMonth(String.valueOf(settlementMonth), String.valueOf(employeeNo));
        if (totalWorkTime == null) {
            // 如果没有记录，则表示该月没有工时，返回 0
            return BigDecimal.ZERO;
        }
        // 根据 salaryMath 基数确定工价
        BigDecimal hourlyRate;
        switch (salaryMath.intValue()) {
            case 1:
                hourlyRate = BigDecimal.valueOf(10);
                break;
            case 2:
                hourlyRate = BigDecimal.valueOf(20);
                break;
            case 3:
                hourlyRate = BigDecimal.valueOf(30);
                break;
            default:
                throw new IllegalArgumentException("未知的工资基数: " + salaryMath);
        }
        // 计算总工资
        BigDecimal totalSalary = totalWorkTime.multiply(hourlyRate);
        return totalSalary;
    }

}
