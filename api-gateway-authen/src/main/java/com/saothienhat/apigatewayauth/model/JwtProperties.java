package com.saothienhat.apigatewayauth.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {

//	@Value("${security.jwt.uri:/auth/**}")
//    private String Uri;
//
//    @Value("${security.jwt.header:Authorization}")
//    private String header;
//
//    @Value("${security.jwt.prefix:Bearer }")
//    private String prefix;
//
//    @Value("${security.jwt.expiration:#{24*60*60}}")
//    private int expiration;
//
//    @Value("${security.jwt.secret:JwtSecretKey}")
//    private String secret;
}
