package com.ccloud.main.logic;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccloud.main.entity.BusinessActivationCode;
import com.ccloud.main.entity.BusinessUser;
import com.ccloud.main.mapper.BusinessActivationCodeMapper;
import com.ccloud.main.pojo.query.UpdatePageQueryVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * B端激活码
 *
 * @author wangjie
 */
@Service
public class BusinessActivationCodeLogic {

    @Resource
    private BusinessActivationCodeMapper businessActivationCodeMapper;


    /**
     * 分页获取 APP 激活码
     *
     * @param currentUser
     * @param updatePageQueryVo
     * @return
     */
    public IPage<BusinessActivationCode> getPageUpdateByAppId(BusinessUser currentUser, UpdatePageQueryVo updatePageQueryVo) {
        Page<BusinessActivationCode> page = new Page<>(updatePageQueryVo.getPageNum(), updatePageQueryVo.getPageSize());
        IPage<BusinessActivationCode> pageData = businessActivationCodeMapper.selectPage(page, new LambdaQueryWrapper<BusinessActivationCode>().eq(BusinessActivationCode::getAppId, updatePageQueryVo.getAppId()));
        return pageData;
    }

    /**
     * 不分页获取所有激活码
     *
     * @param currentUser
     * @param appId
     * @return
     */
    public List<BusinessActivationCode> getAllUpdateByAppId(BusinessUser currentUser, Integer appId) {
        return businessActivationCodeMapper.selectList(new LambdaQueryWrapper<BusinessActivationCode>()
                .eq(BusinessActivationCode::getStatus, 0)
                .eq(BusinessActivationCode::getAppId, appId));
    }

}
