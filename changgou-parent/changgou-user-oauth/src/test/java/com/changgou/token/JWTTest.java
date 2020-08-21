package com.changgou.token;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.mockito.internal.configuration.ClassPathLoader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Robod
 * @date 2020/8/21 8:51
 */
public class JWTTest {

    //创建令牌
    @Test
    public void createJWT() {
        ClassPathResource classPathResource = new ClassPathResource("robod666.jks");
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(classPathResource, "robod666".toCharArray());
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair("robod666");
        PrivateKey privateKey = keyPair.getPrivate();
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("id", "1");
        tokenMap.put("name", "robod");
        tokenMap.put("roles", "ROLE_VIP,ROLE_USER");
        Jwt jwt = JwtHelper.encode(JSON.toJSONString(tokenMap), new RsaSigner((RSAPrivateKey) privateKey));
        System.out.println(jwt.getEncoded());
    }

    //解析令牌
    @Test
    public void parseJWT() {
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlcyI6IlJPTEVfVklQLFJPTEVfVVNFUiIsIm5hbWUiOiJyb2JvZCIsImlkIjoiMSJ9.KkPXXlYTkDCWq2VN5qy6w6FI5TgCbIy-GkQShaYGbfvcZsj0165XsgXSx7Sf5aUpGB-494ds-ZnLxs3oVMZ7_tbu-is1-gZOeQ0G1GLla0ytNkImXabnujgWH2B4bmX4lBLK7d8xTEQ4WoAnydWUusCmPjQDgdFZGHmccJLuYKqQPzru-4go1mFgjEeB7gNu6cLYyQc79bZdF2Mk2OX1Nxpb88sux0QkNAlb1-JuUhmjbUYwMK5l5W5zeNckRJtGy_Zy7OTwXviuRp6uISmWD7p1HYbkKH-ROKCgSu1cqnok0645Uou7Y54Nd8NosIqShuNYbBBo_BHWuyx_lKdk1g";
        String publicKey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiq6KfbXc/viuB6oQ/80cfLSFIr7pX3PmteAQ2/dA+ReMLgULJb+U8Dax3xNpBgLAp+Ei2IMkBFJlJRn/iaYi5eMnCY2vyfHkC69x6OhhCtzWBRxGJkPRjLDU+Obhak2MrDI4zIpzQs2/phjqWXuEPMz7KMd5UhoAFZWLTW1Ih3CP962fuJdV83hj/2uWN/yaAgaLRxRlTw7HHoIEy1dX9prAnqQ/rOl2Igvwi23GNnzMrqlvR9qt1gBI+noHtMv07hkavUT1nmoYnt/pw2+FLMLFEun2gR3DUmqu79QC6trDf3cVfKyRP9A7TBjUEv+Ecrh8JQosQa8GongTzHhmOwIDAQAB-----END PUBLIC KEY-----";
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(publicKey));
        String claims = jwt.getClaims();
        System.out.println(claims);
    }
}
