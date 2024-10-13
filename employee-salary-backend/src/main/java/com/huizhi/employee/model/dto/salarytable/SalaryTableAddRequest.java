package com.huizhi.employee.model.dto.salarytable;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 创建工资结算表请求
 *
 * @author   小赵学Java
 *
 */
@Data
public class SalaryTableAddRequest implements Serializable {


    /**
     * 员工姓名
     */
    private String employeeName;

    /**
     * 员工编号
     */
    private String employeeNo;

    /**
     * 计时工资基数
     */
    private BigDecimal salaryMath;


    /**
     * 结算月份
     */
    private String settlementMonth;

    private static final long serialVersionUID = 1L;
}