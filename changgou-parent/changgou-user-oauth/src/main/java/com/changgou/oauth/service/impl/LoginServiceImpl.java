package com.changgou.oauth.service.impl;

import com.changgou.oauth.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Map;

/**
 * 描述
 *
 * @author www.itheima.com
 * @version 1.0
 * @package com.changgou.oauth.service.impl *
 * @since 1.0
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Override
    public Map login(String username, String password, String clientId, String clientSecret, String grantType) {
        String url = loadBalancerClient.choose("user-auth").getUri() + "/oauth/token";
        //请求体
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("username", username);
        body.add("password", password);
        body.add("grant_type", grantType);
        //请求头
        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        String authorization = "Basic " +
                new String(Base64.getEncoder().encode((clientId + ":" + clientSecret).getBytes()));
        header.add("Authorization", authorization);
        HttpEntity httpEntity = new HttpEntity(body, header);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Map.class);
        return response.getBody();
    }

}
