package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private final String wx_login = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private WeChatProperties properties;

    @Autowired
    private UserMapper userMapper;


    public User wxLogin(UserLoginDTO userLoginDTO) {

        String openid = getOpenid(userLoginDTO.getCode());
        if (openid == null)
            throw new RuntimeException(MessageConstant.LOGIN_FAILED);

        User user = userMapper.getByopenif(openid);

        if (user == null) {
            user = User.builder()
                    .createTime(LocalDateTime.now())
                    .openid(openid)
                    .build();

            userMapper.insert(user);
        }
        return user;
    }

    private String getOpenid(String code)
    {
        Map<String, String> params = new HashMap<String, String>();
        params.put("grant_type", "authorization_code");
        params.put("appid",properties.getAppid());
        params.put("secret",properties.getSecret());
        params.put("js_code",code);
        String json = HttpClientUtil.doGet(wx_login,params);
        JSONObject jsonObject = JSON.parseObject(json);

        return jsonObject.getString("openid");

    }
}
