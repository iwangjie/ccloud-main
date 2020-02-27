package com.ccloud.main.logic;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ccloud.main.entity.ClientUser;
import com.ccloud.main.mapper.ClientUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wangjie
 */
@Service
public class ClientUserLogic {

    @Resource
    private ClientUserMapper clientUserMapper;

    public ClientUser findByName(String username) {
        ClientUser clientUser = clientUserMapper.selectOne(new LambdaQueryWrapper<ClientUser>().eq(ClientUser::getStatus, 0).eq(ClientUser::getUserStatus, 0).eq(ClientUser::getUsername, username));
        return clientUser;
    }
}
