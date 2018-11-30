package com.mao.user.service.Impl;

import com.mao.user.dao.UserInfoDao;
import com.mao.user.entity.UserInfo;
import com.mao.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoDao userInfoDao;
    /**
     * 通过openid查询用户信息
     *
     * @param openid
     * @return
     */
    @Override
    public UserInfo findByOpenid(String openid) {
        return userInfoDao.findByOpenid(openid);
    }
}
