package com.huizhi.employee.model.vo;

import com.huizhi.employee.model.entity.SalaryTable;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 工资结算表视图
 *
 * @author   小赵学Java
 *
 */
@Data
public class SalaryTableVO implements Serializable {

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

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 封装类转对象
     *
     * @param salaryTableVO
     * @return
     */
    public static SalaryTable voToObj(SalaryTableVO salaryTableVO) {
        if (salaryTableVO == null) {
            return null;
        }
        SalaryTable salaryTable = new SalaryTable();
        BeanUtils.copyProperties(salaryTableVO, salaryTable);
        return salaryTable;
    }

    /**
     * 对象转封装类
     *
     * @param salaryTable
     * @return
     */
    public static SalaryTableVO objToVo(SalaryTable salaryTable) {
        if (salaryTable == null) {
            return null;
        }
        SalaryTableVO salaryTableVO = new SalaryTableVO();
        BeanUtils.copyProperties(salaryTable, salaryTableVO);
        return salaryTableVO;
    }
}
