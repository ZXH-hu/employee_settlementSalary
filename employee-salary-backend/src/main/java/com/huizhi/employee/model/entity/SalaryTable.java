package com.huizhi.employee.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 工资结算表
 * @TableName salary_table
 */
@TableName(value ="salary_table")
@Data
public class SalaryTable implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
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

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}