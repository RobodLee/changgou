package com.changgou.oauth.util;

import lombok.Data;

import java.io.Serializable;

/****
 * @Author:www.itheima
 * @Date:2019/5/18 14:52
 * @Description:用户令牌封装
 *****/
@Data
public class AuthToken implements Serializable{

    //令牌信息
    String accessToken;
    //刷新token(refresh_token)
    String refreshToken;
    //jwt短令牌
    String jti;

}