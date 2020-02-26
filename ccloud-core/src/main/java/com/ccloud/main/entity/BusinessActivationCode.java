package com.ccloud.main.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 激活码表
 * </p>
 *
 * @author Generator
 * @since 2020-02-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class BusinessActivationCode extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 激活码id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 应用ID
     */
    private Integer appId;

    /**
     * 激活码
     */
    private String activationCode;

    /**
     * 核销状态
     * 0.未核销
     * 1.已核销
     */
    private Integer writeOffStatus;

    /**
     * 激活时长
     * 单位：天
     */
    private Integer days;

    /**
     * 附加信息
     */
    private String ext;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更细时间
     */
    private LocalDateTime updateTime;

    /**
     * 状态
     * 0.有效
     * 1.删除
     */
    private Integer status;


}
