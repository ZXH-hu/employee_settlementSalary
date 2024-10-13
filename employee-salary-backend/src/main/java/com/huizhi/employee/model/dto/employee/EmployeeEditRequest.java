package com.huizhi.employee.model.dto.employee;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 编辑员工表请求
 *
 * @author   小赵学Java
 *
 */
@Data
public class EmployeeEditRequest implements Serializable {

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
     * 计时类型名称
     */
    private String timeType;

    /**
     * 计时工资基数
     */
    private BigDecimal salaryMath;

    private static final long serialVersionUID = 1L;
}