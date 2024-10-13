package com.huizhi.employee.mapper;

import com.huizhi.employee.model.entity.EmployeeTimeManage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

/**
* @author 小赵
* @description 针对表【employee_time_manage(员工工时表)】的数据库操作Mapper
* @createDate 2024-10-10 22:15:32
* @Entity com.huizhi.employee.model.entity.EmployeeTimeManage
*/
public interface EmployeeTimeManageMapper extends BaseMapper<EmployeeTimeManage> {

    BigDecimal getTotalWorkTimeForMonth(@Param("settlementMonth") String settlementMonth, @Param("employeeNo") String employeeNo);

}




