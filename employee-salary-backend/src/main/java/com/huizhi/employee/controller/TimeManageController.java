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
import com.huizhi.employee.model.dto.timemanage.TimeManageAddRequest;
import com.huizhi.employee.model.dto.timemanage.TimeManageQueryRequest;
import com.huizhi.employee.model.dto.timemanage.TimeManageUpdateRequest;
import com.huizhi.employee.model.entity.TimeManage;
import com.huizhi.employee.model.entity.User;
import com.huizhi.employee.model.vo.TimeManageVO;
import com.huizhi.employee.service.TimeManageService;
import com.huizhi.employee.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 计时管理表接口
 *
 * @author   小赵学Java
 *
 */
@RestController
@RequestMapping("/timeManage")
@Slf4j
public class TimeManageController {

    @Resource
    private TimeManageService timeManageService;

    @Resource
    private UserService userService;

    // region 增删改查

    /**
     * 创建计时管理表
     *
     * @param timeManageAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addTimeManage(@RequestBody TimeManageAddRequest timeManageAddRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(timeManageAddRequest == null, ErrorCode.PARAMS_ERROR);
        // 在此处将实体类和 DTO 进行转换
        TimeManage timeManage = new TimeManage();
        BeanUtils.copyProperties(timeManageAddRequest, timeManage);
        // 数据校验
        timeManageService.validTimeManage(timeManage, true);
        // 写入数据库
        boolean result = timeManageService.save(timeManage);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        // 返回新写入的数据 id
        long newTimeManageId = timeManage.getId();
        return ResultUtils.success(newTimeManageId);
    }

    /**
     * 删除计时管理表
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteTimeManage(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        TimeManage oldTimeManage = timeManageService.getById(id);
        ThrowUtils.throwIf(oldTimeManage == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 操作数据库
        boolean result = timeManageService.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 更新计时管理表（仅管理员可用）
     *
     * @param timeManageUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateTimeManage(@RequestBody TimeManageUpdateRequest timeManageUpdateRequest) {
        if (timeManageUpdateRequest == null || timeManageUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 在此处将实体类和 DTO 进行转换
        TimeManage timeManage = new TimeManage();
        BeanUtils.copyProperties(timeManageUpdateRequest, timeManage);
        // 数据校验
        timeManageService.validTimeManage(timeManage, false);
        // 判断是否存在
        long id = timeManageUpdateRequest.getId();
        TimeManage oldTimeManage = timeManageService.getById(id);
        ThrowUtils.throwIf(oldTimeManage == null, ErrorCode.NOT_FOUND_ERROR);
        // 操作数据库
        boolean result = timeManageService.updateById(timeManage);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 根据 id 获取计时管理表（封装类）
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<TimeManageVO> getTimeManageVOById(long id, HttpServletRequest request) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        TimeManage timeManage = timeManageService.getById(id);
        ThrowUtils.throwIf(timeManage == null, ErrorCode.NOT_FOUND_ERROR);
        // 获取封装类
        return ResultUtils.success(timeManageService.getTimeManageVO(timeManage, request));
    }

    /**
     * 分页获取计时管理表列表（仅管理员可用）
     *
     * @param timeManageQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<TimeManage>> listTimeManageByPage(@RequestBody TimeManageQueryRequest timeManageQueryRequest) {
        long current = timeManageQueryRequest.getCurrent();
        long size = timeManageQueryRequest.getPageSize();
        // 查询数据库
        Page<TimeManage> timeManagePage = timeManageService.page(new Page<>(current, size),
                timeManageService.getQueryWrapper(timeManageQueryRequest));
        return ResultUtils.success(timeManagePage);
    }

    /**
     * 分页获取计时管理表列表（封装类）
     *
     * @param timeManageQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<TimeManageVO>> listTimeManageVOByPage(@RequestBody TimeManageQueryRequest timeManageQueryRequest,
                                                               HttpServletRequest request) {
        long current = timeManageQueryRequest.getCurrent();
        long size = timeManageQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Page<TimeManage> timeManagePage = timeManageService.page(new Page<>(current, size),
                timeManageService.getQueryWrapper(timeManageQueryRequest));
        // 获取封装类
        return ResultUtils.success(timeManageService.getTimeManageVOPage(timeManagePage, request));
    }
    // endregion
}
