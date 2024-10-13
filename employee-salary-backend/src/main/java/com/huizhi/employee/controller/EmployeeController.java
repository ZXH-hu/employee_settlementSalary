package com.huizhi.employee.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huizhi.employee.annotation.AuthCheck;
import com.huizhi.employee.common.BaseResponse;
import com.huizhi.employee.common.DeleteRequest;
import com.huizhi.employee.common.ErrorCode;
import com.huizhi.employee.common.ResultUtils;
import com.huizhi.employee.constant.UserConstant;
import com.huizhi.employee.exception.BusinessException;
import com.huizhi.employee.exception.ThrowUtils;
import com.huizhi.employee.model.dto.employee.EmployeeAddRequest;
import com.huizhi.employee.model.dto.employee.EmployeeEditRequest;
import com.huizhi.employee.model.dto.employee.EmployeeQueryRequest;
import com.huizhi.employee.model.dto.employee.EmployeeUpdateRequest;
import com.huizhi.employee.model.entity.Employee;
import com.huizhi.employee.model.entity.User;
import com.huizhi.employee.model.vo.EmployeeVO;
import com.huizhi.employee.service.EmployeeService;
import com.huizhi.employee.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * 员工表接口
 *
 * @author   小赵学Java
 *
 */
@RestController
@RequestMapping("/employee")
@Slf4j
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;

    @Resource
    private UserService userService;

    // region 增删改查

    /**
     * 创建员工表
     *
     * @param employeeAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addEmployee(@RequestBody EmployeeAddRequest employeeAddRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(employeeAddRequest == null, ErrorCode.PARAMS_ERROR);
        // 在此处将实体类和 DTO 进行转换
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeAddRequest, employee);
        // 数据校验
        employeeService.validEmployee(employee, true);
        // 自动填充员工编号
        String uniqueID = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        String employeeNo = "Emp" + uniqueID;
        employee.setEmployeeNo(employeeNo);
        // 写入数据库
        boolean result = employeeService.save(employee);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        // 返回新写入的数据 id
        long newEmployeeId = employee.getId();
        return ResultUtils.success(newEmployeeId);
    }

    /**
     * 删除员工表
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteEmployee(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = deleteRequest.getId();
        // 判断是否存在
        Employee oldEmployee = employeeService.getById(id);
        ThrowUtils.throwIf(oldEmployee == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 操作数据库
        boolean result = employeeService.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 更新员工表（仅管理员可用）
     *
     * @param employeeUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateEmployee(@RequestBody EmployeeUpdateRequest employeeUpdateRequest) {
        if (employeeUpdateRequest == null || employeeUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 在此处将实体类和 DTO 进行转换
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeUpdateRequest, employee);
        // 数据校验
        employeeService.validEmployee(employee, false);
        // 判断是否存在
        long id = employeeUpdateRequest.getId();
        Employee oldEmployee = employeeService.getById(id);
        ThrowUtils.throwIf(oldEmployee == null, ErrorCode.NOT_FOUND_ERROR);
        // 操作数据库
        boolean result = employeeService.updateById(employee);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 根据 id 获取员工表（封装类）
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<EmployeeVO> getEmployeeVOById(long id, HttpServletRequest request) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Employee employee = employeeService.getById(id);
        ThrowUtils.throwIf(employee == null, ErrorCode.NOT_FOUND_ERROR);
        // 获取封装类
        return ResultUtils.success(employeeService.getEmployeeVO(employee, request));
    }

    /**
     * 分页获取员工表列表（仅管理员可用）
     *
     * @param employeeQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<Employee>> listEmployeeByPage(@RequestBody EmployeeQueryRequest employeeQueryRequest) {
        long current = employeeQueryRequest.getCurrent();
        long size = employeeQueryRequest.getPageSize();
        // 查询数据库
        Page<Employee> employeePage = employeeService.page(new Page<>(current, size),
                employeeService.getQueryWrapper(employeeQueryRequest));
        return ResultUtils.success(employeePage);
    }

    /**
     * 分页获取员工表列表（封装类）
     *
     * @param employeeQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<EmployeeVO>> listEmployeeVOByPage(@RequestBody EmployeeQueryRequest employeeQueryRequest,
                                                               HttpServletRequest request) {
        long current = employeeQueryRequest.getCurrent();
        long size = employeeQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Page<Employee> employeePage = employeeService.page(new Page<>(current, size),
                employeeService.getQueryWrapper(employeeQueryRequest));
        // 获取封装类
        return ResultUtils.success(employeeService.getEmployeeVOPage(employeePage, request));
    }


    // endregion
}
