package com.mao.user.service;

import com.mao.user.entity.UserInfo;

public interface UserService {

    /**
     * 通过openid查询用户信息
     * @param openid
     * @return
     */
    UserInfo findByOpenid(String openid);
}
