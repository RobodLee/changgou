package com.robod.filter;

import com.robod.entity.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Robod
 * @date 2020/8/5 11:07
 */
@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {

    private static final String AUTHORIZE_TOKEN = "Authorization";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        //从头中获取Token
        String token = request.getHeaders().getFirst(AUTHORIZE_TOKEN);
        boolean hasTokenInHeader = true;
        //请求头中没有Token就从参数中获取
        if (StringUtils.isEmpty(token)){
            token = request.getQueryParams().getFirst(AUTHORIZE_TOKEN);
            hasTokenInHeader = false;
        }
        //参数中再没有Token就从Cookie中获取
        if (StringUtils.isEmpty(token)){
            HttpCookie cookie = request.getCookies().getFirst(AUTHORIZE_TOKEN);
            if (cookie!=null){
                token = cookie.getValue();
            }
        }
        //还是没有Token就拦截
        if (StringUtils.isEmpty(token)){
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        } else {
            if (!hasTokenInHeader) {
                if (!(token.startsWith("brarer ") || token.startsWith("Bearer "))) {
                    token = "Bearer " + token;
                }
                request.mutate().header("Authorization",token);
            }
        }
        //Token不为空就校验Token
        // try {
        //     JwtUtil.parseJWT(token);
        // } catch (Exception e) {
        //     //报异常说明Token是错误的，拦截
        //     response.setStatusCode(HttpStatus.UNAUTHORIZED);
        //     return response.setComplete();
        // }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
