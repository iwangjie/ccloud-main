package com.ccloud.main.pojo.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author wangjie
 */
@Data
public class NoticePageQueryVo {

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
