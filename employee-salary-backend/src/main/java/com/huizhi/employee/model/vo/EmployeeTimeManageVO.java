package com.huizhi.employee.model.vo;

import com.huizhi.employee.model.entity.EmployeeTimeManage;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 员工工时表视图
 *
 * @author   小赵学Java
 *
 */
@Data
public class EmployeeTimeManageVO implements Serializable {

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

    /**
     * 员工工时
     */
    private BigDecimal workTime;

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
     * @param employeeTimeManageVO
     * @return
     */
    public static EmployeeTimeManage voToObj(EmployeeTimeManageVO employeeTimeManageVO) {
        if (employeeTimeManageVO == null) {
            return null;
        }
        EmployeeTimeManage employeeTimeManage = new EmployeeTimeManage();
        BeanUtils.copyProperties(employeeTimeManageVO, employeeTimeManage);
        return employeeTimeManage;
    }

    /**
     * 对象转封装类
     *
     * @param employeeTimeManage
     * @return
     */
    public static EmployeeTimeManageVO objToVo(EmployeeTimeManage employeeTimeManage) {
        if (employeeTimeManage == null) {
            return null;
        }
        EmployeeTimeManageVO employeeTimeManageVO = new EmployeeTimeManageVO();
        BeanUtils.copyProperties(employeeTimeManage, employeeTimeManageVO);
        return employeeTimeManageVO;
    }
}
