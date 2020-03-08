package com.ccloud.main.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 激活码使用记录表
 * </p>
 *
 * @author Generator
 * @since 2020-02-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ClientBusinessActivationLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 记录id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 应用ID
     */
    private Integer appId;

    /**
     * C端用户id
     */
    private Integer userId;

    /**
     * 激活码ID
     */
    private Integer activationCodeId;

    /**
     * 激活码
     */
    private String activationCode;

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
