package com.mao.user.controller;

import com.mao.user.contant.CookieContant;
import com.mao.user.contant.RedisContant;
import com.mao.user.entity.UserInfo;
import com.mao.user.enums.ResultEnum;
import com.mao.user.enums.RoleEnum;
import com.mao.user.service.UserService;
import com.mao.user.utils.CookieUtil;
import com.mao.user.utils.ResultVOUtil;
import com.mao.user.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("buyer")
    public ResultVO buyer(@RequestParam("openid") String openid, HttpServletResponse response) {
        //1.openid 和数据库数据匹配
        UserInfo userInfo = userService.findByOpenid(openid);
        if (userInfo == null) {
            return ResultVOUtil.error(ResultEnum.LOGIN_FAIL);
        }
        //2.判断角色
        if (RoleEnum.BUYER.getCode() != userInfo.getRole()) {
            return ResultVOUtil.error(ResultEnum.ROLE_ERROR);
        }
        //3. cookie里设置openid
        CookieUtil.set(response, CookieContant.OPENID,openid,CookieContant.expire);
        return ResultVOUtil.success();
    }

    @GetMapping("seller")
    public ResultVO seller(@RequestParam("openid") String openid, HttpServletResponse response, HttpServletRequest request) {
        //判断是否已登录
        Cookie cookie = CookieUtil.get(request,CookieContant.TOKEN);
        if (cookie != null && !StringUtils.isEmpty(stringRedisTemplate.opsForValue().get(String.format(RedisContant.TOKEN_TEMPLATE,cookie.getValue())))){
            //1.openid 和数据库数据匹配
            UserInfo userInfo = userService.findByOpenid(openid);
            if (userInfo == null) {
                return ResultVOUtil.error(ResultEnum.LOGIN_FAIL);
            }else //2.判断角色
                if (RoleEnum.SELLER.getCode() != userInfo.getRole()) {
                    return ResultVOUtil.error(ResultEnum.ROLE_ERROR);
                }
             else  return ResultVOUtil.success();
        }
        //3.redis设置KEY=UUID,value=xyz
        String token = UUID.randomUUID().toString();
        Integer expire = CookieContant.expire;
        stringRedisTemplate.opsForValue().set(String.format(RedisContant.TOKEN_TEMPLATE,token),openid,expire, TimeUnit.SECONDS);

        //4. cookie里设置token = UUID
        CookieUtil.set(response, CookieContant.TOKEN,token,CookieContant.expire);
        return ResultVOUtil.success();
    }
}
