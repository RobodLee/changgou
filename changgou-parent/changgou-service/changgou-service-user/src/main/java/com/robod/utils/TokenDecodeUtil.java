package com.robod.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Robod
 * @date 2020/8/26 10:28
 */
@Component
public class TokenDecodeUtil {

    //公钥路径
    private static final String PUBLIC_KEY_PATH = "public.key";

    //公钥的内容
    private static String publicKey="";

    /***
     * 获取用户信息
     * @return
     */
    public Map<String,String> getUserInfo() {
        //获取授权信息
        OAuth2AuthenticationDetails authentication = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        //令牌解码
        return decodeToken(authentication.getTokenValue());
    }

    /***
     * 读取令牌数据
     */
    public Map<String,String> decodeToken(String token){
        //校验Jwt
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(getPubKey()));

        //获取Jwt原始内容
        String claims = jwt.getClaims();
        return JSON.parseObject(claims,Map.class);
    }

    /**
     * 获取非对称加密公钥 Key
     * @return 公钥 Key
     */
    public String getPubKey() {
        if(!StringUtils.isEmpty(publicKey)){
            return publicKey;
        }
        Resource resource = new ClassPathResource(PUBLIC_KEY_PATH);
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(resource.getInputStream());
            BufferedReader br = new BufferedReader(inputStreamReader);
            publicKey = br.lines().collect(Collectors.joining("\n"));
            return publicKey;
        } catch (IOException ioe) {
            return null;
        }

    }

}
