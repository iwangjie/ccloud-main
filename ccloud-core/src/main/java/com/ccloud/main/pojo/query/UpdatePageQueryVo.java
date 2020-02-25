package com.ccloud.main.pojo.query;

import lombok.Data;

/**
 * @author wangjie
 */
@Data
public class UpdatePageQueryVo {

    /**
     * 每页显示条数，默认 10
     */
    private long size = 10;

    /**
     * 当前页
     */
    private long current = 1;

    private Integer appId;
}
