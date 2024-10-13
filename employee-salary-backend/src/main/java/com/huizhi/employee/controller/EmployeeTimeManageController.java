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
import com.huizhi.employee.model.dto.employee.EmployeeTimeManageAddRequest;
import com.huizhi.employee.model.dto.employee.EmployeeTimeManageQueryRequest;
import com.huizhi.employee.model.dto.employee.EmployeeTimeManageUpdateRequest;
import com.huizhi.employee.model.entity.EmployeeTimeManage;
import com.huizhi.employee.model.entity.User;
import com.huizhi.employee.model.vo.EmployeeTimeManageVO;
import com.huizhi.employee.service.EmployeeTimeManageService;
import com.huizhi.employee.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * 员工工时表接口
 *
 * @author   小赵学Java
 *
 */
@RestController
@RequestMapping("/employeeTimeManage")
@Slf4j
public class EmployeeTimeManageController {

    @Resource
    private EmployeeTimeManageService employeeTimeManageService;

    @Resource
    private UserService userService;

    // region 增删改查

    /**
     * 创建员工工时表
     *
     * @param employeeTimeManageAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addEmployeeTimeManage(@RequestBody EmployeeTimeManageAddRequest employeeTimeManageAddRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(employeeTimeManageAddRequest == null, ErrorCode.PARAMS_ERROR);
        // 在此处将实体类和 DTO 进行转换
        EmployeeTimeManage employeeTimeManage = new EmployeeTimeManage();
        BeanUtils.copyProperties(employeeTimeManageAddRequest, employeeTimeManage);
        // 数据校验
        employeeTimeManageService.validEmployeeTimeManage(employeeTimeManage, true);
        // 写入数据库
        boolean result = employeeTimeManageService.save(employeeTimeManage);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        // 返回新写入的数据 id
        long newEmployeeTimeManageId = employeeTimeManage.getId();
        return ResultUtils.success(newEmployeeTimeManageId);
    }

    /**
     * 删除员工工时表
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteEmployeeTimeManage(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        EmployeeTimeManage oldEmployeeTimeManage = employeeTimeManageService.getById(id);
        ThrowUtils.throwIf(oldEmployeeTimeManage == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 操作数据库
        boolean result = employeeTimeManageService.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 更新员工工时表（仅管理员可用）
     *
     * @param employeeTimeManageUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateEmployeeTimeManage(@RequestBody EmployeeTimeManageUpdateRequest employeeTimeManageUpdateRequest) {
        if (employeeTimeManageUpdateRequest == null || employeeTimeManageUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 在此处将实体类和 DTO 进行转换
        EmployeeTimeManage employeeTimeManage = new EmployeeTimeManage();
        BeanUtils.copyProperties(employeeTimeManageUpdateRequest, employeeTimeManage);
        // 数据校验
        employeeTimeManageService.validEmployeeTimeManage(employeeTimeManage, false);
        // 判断是否存在
        long id = employeeTimeManageUpdateRequest.getId();
        EmployeeTimeManage oldEmployeeTimeManage = employeeTimeManageService.getById(id);
        ThrowUtils.throwIf(oldEmployeeTimeManage == null, ErrorCode.NOT_FOUND_ERROR);
        // 操作数据库
        boolean result = employeeTimeManageService.updateById(employeeTimeManage);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 根据 id 获取员工工时表（封装类）
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<EmployeeTimeManageVO> getEmployeeTimeManageVOById(long id, HttpServletRequest request) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        EmployeeTimeManage employeeTimeManage = employeeTimeManageService.getById(id);
        ThrowUtils.throwIf(employeeTimeManage == null, ErrorCode.NOT_FOUND_ERROR);
        // 获取封装类
        return ResultUtils.success(employeeTimeManageService.getEmployeeTimeManageVO(employeeTimeManage, request));
    }

    /**
     * 分页获取员工工时表列表（仅管理员可用）
     *
     * @param employeeTimeManageQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<EmployeeTimeManage>> listEmployeeTimeManageByPage(@RequestBody EmployeeTimeManageQueryRequest employeeTimeManageQueryRequest) {
        long current = employeeTimeManageQueryRequest.getCurrent();
        long size = employeeTimeManageQueryRequest.getPageSize();
        // 查询数据库
        Page<EmployeeTimeManage> employeeTimeManagePage = employeeTimeManageService.page(new Page<>(current, size),
                employeeTimeManageService.getQueryWrapper(employeeTimeManageQueryRequest));
        return ResultUtils.success(employeeTimeManagePage);
    }

    /**
     * 分页获取员工工时表列表（封装类）
     *
     * @param employeeTimeManageQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<EmployeeTimeManageVO>> listEmployeeTimeManageVOByPage(@RequestBody EmployeeTimeManageQueryRequest employeeTimeManageQueryRequest,
                                                               HttpServletRequest request) {
        long current = employeeTimeManageQueryRequest.getCurrent();
        long size = employeeTimeManageQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Page<EmployeeTimeManage> employeeTimeManagePage = employeeTimeManageService.page(new Page<>(current, size),
                employeeTimeManageService.getQueryWrapper(employeeTimeManageQueryRequest));
        // 获取封装类
        return ResultUtils.success(employeeTimeManageService.getEmployeeTimeManageVOPage(employeeTimeManagePage, request));
    }

    // endregion
}
