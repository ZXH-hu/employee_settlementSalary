package com.huizhi.employee.model.dto.employee;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 更新员工工时表请求
 *
 * @author   小赵学Java
 *
 */
@Data
public class EmployeeTimeManageUpdateRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 员工姓名
     */
    private String employeeName;

    /**
     * 员工编号
     */
    private String employeeNo;

    /**
     * 计时类型名称
     */
    private String timeType;

    /**
     * 计时工资基数
     */
    private BigDecimal salaryMath;

    /**
     * 员工工时
     */
    private BigDecimal workTime;


    private static final long serialVersionUID = 1L;
}