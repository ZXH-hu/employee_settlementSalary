package com.huizhi.employee.common;

import java.io.Serializable;
import lombok.Data;

/**
 * 删除请求
 *
 * @author   小赵学Java
 *    
 */
@Data
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}