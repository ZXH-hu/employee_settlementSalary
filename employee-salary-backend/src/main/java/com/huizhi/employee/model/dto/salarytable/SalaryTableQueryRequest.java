package com.huizhi.employee.model.dto.salarytable;

import com.huizhi.employee.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 查询工资结算表请求
 *
 * @author   小赵学Java
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SalaryTableQueryRequest extends PageRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 员工编号
     */
    private String employeeNo;

    /**
     * 员工姓名
     */
    private String employeeName;

    /**
     * 员工总工时
     */
    private BigDecimal workTime;

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