package com.huizhi.employee.model.vo;

import com.huizhi.employee.model.entity.TimeManage;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 计时管理表视图
 *
 * @author   小赵学Java
 *
 */
@Data
public class TimeManageVO implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 计时类型名称
     */
    private String timeType;

    /**
     * 计时工资基数
     */
    private Integer salaryMath;

    /**
     * 状态：0-已启用, 1-已停用
     */
    private Integer runStatus;

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
     * @param timeManageVO
     * @return
     */
    public static TimeManage voToObj(TimeManageVO timeManageVO) {
        if (timeManageVO == null) {
            return null;
        }
        TimeManage timeManage = new TimeManage();
        BeanUtils.copyProperties(timeManageVO, timeManage);
        return timeManage;
    }

    /**
     * 对象转封装类
     *
     * @param timeManage
     * @return
     */
    public static TimeManageVO objToVo(TimeManage timeManage) {
        if (timeManage == null) {
            return null;
        }
        TimeManageVO timeManageVO = new TimeManageVO();
        BeanUtils.copyProperties(timeManage, timeManageVO);
        return timeManageVO;
    }
}
