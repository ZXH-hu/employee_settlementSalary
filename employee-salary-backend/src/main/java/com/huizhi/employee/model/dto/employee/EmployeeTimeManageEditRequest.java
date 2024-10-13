package com.huizhi.employee.model.dto.employee;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 编辑员工工时表请求
 *
 * @author   小赵学Java
 *
 */
@Data
public class EmployeeTimeManageEditRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 员工姓名
     */
    private String employeeName;

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