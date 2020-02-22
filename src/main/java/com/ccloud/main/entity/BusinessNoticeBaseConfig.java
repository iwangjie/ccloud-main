package com.ccloud.main.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ccloud.main.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Generator
 * @since 2020-02-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class BusinessNoticeBaseConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 公告ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 所属appid
     */
    private Integer appId;

    /**
     * 公告标题
     */
    private String noticeTitle;

    /**
     * 公告内容
     */
    private String noticeInfo;

    /**
     * 公告图标
     */
    private String noticeIcon;

    /**
     * 公告开始时间
     */
    private LocalDateTime noticeReleaseStartTime;

    /**
     * 公告截至时间
     */
    private LocalDateTime noticeReleaseEndTime;

    private LocalDateTime createTime;

    /**
     * 公告截至时间
     */
    private LocalDateTime updateTime;

    /**
     * 状态
0.正常
1.删除
     */
    private Integer status;


}
