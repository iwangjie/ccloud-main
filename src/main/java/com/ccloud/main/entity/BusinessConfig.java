package com.ccloud.main.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.ccloud.main.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * B端基本配置表
配置一下Bduan 用户的设置信息
 * </p>
 *
 * @author Generator
 * @since 2020-02-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class BusinessConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 配置ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 配置编码
     */
    private String configCode;

    /**
     * 配置默认值
     */
    private String configDefaultValue;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /**
     * 状态
0.正常
1.删除
     */
    private Integer status;


}
