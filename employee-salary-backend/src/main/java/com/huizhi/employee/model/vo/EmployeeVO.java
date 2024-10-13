package com.huizhi.employee.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.huizhi.employee.model.entity.Employee;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 员工表视图
 *
 * @author   小赵学Java
 *
 */
@Data
public class EmployeeVO implements Serializable {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
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
     * @param employeeVO
     * @return
     */
    public static Employee voToObj(EmployeeVO employeeVO) {
        if (employeeVO == null) {
            return null;
        }
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeVO, employee);
        return employee;
    }

    /**
     * 对象转封装类
     *
     * @param employee
     * @return
     */
    public static EmployeeVO objToVo(Employee employee) {
        if (employee == null) {
            return null;
        }
        EmployeeVO employeeVO = new EmployeeVO();
        BeanUtils.copyProperties(employee, employeeVO);
        return employeeVO;
    }
}
