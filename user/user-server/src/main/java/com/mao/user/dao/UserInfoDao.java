package com.mao.user.dao;

import com.mao.user.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户Dao
 */
public interface UserInfoDao extends JpaRepository<UserInfo,String> {

    UserInfo findByOpenid(String openid);


}
