package com.ccloud.main.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 简单的 VO 示例
 *
 * @author wangjie
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DemoVo {

    /**
     * 响应结果字段1
     */
    private String feild1;

    /**
     * 响应结果字段2
     */
    @JsonFormat
    private Date feild2;


}
