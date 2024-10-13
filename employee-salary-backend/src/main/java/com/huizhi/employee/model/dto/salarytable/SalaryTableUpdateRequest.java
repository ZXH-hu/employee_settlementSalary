package com.huizhi.employee.model.dto.salarytable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 更新工资结算表请求
 *
 * @author   小赵学Java
 *
 */
@Data
public class SalaryTableUpdateRequest implements Serializable {

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
     * 计时工资基数
     */
    private BigDecimal salaryMath;

    /**
     * 员工总工时
     */
    private BigDecimal workTime;

    /**
     * 员工工时工资
     */
    private BigDecimal workMoney;

    /**
     * 结算月份
     */
    private String settlementMonth;


    private static final long serialVersionUID = 1L;
}