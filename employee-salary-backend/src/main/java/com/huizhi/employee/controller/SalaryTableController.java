package com.huizhi.employee.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huizhi.employee.annotation.AuthCheck;
import com.huizhi.employee.common.BaseResponse;
import com.huizhi.employee.common.DeleteRequest;
import com.huizhi.employee.common.ErrorCode;
import com.huizhi.employee.common.ResultUtils;
import com.huizhi.employee.constant.UserConstant;
import com.huizhi.employee.exception.BusinessException;
import com.huizhi.employee.exception.ThrowUtils;
import com.huizhi.employee.mapper.EmployeeTimeManageMapper;
import com.huizhi.employee.model.dto.salarytable.SalaryTableAddRequest;
import com.huizhi.employee.model.dto.salarytable.SalaryTableEditRequest;
import com.huizhi.employee.model.dto.salarytable.SalaryTableQueryRequest;
import com.huizhi.employee.model.dto.salarytable.SalaryTableUpdateRequest;
import com.huizhi.employee.model.entity.SalaryTable;
import com.huizhi.employee.model.entity.User;
import com.huizhi.employee.model.vo.SalaryTableVO;
import com.huizhi.employee.service.SalaryTableService;
import com.huizhi.employee.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * 工资结算表接口
 *
 * @author   小赵学Java
 *
 */
@RestController
@RequestMapping("/salaryTable")
@Slf4j
public class SalaryTableController {

    @Resource
    private SalaryTableService salaryTableService;

    @Resource
    private UserService userService;

    @Autowired
    private EmployeeTimeManageMapper employeeTimeManageMapper;

    // region 增删改查

    /**
     * 创建工资结算表
     *
     * @param salaryTableAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addSalaryTable(@RequestBody SalaryTableAddRequest salaryTableAddRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(salaryTableAddRequest == null, ErrorCode.PARAMS_ERROR);
        String employeeNo = salaryTableAddRequest.getEmployeeNo();
        String settlementMonth = salaryTableAddRequest.getSettlementMonth();
        // 在此处将实体类和 DTO 进行转换
        SalaryTable salaryTable = new SalaryTable();
        BeanUtils.copyProperties(salaryTableAddRequest, salaryTable);
        // 数据校验
        salaryTableService.validSalaryTable(salaryTable, true);
        BigDecimal totalWorkTime = employeeTimeManageMapper.getTotalWorkTimeForMonth(String.valueOf(settlementMonth), String.valueOf(employeeNo));
        salaryTable.setWorkTime(totalWorkTime);
        // 工资结算
        BigDecimal workMoney = salaryTableService.settlementSalary(salaryTable);
        salaryTable.setWorkMoney(workMoney);

        // 查询数据库中是否存在相同 employeeNo 和 settlementMonth 的记录
        LambdaQueryWrapper<SalaryTable> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SalaryTable::getEmployeeNo, employeeNo)
                .eq(SalaryTable::getSettlementMonth, settlementMonth);
        SalaryTable existingSalaryTable = salaryTableService.getOne(queryWrapper);

        // 判断是否存在已有记录
        if (existingSalaryTable != null) {
            // 如果记录存在，则获取到现有记录的 ID 并更新
            salaryTable.setId(existingSalaryTable.getId());
            boolean result = salaryTableService.updateById(salaryTable);
            ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
            return ResultUtils.success(existingSalaryTable.getId());
        } else {
            // 如果记录不存在，则插入新记录
            // 写入数据库
            boolean result = salaryTableService.save(salaryTable);
            ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
            // 返回新写入的数据 id
            long newSalaryTableId = salaryTable.getId();
            return ResultUtils.success(newSalaryTableId);
        }
    }

    /**
     * 删除工资结算表
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteSalaryTable(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = deleteRequest.getId();
        // 判断是否存在
        SalaryTable oldSalaryTable = salaryTableService.getById(id);
        ThrowUtils.throwIf(oldSalaryTable == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 操作数据库
        boolean result = salaryTableService.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 更新工资结算表（仅管理员可用）
     *
     * @param salaryTableUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateSalaryTable(@RequestBody SalaryTableUpdateRequest salaryTableUpdateRequest) {
        if (salaryTableUpdateRequest == null || salaryTableUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 在此处将实体类和 DTO 进行转换
        SalaryTable salaryTable = new SalaryTable();
        BeanUtils.copyProperties(salaryTableUpdateRequest, salaryTable);
        // 数据校验
        salaryTableService.validSalaryTable(salaryTable, false);
        // 判断是否存在
        long id = salaryTableUpdateRequest.getId();
        SalaryTable oldSalaryTable = salaryTableService.getById(id);
        ThrowUtils.throwIf(oldSalaryTable == null, ErrorCode.NOT_FOUND_ERROR);
        // 操作数据库
        boolean result = salaryTableService.updateById(salaryTable);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 根据 id 获取工资结算表（封装类）
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<SalaryTableVO> getSalaryTableVOById(long id, HttpServletRequest request) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        SalaryTable salaryTable = salaryTableService.getById(id);
        ThrowUtils.throwIf(salaryTable == null, ErrorCode.NOT_FOUND_ERROR);
        // 获取封装类
        return ResultUtils.success(salaryTableService.getSalaryTableVO(salaryTable, request));
    }

    /**
     * 分页获取工资结算表列表（仅管理员可用）
     *
     * @param salaryTableQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<SalaryTable>> listSalaryTableByPage(@RequestBody SalaryTableQueryRequest salaryTableQueryRequest) {
        long current = salaryTableQueryRequest.getCurrent();
        long size = salaryTableQueryRequest.getPageSize();
        // 查询数据库
        Page<SalaryTable> salaryTablePage = salaryTableService.page(new Page<>(current, size),
                salaryTableService.getQueryWrapper(salaryTableQueryRequest));
        return ResultUtils.success(salaryTablePage);
    }

    /**
     * 分页获取工资结算表列表（封装类）
     *
     * @param salaryTableQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<SalaryTableVO>> listSalaryTableVOByPage(@RequestBody SalaryTableQueryRequest salaryTableQueryRequest,
                                                               HttpServletRequest request) {
        long current = salaryTableQueryRequest.getCurrent();
        long size = salaryTableQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Page<SalaryTable> salaryTablePage = salaryTableService.page(new Page<>(current, size),
                salaryTableService.getQueryWrapper(salaryTableQueryRequest));
        // 获取封装类
        return ResultUtils.success(salaryTableService.getSalaryTableVOPage(salaryTablePage, request));
    }

    // endregion
}
