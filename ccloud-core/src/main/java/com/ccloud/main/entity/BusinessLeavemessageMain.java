package com.ccloud.main.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2020-02-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class BusinessLeavemessageMain extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 所属appid
     */
    private Integer appId;

    /**
     * 留言标题
     */
    private String messageTitle;

    /**
     * 留言类型
     */
    private Integer messageType;

    /**
     * 留言说明
     */
    private String messageDesc;

    /**
     * 留言内容
     */
    private String messageContent;

    /**
     * 留言人
     */
    private String author;

    /**
     * 电话
     */
    private String telephone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 状态:0.正常 1.删除
     */
    private String status;


}
