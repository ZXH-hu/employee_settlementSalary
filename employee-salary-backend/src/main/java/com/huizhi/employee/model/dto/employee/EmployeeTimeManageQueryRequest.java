package com.huizhi.employee.model.dto.employee;

import com.huizhi.employee.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 查询员工工时表请求
 *
 * @author   小赵学Java
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EmployeeTimeManageQueryRequest extends PageRequest implements Serializable {

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
     * 员工工时
     */
    private BigDecimal workTime;


    private static final long serialVersionUID = 1L;
}