package com.huizhi.employee.model.dto.timemanage;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 编辑计时管理表请求
 *
 * @author   小赵学Java
 *
 */
@Data
public class TimeManageEditRequest implements Serializable {

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

    private static final long serialVersionUID = 1L;
}