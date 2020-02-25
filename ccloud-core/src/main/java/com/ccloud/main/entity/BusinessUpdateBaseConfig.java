package com.ccloud.main.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 更新基本信息表
 * </p>
 *
 * @author Generator
 * @since 2020-02-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class BusinessUpdateBaseConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 应用id
     */
    private Integer appId;

    /**
     * 更新标题
     */
    private String updateTitle;

    /**
     * 更新说明
     */
    private String updateInfo;

    /**
     * 强制更新
     * 0.否
     * 1.是
     */
    private Integer updateForced;

    private LocalDateTime createTime;

    private String updateTime;

    private Integer status;


}
