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
 * 
 * </p>
 *
 * @author Generator
 * @since 2020-02-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class BusinessRequestLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * appid
     */
    private Integer appId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 请求url
     */
    private String requestUrl;

    /**
     * 请求方式(get/post)
     */
    private String requestWay;

    /**
     * 方法描述
     */
    private String description;

    /**
     * 包名
     */
    private String packageName;

    /**
     * 参数拼接
     */
    private String parameterSplit;

    /**
     * 状态
     */
    private String status;

    /**
     * 请求端类型 ios(iphone手机),Android(安卓手机),computer(电脑)
     */
    private String type;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
