package com.ccloud.main.pojo.system;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Wangjie
 * @date 2019-02-25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiOperation("通用响应接口对象")
public class Result<T> {

    @ApiModelProperty("状态码")
    private Integer code;

    @ApiModelProperty("消息")
    private String msg;

    @ApiModelProperty("数据")
    private T data;
}
