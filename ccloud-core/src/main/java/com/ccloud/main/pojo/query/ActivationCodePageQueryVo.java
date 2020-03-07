package com.ccloud.main.pojo.query;

import lombok.Data;

/**
 * @author wangjie
 */
@Data
public class ActivationCodePageQueryVo {

    /**
     * 每页显示条数，默认 10
     */
    private int pageSize = 10;

    /**
     * 当前页
     */
    private int pageNum = 1;

    private Integer appId;
}
