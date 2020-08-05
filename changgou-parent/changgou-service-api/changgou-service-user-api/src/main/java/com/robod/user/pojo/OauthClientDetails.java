package com.robod.user.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/****
 * @Author:admin
 * @Description:OauthClientDetails构建
 * @Date 2019/6/14 19:13
 *****/
@Data
@Table(name="oauth_client_details")
public class OauthClientDetails implements Serializable{

	@Id
    @Column(name = "client_id")
	private String clientId;//客户端ID，主要用于标识对应的应用

    @Column(name = "resource_ids")
	private String resourceIds;//

    @Column(name = "client_secret")
	private String clientSecret;//客户端秘钥，BCryptPasswordEncoder加密算法加密

    @Column(name = "scope")
	private String scope;//对应的范围

    @Column(name = "authorized_grant_types")
	private String authorizedGrantTypes;//认证模式

    @Column(name = "web_server_redirect_uri")
	private String webServerRedirectUri;//认证后重定向地址

    @Column(name = "authorities")
	private String authorities;//

    @Column(name = "access_token_validity")
	private Integer accessTokenValidity;//令牌有效期

    @Column(name = "refresh_token_validity")
	private Integer refreshTokenValidity;//令牌刷新周期

    @Column(name = "additional_information")
	private String additionalInformation;//

    @Column(name = "auto_approve")
	private String autoApprove;//

}
