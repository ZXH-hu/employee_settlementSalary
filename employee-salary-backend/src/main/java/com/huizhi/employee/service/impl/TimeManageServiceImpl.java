package com.huizhi.employee.service.impl;
import java.util.Date;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huizhi.employee.common.ErrorCode;
import com.huizhi.employee.constant.CommonConstant;
import com.huizhi.employee.exception.ThrowUtils;
import com.huizhi.employee.mapper.TimeManageMapper;
import com.huizhi.employee.model.dto.timemanage.TimeManageQueryRequest;
import com.huizhi.employee.model.entity.TimeManage;
import com.huizhi.employee.model.entity.User;
import com.huizhi.employee.model.enums.RunStatusEnum;
import com.huizhi.employee.model.vo.TimeManageVO;
import com.huizhi.employee.model.vo.UserVO;
import com.huizhi.employee.service.TimeManageService;
import com.huizhi.employee.service.UserService;
import com.huizhi.employee.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 计时管理表服务实现
 *
 * @author   小赵学Java
 *
 */
@Service
@Slf4j
public class TimeManageServiceImpl extends ServiceImpl<TimeManageMapper, TimeManage> implements TimeManageService {

    @Resource
    private UserService userService;

    /**
     * 校验数据
     *
     * @param timeManage
     * @param add      对创建的数据进行校验
     */
    @Override
    public void validTimeManage(TimeManage timeManage, boolean add) {
        ThrowUtils.throwIf(timeManage == null, ErrorCode.PARAMS_ERROR);
        // 从对象中取值
        String timeType = timeManage.getTimeType();
        Integer salaryMath = timeManage.getSalaryMath();
        Integer runStatus = timeManage.getRunStatus();
        // 创建数据时，参数不能为空
        if (add) {
            // 补充校验规则
            ThrowUtils.throwIf(StringUtils.isBlank(timeType), ErrorCode.PARAMS_ERROR, "计时类型名称不能为空");
            ThrowUtils.throwIf(ObjectUtils.isEmpty(salaryMath), ErrorCode.PARAMS_ERROR, "计时工资基数不能为空");
            RunStatusEnum runStatusEnum = RunStatusEnum.getEnumByValue(runStatus);
            ThrowUtils.throwIf(runStatusEnum == null, ErrorCode.PARAMS_ERROR, "计时启用状态不能为空");
        }
    }

    /**
     * 获取查询条件
     *
     * @param timeManageQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<TimeManage> getQueryWrapper(TimeManageQueryRequest timeManageQueryRequest) {
        QueryWrapper<TimeManage> queryWrapper = new QueryWrapper<>();
        if (timeManageQueryRequest == null) {
            return queryWrapper;
        }
        // 从对象中取值
        Long id = timeManageQueryRequest.getId();
        String timeType = timeManageQueryRequest.getTimeType();
        Integer salaryMath = timeManageQueryRequest.getSalaryMath();
        Integer runStatus = timeManageQueryRequest.getRunStatus();
        String sortField = timeManageQueryRequest.getSortField();
        String sortOrder = timeManageQueryRequest.getSortOrder();

        // 补充需要的查询条件
        // 模糊查询
        queryWrapper.like(StringUtils.isNotBlank(timeType), "timeType", timeType);
        // 精确查询
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjectUtils.isNotEmpty(salaryMath), "salaryMath", salaryMath);
        queryWrapper.eq(ObjectUtils.isNotEmpty(runStatus), "runStatus", runStatus);
        // 排序规则
        queryWrapper.orderBy(SqlUtils.validSortField(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    /**
     * 获取计时管理表封装
     *
     * @param timeManage
     * @param request
     * @return
     */
    @Override
    public TimeManageVO getTimeManageVO(TimeManage timeManage, HttpServletRequest request) {
        // 对象转封装类
        TimeManageVO timeManageVO = TimeManageVO.objToVo(timeManage);
        return timeManageVO;
    }

    /**
     * 分页获取计时管理表封装
     *
     * @param timeManagePage
     * @param request
     * @return
     */
    @Override
    public Page<TimeManageVO> getTimeManageVOPage(Page<TimeManage> timeManagePage, HttpServletRequest request) {
        List<TimeManage> timeManageList = timeManagePage.getRecords();
        Page<TimeManageVO> timeManageVOPage = new Page<>(timeManagePage.getCurrent(), timeManagePage.getSize(), timeManagePage.getTotal());
        if (CollUtil.isEmpty(timeManageList)) {
            return timeManageVOPage;
        }
        // 对象列表 => 封装对象列表
        List<TimeManageVO> timeManageVOList = timeManageList.stream().map(timeManage -> {
            return TimeManageVO.objToVo(timeManage);
        }).collect(Collectors.toList());
        timeManageVOPage.setRecords(timeManageVOList);
        return timeManageVOPage;
    }

}
