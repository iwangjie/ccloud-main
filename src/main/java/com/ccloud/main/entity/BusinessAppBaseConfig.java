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
 * B端应用基本信息配置表
 * </p>
 *
 * @author Generator
 * @since 2020-02-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class BusinessAppBaseConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 应用id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 应用所属B端用户ID
     */
    private Integer businessUserId;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用包名
     */
    private String appPack;

    /**
     * 应用图标
     */
    private String appIcon;

    /**
     * 应用描述
     */
    private String appDesc;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /**
     * 应用状态
0.正常
1.禁用
     */
    private Integer appStatus;

    /**
     * 状态
0.正常
1.删除
     */
    private Integer status;


}
