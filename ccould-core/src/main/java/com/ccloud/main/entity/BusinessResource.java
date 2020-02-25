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
 * 用户基础资源表
 * </p>
 *
 * @author Generator
 * @since 2020-02-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class BusinessResource extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 资源id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 资源编码
     */
    private String resourceCode;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 资源描述
     */
    private String resourceDesc;

    /**
     * 资源类型
     */
    private Integer resourceType;

    /**
     * 资源父id
     */
    private Integer resourcePid;

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
0.正常
1.删除
     */
    private Integer status;


}
