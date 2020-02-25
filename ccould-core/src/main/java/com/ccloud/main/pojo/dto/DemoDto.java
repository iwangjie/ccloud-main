package com.ccloud.main.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 简单的 DTO 示例
 *
 * @author wangjie
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DemoDto {

    /**
     * 字段 1
     */
    private String field1;

    /**
     * 字段 2
     */
    private LocalDateTime field2;


}
