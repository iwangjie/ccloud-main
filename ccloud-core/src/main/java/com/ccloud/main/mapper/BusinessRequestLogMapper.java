package com.ccloud.main.mapper;

import com.ccloud.main.entity.BusinessRequestLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Generator
 * @since 2020-02-27
 */
@Mapper
@Component
public interface BusinessRequestLogMapper extends BaseMapper<BusinessRequestLog> {

    void processData(List<BusinessRequestLog> list);
}
