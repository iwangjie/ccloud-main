package com.ccloud.main.pojo.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wangjie
 */

@Getter
@AllArgsConstructor
public enum CloudUtilEnum {

    /**
     * 是否移动端
     */
    IS_CLIENT(),

    /**
     * PC端 Token
     */
    CC_AUTHORIZATION(),

    /**
     * 客户端 Token
     */
    CL_AUTHORIZATION();

}
