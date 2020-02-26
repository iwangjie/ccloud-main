package com.ccloud.main.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author Generator
 * @since 2020-02-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ClientUserCode extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * C端用户ID
     */
    private Integer userId;

    /**
     * B端激活码ID
     */
    private Integer activationCodeId;

    /**
     * 激活码
     */
    private String activationCode;

    /**
     * 到期时间
     */
    private LocalDateTime activationEndTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 状态
     * 0.正常
     * 1.删除
     */
    private Integer status;


}
